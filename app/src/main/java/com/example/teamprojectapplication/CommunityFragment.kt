package com.example.teamprojectapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.teamprojectapplication.databinding.FragmentCommunityBinding


class CommunityFragment : Fragment() {

    var binding : FragmentCommunityBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommunityBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnGo1?.setOnClickListener {
            findNavController().navigate(R.id.action_communityFragment_to_postFragment)
        }
        binding?.btnGo2?.setOnClickListener {
            findNavController().navigate(R.id.action_communityFragment_to_searchFragment)
        }
    }


}