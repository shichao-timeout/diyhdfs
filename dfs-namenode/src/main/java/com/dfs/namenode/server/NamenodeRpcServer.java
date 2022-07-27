package com.dfs.namenode.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class NamenodeRpcServer {
    private static final int DEFAULT_PORT = 50070;
    private Server server;
    private FSNameSystem fsNameSystem;
    private DatanodeManager datanodeManager;

    public NamenodeRpcServer(FSNameSystem fsNameSystem, DatanodeManager datanodeManager) {
        this.datanodeManager = datanodeManager;
        this.fsNameSystem = fsNameSystem;
    }


    public void start(){
        try {
            server = ServerBuilder.forPort(DEFAULT_PORT)
                    .addService(new NameNodeServiceImpl(fsNameSystem,datanodeManager))
                    .build()
                    .start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("namenode rpc start...");
    }

}
