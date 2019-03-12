package com.example.DemoGraphQL.grpc.client;

import com.example.DemoGraphQL.grpc.HelloRequest;
import com.example.DemoGraphQL.grpc.HelloResponse;
import com.example.DemoGraphQL.grpc.HelloServiceOuterClass;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcClient {
    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
            .usePlaintext()
            .build();

        HelloServiceOuterClass.HelloServiceBlockingStub stub 
          = HelloServiceOuterClass.newBlockingStub(channel);

        HelloResponse helloResponse = stub.hello(HelloRequest.newBuilder()
            .setFirstName("Baeldung")
            .setLastName("gRPC")
            .build());

        System.out.println("Response received from server:\n" + helloResponse);

        channel.shutdown();
    }
}
