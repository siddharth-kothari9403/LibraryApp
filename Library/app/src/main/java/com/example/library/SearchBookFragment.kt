package com.example.library

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.library.databinding.FragmentSearchBookBinding

class SearchBookFragment : Fragment() {

    private var _binding: FragmentSearchBookBinding? = null
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
        _binding = FragmentSearchBookBinding.inflate(inflater, container, false)
        //return inflater.inflate(R.layout.fragment_search_book, container, false)

        return binding.root
    }

    private fun returnToHome(){
        val action = SearchBookFragmentDirections.actionSearchBookFragmentToStartFragment()
        findNavController().navigate(action)
    }

    private fun moveToDetails(id: Int){
        val action = SearchBookFragmentDirections.actionSearchBookFragmentToDetailFragment(id)
        findNavController().navigate(action)
    }


    private fun isDataValid(): Boolean{
        return (binding.searchKeyword.text?.isBlank() == false)
    }

    private fun searchFunctionality(){
        Log.d("Search Fragment", "Search Clicked")
        if (isDataValid()){
            moveToDetails(binding.searchKeyword.text.toString().toInt())
        }else{
            Toast.makeText(context, "Enter Keywords", Toast.LENGTH_SHORT).show()

        }
    }

    private fun cancel(){
        returnToHome()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.search.setOnClickListener {
            searchFunctionality()
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

