package com.example.demo.domain.model

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class BookTest : DescribeSpec({
    describe("Book creation") {
        it("A book should have a title and an author") {
            val book = Book("The Hobbit", "J.R.R. Tolkien")
            book.title shouldBe  "The Hobbit"
            book.author shouldBe "J.R.R. Tolkien"
        }

        it("Should throw an exception if the title is empty") {
            shouldThrow<IllegalArgumentException> {
                Book("", "J.R.R. Tolkien")
            }
        }

        it("Should throw an exception if the author is empty") {
            shouldThrow<IllegalArgumentException> {
                Book("The Hobbit", "")
            }
        }
    }
})