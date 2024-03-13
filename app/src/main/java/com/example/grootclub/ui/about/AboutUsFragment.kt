package com.example.grootclub.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.grootclub.databinding.FragmentSlideshowBinding

class AboutUsFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initView()
        setOnClicks()
        return root
    }

    private fun initView() {}

    private fun setOnClicks() {


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}