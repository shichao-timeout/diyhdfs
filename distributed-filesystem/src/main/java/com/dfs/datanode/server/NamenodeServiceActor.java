package com.dfs.datanode.server;

import sun.plugin2.main.server.HeartbeatThread;

import java.util.concurrent.CountDownLatch;

public class NamenodeServiceActor {
    public void register(CountDownLatch countDownLatch) {

    }

    public void startHeartbeat() {

    }

    class RegisterThread extends Thread{
        private CountDownLatch countDownLatch;
        RegisterThread(CountDownLatch countDownLatch){
            this.countDownLatch = countDownLatch;
        }
        @Override
        public void run() {
            System.out.println("register datanode to namenode");
            String ip = "127.0.0.1";
            String hostname = "ip-datanode1";
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
        }
    }

    class HeartbeatThread extends Thread{
        @Override
        public void run() {
            while(true){
                System.out.println("send heartbeat to namenode");
                String ip = "127.0.0.1";
                String hostname = "ip-datanode1";
                try {
                    Thread.sleep(30 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
