package com.shawnliang.github.dfs.namenode.server;

/**
 * Description :  用来描述datanode的信息 .
 *
 * @author : Phoebe
 * @date : Created in 2021/10/12
 */
public class DataNodeInfo {

    private String ip;
    private String hostname;

    public long getLatestHeartbeatTime() {
        return latestHeartbeatTime;
    }

    public void setLatestHeartbeatTime(long latestHeartbeatTime) {
        this.latestHeartbeatTime = latestHeartbeatTime;
    }

    private long latestHeartbeatTime = System.currentTimeMillis();


    public DataNodeInfo(String ip, String hostname) {
        this.ip = ip;
        this.hostname = hostname;
    }


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
}
