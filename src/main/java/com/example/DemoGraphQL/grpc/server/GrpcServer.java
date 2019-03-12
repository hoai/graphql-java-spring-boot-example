package com.example.DemoGraphQL.grpc.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GrpcServer {
	public static void main(String[] args) throws Exception {
		Server server = ServerBuilder.forPort(5000).addService(new OauthServiceImpl()).build();

		System.out.println("Starting server...");
		server.start();
		System.out.println("Server started!");
		server.awaitTermination();
	}
}
