package com.example.demo.infrastructure.driving.web

import com.example.demo.domain.model.Book
import com.example.demo.domain.usecase.BookUseCase
import com.example.demo.infrastructure.driving.web.dto.BookDTO
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/books")
class BookController (private val bookUseCase: BookUseCase) {
    @GetMapping
    fun findAll(): List<BookDTO> {
        return bookUseCase.getAllBooks().map { BookDTO(it.title, it.author) }
    }

    @PostMapping
    fun save(@RequestBody bookDTO: BookDTO) {
        bookUseCase.addBook(Book(bookDTO.title, bookDTO.author))
    }
}