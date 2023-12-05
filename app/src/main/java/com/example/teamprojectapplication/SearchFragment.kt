package com.example.teamprojectapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
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
        viewModel.observeSearchWord().observe(viewLifecycleOwner){
            binding?.edtSearch?.setText(it)
        }
        viewModel.searchPosts.observe(viewLifecycleOwner){
            binding?.recSearchPosts?.adapter?.notifyDataSetChanged()
        }


        val adapter = SearchPostAdapter(viewModel.searchPosts)
        adapter.setOnItemClickListener(object : SearchPostAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int, key: String) {
                viewModel.bringKey(key)
                findNavController().navigate(R.id.action_searchFragment_to_postFragment)
            }
        })
        binding?.recSearchPosts?.adapter = adapter
        binding?.recSearchPosts?.layoutManager = LinearLayoutManager(context)
        binding?.recSearchPosts?.setHasFixedSize(true)

        binding?.edtSearch?.setOnEditorActionListener{_,actionId,_ ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                viewModel.searchWord(binding?.edtSearch?.text.toString())
                true}else false
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}