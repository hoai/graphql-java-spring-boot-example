package com.lampart.microservice1.grpc.client;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.ServiceInstanceChooser;

import com.lampart.microservice1.grpc.CompanyServiceGrpc;
import com.lampart.microservice1.grpc.CompanyServiceOuterClass;
import com.lampart.microservice1.grpc.CompanyServiceOuterClass.CompanyResponse;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import graphql.GraphQLException;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class CompanyClient {
//	
//    private DiscoveryClient discoveryClient;
// 
//    private LoadBalancerClient loadBalancer;

	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyClient.class);

//	public CompanyClient(DiscoveryClient discoveryClient, LoadBalancerClient loadBalancer) {
//		this.discoveryClient = discoveryClient;
//		this.loadBalancer = loadBalancer;
//	}

	public static CompanyResponse init(Integer Id, DiscoveryClient discoveryClient, LoadBalancerClient loadBalancer)
			throws InterruptedException {

		String serviceName = "MICROSERVICE1".toLowerCase();
		System.out.println(discoveryClient.getServices());
		List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
		if (instances == null || instances.isEmpty()) {
			throw new GraphQLException("No instances for service: " + serviceName);
		}

		for (ServiceInstance serviceInstance : instances) {
			System.out.println(serviceInstance.getUri());
		}

		try {
			// May be throw IllegalStateException (No instances available)
			ServiceInstance serviceInstance = loadBalancer.choose(serviceName);
			System.out.println("Load Balancer choose: " + serviceInstance.getUri());
			// html += "<br>===> Load Balancer choose: " + serviceInstance.getUri();

//            String url = "choose host: http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/";
//            System.out.println(url);

			// Channel is the abstraction to connect to a service endpoint
			// Let's use plaintext communication because we don't have certs
			final ManagedChannel channel = ManagedChannelBuilder
					.forAddress(serviceInstance.getHost(), serviceInstance.getPort()).usePlaintext(true).build();

			// It is up to the client to determine whether to block the call
			// Here we create a blocking stub, but an async stub,
			// or an async stub with Future are always possible.
			CompanyServiceGrpc.CompanyServiceBlockingStub stub = CompanyServiceGrpc.newBlockingStub(channel);
			CompanyServiceOuterClass.CompanyRequest request = CompanyServiceOuterClass.CompanyRequest.newBuilder()
					.setId(Id).build();

			// Finally, make the call using the stub
			CompanyServiceOuterClass.CompanyResponse response = stub.getCompany(request);

			System.out.println(response);

			// A Channel should be shutdown before stopping the process.
			channel.shutdownNow();
			return response;

		} catch (IllegalStateException e) {
			System.out.println("loadBalancer.choose ERROR: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Other ERROR: " + e.getMessage());
			e.printStackTrace();
		}

		return null;

	}
}
