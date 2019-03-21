package com.lampart.apigateway.repository;

import org.springframework.data.repository.CrudRepository;

import com.lampart.apigateway.model.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
