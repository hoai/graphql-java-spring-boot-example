package com.example.DemoGraphQL.model;

public class Company {

    private Integer id;

    private String name;
    
    private Department department;

	public Integer getId() {
		return id;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Company(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
    

  
}
