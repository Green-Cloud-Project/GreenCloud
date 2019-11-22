package com.share.greencloud.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.share.greencloud.R
import com.share.greencloud.databinding.ItemUserFavoriteBinding
import com.share.greencloud.domain.model.RentalOffice
import com.share.greencloud.presentation.viewmodel.ItemUserFavoritePlaceViewModel

class UserFavoritePlaceAdapter(val userData: MutableList<RentalOffice>) : RecyclerView.Adapter<UserFavoritePlaceAdapter.UserFavoritePlaceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserFavoritePlaceViewHolder {
        val binding = DataBindingUtil.inflate<ItemUserFavoriteBinding>(LayoutInflater.from(parent.context), R.layout.item_user_favorite,
                parent, false)

        return UserFavoritePlaceViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userData.size
    }

    fun removeAt(position: Int): Int {
        val id = userData[position].office_id
       userData.removeAt(position)
        notifyItemRemoved(position)
        return id
    }

    override fun onBindViewHolder(holder: UserFavoritePlaceViewHolder, position: Int) {
        holder.bindPlace(userData[position])
    }

    class UserFavoritePlaceViewHolder(binding: ItemUserFavoriteBinding) : RecyclerView.ViewHolder(binding.itemUserFavorite) {
        private val itemUserFavoriteBinding: ItemUserFavoriteBinding = binding

        fun bindPlace(rentalOffice: RentalOffice) {
            itemUserFavoriteBinding.viewmodel = ItemUserFavoritePlaceViewModel(rentalOffice, itemView.context)
        }
    }
}