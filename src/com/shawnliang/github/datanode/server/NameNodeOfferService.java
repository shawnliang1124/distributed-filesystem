package com.shawnliang.github.datanode.server;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/10/12
 */
public class NameNodeOfferService {

    /**
     * 负责跟NameNode主节点通信的ServiceActor组件
     */
    private NameNodeServiceActor activeServiceActor;
    /**
     * 负责跟NameNode备节点通信的ServiceActor组件
     */
    private NameNodeServiceActor standbyServiceActor;
    /**
     * 这个datanode上保存的ServiceActor列表
     */
    private CopyOnWriteArrayList<NameNodeServiceActor> serviceActors;

    /**
     * 构造函数
     */
    public NameNodeOfferService() {
        this.activeServiceActor = new NameNodeServiceActor();
        this.standbyServiceActor = new NameNodeServiceActor();

        this.serviceActors = new CopyOnWriteArrayList<NameNodeServiceActor>();
        this.serviceActors.add(activeServiceActor);
        this.serviceActors.add(standbyServiceActor);
    }

    /**
     * 启动OfferService组件
     */
    public void start() {
        // 直接使用两个ServiceActor组件分别向主备两个NameNode节点进行注册
        register();
    }


    /**
     * 向主备两个NameNode节点进行注册
     */
    private void register() {
        try {
            CountDownLatch latch = new CountDownLatch(2);
            this.activeServiceActor.register(latch);
            this.standbyServiceActor.register(latch);
            latch.await();
            System.out.println("主备NameNode全部注册完毕......");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 关闭指定的一个ServiceActor
     * @param serviceActor
     */
    public void shutdown(NameNodeServiceActor serviceActor) {
        this.serviceActors.remove(serviceActor);
    }

    /**
     * 迭代遍历ServiceActor
     */
    public void iterateServiceActors() {
        Iterator<NameNodeServiceActor> iterator = serviceActors.iterator();
        while(iterator.hasNext()) {
            iterator.next();
        }
    }

}
