package com.example.DemoGraphQL.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.example.DemoGraphQL.model.Company;
import com.example.DemoGraphQL.model.Department;
import com.lampart.microservice2.grpc.DepartmentServiceOuterClass.DepartmentResponse;
import com.lampart.microservice2.grpc.client.DepartmentClient;

public class CompanyResolver implements GraphQLResolver<Company> {

	public CompanyResolver() {

	}

	public Department getDepartment(Company company) throws IllegalAccessException {

		try {
			DepartmentResponse response2 = DepartmentClient.init(company.getId());
			System.out.println("Request received from microserver 2:\n" + response2);

			Department department_result = new Department(response2.getId(), response2.getName());

			return department_result;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
