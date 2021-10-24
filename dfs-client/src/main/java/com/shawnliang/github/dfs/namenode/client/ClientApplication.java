package com.shawnliang.github.dfs.namenode.client;

import java.util.concurrent.CountDownLatch;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/10/24
 */
public class ClientApplication {

    private static final CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        IFileSystemImpl iFileSystem = new IFileSystemImpl();
        iFileSystem.mkdir("/usr/local/kafka/data");

        countDownLatch.await();
    }

}
