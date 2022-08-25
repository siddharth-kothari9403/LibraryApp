package com.example.library

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.library.databinding.FragmentMapBinding

class MapFragment : Fragment() {

    private val viewModel: LibraryViewModel by activityViewModels {
        LibraryViewModelFactory(
            (activity?.application as LibraryApplication).database
                .bookDao()
        )
    }

    private var _binding : FragmentMapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        //return inflater.inflate(R.layout.fragment_map, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = BookListAdapter()
        binding.recyclerView.adapter = adapter

        viewModel.allItems.observe(this.viewLifecycleOwner) {
            items -> items.let {
                adapter.submitList(it)
            }
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)

        binding.back.setOnClickListener {
            goBack()
        }
    }

    private fun goBack(){
        val action = MapFragmentDirections.actionMapFragmentToStartFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}