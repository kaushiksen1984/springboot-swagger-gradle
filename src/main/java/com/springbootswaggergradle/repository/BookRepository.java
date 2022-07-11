package com.springbootswaggergradle.repository;

import com.springbootswaggergradle.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String> {

    Optional<Book> findByAuthor(String name);

}
