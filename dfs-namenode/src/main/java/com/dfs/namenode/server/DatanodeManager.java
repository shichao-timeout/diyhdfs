package com.dfs.namenode.server;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DatanodeManager {
    private Map<String,DatanodeInfo> datanodes = new ConcurrentHashMap<>();

    public Boolean register(String ip, String hostname) {
        DatanodeInfo datanodeInfo = new DatanodeInfo(ip, hostname,System.currentTimeMillis());
        datanodes.put(ip+"-"+hostname,datanodeInfo);
        return true;
    }

    public DatanodeManager(){
        new DataNodeAliveMonitor().start();
    }

    public Boolean heartbeat(String ip,String hostname){
        DatanodeInfo datanodeInfo = datanodes.get(ip+"-"+hostname);
        if(null != datanodeInfo){
            datanodeInfo.setLatestHeartbeatTime(System.currentTimeMillis());
        }
        return true;
    }

    class DataNodeAliveMonitor extends Thread{
        @Override
        public void run() {
            while(true){
                datanodes.forEach((k,v)->{
                    if(System.currentTimeMillis() -v.getLatestHeartbeatTime() > 300 * 1000 ){
                        datanodes.remove(k);
                    }
                });
                try {
                    Thread.sleep(30 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
