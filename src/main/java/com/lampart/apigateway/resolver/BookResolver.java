package com.lampart.apigateway.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.lampart.apigateway.model.Author;
import com.lampart.apigateway.model.Book;
import com.lampart.apigateway.repository.AuthorRepository;

public class BookResolver implements GraphQLResolver<Book> {
    private AuthorRepository authorRepository;

    public BookResolver(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author getAuthor(Book book) {
        return authorRepository.findOne(book.getAuthor().getId());
    }
}
