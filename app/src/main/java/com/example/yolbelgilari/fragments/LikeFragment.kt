package com.example.yolbelgilari.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.yolbelgilari.AddActivity
import com.example.yolbelgilari.R
import com.example.yolbelgilari.adapters.MyRvAdapter
import com.example.yolbelgilari.adapters.RvClickInterface
import com.example.yolbelgilari.databinding.FragmentLikeBinding
import com.example.yolbelgilari.db.Constants
import com.example.yolbelgilari.db.MyDbHelper
import com.example.yolbelgilari.models.MySign
import java.io.File

class LikeFragment : Fragment(), RvClickInterface {

    private lateinit var binding: FragmentLikeBinding
    private lateinit var itemAboutFragment: ItemAboutFragment
    private lateinit var myRvAdapter: MyRvAdapter
    private lateinit var myDbHelper: MyDbHelper
    private var position = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLikeBinding.inflate(layoutInflater)

        itemAboutFragment = ItemAboutFragment()
        myDbHelper = MyDbHelper(requireContext())
        myRvAdapter = MyRvAdapter(requireContext(), myDbHelper.getLikedSigns(), this)

        binding.myLikedRv.adapter = myRvAdapter

        return binding.root
    }

    override fun changeLiked(position: Int, mySign: MySign) {
        myDbHelper.editSign(mySign)
        myRvAdapter.list.removeAt(position)
        myRvAdapter.notifyItemRemoved(position)
    }

    override fun edit(mySign: MySign, position: Int) {
        val intent = Intent(requireContext(), AddActivity::class.java)
        intent.putExtra("isEdit", true)
        intent.putExtra("sign", mySign)
        requireContext().startActivity(intent)
        this.position = position
    }

    override fun delete(mySign: MySign, position: Int) {
        myDbHelper.deleteSign(mySign)
        myRvAdapter.list.removeAt(position)
        myRvAdapter.notifyItemRemoved(position)
        myRvAdapter.notifyItemRangeChanged(0, myRvAdapter.itemCount)

        val file = File(requireActivity().filesDir,"${mySign.id}.jpg")
        file.delete()
    }

    override fun itemClick(mySign: MySign, position: Int) {
        val bundle = Bundle()
        bundle.putSerializable("keySign", mySign)
        itemAboutFragment.arguments = bundle
        parentFragmentManager.beginTransaction()
            .replace(R.id.my_container, itemAboutFragment)
            .addToBackStack(itemAboutFragment.toString())
            .commit()
    }

    override fun onStart() {
        if (Constants.SIGN_EDITED) {
            Constants.SIGN_EDITED = false
            myRvAdapter.list[position] = Constants.TEMP_SIGN
            myRvAdapter.notifyItemChanged(position)
        }
        super.onStart()

    }


}