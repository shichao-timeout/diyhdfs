package com.dfs.namenode.server;

import com.dfs.namenode.rpc.model.*;
import com.dfs.namenode.rpc.service.NameNodeServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

public class NameNodeServiceImpl extends NameNodeServiceGrpc.NameNodeServiceImplBase {

    public static final Integer STATUS_SUCCESS = 1;
    public static final Integer STATUS_FAILURE = 2;

    private FSNameSystem fsNameSystem;
    private DatanodeManager datanodeManager;

    public NameNodeServiceImpl(
            FSNameSystem namesystem,
            DatanodeManager datanodeManager) {
        this.fsNameSystem = namesystem;
        this.datanodeManager = datanodeManager;
    }

    @Override
    public void register(RegisterRequest request, StreamObserver<RegisterResponse> responseObserver) {
        System.out.println("接收到注册请求："+request.getHostname() + ":" + request.getIp());
        Boolean register = datanodeManager.register(request.getIp(), request.getHostname());
        RegisterResponse response = RegisterResponse.newBuilder().setStatus(STATUS_SUCCESS).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void heartbeat(HeartbeatRequest request, StreamObserver<HeartbeatResponse> responseObserver) {
        System.out.println("接收到心跳请求："+request.getHostname() + ":" + request.getIp());
        Boolean heartbeat = datanodeManager.heartbeat(request.getIp(), request.getHostname());
        HeartbeatResponse response = HeartbeatResponse.newBuilder().setStatus(STATUS_SUCCESS).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void mkdir(MkdirRequest request, StreamObserver<MkdirResponse> responseObserver) {
        System.out.println("接收到mkdir请求："+request.getPath());
        try {
            Boolean mkdir = fsNameSystem.mkdir(request.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        MkdirResponse response = MkdirResponse.newBuilder().setStatus(STATUS_SUCCESS).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
