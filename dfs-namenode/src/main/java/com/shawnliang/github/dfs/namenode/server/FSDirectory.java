package com.shawnliang.github.dfs.namenode.server;

import java.util.LinkedList;
import java.util.List;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/10/12
 */
public class FSDirectory {

    /**
     * 内存中的文件目录树
     */
    private final INodeDirectory dirTree;

    /**
     * 构造函数初始化为根目录
     */
    public FSDirectory() {
        this.dirTree = new INodeDirectory("/");
    }

    /**
     * 查询文件目录
     * @param dir
     * @param path
     * @return
     */
    private INodeDirectory findDirectory(INodeDirectory dir, String path) {
        if(dir.getChildren().size() == 0) {
            return null;
        }

        INodeDirectory resultDir = null;

        for(INode child : dir.getChildren()) {
            if(child instanceof INodeDirectory) {
                INodeDirectory childDir = (INodeDirectory) child;

                if((childDir.getPath().equals(path))) {
                    return childDir;
                }
            }
        }

        return null;
    }


    /**
     * 创建目录
     * @param path 目录路径
     */
    public void mkdir(String path) {
        // path = /usr/warehouse/hive
        // 你应该先判断一下，“/”根目录下有没有一个“usr”目录的存在
        // 如果说有的话，那么再判断一下，“/usr”目录下，有没有一个“/warehouse”目录的存在
        // 如果说没有，那么就得先创建一个“/warehosue”对应的目录，挂在“/usr”目录下
        // 接着再对“/hive”这个目录创建一个节点挂载上去

        synchronized(dirTree) {
            String[] pathes = path.split("/");
            INodeDirectory parent = dirTree;

            for(String splitedPath : pathes) {
                if("".equals(splitedPath.trim())) {
                    continue;
                }

                INodeDirectory dir = findDirectory(parent, splitedPath);
                if(dir != null) {
                    parent = dir;
                    continue;
                }

                INodeDirectory child = new INodeDirectory(splitedPath);
                parent.addChild(child);
                parent = child;
            }
        }

        printDirTree(dirTree, "");
    }

    private void printDirTree(INodeDirectory dirTree, String blank) {
        if(dirTree.getChildren().size() == 0) {
            return;
        }
        for(INode dir : dirTree.getChildren()) {
            System.out.println(blank + ((INodeDirectory) dir).getPath());
            printDirTree((INodeDirectory) dir, blank + " ");
        }
    }

    /**
     * 代表是文件目录树的某个节点
     */
    private interface INode {

    }

    /**
     * 代表文件目录树中的一个目录
     */
    public static class INodeDirectory implements INode {
        private String path;
        private List<INode> children;

        public INodeDirectory(String path) {
            this.path = path;
            this.children = new LinkedList<INode>();
        }

        public void addChild(INode inode) {
            this.children.add(inode);
        }

        public String getPath() {
            return path;
        }
        public void setPath(String path) {
            this.path = path;
        }
        public List<INode> getChildren() {
            return children;
        }
        public void setChildren(List<INode> children) {
            this.children = children;
        }
    }

    /**
     * 代表文件目录树中的一个文件
     */
    public static class INodeFile implements INode {

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String name;

    }
}
