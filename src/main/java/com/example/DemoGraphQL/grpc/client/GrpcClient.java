package com.example.DemoGraphQL.grpc.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.DemoGraphQL.model.AuthData;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import com.codenotfound.grpc.HelloWorldClient;
import com.example.DemoGraphQL.grpc.OauthServiceGrpc;
import com.example.DemoGraphQL.grpc.OauthServiceOuterClass;

public class GrpcClient {
	 private static final Logger LOGGER =
		      LoggerFactory.getLogger(HelloWorldClient.class);
    public static void init(AuthData auth) throws InterruptedException {
    	// Channel is the abstraction to connect to a service endpoint
        // Let's use plaintext communication because we don't have certs
        final ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:6565")
          .usePlaintext(true)
          .build();

        // It is up to the client to determine whether to block the call
        // Here we create a blocking stub, but an async stub,
        // or an async stub with Future are always possible.
        OauthServiceGrpc.OauthServiceBlockingStub stub = OauthServiceGrpc.newBlockingStub(channel);
        OauthServiceOuterClass.OauthRequest request =
          OauthServiceOuterClass.OauthRequest.newBuilder()
            .setAuthorization("Basic c3ByaW5nLXNlY3VyaXR5LW9hdXRoMi1yZWFkLXdyaXRlLWNsaWVudDpzcHJpbmctc2VjdXJpdHktb2F1dGgyLXJlYWQtd3JpdGUtY2xpZW50LXBhc3N3b3JkMTIzNA==")
            .setGrantType("password")
            .setUsername("admin")
            .setPassword("admin1234")
            .build();

        // Finally, make the call using the stub
        OauthServiceOuterClass.OauthResponse response = 
          stub.loginUser(request);

        System.out.println(response);

        // A Channel should be shutdown before stopping the process.
        channel.shutdownNow();
    }
}
