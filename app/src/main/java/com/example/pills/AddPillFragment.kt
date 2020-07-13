package com.example.pills

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.pills.databinding.FragmentAddPillBinding
import com.google.android.material.snackbar.Snackbar

class AddPillFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentAddPillBinding.inflate(inflater, container, false)

        val viewModel = ViewModelProvider(this).get(AddPillViewModel::class.java)

        binding.requiredDosesPicker.apply {
            minValue = 1
            maxValue = 100
            wrapSelectorWheel = false
        }

        binding.pillNameEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.doneButton.isEnabled = !s.isNullOrEmpty()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.apply {
            doneButton.setOnClickListener {
                viewModel.onDoneButton(
                    pillNameEditText.text.toString(),
                    requiredDosesPicker.value
                )
            }
        }

        viewModel.navigateBackToList.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                findNavController().navigate(AddPillFragmentDirections.actionAddPillFragmentToPillsListFragment())
                viewModel.onNavigationToListCompleted()
            }
        })

        viewModel.duplicateFoundEvent.observe(viewLifecycleOwner, Observer { duplicate ->
            if (duplicate) {
                Snackbar.make(
                    binding.root,
                    R.string.duplicate_found_snackbar,
                    Snackbar.LENGTH_SHORT
                ).show()
                viewModel.onDuplicateEventHandled()
            }
        })

        return binding.root
    }
}