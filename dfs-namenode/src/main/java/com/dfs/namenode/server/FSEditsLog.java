package com.dfs.namenode.server;


import java.io.IOException;

public class FSEditsLog {
    /**
     * 当前递增到的txid序号
     */
    private long txidSeq = 0L;
    //是否有线程正在刷磁盘
    private volatile Boolean isSyncRunning = false;
    //当前线程是否等待刷下一批editslog到磁盘中去
    private volatile Boolean isWaitSync = false;
    //在同步磁盘中的最大一个txid
    private volatile Long syncTxid = 0L;
    //每个线程自己的txid副本
    private ThreadLocal<Long> localTxid = new ThreadLocal<>();
    //是否正在调度一次刷盘的操作
    private volatile boolean isSchedulerSync = false;

    private DoubleBuffer editLogBuffer = new DoubleBuffer();


    public void logEdit(String content) throws IOException {
        synchronized (this) {
            //如果有线程在等待刷磁盘，则此程序等待
            //等待动作会释放锁，所以可以有多个线程能偶执行到这里等待。
            waitSchedulingSync();
            //全局id递增
            txidSeq++;
            long txid = txidSeq;
            localTxid.set(txid);
            EditLog editLog = new EditLog(txid, content);
            editLog.setTxid(txid);
            editLogBuffer.write(editLog);
            //判断是否当前使用的这块缓冲区已经到达刷磁盘的阈值大小
            if (!editLogBuffer.shouldSyncDisk()) {
                return;
            }
            //执行到这里说明需要刷磁盘
            isSchedulerSync = true;
        }
        logSync();
    }

    private void waitSchedulingSync(){
        while(isSchedulerSync){
            try {
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void logSync() throws IOException {
        long txid = localTxid.get();
        //这里就只有一个线程能够进来了
        synchronized (this) {

            if (isSyncRunning) {
                //说明此时有比他大的包含他的id的线程正在刷
                if (txid < syncTxid) {
                    return;
                }
                //等待正在刷磁盘的线程，直到这个线程刷完磁盘，修改标志位，并notify本线程，从而打破这个等待。
                while (isSyncRunning) {
                    try {
                        wait(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            //交换两块缓冲区
            editLogBuffer.setReadyToSync();
            syncTxid = txid;
            isSchedulerSync = false;
            notifyAll();
            isSyncRunning =true;
        }
        editLogBuffer.flush();
        synchronized (this) {
            isSyncRunning = false;
            notifyAll();//唤醒isWaitSync的那个线程
        }
    }
}
