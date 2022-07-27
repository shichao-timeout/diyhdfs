package com.dfs.namenode.server;

public class FSNameSystem {
    private FSDirectory fsDirectory;
    private FSEditsLog fsEditsLog;

    public FSNameSystem(){
        this.fsDirectory = new FSDirectory();
        this.fsEditsLog = new FSEditsLog();
    }

    public Boolean mkdir(String path) {
        this.fsDirectory.mkdkr(path);
        this.fsEditsLog.logEdit("创建了一个目录："+path);
        return true;
    }
}
