package com.example.teamprojectapplication

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.teamprojectapplication.databinding.FragmentAddDdayBinding
import com.example.teamprojectapplication.viewmodel.PostsViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


class AddDdayFragment : Fragment() {
    val viewModel: PostsViewModel by activityViewModels()

    private var binding: FragmentAddDdayBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("flow","oncreateview")
        binding = FragmentAddDdayBinding.inflate(inflater, container, false)
        return binding?.root
    }

    /*private fun showColorPicker() {
        binding?.btnColor?.setOnClickListener {
            ColorPickerDialog
                .Builder(requireContext())  // Fragment 내에서 실행하는 경우 requireContext()를 사용하여 컨텍스트를 참조
                .setTitle("Pick Color")
                .setDefaultColor(Color.parseColor("#ffffff"))  // parseColor() 메서드 대신 Color.parseColor()를 사용하여 색상을 지정
                .setColorShape(ColorShape.CIRCLE)
                .setColorListener { color, colorHex ->
                    // Handle Color Selection
                }
                .show()
        }
    }
     */

    //날짜 차이 계산하기
    @RequiresApi(Build.VERSION_CODES.O)
    private fun calDifference(): String {
        val selectedDateStr = binding?.edtDaydate?.text.toString()
        val selectedDate = LocalDate.parse(selectedDateStr, DateTimeFormatter.ISO_DATE)
        val difference = LocalDate.now().until(selectedDate, ChronoUnit.DAYS).toInt()
        val resOfDifference = if (difference >= 0) "D-$difference" else "D$difference"
        return resOfDifference
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("flow","onviewcreated")

        //날짜 계산하기
        binding?.edtDaydate?.setOnEditorActionListener{_,actionId,_ ->
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                Log.d("func","${calDifference()}") // TODO: 로그 메시지 -> MVVM
                binding?.txtViewdday?.setText(calDifference())
                true }else false
        }


        //네비게이션
        binding?.btnNext?.setOnClickListener {
            viewModel.setPost()
            viewModel.setTitle(binding?.edtDaytitle?.text.toString())
            viewModel.setDate(binding?.edtDaydate?.text.toString())
            viewModel.setDday(binding?.txtViewdday?.text.toString())
            viewModel.setPrivate(binding?.chkPrivate?.isChecked ?: false)
            findNavController().navigate(R.id.action_addDdayFragment_to_addDiaryFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}