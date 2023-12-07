package com.example.teamprojectapplication

import android.app.Activity.RESULT_OK
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.teamprojectapplication.databinding.FragmentAddDiaryBinding
import com.example.teamprojectapplication.viewmodel.postViewModel
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts

class AddDiaryFragment : Fragment() {
    private var binding : FragmentAddDiaryBinding? = null
    val viewModel: postViewModel by activityViewModels()

    var photoUri : Uri? = null
    private val photoResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){//photoResult.launch(intent) 비동기 -> 콜백함수
        if( it.resultCode == RESULT_OK ) {
            photoUri = it.data?.data
            binding?.imgArea?.setImageURI(photoUri)
        }
    }

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

        binding?.imgArea?.setOnClickListener {//apply
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            photoResult.launch(intent)
        }

        binding?.btnSkip?.setOnClickListener {
            viewModel.setPost()
            findNavController().navigate(R.id.action_addDiaryFragment_to_homeFragment)
        }

        binding?.btnSave?.setOnClickListener {
            viewModel.imageUpload(photoUri)
            viewModel.setDiary(binding?.edtContents?.text.toString())
            viewModel.setPost()
            findNavController().navigate(R.id.action_addDiaryFragment_to_homeFragment)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}