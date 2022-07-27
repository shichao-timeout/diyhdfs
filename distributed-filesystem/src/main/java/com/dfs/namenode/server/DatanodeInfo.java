package com.dfs.namenode.server;

public class DatanodeInfo {
    private String name ;
    private String hostname;
    private Long latestHeartbeatTime;

    public DatanodeInfo(String name, String hostname, Long latestHeartbeatTime) {
        this.name = name;
        this.hostname = hostname;
        this.latestHeartbeatTime = latestHeartbeatTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Long getLatestHeartbeatTime() {
        return latestHeartbeatTime;
    }

    public void setLatestHeartbeatTime(Long latestHeartbeatTime) {
        this.latestHeartbeatTime = latestHeartbeatTime;
    }
}
