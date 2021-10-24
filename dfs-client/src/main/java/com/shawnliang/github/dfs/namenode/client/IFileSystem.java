package com.shawnliang.github.dfs.namenode.client;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/10/24
 */
public interface IFileSystem {

    /**
     * 创建文件目录
     * @param path
     * @throws Exception
     */
    void mkdir(String path) throws Exception;
}
