package com.dfs.datanode.server;

public class NamenodeOfferService {
    private NamenodeServiceActor serviceActor;

    public NamenodeOfferService(){
        serviceActor =new NamenodeServiceActor();
    }

    public void start(){
        regiter();
        startHeartbeat();
    }

    private void startHeartbeat() {
        this.serviceActor.startHeartbeat();
    }

    private void regiter() {
        try {
            this.serviceActor.register();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("register finished");
    }



}
