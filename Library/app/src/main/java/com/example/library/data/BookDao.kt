package com.example.library.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(book: Book)

    @Update
    suspend fun update(book: Book)

    @Query("SELECT * FROM book WHERE id = :id")
    fun getBookById(id: Int): Flow<Book>

    @Query("SELECT * FROM book")
    fun getAllBooks(): Flow<List<Book>>

    @Query("SELECT * FROM book where book_name = :name")
    fun getBookByName(name : String) : Flow<List<Book>>

    @Query("SELECT * FROM book where author_name = :author")
    fun getBookByAuthor(author: String) : Flow<List<Book>>

    @Query("SELECT * FROM book where genre = :genre")
    fun getBookByGenre(genre: String): Flow<List<Book>>
}

