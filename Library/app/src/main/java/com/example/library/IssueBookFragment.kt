package com.example.library

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.library.data.Book
import com.example.library.databinding.FragmentIssueBookBinding

class IssueBookFragment : Fragment() {

    private val navigationArgs : IssueBookFragmentArgs by navArgs()

    private val viewModel : LibraryViewModel by activityViewModels {
        LibraryViewModelFactory(
            (activity?.application as LibraryApplication).database.bookDao()
        )
    }

    lateinit var book: Book


    private var _binding: FragmentIssueBookBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentIssueBookBinding.inflate(inflater, container, false)
        //return inflater.inflate(R.layout.fragment_issue_book, container, false)
        return binding.root
    }

    private fun areValuesValid() : Boolean {
        return viewModel.isDataComplete(
            binding.bookNameInput.text.toString(),
            binding.studentNameInput.text.toString(),
            binding.rollnoInput.text.toString()
        )
    }

    private fun bookIssuable() : Boolean {
        if (book.studentName =="" && book.rollno == ""){
            return true
        }
        return false
    }

    private fun issueBook() {
        Log.d("Issue Fragment", "Issue starts")
        if (areValuesValid()){
            Log.d("Issue Fragment", "Retrieval starts")

            if (bookIssuable()){
                Log.d("Issue Fragment", "Issue function to be called")
                viewModel.issueBookToStudent(book, binding.studentNameInput.text.toString(), binding.rollnoInput.text.toString())

                Toast.makeText(context, "Book issued successfully", Toast.LENGTH_SHORT).show()
                val action = IssueBookFragmentDirections.actionIssueBookFragmentToStartFragment()
                findNavController().navigate(action)

            }else{
                Toast.makeText(context, "Book already issued to someone else", Toast.LENGTH_SHORT).show()

                val action = IssueBookFragmentDirections.actionIssueBookFragmentToStartFragment()
                findNavController().navigate(action)
            }
        }else{
            Toast.makeText(context, "One or more fields are incomplete", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cancel() {
        Toast.makeText(context, "Task aborted", Toast.LENGTH_SHORT).show()

        val action = IssueBookFragmentDirections.actionIssueBookFragmentToStartFragment()
        findNavController().navigate(action)
    }

    private fun bind(value : Int) {
        binding.apply {
            bookNameInput.setText(value.toString())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind(navigationArgs.id)

        viewModel.retrieveInfoById(navigationArgs.id).observe(this.viewLifecycleOwner){
            selectedBook ->
            book = selectedBook
        }

        binding.issueButton.setOnClickListener {
            issueBook()
        }

        binding.cancelButton.setOnClickListener {
            cancel()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding= null
    }
}