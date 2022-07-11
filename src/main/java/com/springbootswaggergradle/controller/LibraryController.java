package com.springbootswaggergradle.controller;

import com.springbootswaggergradle.model.Book;
import com.springbootswaggergradle.repository.BookRepository;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/library")
@Api(value="",tags = {"Library Service"})
@Tag(name = "Library Service", description = "Sample Service for Library to get, add, update and delete books")
public class LibraryController {

    @Autowired
    BookRepository bookRepository;

    @Operation(summary = "Get All the books", description = "Get al list of books in the library", tags = "Get")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the books",
            content = {@Content(mediaType = "application/json",
            schema = @Schema(implementation = Book.class))}),
        @ApiResponse(responseCode = "204", description = "No Books found",
                content = @Content)})
    @GetMapping(value = "/getAllBooks")
    public ResponseEntity<List<Book>> getAllBooks() {
        try
        {
            List<Book> listOfBooks = new ArrayList<Book>();
            listOfBooks = bookRepository.findAll();

            if(listOfBooks.isEmpty())
            {
                return new ResponseEntity<List<Book>>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<List<Book>>(listOfBooks, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/books/{id}")
    public ResponseEntity getBookById(@PathVariable("id") String id)
    {
        try
        {
            Optional bookOptional = bookRepository.findById(id);

            return new ResponseEntity<>(bookOptional.get(), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/books")
    public ResponseEntity addABookToLibrary(@RequestBody Book book)
    {
        try
        {
            Book createdBook = bookRepository.save(new Book(book.getTitle(), book.getAuthor(),
                    book.getIsbn()));
            return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/books/{id}")
    public ResponseEntity updateABook(@PathVariable("id") String id, @RequestBody Book book)
    {
        Optional bookOptional = bookRepository.findById(id);

        if(bookOptional.isPresent())
        {
            Book updatedBook = (Book) bookOptional.get();
            updatedBook.setTitle(book.getTitle());
            updatedBook.setAuthor(book.getAuthor());
            updatedBook.setIsbn(book.getIsbn());
            return new ResponseEntity<>(bookRepository.save(updatedBook), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity deleteABook(@PathVariable("id") String id)
    {
        try
        {
            bookRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
