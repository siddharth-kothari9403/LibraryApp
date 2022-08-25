package com.example.library.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "book_name")
    val bookName: String,
    @ColumnInfo(name = "author_name")
    val authorName: String,
    @ColumnInfo(name = "genre")
    val genre: String,
    @ColumnInfo(name = "publisher")
    val publisher: String,
    @ColumnInfo(name = "student_name")
    val studentName: String,
    @ColumnInfo(name = "student_rollno")
    val rollno: String

)

