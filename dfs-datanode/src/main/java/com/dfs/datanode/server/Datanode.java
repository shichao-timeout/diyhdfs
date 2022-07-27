package com.dfs.datanode.server;

public class Datanode {
    private volatile boolean shouldRun = false;
    private NamenodeOfferService namenodeOfferService;

    private void initialize(){
        this.shouldRun = true;
        this.namenodeOfferService = new NamenodeOfferService();
        this.namenodeOfferService.start();
    }

    private void run(){
        while (shouldRun){
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Datanode datanode = new Datanode();
        datanode.initialize();
        datanode.run();
    }

}
