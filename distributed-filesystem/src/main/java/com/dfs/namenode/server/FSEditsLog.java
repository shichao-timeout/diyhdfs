package com.dfs.namenode.server;


import java.util.LinkedList;

public class FSEditsLog {

    private long txidSeq = 0L;

    private volatile Boolean isSyncRunning = false;
    //当前线程是否等待刷下一批editslog到磁盘中去
    private volatile Boolean isWaitSync = false;
    //在同步磁盘中的最大一个txid
    private volatile Long syncMaxTxid = 0L;
    //每个线程自己的txid副本
    private ThreadLocal<Long> localTxid = new ThreadLocal<>();

    private DoubleBuffer editLogBuffer = new DoubleBuffer();


    public void logEdit(String content) {
        synchronized (this){
            //全局id递增
            txidSeq++;
            long txid = txidSeq;
            localTxid.set(txid);
            EditLog editLog = new EditLog(txid,content);
            editLogBuffer.write(editLog);
        }
        logSync();
    }

    private void logSync(){
        synchronized (this){
           if(isSyncRunning){
               long txid = localTxid.get();
               //说明此时有比他大的包含他的id的线程正在刷
               if(txid < syncMaxTxid){
                   return;
               }
               //已经有线程等待刷磁盘，要等待
               //只能有一个线程在等待刷磁盘
               if(isWaitSync){
                   return;
               }
               isWaitSync = true;
               while (isSyncRunning){
                   try {
                       wait(2000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
               isWaitSync = false;
           }
           editLogBuffer.setReadyToSync();
           syncMaxTxid = editLogBuffer.getSyncMaxTxid();
           isSyncRunning = true;
           editLogBuffer.flush();
           synchronized (this){
               isSyncRunning = false;
               notifyAll();//唤醒isWaitSync的那个线程
           }
        }
    }

    class EditLog{
        long txid;
        String content;
        public EditLog(long txid,String content){
            this.txid = txid;
            this.content = content;
        }
    }

    class DoubleBuffer{
        LinkedList<EditLog> currentBuffer = new LinkedList<>();
        LinkedList<EditLog> syncBuffer = new LinkedList<>();
        public void write(EditLog editLog){
            currentBuffer.add(editLog);
        }

        /**
         * 交换两块缓存区，为刷磁盘做准备
         */
        public void setReadyToSync(){
            LinkedList<EditLog>  tmp = currentBuffer;
            currentBuffer = syncBuffer;
            syncBuffer = tmp;
        }
        public Long getSyncMaxTxid(){
            return syncBuffer.getLast().txid;
        }

        public void flush(){
            for (EditLog editLog: syncBuffer) {
                System.out.println("将editslog写入磁盘");
            }
            syncBuffer.clear();
        }
    }
}
