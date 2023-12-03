package com.example.teamprojectapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.teamprojectapplication.databinding.FragmentHomeBinding
import com.example.teamprojectapplication.databinding.FragmentPostBinding
import com.example.teamprojectapplication.viewmodel.PostsViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.Transaction
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database


class PostFragment : Fragment() {

    var binding: FragmentPostBinding? =null
    val viewModel: PostsViewModel by activityViewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPostBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val key = viewModel.retriveKey()

        if (key != null) {
            viewModel.posts.observe(viewLifecycleOwner) {
                viewModel.bringEmail(key){ email ->
                    binding?.fragmentPostsProfileTextview?.text = email
                }
                viewModel.bringText(key){ text ->
                    binding?.fragmentPostsExplainTextview?.text = text
                }
                viewModel.observeLikeStatus(key).observe(viewLifecycleOwner) { isLiked ->
                    val likeImageResource = if (isLiked) R.drawable.like else R.drawable.like_border
                    binding?.fragmentPostsLikeImageview?.setImageResource(likeImageResource)
                }
                binding?.fragmentPostsLikeImageview?.setOnClickListener {
                    viewModel.likePost(key)
                }
                viewModel.posts.observe(viewLifecycleOwner) { posts ->
                    val post = posts.find { it.key == key }
                    val likeCount = post?.likeCount ?: 0
                    binding?.fragmentPostsLikeCounterTextview?.text = "좋아요" + likeCount.toString() +"개"
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}