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
import java.text.SimpleDateFormat
import java.util.Date
import android.content.Intent
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.storage.FirebaseStorage

class AddDiaryFragment : Fragment() {

    var binding : FragmentAddDiaryBinding? = null
    val viewModel: postViewModel by activityViewModels()



    var photoUri : Uri? = null
    var photoResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_OK){
            photoUri = it.data?.data

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

        binding?.imgArea?.setOnClickListener {

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            photoResult.launch(intent)



            /*viewModel.setOnPhotoSelectedListener { uri ->
                binding?.imgArea?.setImageURI(uri)
            }

             */

            /*val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            registerForActivityResult.launch(intent)

             */
        }

        binding?.btnUpload?.setOnClickListener {
            binding?.imgArea?.setImageURI(photoUri)
            imageUpload()
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
            viewModel.setText(binding?.edtContents?.text.toString())
            viewModel.setPost()
            findNavController().navigate(R.id.action_addDiaryFragment_to_homeFragment)
        }
    }

    /*
    private val registerForActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                AppCompatActivity.RESULT_OK -> {
                    val uri: Uri? = result.data?.data
                    uri?.let {
                        binding?.imgArea?.setImageURI(uri)
                    }
                }
            }
        }

     */

    private fun imageUpload() {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "IMAGE ${timestamp}.png"

        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference.child("image")

        val storagePath = storageRef.child(imageFileName)

        photoUri?.let { uri ->
            storagePath.putFile(uri).continueWithTask {
                return@continueWithTask storagePath.downloadUrl
            }.addOnCompleteListener { downloadUrl ->
                viewModel.setImageUrl(downloadUrl.result.toString())
                Toast.makeText(requireActivity(), "업로드 성공 : ${downloadUrl.result}", Toast.LENGTH_LONG).show()

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