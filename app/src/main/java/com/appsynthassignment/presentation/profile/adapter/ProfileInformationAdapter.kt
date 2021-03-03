package com.appsynthassignment.presentation.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appsynthassignment.data.model.uimodel.NotificationUiModel
import com.appsynthassignment.databinding.ItemProfileinformationBinding


class ProfileInformationAdapter :
    RecyclerView.Adapter<ProfileInformationAdapter.ProfileInformationViewHolder>() {

    private var items: List<NotificationUiModel> = listOf()

    class ProfileInformationViewHolder(
        private val binding: ItemProfileinformationBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NotificationUiModel) {
            binding.item = item
            binding.bgcolor = getBackgroundResId(adapterPosition)
        }

        private fun getBackgroundResId(position: Int)
                : Int = if (position % 2 == 0) {
            android.graphics.Color.WHITE
        } else {
            android.graphics.Color.LTGRAY
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileInformationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemProfileinformationBinding.inflate(layoutInflater, parent, false)
        return ProfileInformationViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ProfileInformationViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    fun setItems(listItem: List<NotificationUiModel>) {
        this.items = listItem
        notifyDataSetChanged()
    }

}