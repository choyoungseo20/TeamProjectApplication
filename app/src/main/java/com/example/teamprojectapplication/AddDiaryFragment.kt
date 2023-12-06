package com.example.teamprojectapplication

import android.net.Uri
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
import android.content.Intent
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage

class AddDiaryFragment : Fragment() {

    var binding : FragmentAddDiaryBinding? = null
    val viewModel: PostsViewModel by activityViewModels()

    private lateinit var uri: Uri

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

        binding?.imgArea?.setOnClickListener {
            //viewModel.choosePhoto()
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            registerForActivityResult.launch(intent)
        }

        /*
        binding?.btnUpload?.setOnClickListener {
            Log.d("AddDiary", "URI value: $uri") // Log the URI value
            viewModel.imageUpload(uri)
            //imageUpload(uri)
        }

         */

        binding?.btnSkip?.setOnClickListener {
            viewModel.setPost()
            findNavController().navigate(R.id.action_addDiaryFragment_to_homeFragment)
        }

        binding?.btnSave?.setOnClickListener {
            //viewModel.setImageUrl(binding?.imgArea?)
            viewModel.setText(binding?.edtContents?.text.toString())
            viewModel.setPost()
            findNavController().navigate(R.id.action_addDiaryFragment_to_homeFragment)
        }
    }

    private val registerForActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                AppCompatActivity.RESULT_OK -> {
                    uri = result.data?.data!!
                    binding?.imgArea?.setImageURI(uri)
                }
            }
        }

    /*private fun imageUpload(uri: Uri) {
        val storage = FirebaseStorage.getInstance()
        // storage 참조
        val storageRef = storage.reference.child("image")
        // storage에 저장할 파일명 선언
        val fileName = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
        val mountainsRef = storageRef.child("${fileName}.png")
        val uploadTask = mountainsRef.putFile(uri)
        uploadTask.addOnSuccessListener { taskSnapshot ->
            // 파일 업로드 성공
            Toast.makeText(requireActivity(), "사진 업로드 성공", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            // 파일 업로드 실패
            Toast.makeText(requireActivity(), "사진 업로드 실패", Toast.LENGTH_SHORT).show()
        }
    }

     */

    /*
    private fun imageDownload() {
        val downloadTask = mountainsRef.downloadUrl
        downloadTask.addOnSuccessListener { uri ->
            // 파일 다운로드 성공
            // Glide를 사용하여 이미지를 ImageView에 직접 가져오기
            Glide.with(requireContext()).load(uri).into(binding?.imgArea)
        }.addOnFailureListener {
            // 파일 다운로드 실패
        }
    }

     */

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }



}