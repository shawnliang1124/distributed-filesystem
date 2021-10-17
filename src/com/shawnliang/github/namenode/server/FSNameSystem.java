package com.shawnliang.github.namenode.server;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/10/12
 */
public class FSNameSystem {

    /**
     * 负责管理内存文件目录树的组件
     */
    private FSDirectory directory;
    /**
     * 负责管理edits log写入磁盘的组件
     */
    private FSEditLog editlog;

    public FSNameSystem() {
        this.directory = new FSDirectory();
        this.editlog = new FSEditLog();
    }

    /**
     * 创建目录
     * @param path 目录路径
     * @return 是否成功
     */
    public Boolean mkdir(String path) throws Exception {
        this.directory.mkdir(path);
        this.editlog.logEdit("创建了一个目录：" + path);
        return true;
    }


}
