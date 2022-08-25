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
import com.example.library.databinding.FragmentInsertBookBinding

class InsertBookFragment : Fragment() {

    private val viewModel : LibraryViewModel by activityViewModels {
        LibraryViewModelFactory(
            (activity?.application as LibraryApplication).database.bookDao()
        )
    }

    private var _binding: FragmentInsertBookBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInsertBookBinding.inflate(inflater, container, false)
        //return inflater.inflate(R.layout.fragment_insert_book, container, false)

        return binding.root
    }

    private fun areValuesValid() : Boolean {
        Log.d("Insert Fragment", "Values being checked")
        return viewModel.isEntryValid(
            binding.bookNameInput.text.toString(),
            binding.authorNameInput.text.toString(),
            binding.genreInput.text.toString(),
            binding.publisherInput.text.toString()
        )
    }

    private fun addBookToDatabase(){
        if (areValuesValid()) {
            viewModel.addNewBook(
                binding.bookNameInput.text.toString(),
                binding.authorNameInput.text.toString(),
                binding.genreInput.text.toString(),
                binding.publisherInput.text.toString()
            )

            Toast.makeText(context, "Book Added Successfully", Toast.LENGTH_SHORT).show()

            val action = InsertBookFragmentDirections.actionInsertBookFragmentToStartFragment()
            findNavController().navigate(action)
        }
        else{
            Toast.makeText(context, "One or more fields incomplete", Toast.LENGTH_SHORT).show()
        }

    }

    private fun cancel() {
        val action = InsertBookFragmentDirections.actionInsertBookFragmentToStartFragment()
        findNavController().navigate(action)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Insert Fragment", "CREATED")

        binding.addButton.setOnClickListener {
            Log.d("Insert Fragment", "Button Clicked")
            addBookToDatabase()
            Log.d("Insert Fragment", "Added")

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
        _binding = null

    }

}