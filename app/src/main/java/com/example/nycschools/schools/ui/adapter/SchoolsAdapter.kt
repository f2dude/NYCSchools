package com.example.nycschools.schools.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nycschools.api.model.SchoolListItem
import com.example.nycschools.databinding.SchoolItemBinding

/**
 * Adapter class to bind items in recycler view
 *
 * @author Saikrishna Pawar
 * @since 9/9/22
 */
class SchoolsAdapter(val mListener: ClickListener) : ListAdapter<SchoolListItem, SchoolsAdapter.ViewHolder>(COMPARATOR) {

    private val mItems = mutableListOf<SchoolListItem>()

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<SchoolListItem>() {
            override fun areItemsTheSame(
                oldItem: SchoolListItem,
                newItem: SchoolListItem
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: SchoolListItem,
                newItem: SchoolListItem
            ): Boolean = oldItem.dbn == newItem.dbn
        }
    }

    /**
     * View holder
     */
    inner class ViewHolder(
        private val mBinding: SchoolItemBinding
    ) : RecyclerView.ViewHolder(mBinding.root) {

        /**
         * Bind data to view
         *
         * @param item  [SchoolListItem]
         */
        fun bind(item: SchoolListItem) {
            mBinding.name.text = item.school_name
            mBinding.name.setOnClickListener {
                mListener.onItemClick(item.dbn)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            SchoolItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    /**
     * Set data on list
     *
     * @param items list of school items
     */
    fun setData(items: List<SchoolListItem>) {
        mItems.clear()

        mItems.addAll(items)

        submitList(mItems)
    }

    /**
     * List item click listener
     */
    interface ClickListener {
        fun onItemClick(dbn: String)
    }
}