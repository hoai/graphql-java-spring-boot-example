package com.example.DemoGraphQL.grpc.client;

import com.example.DemoGraphQL.grpc.OauthRequest;
import com.example.DemoGraphQL.grpc.OauthResponse;
import com.example.DemoGraphQL.grpc.OautherviceOuterClass;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcClient {
    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
            .usePlaintext()
            .build();

        OauthServiceOuterClass.HelloServiceBlockingStub stub 
          = OauthServiceOuterClass.newBlockingStub(channel);

        OauthResponse helloResponse = stub.hello(OauthRequest.newBuilder()
            .setFirstName("Baeldung")
            .setLastName("gRPC")
            .build());

        System.out.println("Response received from server:\n" + helloResponse);

        channel.shutdown();
    }
}
