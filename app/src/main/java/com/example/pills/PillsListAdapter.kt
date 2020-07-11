package com.example.pills

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pills.models.Pill
import com.example.pills.databinding.PillsListItemBinding

class PillsListAdapter: ListAdapter<Pill, PillsListAdapter.PillViewHolder>(DiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PillViewHolder {
        return PillViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PillViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PillViewHolder private constructor(private val binding: PillsListItemBinding):
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): PillViewHolder {
                return PillViewHolder(PillsListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ))
            }
        }

        fun bind(pill: Pill) {
            binding.pillName.text = pill.name
            binding.currentDoses.text = pill.currentDoses.toString()
        }
    }

    object DiffItemCallback: DiffUtil.ItemCallback<Pill>() {
        override fun areItemsTheSame(oldItem: Pill, newItem: Pill): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Pill, newItem: Pill): Boolean {
            return oldItem == newItem
        }
    }
}