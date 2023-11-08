package com.example.teamprojectapplication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.teamprojectapplication.databinding.FragmentAddDdayBinding
import com.example.teamprojectapplication.databinding.FragmentAddDiaryBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * A simple [Fragment] subclass.
 * Use the [AddDiaryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddDiaryFragment : Fragment() {

    var binding : FragmentAddDiaryBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddDiaryBinding.inflate(inflater)

        return binding?.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnSkip?.setOnClickListener {
            findNavController().navigate(R.id.action_addDiaryFragment_to_homeFragment)
            //번들 이용해서 앞의 Dday 데이터만 home으로 가지고 감
        }

        binding?.btnSave?.setOnClickListener {
            val data = Intent()
            data.putExtra("contents", binding?.etContents?.text.toString())
            //번들 이용? 앞의 Dday 데이터와 일기 데이터를 가지고 home으로 감
            findNavController().navigate(R.id.action_addDiaryFragment_to_homeFragment)
        }
    }


}