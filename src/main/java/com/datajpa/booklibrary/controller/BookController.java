package com.datajpa.booklibrary.controller;

import com.datajpa.booklibrary.dto.requestDto.BookRequestDto;
import com.datajpa.booklibrary.dto.responseDto.BookResponseDto;
import com.datajpa.booklibrary.service.inter.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/add")
    public ResponseEntity<BookResponseDto> addBook(@RequestBody final BookRequestDto bookRequestDto){
        BookResponseDto bookResponseDto = bookService.addBook(bookRequestDto);
        return new ResponseEntity<>(bookResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<BookResponseDto> getBook(@PathVariable final Long id){
        BookResponseDto bookResponseDto = bookService.getBookById(id);
        return new ResponseEntity<>(bookResponseDto, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<BookResponseDto>> getBooks(){
        List<BookResponseDto> bookResponseDtos = bookService.getBooks();
        return new ResponseEntity<>(bookResponseDtos, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BookResponseDto> deleteBook(@PathVariable final Long id){
        BookResponseDto bookResponseDto = bookService.deleteBook(id);
        return new ResponseEntity<>(bookResponseDto, HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<BookResponseDto> editBook(@PathVariable final Long id,
                                                    @RequestBody final BookRequestDto bookRequestDto){
        BookResponseDto bookResponseDto = bookService.editBook(id,bookRequestDto);
        return new ResponseEntity<>(bookResponseDto, HttpStatus.OK);
    }

    @PostMapping("/addCategory/{categoryId}/to/{bookId}")
    public ResponseEntity<BookResponseDto> addCategory(@PathVariable final Long categoryId,
                                                       @PathVariable final Long bookId){
        BookResponseDto bookResponseDto = bookService.addCategoryToBook(bookId,categoryId);
        return new ResponseEntity<>(bookResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/removeCategory/{categoryId}/from/{bookId}")
    public ResponseEntity<BookResponseDto> removeCategory(@PathVariable final Long categoryId,
                                                          @PathVariable final Long bookId){
        BookResponseDto bookResponseDto = bookService.removeCategoryFromBook(bookId,categoryId);
        return new ResponseEntity<>(bookResponseDto, HttpStatus.OK);
    }

    @PostMapping("/addAuthor{authorId}/to/{bookId}")
    public ResponseEntity<BookResponseDto> addAuthor(@PathVariable final Long authorId,
                                                     @PathVariable final Long bookId){
        BookResponseDto bookResponseDto = bookService.addAuthorToBook(bookId, authorId);
        return new ResponseEntity<>(bookResponseDto, HttpStatus.OK);
    }

    @PostMapping("/removeAuthor/{authorId}/from/{bookId}")
    public ResponseEntity<BookResponseDto> removeAuthor(@PathVariable final Long authorId,
                                                        @PathVariable final Long bookId){
        BookResponseDto bookResponseDto = bookService.deleteAuthorFromBook(bookId,authorId);
        return new ResponseEntity<>(bookResponseDto, HttpStatus.OK);
    }

}
