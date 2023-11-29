package com.example.teamprojectapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamprojectapplication.databinding.FragmentSearchBinding
import com.example.teamprojectapplication.viewmodel.PostsViewModel

class SearchFragment : Fragment() {
    val viewModel : PostsViewModel by activityViewModels()

    private var binding : FragmentSearchBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    /*
        viewModel.posts.observe(viewLifecycleOwner){
            binding?.recSearchPosts?.adapter?.notifyDataSetChanged()
        }




        binding?.recSearchPosts?.layoutManager = LinearLayoutManager(context)
        binding?.recSearchPosts?.adapter = SearchPostAdapter(viewModel.nonPrivatePosts)



     */

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_searchFragment_to_communityFragment)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }
}