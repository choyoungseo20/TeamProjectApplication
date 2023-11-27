package com.example.teamprojectapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamprojectapplication.databinding.FragmentCommunityBinding
import com.example.teamprojectapplication.viewmodel.PostsViewModel


class CommunityFragment : Fragment() {

    var binding: FragmentCommunityBinding? = null
    val viewModel: PostsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommunityBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // RecyclerView 초기화
        //현재 Fragment의 컨텍스트로 LinearLayoutManager 초기화

        // 어댑터를 초기화
        viewModel.posts.observe(viewLifecycleOwner) {
            binding?.recPosts?.adapter?.notifyDataSetChanged()
        }

        val adapter = PostListAdapter(viewModel.posts)
        adapter.setOnItemClickListener(object : PostListAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                viewModel.findKey()
                findNavController().navigate(R.id.action_communityFragment_to_postFragment)
            }

        })
        binding?.recPosts?.adapter = adapter
        binding?.recPosts?.layoutManager = LinearLayoutManager(context)
        binding?.recPosts?.setHasFixedSize(true)


        binding?.btnGo2?.setOnClickListener {
            findNavController().navigate(R.id.action_communityFragment_to_searchFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}