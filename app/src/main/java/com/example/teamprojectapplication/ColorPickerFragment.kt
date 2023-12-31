package com.example.teamprojectapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.teamprojectapplication.databinding.FragmentColorPickerBinding
import com.example.teamprojectapplication.viewmodel.postViewModel
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener

class ColorPickerFragment : DialogFragment() {
    val viewModel: postViewModel by activityViewModels()
    private var binding: FragmentColorPickerBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentColorPickerBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val colorPickerView = binding?.colorPickerView

        colorPickerView?.setColorListener(object : ColorEnvelopeListener {
            // 사용자(fromUser)가 선택한 색상(envelope)
            override fun onColorSelected(envelope: ColorEnvelope?, fromUser: Boolean) {
                val selectedColor = envelope?.color
                selectedColor?.let {viewModel.setColor(selectedColor)}
            }
        })

        binding?.btnColorEnd?.setOnClickListener {
            dismissAllowingStateLoss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
