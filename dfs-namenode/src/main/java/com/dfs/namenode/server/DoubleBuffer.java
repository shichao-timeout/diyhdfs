package com.dfs.namenode.server;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

class DoubleBuffer {
    public final Integer EDIT_LOG_BUFFER_LIMIT = 1 * 1024;


    private EditLogBuffer currentBuffer = new EditLogBuffer();
    private EditLogBuffer syncBuffer = new EditLogBuffer();

    public void write(EditLog editLog) throws IOException {
        currentBuffer.write(editLog);
    }

    public boolean shouldSyncDisk() {
        return currentBuffer.size() >= EDIT_LOG_BUFFER_LIMIT;
    }

    /**
     * 交换两块缓存区，为刷磁盘做准备
     * 这里只有一个线程可以进来，所以不需要加锁
     */
    public void setReadyToSync() {
        EditLogBuffer tmp = currentBuffer;
        currentBuffer = syncBuffer;
        syncBuffer = tmp;
    }


    public void flush() throws IOException {
        syncBuffer.flush();
        syncBuffer.clear();
    }

    class EditLogBuffer{
        ByteArrayOutputStream buffer;
        long maxTxid = 0L;
        long lastMaxTxid = 0L;

        public EditLogBuffer(){
            this.buffer = new ByteArrayOutputStream(EDIT_LOG_BUFFER_LIMIT * 2);
        }

        public void write(EditLog log) throws IOException {
            this.maxTxid = log.getTxid();
            buffer.write(log.getContent().getBytes());
            buffer.write("\n".getBytes());
            System.out.println("当前缓冲区的大小是："+size());
        }

        private int size(){
            return buffer.size();
        }

        public void flush() throws IOException{
            byte[] data = buffer.toByteArray();
            ByteBuffer dataBuffer = ByteBuffer.wrap(data);
            String editsLogFilePath = "E:\\editslog\\edits-"+ (++lastMaxTxid) + "~"+maxTxid + ".log";
            RandomAccessFile file = null;
            FileOutputStream out = null;
            FileChannel editsLogFileChannel = null;
            try{
                file = new RandomAccessFile(editsLogFilePath,"rw");
                out = new FileOutputStream(file.getFD());
                editsLogFileChannel = out.getChannel();
                editsLogFileChannel.write(dataBuffer);
                editsLogFileChannel.force(false);
            }catch (Exception e){
                e.printStackTrace();
            }finally{
                if(out!= null){
                    out.close();
                }
                if(file != null) {
                    file.close();
                }
                if(editsLogFileChannel != null) {
                    editsLogFileChannel.close();
                }
            }
            this.lastMaxTxid = maxTxid;
        }

        public void clear(){
            buffer.reset();
        }
    }
}
