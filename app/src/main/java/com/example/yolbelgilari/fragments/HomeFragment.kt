package com.example.yolbelgilari.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.yolbelgilari.AddActivity
import com.example.yolbelgilari.MainActivity
import com.example.yolbelgilari.R
import com.example.yolbelgilari.adapters.RvClickInterface
import com.example.yolbelgilari.adapters.ViewPagerAdapter
import com.example.yolbelgilari.databinding.ActivityMainBinding
import com.example.yolbelgilari.databinding.FragmentHomeBinding
import com.example.yolbelgilari.db.Constants.TYPE_ARRAY
import com.example.yolbelgilari.db.Constants.VIEW_PAGER_ITEM_POSITION
import com.example.yolbelgilari.db.MyDbHelper
import com.example.yolbelgilari.models.MySign
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : Fragment(), RvClickInterface {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var itemAboutFragment: ItemAboutFragment
    private var position = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        myDbHelper = MyDbHelper(requireContext())
        itemAboutFragment = ItemAboutFragment()
        viewPagerAdapter = ViewPagerAdapter(requireContext(), myDbHelper.getAllSign(), this)
        binding.myViewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.myTabLayout, binding.myViewPager) { tab, position ->
            tab.text = TYPE_ARRAY[position]
        }.attach()

        binding.myToolbar.setOnMenuItemClickListener {
            val intent = Intent(requireContext(), AddActivity::class.java)
            intent.putExtra("isEdit", false)
            requireActivity().startActivity(intent)
            true
        }



        return binding.root
    }

    override fun changeLiked(position: Int, mySign: MySign) {
        myDbHelper.editSign(mySign)
    }

    override fun edit(mySign: MySign, position: Int) {
        this.position = position
        val intent = Intent(binding.root.context, AddActivity::class.java)
        intent.putExtra("isEdit", true)
        intent.putExtra("sign", mySign)
        requireActivity().startActivity(intent)
    }

    override fun delete(mySign: MySign, position: Int) {
        viewPagerAdapter.deleteSign(position, binding.myViewPager.currentItem)
        myDbHelper.deleteSign(mySign)
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

    override fun onStop() {
        VIEW_PAGER_ITEM_POSITION = binding.myViewPager.currentItem
        super.onStop()
    }

    override fun onStart() {
        viewPagerAdapter = ViewPagerAdapter(requireContext(), myDbHelper.getAllSign(), this)
        binding.myViewPager.adapter = viewPagerAdapter
        binding.myViewPager.currentItem = VIEW_PAGER_ITEM_POSITION
        super.onStart()
    }
}