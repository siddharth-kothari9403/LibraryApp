package com.example.library

import androidx.lifecycle.*
import com.example.library.data.Book
import com.example.library.data.BookDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class LibraryViewModel(private val bookDao: BookDao): ViewModel() {
    val allItems : LiveData<List<Book>> = bookDao.getAllBooks().asLiveData()

    fun setCurrentList(choice: Int, phrase: String): Flow<List<Book>>{
        var temp : Flow<List<Book>> = bookDao.getAllBooks()
        when (choice) {
            1 -> {
                temp = searchByName(phrase)
            }
            2 -> {
                temp = searchByAuthor(phrase)
            }
            3 -> {
                temp = searchByGenre(phrase)
            }
        }

        return temp
    }

    fun isKeywordValid(phrase: String): Boolean{
        if (phrase.isBlank()){
            return false
        }
        return true
    }

    fun isEntryValid(bookName: String, authorName: String, genre: String, publisher: String): Boolean{
        if (bookName.isBlank() || authorName.isBlank() || genre.isBlank() || publisher.isBlank()){
            return false
        }
        return true
    }

    private fun getNewBookEntry(bookName: String, authorName: String, genre:String, publisher: String): Book {
        return Book(
            bookName = bookName,
            authorName = authorName,
            genre = genre,
            publisher = publisher,
            studentName = "",
            rollno = ""
        )
    }

    private fun insertItem(book: Book){
        viewModelScope.launch {
            bookDao.insert(book)
        }
    }

    fun addNewBook(bookName: String, authorName: String, genre: String, publisher: String){
        val newBook = getNewBookEntry(bookName, authorName, genre, publisher)
        insertItem(newBook)
    }


    fun retrieveInfoById(id: Int): LiveData<Book>{
        return bookDao.getBookById(id).asLiveData()
    }

    private fun searchByName(phrase: String): Flow<List<Book>> {
        return bookDao.getBookByName(phrase)
    }

    private fun searchByAuthor(phrase: String): Flow<List<Book>> {
        return bookDao.getBookByAuthor(phrase)
    }

    private fun searchByGenre(phrase: String): Flow<List<Book>> {
        return bookDao.getBookByGenre(phrase)
    }

    private fun updateItem(book: Book){
        viewModelScope.launch {
            bookDao.update(book)
        }
    }

    fun isDataComplete(id: String, studentName: String, rollno: String): Boolean{
        if (id.isBlank() || studentName.isBlank() || rollno.isBlank()){
            return false
        }
        return true
    }

    fun issueBookToStudent(book: Book, studentName: String, rollno: String){
        val newBook = Book(
            id = book.id,
            bookName = book.bookName,
            authorName = book.authorName,
            genre = book.genre,
            publisher = book.publisher,
            studentName = studentName,
            rollno = rollno
        )
        updateItem(newBook)
    }

    fun isDataCorrect(book: Book, studentName: String, rollno: String): Boolean{
        if (book.studentName == studentName && book.rollno == rollno){
            return true
        }
        return false
    }

    fun returnBook(book: Book){
        val newBook = Book(
            id = book.id,
            bookName = book.bookName,
            authorName = book.authorName,
            genre = book.genre,
            publisher = book.publisher,
            studentName = "",
            rollno = ""
        )

        updateItem(newBook)
    }

}

class LibraryViewModelFactory(private val bookDao: BookDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LibraryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LibraryViewModel(bookDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}