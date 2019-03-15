package com.example.DemoGraphQL.model;

import java.util.List;

import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.lampart.microservice2.grpc.client.DepartmentClient;
import com.lampart.microservice2.grpc.DepartmentServiceOuterClass.DepartmentResponse;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor

public class CompanyResolver implements GraphQLResolver<Company> {


    public Department getDepartment(Company company) throws IllegalAccessException {

		try {
			DepartmentResponse response2 = DepartmentClient.init(company.getId());
			System.out.println("Request received from microserver 2:\n" + response2);
//			for (final Room room : response2) {
//				// Here your room is available
//			}
			Department department_result = new Department(response2.getId(), response2.getName());

			return department_result;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}