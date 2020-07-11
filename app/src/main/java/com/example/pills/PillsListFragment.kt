package com.example.pills

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.pills.databinding.FragmentPillsListBinding

class PillsListFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentPillsListBinding.inflate(inflater, container, false)

        val viewModel = ViewModelProvider(this).get(PillsListViewModel::class.java)

        binding.viewModel = viewModel

        viewModel.navigateToAddPill.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                findNavController().navigate(PillsListFragmentDirections.actionPillsListFragmentToAddPillFragment())
                viewModel.navigationToAddPillCompleted()
            }
        })

        val adapter = PillsListAdapter()
        binding.pillsList.adapter = adapter

        viewModel.


        return binding.root
    }


}