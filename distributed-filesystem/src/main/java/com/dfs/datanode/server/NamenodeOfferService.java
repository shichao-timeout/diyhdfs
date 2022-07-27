package com.dfs.datanode.server;

import com.dfs.namenode.server.Namenode;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

public class NamenodeOfferService {
    private NamenodeServiceActor standbyActor;
    private NamenodeServiceActor activeActor;
    private CopyOnWriteArrayList<NamenodeServiceActor> namenodeServiceActors = new CopyOnWriteArrayList<>();

    public NamenodeOfferService(){
        standbyActor =new NamenodeServiceActor();
        activeActor = new NamenodeServiceActor();
        namenodeServiceActors.add(standbyActor);
        namenodeServiceActors.add(activeActor);
    }

    public void start(){
        regiter();
        startHeartbeat();
    }

    private void startHeartbeat() {
        this.activeActor.startHeartbeat();
        this.standbyActor.startHeartbeat();
    }

    private void regiter() {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        this.activeActor.register(countDownLatch);
        this.standbyActor.register(countDownLatch);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("register finished");
    }

    public void shutdown(NamenodeServiceActor actor){
        this.namenodeServiceActors.remove(actor);
    }

    public void iterateServiceActors(){
        Iterator<NamenodeServiceActor> iterator = namenodeServiceActors.iterator();
        while(iterator.hasNext()) {
            iterator.next();
        }
    }
}
