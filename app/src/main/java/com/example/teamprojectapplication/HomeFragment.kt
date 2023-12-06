package com.example.teamprojectapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamprojectapplication.databinding.FragmentHomeBinding
import com.example.teamprojectapplication.viewmodel.postViewModel

class HomeFragment : Fragment() {

    var binding: FragmentHomeBinding? = null
    val viewModel: postViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 뷰 바인딩 초기화
        binding = FragmentHomeBinding.inflate(layoutInflater)


        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // RecyclerView 초기화
        //현재 Fragment의 컨텍스트로 LinearLayoutManager 초기화
        binding?.recDdays?.layoutManager = LinearLayoutManager(context)
        // 어댑터를 초기화

        viewModel.myPosts.observe(viewLifecycleOwner) {
            binding?.recDdays?.adapter?.notifyDataSetChanged()
        }


        binding?.recDdays?.setHasFixedSize(true)

        val adapter = DdayListAdapter(viewModel.myPosts)
        adapter.setOnItemClickListener(object : DdayListAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int, key: String) {
                viewModel.bringKey(key)
                findNavController().navigate(R.id.action_homeFragment_to_postFragment)
            }
        })
        binding?.recDdays?.adapter = adapter


    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}