package com.example.demo.domain.model

class Book(val title: String, val author: String, val reserved: Boolean = false) {
    init {
        require(title.isNotBlank()) { "Title cannot be empty" }
        require(author.isNotBlank()) { "Author cannot be empty" }
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Book) return false

        if (title != other.title) return false
        if (author != other.author) return false
        if (reserved != other.reserved) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + author.hashCode()
        return result
    }
}