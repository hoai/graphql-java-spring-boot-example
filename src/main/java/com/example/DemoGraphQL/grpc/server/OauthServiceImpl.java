package com.example.DemoGraphQL.grpc.server;

import com.example.DemoGraphQL.grpc.OauthRequest;
import com.example.DemoGraphQL.grpc.OauthResponse;
import com.example.DemoGraphQL.grpc.OauthServiceGrpc.OauthServiceImplBase;

import io.grpc.stub.StreamObserver;

public class OauthServiceImpl extends OauthServiceImplBase {

    @Override
    public void hello(
    		OauthRequest request, StreamObserver<OauthResponse> responseObserver) {
        System.out.println("Request received from client:\n" + request);

        String greeting = new StringBuilder().append("Hello, ")
            .append(request.getFirstName())
            .append(" ")
            .append(request.getLastName())
            .toString();

        OauthResponse Oauth = HelloResponse.newBuilder()
            .setGreeting(greeting)
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
