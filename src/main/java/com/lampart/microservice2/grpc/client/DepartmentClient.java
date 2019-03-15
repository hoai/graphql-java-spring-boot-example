package com.lampart.microservice2.grpc.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lampart.microservice2.grpc.DepartmentServiceGrpc;
import com.lampart.microservice2.grpc.DepartmentServiceOuterClass;
import com.lampart.microservice2.grpc.DepartmentServiceOuterClass.DepartmentResponse;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class DepartmentClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentClient.class);

	public static DepartmentResponse init(Integer Id) throws InterruptedException {
		// Channel is the abstraction to connect to a service endpoint
		// Let's use plaintext communication because we don't have certs
		final ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 4003).usePlaintext(true).build();

		// It is up to the client to determine whether to block the call
		// Here we create a blocking stub, but an async stub,
		// or an async stub with Future are always possible.
		DepartmentServiceGrpc.DepartmentServiceBlockingStub stub = DepartmentServiceGrpc.newBlockingStub(channel);
		DepartmentServiceOuterClass.DepartmentRequest request = DepartmentServiceOuterClass.DepartmentRequest.newBuilder().setId(Id).build();

		// Finally, make the call using the stub
		DepartmentServiceOuterClass.DepartmentResponse response = stub.getDepartment(request);

		System.out.println(response);

		// A Channel should be shutdown before stopping the process.
		channel.shutdownNow();

		return response;

	}
}
