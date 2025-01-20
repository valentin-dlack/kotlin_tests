package com.example.demo.domain.usecase

import com.example.demo.domain.model.Book
import com.example.demo.domain.port.BookRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.next
import io.kotest.property.arbitrary.stringPattern
import io.kotest.property.checkAll

class InMemoryBookPort : BookRepository {
    private val books = mutableListOf<Book>()

    override fun getAllBooks(): List<Book> {
        return books
    }

    override fun save(book: Book) {
        books.add(book)
    }

    override fun reserveBook(book: Book): Boolean {
        return false
    }

    fun clear() {
        books.clear()
    }
}

class BookUseCasePropertyTests: FunSpec({
    val bookRepository = InMemoryBookPort()
    val bookUseCase = BookUseCase(bookRepository)

    test("Should return all books in alphabetical order") {
        checkAll(Arb.int(1..100)) { size ->
            bookRepository.clear()

            val arb = Arb.stringPattern("""[A-Z][a-z]{1,10}""")

            val titles = mutableListOf<String>()

            for (i in 0 until size) {
                val title = arb.next()
                bookUseCase.addBook(Book(title, "John Doe"))
                titles.add(title)
            }

            val sortedTitles = bookUseCase.getAllBooks()
            sortedTitles.map { it.title } shouldContainExactly titles.sorted()
        }
    }
})