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
                binding?.commentBtnSend?.setOnClickListener {
                    val commentContent = binding?.commentEditContent?.text.toString()
                    val userId = FirebaseAuth.getInstance().currentUser?.email // 또는 다른 방식으로 현재 사용자 ID를 가져옴

                    if (commentContent.isNotEmpty() && userId!!.isNotEmpty()) {
                        val comment = Post.Comment(userId = userId, content = commentContent)
                        viewModel.addComment(key, comment)
                    }
                }
                key?.let { postKey ->
                    viewModel.observeComments(postKey)
                    viewModel.comments.observe(viewLifecycleOwner) {
                        binding?.recComments?.adapter?.notifyDataSetChanged()
                    }
                }
                binding?.recComments?.adapter = commentAdapter
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}