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
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;

import graphql.GraphQLException;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class CompanyClient {
    @Autowired
    private DiscoveryClient discoveryClient;
 
    @Autowired
    private LoadBalancerClient loadBalancer;
    
	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyClient.class);

	public  CompanyResponse init(Integer Id) throws InterruptedException {
		
		String serviceId = "microservice1";
		List<InstanceInfo> instances = this.discoveryClient.getInstancesById(serviceId);
		if (instances == null || instances.isEmpty()) {
			throw new GraphQLException("No instances for service: " + serviceId); 
        }
		String html = "<h2>Instances for Service Id: " + serviceId + "</h2>";
		 
        for (InstanceInfo serviceInstance : instances) {
            html += "<h3>Instance :" + serviceInstance.getId() + "</h3>";
        }
 
        html += "<br><h4>Call /hello of service: " + serviceId + "</h4>";
        
        try {
            // May be throw IllegalStateException (No instances available)
            ServiceInstance serviceInstance = this.loadBalancer.choose(serviceId);
 
            html += "<br>===> Load Balancer choose: " + serviceInstance.getUri();
 
            String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/";
 
         // Channel is the abstraction to connect to a service endpoint
    		// Let's use plaintext communication because we don't have certs
    		final ManagedChannel channel = ManagedChannelBuilder.forAddress(serviceInstance.getHost(), serviceInstance.getPort()).usePlaintext(true).build();

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
            
            
        } catch (IllegalStateException e) {
            html += "<br>loadBalancer.choose ERROR: " + e.getMessage();
            e.printStackTrace();
        } catch (Exception e) {
            html += "<br>Other ERROR: " + e.getMessage();
            e.printStackTrace();
        }
        
		

		return response;

	}
}
