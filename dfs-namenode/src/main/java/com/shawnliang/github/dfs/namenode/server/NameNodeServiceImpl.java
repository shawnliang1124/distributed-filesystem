package com.shawnliang.github.dfs.namenode.server;

import com.shawnliang.github.dfs.namenode.rpc.model.HeartbeatRequest;
import com.shawnliang.github.dfs.namenode.rpc.model.HeartbeatResponse;
import com.shawnliang.github.dfs.namenode.rpc.model.MkdirRequest;
import com.shawnliang.github.dfs.namenode.rpc.model.MkdirResponse;
import com.shawnliang.github.dfs.namenode.rpc.model.RegisterRequest;
import com.shawnliang.github.dfs.namenode.rpc.model.RegisterResponse;
import com.shawnliang.github.dfs.namenode.rpc.service.NameNodeServiceGrpc;
import io.grpc.stub.StreamObserver;

/**
 * Description :   .
 *
 * @author : Phoebe
 * @date : Created in 2021/10/17
 */
public class NameNodeServiceImpl extends NameNodeServiceGrpc.NameNodeServiceImplBase {
    public static final Integer STATUS_SUCCESS = 1;
    public static final Integer STATUS_FAILURE = 2;


    /**
     * 负责管理元数据的核心组件
     */
    private FSNameSystem fsNameSystem;
    /**
     * 负责管理集群中所有的datanode的组件
     */
    private DataNodeManager datanodeManager;

    public NameNodeServiceImpl(FSNameSystem fsNameSystem,
            DataNodeManager datanodeManager) {
        this.fsNameSystem = fsNameSystem;
        this.datanodeManager = datanodeManager;
    }


    @Override
    public void mkdir(MkdirRequest request, StreamObserver<MkdirResponse> responseObserver) {
        try {
            fsNameSystem.mkdir(request.getPath());
            System.out.println("创建目录 path" +  request.getPath());

            MkdirResponse response = MkdirResponse.newBuilder()
                    .setStatus(STATUS_SUCCESS)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void register(RegisterRequest request,
            StreamObserver<RegisterResponse> responseObserver) {
        datanodeManager.register(request.getIp(), request.getHostname());

        RegisterResponse response = RegisterResponse.newBuilder()
                .setStatus(STATUS_SUCCESS)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }


    @Override
    public void heartbeat(HeartbeatRequest request,
            StreamObserver<HeartbeatResponse> responseObserver) {
        datanodeManager.heartBeat(request.getIp(), request.getHostname());

        HeartbeatResponse response = HeartbeatResponse.newBuilder()
                .setStatus(STATUS_SUCCESS)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
