package com.example.library

import android.content.Context
import android.os.Bundle
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
import com.example.library.databinding.FragmentReturnBinding

class ReturnFragment : Fragment() {

    private val navigationArgs : ReturnFragmentArgs by navArgs()

    lateinit var book: Book

    private val viewModel : LibraryViewModel by activityViewModels {
        LibraryViewModelFactory(
            (activity?.application as LibraryApplication).database.bookDao()
        )
    }

    private var _binding: FragmentReturnBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentReturnBinding.inflate(inflater, container, false)
        //return inflater.inflate(R.layout.fragment_return, container, false)

        return binding.root
    }

    private fun areValuesValid() : Boolean {
        return viewModel.isDataComplete(
            binding.bookNameInput.text.toString(),
            binding.studentNameInput.text.toString(),
            binding.rollnoInput.text.toString()
        )
    }

    private fun checkData() : Boolean {
        return viewModel.isDataCorrect(
            book,
            binding.studentNameInput.text.toString(),
            binding.rollnoInput.text.toString()
        )
    }

    private fun returnBook() {
        if (areValuesValid()){
            if (checkData()){
                viewModel.returnBook(
                    book
                )

                Toast.makeText(context, "Book returned successfully", Toast.LENGTH_SHORT).show()

                val action = ReturnFragmentDirections.actionReturnFragmentToStartFragment()
                findNavController().navigate(action)
            }else{
                Toast.makeText(context, "Incorrect Info", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(context, "One or more fields incomplete", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cancel() {
        Toast.makeText(context, "Task aborted", Toast.LENGTH_SHORT).show()

        val action = ReturnFragmentDirections.actionReturnFragmentToStartFragment()
        findNavController().navigate(action)
    }

    private fun bind(value : Int){
        binding.apply {
            bookNameInput.setText(value.toString())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind(navigationArgs.id)

        viewModel.retrieveInfoById(navigationArgs.id).observe(this.viewLifecycleOwner){
            selectedBook->
            book = selectedBook

        }

        binding.returnButton.setOnClickListener {
            returnBook()
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

