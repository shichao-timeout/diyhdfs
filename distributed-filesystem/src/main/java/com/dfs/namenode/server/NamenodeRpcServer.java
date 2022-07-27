package com.dfs.namenode.server;

public class NamenodeRpcServer {
    private FSNameSystem fsNameSystem;
    private DatanodeManager datanodeManager;

    public NamenodeRpcServer(FSNameSystem fsNameSystem, DatanodeManager datanodeManager) {
        this.datanodeManager = datanodeManager;
        this.fsNameSystem = fsNameSystem;
    }


    public void start(){
        System.out.println("namenode rpc start...");
    }

    public Boolean mkdir(String path){
        return fsNameSystem.mkdir(path);
    }

    public Boolean register(String ip,int port){
        return datanodeManager.register(ip,port);
    }
}
