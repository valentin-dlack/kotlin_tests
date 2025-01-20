package com.example.demo.infrastructure.driven.adapter

import com.example.demo.domain.model.Book
import com.example.demo.domain.port.BookRepository
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service

@Service
class BookDAO (
    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate
): BookRepository {
    override fun getAllBooks(): List<Book> {
       return namedParameterJdbcTemplate
           .query("SELECT title, author FROM book") { rs, _ ->
               Book(rs.getString("title"), rs.getString("author"))
           }
    }

    override fun save(book: Book) {
        namedParameterJdbcTemplate.update(
            "INSERT INTO book (title, author) VALUES (:title, :author)",
            mapOf("title" to book.title, "author" to book.author)
        )
    }

    override fun reserveBook(book: Book): Boolean {
        val updatedRows = namedParameterJdbcTemplate.update(
            "UPDATE book SET reserved = true WHERE title = :title AND author = :author AND reserved = false",
            mapOf("title" to book.title, "author" to book.author)
        )
        //print(updatedRows == 1)
        return updatedRows == 1
    }
}