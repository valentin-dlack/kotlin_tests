package com.example.demo.domain.usecase

import com.example.demo.domain.model.Book
import com.example.demo.domain.port.BookRepository
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeSortedBy
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class BookUseCaseTests : DescribeSpec ({
    val mockRepo = mockk<BookRepository>(relaxed = true)
    val bookUseCase = BookUseCase(mockRepo)

    it("Should add a book to the repo") {
        val book = Book("The Hobbit", "J.R.R. Tolkien")
        bookUseCase.addBook(book)
        verify { mockRepo.save(book) }
    }

    it("Should return all Books sorted by title") {
        val bookList = listOf(
            Book("The Hobbit", "J.R.R. Tolkien"),
            Book("Animal Farm", "George Orwell"),
            Book("1984", "George Orwell")
        )
        bookList.forEach { bookUseCase.addBook(it) }
        val sortedList = bookUseCase.getAllBooks()

        sortedList shouldBeSortedBy { it.title }
    }

    describe("Reserve a book") {
        it("should reserve a book if it is not already reserved") {
            val book = Book("The Hobbit", "J.R.R. Tolkien", reserved = false)
            every { mockRepo.reserveBook(book) } returns true

            val result = bookUseCase.reserveBook(book)

            result shouldBe true
            verify { mockRepo.reserveBook(book) }
        }

        it("should not reserve a book if it is already reserved") {
            val book = Book("The Hobbit", "J.R.R. Tolkien", reserved = true)
            every { mockRepo.reserveBook(book) } returns false

            val result = bookUseCase.reserveBook(book)

            result shouldBe false
            verify { mockRepo.reserveBook(book) }
        }
    }
})