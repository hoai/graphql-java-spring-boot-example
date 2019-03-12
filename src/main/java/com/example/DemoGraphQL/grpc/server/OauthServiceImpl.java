package com.example.DemoGraphQL.grpc.server;

import com.example.DemoGraphQL.grpc.OauthServiceGrpc;
import com.example.DemoGraphQL.grpc.OauthServiceOuterClass;

import io.grpc.stub.StreamObserver;

public class OauthServiceImpl extends OauthServiceGrpc.OauthServiceImplBase {

    @Override
    public void loginUser(
    		OauthServiceOuterClass.OauthRequest request, StreamObserver<OauthServiceOuterClass.OauthResponse> responseObserver) {
        System.out.println("Request received from client:\n" + request);
     
        
     // You must use a builder to construct a new Protobuffer object
        OauthServiceOuterClass.OauthResponse response = OauthServiceOuterClass.OauthResponse.newBuilder()
          .setAccess_token("309cc879-06ce-45c2-8c79-79f2f65e1365")
          .setToken_type("bearer")
          .setRefresh_token("ef03a088-51f6-4b08-af25-103625790347")
          .setExpires_in(10799)
          .setScope("read write")
          .build();

        // Use responseObserver to send a single response back
        responseObserver.onNext(response);

        // When you are done, you must call onCompleted.
        responseObserver.onCompleted();
    }
}
