package com.shawnliang.github.datanode.server;

import java.util.concurrent.CountDownLatch;

/**
 * Description :  负责跟一组NameNode中的某一个进行通信的线程组件 .
 *
 * @author : Phoebe
 * @date : Created in 2021/10/12
 */
public class NameNodeServiceActor {

    /**
     * 注册
     * @param latch
     */
    public void register(CountDownLatch latch) {
        RegisterThread registerThread = new RegisterThread(latch);
        registerThread.start();
    }

    class RegisterThread extends Thread {

        private CountDownLatch latch;

        public RegisterThread(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                // 发送rpc接口调用请求到NameNode去进行注册
                System.out.println("发送RPC请求到NameNode进行注册.......");

                // 在这里进行注册的时候会提供哪些信息过去呢？
                // 比如说当前这台机器的ip地址、hostname，这两个东西假设是写在配置文件里的
                // 我们写代码的时候，主要是在本地来运行和测试，有一些ip和hostname，就直接在代码里写死了
                // 大家后面自己可以留空做一些完善，你可以加一些配置文件读取的代码
                String ip = "127.0.0.1";
                String hostname = "dfs-data-01";
                // 通过RPC接口发送到NameNode他的注册接口上去

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                this.latch.countDown();
            }
        }
    }

}
