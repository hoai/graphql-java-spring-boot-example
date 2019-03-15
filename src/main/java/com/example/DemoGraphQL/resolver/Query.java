package com.example.DemoGraphQL.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.DemoGraphQL.model.Author;
import com.example.DemoGraphQL.model.Book;
import com.example.DemoGraphQL.model.Company;
import com.example.DemoGraphQL.model.Department;
import com.example.DemoGraphQL.repository.AuthorRepository;
import com.example.DemoGraphQL.repository.BookRepository;
import com.lampart.microservice1.grpc.CompanyServiceOuterClass.CompanyResponse;
import com.lampart.microservice1.grpc.client.CompanyClient;
import com.lampart.microservice2.grpc.client.DepartmentClient;
import com.lampart.microservice2.grpc.DepartmentServiceOuterClass.DepartmentResponse;

public class Query implements GraphQLQueryResolver {
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public Query(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public Iterable<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Iterable<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    public long countBooks() {
        return bookRepository.count();
    }
    public long countAuthors() {
        return authorRepository.count();
    }
    
    public Company getCompany(Integer id) throws IllegalAccessException {

		try {
			CompanyResponse response1 = CompanyClient.init(id);
			System.out.println("Request received from microserver 1:\n" + response1);
			
			Company company_result = new Company(response1.getId(), response1.getName());

//			DepartmentResponse response2 = DepartmentClient.init(id);
//			System.out.println("Request received from microserver 2:\n" + response2);
//			
//			Department department_result = new Department(response2.getId(), response2.getName());
//			
//			company_result.setDepartment(department_result);
			
			return company_result;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
        
        //throw new GraphQLException("Invalid credentials");
     }
   
    
}
