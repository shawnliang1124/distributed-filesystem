package com.shawnliang.github.dfs.namenode.client;

import com.shawnliang.github.dfs.namenode.rpc.model.MkdirRequest;
import com.shawnliang.github.dfs.namenode.rpc.model.MkdirResponse;
import com.shawnliang.github.dfs.namenode.rpc.service.NameNodeServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/10/24
 */
public class IFileSystemImpl implements IFileSystem{
    private static final String NAMENODE_HOSTNAME = "localhost";
    private static final Integer NAMENODE_PORT = 50070;

    private NameNodeServiceGrpc.NameNodeServiceBlockingStub nameNode;

    /**
     * 初始化客户端连接
     */
    public IFileSystemImpl() {
        ManagedChannel channel = NettyChannelBuilder
                .forAddress(NAMENODE_HOSTNAME, NAMENODE_PORT)
                .negotiationType(NegotiationType.PLAINTEXT)
                .build();
        nameNode = NameNodeServiceGrpc.newBlockingStub(channel);
    }


    public void mkdir(String path) throws Exception {
        MkdirRequest request = MkdirRequest.newBuilder()
                .setPath(path)
                .build();

        MkdirResponse response = nameNode.mkdir(request);
        System.out.println("创建目录响应体 " + response.getStatus());
    }
}
