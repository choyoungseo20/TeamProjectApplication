package com.example.teamprojectapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.text.SimpleDateFormat
import java.util.*
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.teamprojectapplication.databinding.FragmentAddDdayBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class AddDdayFragment : Fragment() {
    // TODO: Rename and change types of parameters
    var binding: FragmentAddDdayBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddDdayBinding.inflate(inflater)


        /* binding.daydate.setOnFocusChangeListener { _, hasFocus ->
             if (!hasFocus) {
                 val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                 val selectedDate = sdf.parse(binding.daydate.text.toString())
                 val currentDate = Date()

                 if (selectedDate != null) {
                     val difference = currentDate.time - selectedDate.time
                     val differenceInDays = (difference / (1000 * 60 * 60 * 24)).toInt()

                     binding.dday.text = "날짜 차이: $differenceInDays 일"
                 } else {
                     binding.dday.text = "올바른 날짜를 입력해주세요."
                 }
             }
         }
 */
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnNext?.setOnClickListener{
            findNavController().navigate(R.id.action_addDdayFragment_to_addDiaryFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}