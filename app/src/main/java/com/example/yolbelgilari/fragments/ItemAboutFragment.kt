package com.example.yolbelgilari.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.yolbelgilari.databinding.FragmentItemAboutBinding
import com.example.yolbelgilari.db.MyDbHelper
import com.example.yolbelgilari.models.MySign


class ItemAboutFragment : Fragment() {

    private lateinit var binding: FragmentItemAboutBinding
    private lateinit var myDbHelper: MyDbHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemAboutBinding.inflate(layoutInflater)
        val sign = arguments?.getSerializable("keySign") as MySign
        myDbHelper = MyDbHelper(requireContext())

        binding.myToolBar.title = sign.name
        binding.imgView.setImageURI(Uri.parse(sign.photoPath!!))
        binding.txtTitle.text = sign.name
        binding.txtAbout.text = sign.about

        binding.myToolBar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return binding.root
    }


}