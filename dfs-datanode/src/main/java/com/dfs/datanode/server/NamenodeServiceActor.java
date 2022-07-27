package com.dfs.datanode.server;

import com.dfs.namenode.rpc.model.HeartbeatRequest;
import com.dfs.namenode.rpc.model.HeartbeatResponse;
import com.dfs.namenode.rpc.model.RegisterRequest;
import com.dfs.namenode.rpc.model.RegisterResponse;
import com.dfs.namenode.rpc.service.NameNodeServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;

public class NamenodeServiceActor {
    private static final String NAMENODE_HOSTNAME = "localhost";
    private static final Integer NAMENODE_PORT = 50070;
    private NameNodeServiceGrpc.NameNodeServiceBlockingStub namenode;
    public NamenodeServiceActor(){
        ManagedChannel channel = NettyChannelBuilder
                .forAddress(NAMENODE_HOSTNAME,NAMENODE_PORT)
                .negotiationType(NegotiationType.PLAINTEXT)
                .build();
        this.namenode = NameNodeServiceGrpc.newBlockingStub(channel);
    }

    public void register() throws InterruptedException {
        Thread resgisterThread = new RegisterThread();
        resgisterThread.start();
        resgisterThread.join();
    }

    public void startHeartbeat() {
        try {
            while(true){
                System.out.println("send heartbeat to namenode...");
                String ip = "127.0.0.1";
                String hostname = "ip-datanode1";
                HeartbeatRequest request = HeartbeatRequest.newBuilder().setHostname(hostname)
                        .setIp(ip)
                        .build();
                HeartbeatResponse response = namenode.heartbeat(request);
                System.out.println("心跳返回结果："+response.getStatus());
                Thread.sleep(30 * 1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class RegisterThread extends Thread{

        @Override
        public void run() {
            System.out.println("register datanode to namenode");
            String ip = "127.0.0.1";
            String hostname = "ip-datanode1";
            RegisterRequest request = RegisterRequest.newBuilder().setIp(ip)
                    .setHostname(hostname)
                    .build();
            RegisterResponse response = namenode.register(request);
            System.out.println("接收到NameNode返回的注册响应：" +response.getStatus());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

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
