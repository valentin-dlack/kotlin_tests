package com.example.demo.infrastructure.driving.web.dto

import com.example.demo.domain.model.Book

data class BookDTO(val title: String, val author: String, val reserved: Boolean) {
    fun toDomain(): Book {
        return Book(
            title = this.title,
            author = this.author,
            reserved = this.reserved
        )
    }
}

fun Book.toDTO() = BookDTO(
    title = this.title,
    author = this.author,
    reserved = this.reserved
)