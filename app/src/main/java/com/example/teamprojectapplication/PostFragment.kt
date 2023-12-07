package com.example.teamprojectapplication

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.teamprojectapplication.databinding.FragmentPostBinding
import com.example.teamprojectapplication.Model.Post
import com.example.teamprojectapplication.viewmodel.postViewModel


class PostFragment : Fragment() {

    var binding: FragmentPostBinding? =null
    val viewModel: postViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val key = viewModel.retriveKey()

        val commentAdapter = CommentListAdapter(viewModel.comments)
        binding?.recComments?.layoutManager = LinearLayoutManager(context)


        val userId = viewModel.getCurrUserEmail()
        val userUid = viewModel.getCurrUserUid()

        fun showDeletePostConfirmationDialog() {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("게시물 삭제 확인")
                .setMessage("게시물을 삭제하시겠습니까?")
                .setPositiveButton("예") { _, _ ->
                    viewModel.deletePost(key!!)
                }
                .setNegativeButton("아니요", null)
                .show()
        }

        fun showDeleteCommentConfirmationDialog(comment: Post.Comment) {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("댓글 삭제 확인")
                .setMessage("댓글을 삭제하시겠습니까?")
                .setPositiveButton("예") { _, _ ->
                    viewModel.deleteComment(key!!, comment)
                }
                .setNegativeButton("아니요", null)
                .show()
        }

        commentAdapter.setOnItemClickListener(object : CommentListAdapter.OnItemClickListener{
            override fun onItemClick(view: View, position: Int, commentKey: String, commentEmail: String) {
                viewModel.bringCommentKey(commentKey)
                if(commentEmail == userId){
                    val commentList = viewModel.comments.value
                    val clickedComment = commentList?.getOrNull(position)

                    clickedComment?.let {
                        showDeleteCommentConfirmationDialog(it)
                    }
                }
                else {
                    Toast.makeText(requireContext(), "삭제 권한이 없습니다.", Toast.LENGTH_SHORT).show()
                }

            }

        })


        if (key != null) {
            viewModel.posts.observe(viewLifecycleOwner) { posts ->
                val post = posts.find { it.key == key }
                val likeCount = post?.likeCount ?: 0
                binding?.fragmentPostsLikeCounterTextview?.text = "좋아요 ${likeCount}개"

                if (post?.email == userId) {
                    binding?.btnDeletePost?.visibility = View.VISIBLE
                    binding?.btnDeletePost?.setOnClickListener {
                        showDeletePostConfirmationDialog()
                    }
                } else {
                    binding?.btnDeletePost?.visibility = View.GONE
                }

                viewModel.bringEmail(key){ email ->
                    binding?.fragmentPostsProfileTextview?.text = email
                }

                viewModel.bringText(key){ text ->
                    binding?.fragmentPostsExplainTextview?.text = text
                }

                viewModel.bringImage(key){ imageUrl ->
                    binding?.fragmentPostsImageviewContent?.let { imageView ->
                        Glide.with(requireContext()).load(imageUrl).into(imageView)
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

            binding?.fragmentPostsLikeImageview?.setOnClickListener {
                viewModel.likePost(key)
            }

            viewModel.observeLikeStatus(key, userUid!!).observe(viewLifecycleOwner) { isLiked ->
                if (isLiked) {
                    binding?.fragmentPostsLikeImageview?.setImageResource(R.drawable.like)
                } else {
                    binding?.fragmentPostsLikeImageview?.setImageResource(R.drawable.like_border)
                }
            }

            binding?.commentBtnSend?.setOnClickListener {
                val commentContent = binding?.commentEditContent?.text.toString()

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