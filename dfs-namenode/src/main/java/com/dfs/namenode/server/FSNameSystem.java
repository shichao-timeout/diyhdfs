package com.dfs.namenode.server;

import java.io.IOException;

public class FSNameSystem {
    private FSDirectory fsDirectory;
    private FSEditsLog fsEditsLog;

    public FSNameSystem(){
        this.fsDirectory = new FSDirectory();
        this.fsEditsLog = new FSEditsLog();
    }

    public Boolean mkdir(String path) throws IOException {
        this.fsDirectory.mkdkr(path);
        this.fsEditsLog.logEdit("{'OP':'MKDIR','PATH':'" + path + "'}");
        return true;
    }
}
