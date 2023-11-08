package com.example.teamprojectapplication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.teamprojectapplication.databinding.FragmentAddDiaryBinding

/**
 * A simple [Fragment] subclass.
 * Use the [AddDiaryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddDiaryFragment : Fragment() {

    var binding : FragmentAddDiaryBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentAddDiaryBinding.inflate(layoutInflater)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnSave?.setOnClickListener {
            val data = Intent()
            data.putExtra("title", binding?.etTitle?.text.toString())
            data.putExtra("contents", binding?.etContents?.text.toString())

        }
    }


}