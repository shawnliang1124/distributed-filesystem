package com.shawnliang.github.namenode.server;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/10/12
 */
public class NameNodeRpcServer {

    /**
     *负责管理元数据的核心组件
     * EditLog, 文件目录信息
     */
    private FSNameSystem fsNamesystem;

    /**
     *负责管理集群中所有的datanode的组件
     */
    private DataNodeManager dataNodeManager;

    public NameNodeRpcServer(
            FSNameSystem namesystem,
            DataNodeManager datanodeManager) {
        this.fsNamesystem = namesystem;
        this.dataNodeManager = datanodeManager;
    }

    /**
     * 创建目录
     * @param path 目录路径
     * @return 是否创建成功
     * @throws Exception
     */
    public Boolean mkdir(String path) throws Exception {
        return this.fsNamesystem.mkdir(path);
    }

    public Boolean register(String ip, String hostName) {
        return this.dataNodeManager.register(ip, hostName);
    }

    public void start() {
        System.out.println("开始监听指定的rpc server的端口号，来接收请求");
    }

}
