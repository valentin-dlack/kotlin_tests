package com.example.demo.infrastructure.driving.web.dto

import com.example.demo.domain.model.Book

data class BookDTO(val title: String, val author: String) {
    fun toDomain(): Book {
        return Book(
            title = this.title,
            author = this.author,
        )
    }
}

fun Book.toDTO() = BookDTO(
    title = this.title,
    author = this.author,
)