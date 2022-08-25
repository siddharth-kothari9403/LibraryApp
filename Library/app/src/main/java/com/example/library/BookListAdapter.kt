package com.example.library

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.library.data.Book

class BookListAdapter: ListAdapter<Book, BookListAdapter.BookViewHolder>(DiffCallBack){

    class BookViewHolder(private var view: View) : RecyclerView.ViewHolder(view) {
        val idTextView : TextView = view.findViewById(R.id.id_text_view)
        val nameTextView : TextView = view.findViewById(R.id.name_text_view)
        val authorTextView : TextView = view.findViewById(R.id.author_text_view)
        val genreTextView : TextView = view.findViewById(R.id.genre_text_view)
        val publisherTextView : TextView = view.findViewById(R.id.publisher_text_view)
        val studentTextView: TextView = view.findViewById(R.id.student_text_view)
        val rollnoTextView : TextView = view.findViewById(R.id.rollno_text_view)
    }

    companion object {
        private val DiffCallBack = object :DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.map_list_item, parent, false)
        return BookViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = getItem(position)
        holder.idTextView.text = book.id.toString()
        holder.nameTextView.text = book.bookName
        holder.authorTextView.text = book.authorName
        holder.genreTextView.text = book.genre
        holder.publisherTextView.text = book.publisher
        holder.studentTextView.text = book.studentName
        holder.rollnoTextView.text = book.rollno
    }
}