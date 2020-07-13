package com.example.pills

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pills.models.Pill
import com.example.pills.databinding.PillsListItemBinding
import com.example.pills.models.getDoseTakenToday
import timber.log.Timber

class PillsListAdapter(private val clickListener: PillsListItemClickListener): ListAdapter<Pill, PillsListAdapter.PillViewHolder>(PillsDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PillViewHolder {
        return PillViewHolder.from(parent, clickListener)
    }

    override fun onBindViewHolder(holder: PillViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PillViewHolder private constructor(private val binding: PillsListItemBinding,
                                             private val clickListener: PillsListItemClickListener):
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup, clickListener: PillsListItemClickListener): PillViewHolder {
                return PillViewHolder(PillsListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), clickListener)
            }
        }

        fun bind(pill: Pill) {
            binding.pillName.text = pill.name
            binding.doses.text = binding.doses.context.getString(
                R.string.doses_text,
                pill.getDoseTakenToday(),
                pill.requiredDoses
            )
            binding.deleteButton.setOnClickListener {
                clickListener.onDeleteClicked(pill)
            }
            binding.plusButton.setOnClickListener {
                clickListener.onPlusClicked(binding, pill)
            }
            binding.minusButton.setOnClickListener {
                clickListener.onMinusClicked(binding, pill)
            }
        }
    }

    object PillsDiffItemCallback: DiffUtil.ItemCallback<Pill>() {
        override fun areItemsTheSame(oldItem: Pill, newItem: Pill): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Pill, newItem: Pill): Boolean {
            return oldItem == newItem
        }
    }
}

interface PillsListItemClickListener {
    fun onPlusClicked(binding: PillsListItemBinding, pill: Pill)
    fun onMinusClicked(binding: PillsListItemBinding, pill: Pill)
    fun onDeleteClicked(pill: Pill)
}