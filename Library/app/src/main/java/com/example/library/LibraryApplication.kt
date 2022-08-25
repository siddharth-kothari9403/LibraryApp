package com.example.library

import android.app.Application
import com.example.library.data.BookDatabase

class LibraryApplication :Application() {
    val database : BookDatabase by lazy { BookDatabase.getDatabase(this)}
}