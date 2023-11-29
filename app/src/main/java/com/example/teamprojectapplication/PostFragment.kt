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
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.Transaction
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database


class PostFragment : Fragment() {

    var binding: FragmentPostBinding? =null
    val viewModel: PostsViewModel by activityViewModels()


    /*fun likeEvent() {
        val key = viewModel.retriveKey()
        val database = Firebase.database
        val postRef = database.getReference("post")
        val contentUid = contentUidList[position]
        val currentEmail = Firebase.auth?.currentUser?.email

        postRef.child(contentUid).addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val contentDTO = dataSnapshot.getValue
            }
        })



    }
    */
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
            }
        }

        /*
        if (key != null) {
            viewModel.getPostDataByKey(key).observe(viewLifecycleOwner) { post ->
                binding?.fragmentPostsProfileTextview?.text = post.email
                binding?.fragmentPostsExplainTextview?.text = post.text
            }
        }
         */



        binding?.fragmentPostsLikeImageview?.setOnClickListener {
            //likeEvent(position)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}