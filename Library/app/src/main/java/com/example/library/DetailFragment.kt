package com.example.library

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.library.data.Book
import com.example.library.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private val navigationArgs : DetailFragmentArgs by navArgs()

    lateinit var book: Book

    private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LibraryViewModel by activityViewModels {
        LibraryViewModelFactory(
            (activity?.application as LibraryApplication).database.bookDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
        //return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    private fun moveToIssueFragment() {
        val action = DetailFragmentDirections.actionDetailFragmentToIssueBookFragment(navigationArgs.id)
        findNavController().navigate(action)
    }

    private fun moveToReturnFragment() {
        val action = DetailFragmentDirections.actionDetailFragmentToReturnFragment(navigationArgs.id)
        findNavController().navigate(action)
    }

    private fun moveToStartFragment() {
        val action = DetailFragmentDirections.actionDetailFragmentToStartFragment()
        findNavController().navigate(action)
    }

    private fun bind(book: Book){
        binding.apply {
            idTextView.text = book.id.toString()
            nameTextView.text = book.bookName
            authorTextView.text = book.authorName
            genreTextView.text = book.genre
            publisherTextView.text = book.publisher
            studentTextView.text = book.studentName
            rollnoTextView.text = book.rollno
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.retrieveInfoById(navigationArgs.id).observe(this.viewLifecycleOwner){
            selectedBook ->
            book = selectedBook
            bind(book)
        }

        binding.issueButtonDetail.setOnClickListener {

            if (book.studentName == "" && book.rollno == "") {
                moveToIssueFragment()
            }else{
                Toast.makeText(context, "Issued to Someone Else", Toast.LENGTH_SHORT).show()
            }

        }
        binding.cancelButtonDetail.setOnClickListener {
            moveToStartFragment()
        }
        binding.returnButtonDetail.setOnClickListener {
            if ( book.studentName != "" || book.rollno != "" ){
                moveToReturnFragment()
            }else{
                Toast.makeText(context, "Not issued to anyone", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}