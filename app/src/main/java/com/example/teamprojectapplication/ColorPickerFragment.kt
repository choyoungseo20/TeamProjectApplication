package com.example.teamprojectapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.teamprojectapplication.databinding.FragmentColorPickerBinding
import com.example.teamprojectapplication.viewmodel.PostsViewModel
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener

class ColorPickerFragment : DialogFragment() {
    val viewModel: PostsViewModel by activityViewModels()
    private var binding: FragmentColorPickerBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("flow", "onCreateView")
        binding = FragmentColorPickerBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("flow", "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        val colorPickerView = binding?.colorPickerView

        colorPickerView?.setColorListener(object : ColorEnvelopeListener {
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
