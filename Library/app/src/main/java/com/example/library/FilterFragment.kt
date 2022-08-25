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
import com.example.library.databinding.FragmentFilterBinding
import com.example.library.databinding.FragmentStartBinding


class FilterFragment : Fragment() {

    private val viewModel : LibraryViewModel by activityViewModels {
        LibraryViewModelFactory(
            (activity?.application as LibraryApplication).database.bookDao()
        )
    }

    private var _binding : FragmentFilterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        //return inflater.inflate(R.layout.fragment_start, container, false)

        return binding.root
    }

    private fun goToHome() {
        val action = FilterFragmentDirections.actionFilterFragmentToStartFragment()
        findNavController().navigate(action)
    }

    private fun searchName(){
        val action = FilterFragmentDirections.actionFilterFragmentToFilteredListFragment(binding.searchKeyword.text.toString(), 1)
        findNavController().navigate(action)
    }

    private fun searchAuthor(){
        val action = FilterFragmentDirections.actionFilterFragmentToFilteredListFragment(binding.searchKeyword.text.toString(), 2)
        findNavController().navigate(action)
    }

    private fun searchGenre(){
        val action = FilterFragmentDirections.actionFilterFragmentToFilteredListFragment(binding.searchKeyword.text.toString(), 3)
        findNavController().navigate(action)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cancelButton.setOnClickListener {
            goToHome()
        }

        binding.bookNameButton.setOnClickListener {
            if (viewModel.isKeywordValid(binding.searchKeyword.text.toString())){
                searchName()
            }else{
                Toast.makeText(context, "Field Empty", Toast.LENGTH_SHORT).show()
            }
        }

        binding.authorNameButton.setOnClickListener {
            if (viewModel.isKeywordValid(binding.searchKeyword.text.toString())){
                searchAuthor()
            }else{
                Toast.makeText(context, "Field Empty", Toast.LENGTH_SHORT).show()
            }
        }

        binding.genreButton.setOnClickListener {
            if (viewModel.isKeywordValid(binding.searchKeyword.text.toString())){
                searchGenre()
            }else{
                Toast.makeText(context, "Field Empty", Toast.LENGTH_SHORT).show()
            }
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