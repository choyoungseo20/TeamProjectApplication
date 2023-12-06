package com.example.teamprojectapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.teamprojectapplication.databinding.FragmentAddDdayBinding
import com.example.teamprojectapplication.viewmodel.postViewModel

class AddDdayFragment : Fragment() {
    val viewModel: postViewModel by activityViewModels()
    private var binding: FragmentAddDdayBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddDdayBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding?.edtDaydate?.setOnEditorActionListener{_,actionId,_ ->
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                binding?.txtViewdday?.text = viewModel.calDiffernce(binding?.edtDaydate?.text.toString())
                true}else false
        }

        binding?.btnColor?.setOnClickListener {
            ColorPickerFragment().show(parentFragmentManager,"ColorPicker")
        }

        binding?.btnNext?.setOnClickListener{
            viewModel.setTitle(binding?.edtDaytitle?.text.toString())
            viewModel.setDate(binding?.edtDaydate?.text.toString())
            viewModel.setPrivate(binding?.chkPrivate?.isChecked ?: false)
            findNavController().navigate(R.id.action_addDdayFragment_to_addDiaryFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}