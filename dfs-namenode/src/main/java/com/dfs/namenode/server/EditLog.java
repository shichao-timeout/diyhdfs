package com.dfs.namenode.server;


import com.alibaba.fastjson.JSONObject;

class EditLog {
    long txid;
    String content;


    public EditLog(long txid, String content) {
        this.txid = txid;
        this.content = content;
    }

    public long getTxid() {
        return txid;
    }
    public void setTxid(long txid) {
        this.txid = txid;

        JSONObject jsonObject = JSONObject.parseObject(content);
        jsonObject.put("txid", txid);

        this.content = jsonObject.toJSONString();
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "EditLog [txid=" + txid + ", content=" + content + "]";
    }
}