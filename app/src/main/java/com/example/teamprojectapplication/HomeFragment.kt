package com.example.teamprojectapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamprojectapplication.databinding.FragmentHomeBinding
import com.example.teamprojectapplication.viewmodel.PostsViewModel

class HomeFragment : Fragment() {

    /*val exddays = arrayOf(
        Post("돌잔치", "2023.11.09", "D-day", 0, 0, false),
        Post("처음 말한 날", "2023.10.30", "D+10", 27, 5, true),
        Post("처음 일어선 날", "2023.03.14", "D+240", 42, 12, true),
        Post("뒤집기 성공", "2022.12.14", "D+330", 0, 0, false),
        Post("김공주 탄생", "2022.11.09", "D+365", 99, 64, true)
    )

     */

    var binding: FragmentHomeBinding? = null
    val viewModel: PostsViewModel by viewModels()

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
        //val intent = Intent(this.context, PostFragment::class.java)

        // RecyclerView 초기화
        //현재 Fragment의 컨텍스트로 LinearLayoutManager 초기화
        binding?.recDdays?.layoutManager = LinearLayoutManager(requireContext())
        // 어댑터를 초기화

        binding?.recDdays?.adapter = DdayListAdapter(viewModel.ddays)

        DdayListAdapter(viewModel.ddays).setOnItemClickListener(object: DdayListAdapter.OnItemClickListener{
            override fun onItemClick(view: View, position: Int) {
                findNavController().navigate(R.id.action_homeFragment_to_postFragment)
            }

        })

    }

}
