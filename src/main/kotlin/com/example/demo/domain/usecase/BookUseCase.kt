package com.example.demo.domain.usecase

import com.example.demo.domain.model.Book
import com.example.demo.domain.port.BookRepository

class BookUseCase(private val bookRepository: BookRepository) {
    fun addBook(book: Book) {
        bookRepository.save(book)
    }

    fun getAllBooks(): List<Book> {
        return bookRepository.getAllBooks().sortedBy { it.title.lowercase() }
    }
}