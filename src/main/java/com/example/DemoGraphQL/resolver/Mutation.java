package com.example.DemoGraphQL.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.DemoGraphQL.exception.BookNotFoundException;
import com.example.DemoGraphQL.grpc.OauthServiceOuterClass.OauthResponse;
import com.example.DemoGraphQL.grpc.client.GrpcClient;
import com.example.DemoGraphQL.model.AuthData;
import com.example.DemoGraphQL.model.Author;
import com.example.DemoGraphQL.model.Book;
import com.example.DemoGraphQL.model.Token;
import com.example.DemoGraphQL.repository.AuthorRepository;
import com.example.DemoGraphQL.repository.BookRepository;

public class Mutation implements GraphQLMutationResolver {
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public Mutation(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public Author newAuthor(String firstName, String lastName) {
        Author author = new Author();
        author.setFirstName(firstName);
        author.setLastName(lastName);

        authorRepository.save(author);

        return author;
    }

    public Book newBook(String title, String isbn, Integer pageCount, Long authorId) {
        Book book = new Book();
        book.setAuthor(new Author(authorId));
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setPageCount(pageCount != null ? pageCount : 0);

        bookRepository.save(book);

        return book;
    }

    public boolean deleteBook(Long id) {
        bookRepository.delete(id);
        return true;
    }

    public Book updateBookPageCount(Integer pageCount, Long id) {
        Book book = bookRepository.findOne(id);
        if(book == null) {
            throw new BookNotFoundException("The book to be updated was found", id);
        }
        book.setPageCount(pageCount);

        bookRepository.save(book);

        return book;
    }
    
    public OauthResponse loginUser(AuthData auth) throws IllegalAccessException {
    	//String password = auth.getPassword();
       // Token result = new Token("309cc879-06ce-45c2-8c79-79f2f65e1365", "bearer", "ef03a088-51f6-4b08-af25-103625790347", (long) 10799, "read write");
//        if(password == "admin1234" ) {
//        	return result;
//        }
		try {
			OauthResponse response1 = GrpcClient.init(auth);
			return response1;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
        
        //throw new GraphQLException("Invalid credentials");
     }
}
