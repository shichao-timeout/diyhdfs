// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: NameNodeRpcServer.proto

package com.dfs.namenode.rpc.service;

public final class NameNodeServer {
  private NameNodeServer() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\027NameNodeRpcServer.proto\022\024com.dfs.namen" +
      "ode.rpc\032\026NameNodeRpcModel.proto2\367\002\n\017Name" +
      "NodeService\022Y\n\010register\022%.com.dfs.nameno" +
      "de.rpc.RegisterRequest\032&.com.dfs.namenod" +
      "e.rpc.RegisterResponse\022\\\n\theartbeat\022&.co" +
      "m.dfs.namenode.rpc.HeartbeatRequest\032\'.co" +
      "m.dfs.namenode.rpc.HeartbeatResponse\022P\n\005" +
      "mkdir\022\".com.dfs.namenode.rpc.MkdirReques" +
      "t\032#.com.dfs.namenode.rpc.MkdirResponse\022Y" +
      "\n\010shutdown\022%.com.dfs.namenode.rpc.Shutdo",
      "wnRequest\032&.com.dfs.namenode.rpc.Shutdow" +
      "nResponseB0\n\034com.dfs.namenode.rpc.servic" +
      "eB\016NameNodeServerP\001b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.dfs.namenode.rpc.model.NameNodeRpcModel.getDescriptor(),
        }, assigner);
    com.dfs.namenode.rpc.model.NameNodeRpcModel.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
