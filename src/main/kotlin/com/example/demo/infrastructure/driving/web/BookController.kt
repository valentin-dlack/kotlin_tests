package com.example.demo.infrastructure.driving.web

import com.example.demo.domain.model.Book
import com.example.demo.domain.usecase.BookUseCase
import com.example.demo.infrastructure.driving.web.dto.BookDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/books")
class BookController (private val bookUseCase: BookUseCase) {
    @CrossOrigin
    @GetMapping
    fun findAll(): List<BookDTO> {
        return bookUseCase.getAllBooks().map { BookDTO(it.title, it.author, it.reserved) }
    }

    @CrossOrigin
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@RequestBody bookDTO: BookDTO) {
        bookUseCase.addBook(Book(bookDTO.title, bookDTO.author))
    }

    @CrossOrigin
    @PostMapping("/reserve")
    fun reserve(@RequestBody bookDTO: BookDTO): ResponseEntity<Void> {
        return if (bookUseCase.reserveBook(Book(bookDTO.title, bookDTO.author))) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.status(HttpStatus.CONFLICT).build()
        }
    }
}