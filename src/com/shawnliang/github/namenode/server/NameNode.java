package com.shawnliang.github.namenode.server;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/10/12
 */
public class NameNode {

    /**
     * NameNode是否在运行
     */
    private volatile Boolean shouldRun;

    /**
     * 负责管理元数据的核心组件：管理的是一些文件目录树，支持权限设置
     */
    private FSNameSystem nameSystem;


    /**
     *负责管理集群中所有的Datanode的组件
     */
    private DataNodeManager dataNodeManager;

    /**
     * NameNode对外提供rpc接口的server，可以响应请求
     */
    private NameNodeRpcServer rpcServer;

    public NameNode() {
        this.shouldRun = true;
    }

    /**
     * 初始化NameNode
     */
    private void initialize() {
        this.nameSystem = new FSNameSystem();
        this.dataNodeManager = new DataNodeManager();
        this.rpcServer = new NameNodeRpcServer(this.nameSystem, this.dataNodeManager);
        this.rpcServer.start();
    }

    /**
     * 让NameNode运行起来
     */
    private void run() {
        try {
            while(shouldRun) {
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        NameNode namenode = new NameNode();
        namenode.initialize();
        namenode.run();
    }


}
