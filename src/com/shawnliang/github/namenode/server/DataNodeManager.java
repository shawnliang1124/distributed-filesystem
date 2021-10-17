package com.shawnliang.github.namenode.server;

import com.shawnliang.github.datanode.server.DataNode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/10/12
 */
public class DataNodeManager {

    /**
     * 内存中维护的datanode列表
     */
    private Map<String, DataNodeInfo> datanodes =
            new ConcurrentHashMap<>();

    /**
     * datanode进行注册
     * @param ip
     * @param hostname
     */
    public Boolean register(String ip, String hostname) {
        DataNodeInfo datanode = new DataNodeInfo(ip, hostname);
        datanodes.put(ip + "-" + hostname, datanode);
        return true;
    }

    /**
     * data node 进行心跳
     * @param ip
     * @param hostName
     * @return
     */
    public Boolean heartBeat(String ip, String hostName) {
        DataNodeInfo dataNodeInfo = datanodes.get(ip + "-" + hostName);

        if (dataNodeInfo != null) {
            dataNodeInfo.setLatestHeartbeatTime(System.currentTimeMillis());
        }
        return true;
    }

    /**
     * 心跳检查线程，删除无用的dataNode
     */
    class MonitorThread extends Thread {

        @Override
        public void run() {
            List<String> toRemoveDataNodes = new ArrayList<String>();

            while (true) {
                Iterator<DataNodeInfo> iterator = datanodes.values().iterator();

                while (iterator.hasNext()) {
                    DataNodeInfo dataNodeInfo = iterator.next();

                    // 如果大于90S内未收到心跳，将该实例摘除
                    if (System.currentTimeMillis() - dataNodeInfo.getLatestHeartbeatTime()
                    > 90 * 1000) {
                        toRemoveDataNodes.add(dataNodeInfo.getHostname() + "-"
                        + dataNodeInfo.getIp());
                    }
                }

                if (!toRemoveDataNodes.isEmpty()) {
                    for (String toRemoveDataNode : toRemoveDataNodes) {
                        datanodes.remove(toRemoveDataNode);
                    }
                }

                try {
                    // 休眠30S后再重新开启
                    Thread.sleep(30 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
