package com.dfs.client;

import com.dfs.namenode.rpc.model.MkdirRequest;
import com.dfs.namenode.rpc.model.MkdirResponse;
import com.dfs.namenode.rpc.service.NameNodeServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.netty.NegotiationType;
import io.grpc.netty.NettyChannelBuilder;

public class FileSystemImpl implements FileSystem{
    private static final String NAMENODE_HOSTNAME = "localhost";
    private static final Integer NAMENODE_PORT = 50070;
    private NameNodeServiceGrpc.NameNodeServiceBlockingStub namenode;

    public FileSystemImpl(){
        ManagedChannel channel = NettyChannelBuilder.forAddress(NAMENODE_HOSTNAME,NAMENODE_PORT)
                .negotiationType(NegotiationType.PLAINTEXT)
                .build();
        this.namenode = NameNodeServiceGrpc.newBlockingStub(channel);
    }

    public void mkdir(String path) throws Exception {
        MkdirRequest request = MkdirRequest.newBuilder()
                .setPath(path)
                .build();

        MkdirResponse response = namenode.mkdir(request);

        System.out.println("创建目录的响应：" + response.getStatus());
    }

    public static void main(String[] args) throws Exception {
        final FileSystem fileSystem = new FileSystemImpl();
        		for(int j = 0; j < 10; j++) {
			new Thread() {
				public void run() {
					for(int i = 0; i < 100; i++) {
						try {
                            fileSystem.mkdir("/usr/warehouse/hive" + i + "_" + Thread.currentThread().getName());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				};

			}.start();
		}
        Thread.sleep(1000000);
    }
}
