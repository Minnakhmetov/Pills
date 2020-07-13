package com.example.pills

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.pills.databinding.FragmentPillsListBinding
import com.example.pills.databinding.PillsListItemBinding
import com.example.pills.models.Pill
import kotlinx.android.synthetic.main.fragment_pills_list.*
import timber.log.Timber

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

        val adapter = PillsListAdapter(object: PillsListItemClickListener {
            override fun onPlusClicked(binding: PillsListItemBinding, pill: Pill) {
                viewModel.onDoseIncrease(pill)
            }

            override fun onMinusClicked(binding: PillsListItemBinding, pill: Pill) {
                viewModel.onDoseDecrease(pill)
            }

            override fun onDeleteClicked(pill: Pill) {
                viewModel.onPillDeletion(pill)
            }
        })

        binding.pillsList.adapter = adapter

        binding.pillsList.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )

        viewModel.pillsList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        binding.displayOptions.setOnCheckedChangeListener { _, id ->
            viewModel.pillsList.removeObservers(viewLifecycleOwner)
            viewModel.filter = when (id) {
                R.id.hide_completed_filter -> Filter.HIDE_COMPLETED
                R.id.not_completed_first_filter -> Filter.NOT_COMPLETED_FIRST
                else -> Filter.NONE
            }
            viewModel.pillsList.observe(viewLifecycleOwner, Observer {
                adapter.submitList(it)
            })
        }

        return binding.root
    }

}