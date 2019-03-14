package com.lampart.microservice1.grpc.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.DemoGraphQL.model.Company;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import com.lampart.microservice1.grpc.CompanyServiceGrpc;
import com.lampart.microservice1.grpc.CompanyServiceOuterClass;
import com.lampart.microservice1.grpc.CompanyServiceOuterClass.CompanyResponse;

public class CompanyClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyClient.class);

	public static CompanyResponse init(Integer Id) throws InterruptedException {
		// Channel is the abstraction to connect to a service endpoint
		// Let's use plaintext communication because we don't have certs
		final ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 4002).usePlaintext(true).build();

		// It is up to the client to determine whether to block the call
		// Here we create a blocking stub, but an async stub,
		// or an async stub with Future are always possible.
		CompanyServiceGrpc.CompanyServiceBlockingStub stub = CompanyServiceGrpc.newBlockingStub(channel);
		CompanyServiceOuterClass.CompanyRequest request = CompanyServiceOuterClass.CompanyRequest.newBuilder().setId(Id).build();

		// Finally, make the call using the stub
		CompanyServiceOuterClass.CompanyResponse response = stub.getCompany(request);

		System.out.println(response);

		// A Channel should be shutdown before stopping the process.
		channel.shutdownNow();

		return response;

	}
}
