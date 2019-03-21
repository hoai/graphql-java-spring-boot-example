package com.lampart.apigateway.repository;

import org.springframework.data.repository.CrudRepository;

import com.lampart.apigateway.model.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
}
