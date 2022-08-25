package com.example.library

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.library.databinding.FragmentStartBinding


class StartFragment : Fragment() {

    private var _binding : FragmentStartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        //return inflater.inflate(R.layout.fragment_start, container, false)

        return binding.root
    }

    private fun navigateToAdd() {
        val action = StartFragmentDirections.actionStartFragmentToInsertBookFragment()
        findNavController().navigate(action)
    }

    private fun navigateToSearch() {
        val action = StartFragmentDirections.actionStartFragmentToSearchBookFragment()
        findNavController().navigate(action)
    }


    private fun navigateToLibrary() {
        val action = StartFragmentDirections.actionStartFragmentToMapFragment()
        findNavController().navigate(action)
    }

    private fun navigateToFilter() {
        val action = StartFragmentDirections.actionStartFragmentToFilterFragment()
        findNavController().navigate(action)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addBookButton.setOnClickListener {
            Log.d("Start Fragment", "Add button Clicked")
            navigateToAdd()
        }
        binding.searchBookButton.setOnClickListener {
            navigateToSearch()
        }
        binding.viewLibrary.setOnClickListener {
            navigateToLibrary()
        }

        binding.filterBooks.setOnClickListener {
            navigateToFilter()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}