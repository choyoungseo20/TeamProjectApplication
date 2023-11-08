package com.example.teamprojectapplication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teamprojectapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    val ddays = arrayOf(
        Dday("돌잔치", "2023.11.09", "D-day", 0, 0, false),
        Dday("처음 말한 날", "2023.10.30", "D+10", 27, 5, true),
        Dday("처음 일어선 날", "2023.03.14", "D+240", 42, 12, true),
        Dday("뒤집기 성공", "2022.12.14", "D+330", 0, 0, false),
        Dday("김공주 탄생", "2022.11.09", "D+365", 99, 64, true)
    )
    // 뷰 바인딩을 위한 변수
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 뷰 바인딩 초기화
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        //val intent = Intent(this.context, PostFragment::class.java)

        // RecyclerView 초기화
        binding.recDdays.layoutManager = LinearLayoutManager(requireContext())//현재 Fragment의 컨텍스트로 LinearLayoutManager 초기화
        // 어댑터를 초기화

        val ddayAdapter = DdayAdapter(ddays)
        binding.recDdays.adapter = ddayAdapter
        /*
        ddayAdapter.setOnItemClickListener(object: DdayAdapter.OnItemClickListener{
            override fun onItemClick(view: View, position: Int) {
                startActivity(intent)
            }

        })*/

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //val intent = Intent(this.context, PostFragment::class.java)

    }
}
