package com.example.teamprojectapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamprojectapplication.databinding.FragmentPostBinding
import com.example.teamprojectapplication.viewmodel.PostsViewModel
import com.google.firebase.auth.FirebaseAuth


class PostFragment : Fragment() {

    var binding: FragmentPostBinding? =null
    val viewModel: PostsViewModel by activityViewModels()


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

        binding?.recComments?.layoutManager = LinearLayoutManager(context)


        val commentAdapter = CommentListAdapter(viewModel.comments)

        if (key != null) {
            viewModel.posts.observe(viewLifecycleOwner) { posts ->
                val post = posts.find { it.key == key }
                val likeCount = post?.likeCount ?: 0
                binding?.fragmentPostsLikeCounterTextview?.text = "좋아요" + likeCount.toString() +"개"

                viewModel.bringEmail(key){ email ->
                    binding?.fragmentPostsProfileTextview?.text = email
                }
                viewModel.bringText(key){ text ->
                    binding?.fragmentPostsExplainTextview?.text = text
                }
                key?.let { postKey ->
                    viewModel.observeComments(postKey)
                    viewModel.comments.observe(viewLifecycleOwner) {
                        binding?.recComments?.adapter?.notifyDataSetChanged()
                    }
                }
                binding?.recComments?.adapter = commentAdapter
            }

            binding?.fragmentPostsLikeImageview?.setOnClickListener {
                viewModel.likePost(key)
            }
            viewModel.observeLikeStatus(key, FirebaseAuth.getInstance().currentUser?.uid!!).observe(viewLifecycleOwner) { isLiked ->
                if (isLiked) {
                    binding?.fragmentPostsLikeImageview?.setImageResource(R.drawable.like)
                } else {
                    binding?.fragmentPostsLikeImageview?.setImageResource(R.drawable.like_border)
                }
            }
            binding?.commentBtnSend?.setOnClickListener {
                val commentContent = binding?.commentEditContent?.text.toString()
                val userId = FirebaseAuth.getInstance().currentUser?.email
                if (commentContent.isNotEmpty() && userId!!.isNotEmpty()) {
                    val comment = Post.Comment(userId = userId, content = commentContent)
                    viewModel.addComment(key, comment)
                }
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}