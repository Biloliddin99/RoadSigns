package com.example.yolbelgilari.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yolbelgilari.R
import com.example.yolbelgilari.databinding.ItemRvBinding
import com.example.yolbelgilari.models.MySign

class MyRvAdapter(
    val context: Context,
    val list: ArrayList<MySign>,
    val rvClickInterface: RvClickInterface
) : RecyclerView.Adapter<MyRvAdapter.Vh>() {

    inner class Vh(private val itemRvBinding: ItemRvBinding) :
        RecyclerView.ViewHolder(itemRvBinding.root) {

        fun onBind(mySign: MySign, position: Int) {
            itemRvBinding.tvName.text = mySign.name
            itemRvBinding.image.setImageURI(Uri.parse(mySign.photoPath))
            if (mySign.liked == 1) {
                itemRvBinding.btnLike.setImageResource(R.drawable.full_heart)
            }
            itemRvBinding.apply {
                btnLike.setOnClickListener {
                    if (mySign.liked == 0) {
                        btnLike.setImageResource(R.drawable.full_heart)
                        list[position].liked = 1
                        mySign.liked = 1
                        rvClickInterface.changeLiked(position, mySign)
                    } else {
                        btnLike.setImageResource(R.drawable.empty_heart)
                        list[position].liked = 0
                        mySign.liked = 0
                        rvClickInterface.changeLiked(position, mySign)
                    }
                }
                btnEdit.setOnClickListener {
                    rvClickInterface.edit(mySign, position)
                }
                btnDelete.setOnClickListener {
                    rvClickInterface.delete(mySign, position)
                }
                root.setOnClickListener {
                    rvClickInterface.itemClick(mySign, position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

}

interface RvClickInterface {
    fun changeLiked(position: Int, mySign: MySign)
    fun edit(mySign: MySign, position: Int)
    fun delete(mySign: MySign, position: Int)
    fun itemClick(mySign: MySign, position: Int)
}