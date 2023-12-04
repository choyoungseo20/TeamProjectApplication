package com.example.teamprojectapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamprojectapplication.databinding.FragmentCommunityBinding
import com.example.teamprojectapplication.viewmodel.PostsViewModel


class CommunityFragment : Fragment() {

    var binding: FragmentCommunityBinding? = null
    val viewModel: PostsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommunityBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.nonPrivatePosts.observe(viewLifecycleOwner) {
            binding?.recPosts?.adapter?.notifyDataSetChanged()
        }

        binding?.edtSearch?.setOnEditorActionListener{_,actionId,_ ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                viewModel.searchWord(binding?.edtSearch?.text.toString())
                findNavController().navigate(R.id.action_communityFragment_to_searchFragment)
                true}else false
        }

        val adapter = PostListAdapter(viewModel.nonPrivatePosts)
        adapter.setOnItemClickListener(object : PostListAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int, key: String) {
                viewModel.bringKey(key)
                findNavController().navigate(R.id.action_communityFragment_to_postFragment)
            }

        })
        binding?.recPosts?.adapter = adapter
        binding?.recPosts?.layoutManager = LinearLayoutManager(context)
        binding?.recPosts?.setHasFixedSize(true)




    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}