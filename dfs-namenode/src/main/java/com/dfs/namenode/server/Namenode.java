package com.dfs.namenode.server;

import java.util.concurrent.TimeUnit;

public class Namenode {
    private volatile boolean shouldRun = false;

    private NamenodeRpcServer namenodeRpcServer;

    private DatanodeManager datanodeManager;

    private FSNameSystem fsNameSystem;

    public Namenode(){
        this.shouldRun = true;
    }

    private void initialize(){
        this.fsNameSystem = new FSNameSystem();
        this.datanodeManager = new DatanodeManager();
        this.namenodeRpcServer = new NamenodeRpcServer(fsNameSystem,datanodeManager);
        this.namenodeRpcServer.start();
    }

    private void run(){
        while (shouldRun){
            try {
                TimeUnit.SECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Namenode namenode = new Namenode();
        namenode.initialize();
        namenode.run();
    }
}
