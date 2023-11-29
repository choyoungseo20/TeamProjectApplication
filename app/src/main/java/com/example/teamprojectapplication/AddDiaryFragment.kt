package com.example.teamprojectapplication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.teamprojectapplication.databinding.FragmentAddDdayBinding
import com.example.teamprojectapplication.databinding.FragmentAddDiaryBinding
import com.example.teamprojectapplication.viewmodel.PostsViewModel
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
    val viewModel: PostsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddDiaryBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.posts.observe(viewLifecycleOwner) {

        }

        binding?.btnUpload?.setOnClickListener {
            binding?.imgArea
        }

        binding?.btnSkip?.setOnClickListener {
            findNavController().navigate(R.id.action_addDiaryFragment_to_homeFragment)
        }

        binding?.btnSave?.setOnClickListener {
            viewModel.setText(binding?.edtContents?.text.toString())
            findNavController().navigate(R.id.action_addDiaryFragment_to_homeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }



}