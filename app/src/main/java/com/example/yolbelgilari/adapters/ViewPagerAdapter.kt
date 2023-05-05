package com.example.yolbelgilari.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yolbelgilari.databinding.ItemViewPagerBinding
import com.example.yolbelgilari.models.MySign

class ViewPagerAdapter(
    val context: Context,
    val list: ArrayList<MySign>,
    val rvClickInterface: RvClickInterface
) : RecyclerView.Adapter<ViewPagerAdapter.Vh>() {

    lateinit var rvAdapter0: MyRvAdapter
    lateinit var rvAdapter1: MyRvAdapter
    lateinit var rvAdapter2: MyRvAdapter
    lateinit var rvAdapter3: MyRvAdapter

    inner class Vh(private val itemViewPagerBinding: ItemViewPagerBinding) :
        RecyclerView.ViewHolder(itemViewPagerBinding.root) {

        fun onBind(position: Int) {
            val tempList = ArrayList<MySign>()
            for (it in list) {
                if (position == it.type!!) {
                    tempList.add(it)
                }
            }
            when (position) {
                0 -> {
                    rvAdapter0 = MyRvAdapter(context, tempList, rvClickInterface)
                    itemViewPagerBinding.myRv.adapter = rvAdapter0
                }

                1 -> {
                    rvAdapter1 = MyRvAdapter(context, tempList, rvClickInterface)
                    itemViewPagerBinding.myRv.adapter = rvAdapter1
                }

                2 -> {
                    rvAdapter2 = MyRvAdapter(context, tempList, rvClickInterface)
                    itemViewPagerBinding.myRv.adapter = rvAdapter2
                }

                3 -> {
                    rvAdapter3 = MyRvAdapter(context, tempList, rvClickInterface)
                    itemViewPagerBinding.myRv.adapter = rvAdapter3
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemViewPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = 4

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(position)
    }

    fun deleteSign(position: Int, currentItem: Int) {
        when (currentItem) {
            0 -> {
                rvAdapter0.list.removeAt(position)
                rvAdapter0.notifyItemRemoved(position)
                rvAdapter0.notifyItemRangeChanged(0, rvAdapter0.itemCount)
            }

            1 -> {
                rvAdapter1.list.removeAt(position)
                rvAdapter1.notifyItemRemoved(position)
                rvAdapter1.notifyItemRangeChanged(0, rvAdapter1.itemCount)
            }

            2 -> {
                rvAdapter2.list.removeAt(position)
                rvAdapter2.notifyItemRemoved(position)
                rvAdapter2.notifyItemRangeChanged(0, rvAdapter2.itemCount)
            }

            3 -> {
                rvAdapter3.list.removeAt(position)
                rvAdapter3.notifyItemRemoved(position)
                rvAdapter3.notifyItemRangeChanged(0, rvAdapter3.itemCount)
            }
        }
    }
}