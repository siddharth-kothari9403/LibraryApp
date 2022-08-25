package com.example.library

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.library.databinding.FragmentFilteredListBinding
import com.example.library.databinding.FragmentSearchBookBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FilteredListFragment : Fragment() {

    private val navigationArgs: FilteredListFragmentArgs by navArgs()

    private var _binding: FragmentFilteredListBinding? = null
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
        _binding = FragmentFilteredListBinding.inflate(inflater, container, false)
        //return inflater.inflate(R.layout.fragment_search_book, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = BookListAdapter()
        binding.recyclerView.adapter = adapter

        lifecycle.coroutineScope.launch {
            viewModel.setCurrentList(navigationArgs.choice, navigationArgs.keyword).collect() {
                adapter.submitList(it)
            }
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)

        binding.back.setOnClickListener {
            val action = FilteredListFragmentDirections.actionFilteredListFragmentToStartFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}