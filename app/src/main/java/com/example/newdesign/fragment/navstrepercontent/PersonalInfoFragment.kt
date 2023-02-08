package com.example.newdesign.fragment.navstrepercontent

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getDrawable
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.example.newdesign.R
import com.example.newdesign.databinding.PersonalinfofragmentBinding
import com.example.newdesign.fragment.loginandforgetpassword.LoginFragment
import com.example.newdesign.utils.*
import com.example.newdesign.utils.Constans.NameAR
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class PersonalInfoFragment : Fragment() {

     private lateinit var binding:PersonalinfofragmentBinding
    private lateinit var imageFile: File

    @Inject
    lateinit var sp: SpUtil

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= PersonalinfofragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initbutton()

    }


  private fun initbutton()
    {



        binding.etFullNameEn.setText(sp.getUser()?.name.toString())
        binding.etFullNameAR.setText(sp.getUserNameInArabic(NameAR))

        val fullNameEn = binding.etFullNameEn.text.toString()
        val fullNameAr = binding.etFullNameAR.text.toString()
        val mobileNumber = binding.etPhoneNumber.text.toString()
        val email = binding.etEmail.text.toString()
        val gender = binding.etGender.text.toString()
        val birthDate = binding.etDateOfbirth.text.toString()
        val nationality = binding.etNationality.text.toString()

//        if(fullNameEn.trim().isEmpty() || fullNameAr.trim().isEmpty()|| mobileNumber.trim().isEmpty()
//            ||  email.trim().isEmpty()|| gender.trim().isEmpty() ||birthDate.trim().isEmpty() ||nationality.trim().isEmpty()){
//
//
//        }else{
//            binding.btnNext.isEnabled = true
//            binding.btnNext.background= getDrawable(requireContext(),R.color.red)
//        }
        binding.btnNext.setOnClickListener {
        //    findNavController().navigate(R.id.specialtyFragment)


            if (isvalidateFeilds(
                    fullNameEn,
                    fullNameAr,
                    mobileNumber,
                    email,
                    gender,
                    birthDate,
                    nationality
                )
            ) {
              //  user = CreateUser(email, fullNameEn, password, mobileNumber, 2)
              //  viewmodel.registerUser("En", user!!)
            }else{

            }


        }

        binding.layoutUploadImage.setOnClickListener {
            selectPic(it)
        }


        binding.ivArrowdown.setOnClickListener {
            val action=PersonalInfoFragmentDirections.actionPersonalInfoFragment2ToDialogBottomSheetFragment("gender")
            findNavController().navigate(action)
        }
        binding.ivDate.setOnClickListener {
            val action=PersonalInfoFragmentDirections.actionPersonalInfoFragment2ToDialogBottomSheetFragment("date")
            findNavController().navigate(action)
        }
        binding.ivNationality.setOnClickListener {
            val action=PersonalInfoFragmentDirections.actionPersonalInfoFragment2ToDialogBottomSheetFragment("Nationality")
            findNavController().navigate(action)
        }


    }



    private fun isvalidateFeilds(
        fullNameEn: String,
        fullNameAr: String,
        mobileNumber: String,
        email: String,
        gender: String,
        dateOfBirth:String,
        nationality:String
    ): Boolean {

            var isValid=true

        if (fullNameEn.trim().isNullOrEmpty()) {
            binding.etFullNameEn.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_error,
                0
            )
            binding.tvFullNameError.text = getString(R.string.required)
            binding.tvFullNameError.visibility = View.VISIBLE
            binding.textinputFullNameEn.setBackgroundResource(R.drawable.bg_edittext_error)
            isValid = false
        } else if (fullNameEn.trim().split(" ").size < 3) {
            binding.tvFullNameError.visibility = View.VISIBLE
            binding.tvFullNameError.text = getString(R.string.Enter_fullName_english)
            binding.etFullNameEn.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_error,
                0
            )
            binding.textinputFullNameEn.setBackgroundResource(R.drawable.bg_edittext_error)
            isValid = false

        } else {
            binding.tvFullNameError.visibility = View.GONE
            binding.textinputFullNameEn.setBackgroundResource(R.drawable.bg_edittext)
            binding.etFullNameEn.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            isValid = true
        }


        if (fullNameAr.trim().isNullOrEmpty()) {
            binding.etFullNameAR.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_error,
                0
            )
            binding.tvFullNameARError.text = getString(R.string.required)
            binding.tvFullNameARError.visibility = View.VISIBLE
            binding.textinputFullNameAr.setBackgroundResource(R.drawable.bg_edittext_error)

            isValid = false
        } else if (fullNameAr.trim().split(" ").size < 3) {
            binding.tvFullNameARError.text = getString(R.string.Enter_fullName_Arabic)
            binding.tvFullNameARError.visibility = View.VISIBLE
            binding.etFullNameAR.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_error,
                0
            )
            binding.textinputFullNameAr.setBackgroundResource(R.drawable.bg_edittext_error)
            isValid = false

        } else {
            binding.tvFullNameARError.visibility = View.GONE
            binding.textinputFullNameAr.setBackgroundResource(R.drawable.bg_edittext)
            binding.etFullNameAR.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            isValid = true
        }

        if (mobileNumber.trim().isEmpty()) {
            binding.tvPohneError.text = getString(R.string.required)
            binding.tvPohneError.visibility = View.VISIBLE
            binding.textinputPhoneNumber.setBackgroundResource(R.drawable.bg_edittext_error)
            binding.etPhoneNumber.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_error,
                0
            )
            isValid = false
        } else {
            binding.tvPohneError.visibility = View.GONE
            binding.textinputPhoneNumber.setBackgroundResource(R.drawable.bg_edittext)
            binding.etPhoneNumber.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

            isValid = true
        }



        if (email.trim().isEmpty()) {
            binding.tvEmailError.text = getString(R.string.required)
            binding.tvEmailError.visibility = View.VISIBLE
            binding.textinputEmail.setBackgroundResource(R.drawable.bg_edittext_error)
            binding.etEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error, 0)
            isValid = false
        } else if (!email.isValidEmail()) {
            binding.tvEmailError.text = getString(R.string.match_email)
            binding.tvEmailError.visibility = View.VISIBLE
            binding.textinputEmail.setBackgroundResource(R.drawable.bg_edittext_error)
            binding.etEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error, 0)
            isValid = false
        } else {
            binding.tvEmailError.visibility = View.GONE
            binding.textinputEmail.setBackgroundResource(R.drawable.bg_edittext)
            binding.etEmail.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

            isValid = true
        }
        if (gender.trim().isEmpty()) {
            binding.tvGenderError.text = getString(R.string.required)
            binding.tvGenderError.visibility = View.VISIBLE
            binding.layoutGender.setBackgroundResource(R.drawable.bg_edittext_error)
            binding.etGender.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error, 0)
            isValid = false
        } else {
            binding.tvGenderError.visibility = View.GONE
            binding.layoutGender.setBackgroundResource(R.drawable.bg_edittext)
            binding.etGender.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

            isValid = true
        }
        if (dateOfBirth.trim().isEmpty()) {
            binding.tvDateOfBirthError.text = getString(R.string.required)
            binding.tvDateOfBirthError.visibility = View.VISIBLE
            binding.layoutDateOfbirth.setBackgroundResource(R.drawable.bg_edittext_error)
            binding.etDateOfbirth.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error, 0)
            isValid = false
        } else {
            binding.tvDateOfBirthError.visibility = View.GONE
            binding.layoutDateOfbirth.setBackgroundResource(R.drawable.bg_edittext)
            binding.etDateOfbirth.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

            isValid = true
        }

        if (nationality.trim().isEmpty()) {
            binding.tvNationaltyError.text = getString(R.string.required)
            binding.tvNationaltyError.visibility = View.VISIBLE
            binding.layoutNationality.setBackgroundResource(R.drawable.bg_edittext_error)
            binding.etNationality.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error, 0)
            isValid = false
        } else {
            binding.tvNationaltyError.visibility = View.GONE
            binding.layoutNationality.setBackgroundResource(R.drawable.bg_edittext)
            binding.etNationality.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

            isValid = true
        }


        return isValid

    }


    fun String.isValidEmail() =
        !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()


    private fun selectPic(view: View) {
        val pictureImageDialog = AlertDialog.Builder(requireContext())
        if (view.id == binding.layoutUploadImage.id) {
            pictureImageDialog.setTitle("Select Action")
            val pictureImageItem = arrayOf("Select From Camera", "Select From Gallery", "Cancel")
            pictureImageDialog.setItems(pictureImageItem) { dialog, which ->
                when (which) {
                    0 -> checkPermissionFromCamera()
                    1 -> checkPicPermission()
                    2 -> dialog.dismiss()
                }
            }
        }
        pictureImageDialog.show()

    }
    private fun checkPermissionFromCamera() {

        Dexter.withContext(requireActivity())
            .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
            .withListener(
                object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        report?.let {

                            if (report.areAllPermissionsGranted()) {
                                openCamera()
                            }
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: MutableList<PermissionRequest>?,
                        p1: PermissionToken?
                    ) {

                        requireActivity().showRationalePermission()

                    }

                }
            ).onSameThread().check()
    }
    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraOpening.launch(cameraIntent)
    }
    private fun getPicFromGallery() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        galleryOpening.launch(intent)
    }
    private var galleryOpening =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                if (data?.clipData != null) {
                    val count = data.clipData?.itemCount
                    for (i in 0 until count!!) {
                        val imageUri =
                            data.clipData?.getItemAt(i)?.uri ?: return@registerForActivityResult
                        val imageStream = imageUri.let {
                            requireActivity().contentResolver.openInputStream(it)
                        }
                        val selectedImage = BitmapFactory.decodeStream(imageStream)
                        val resizedBitmap = selectedImage.resizePic(1000)
                        val file = resizedBitmap.toFile(requireContext(), 20.getRandomString())
                        binding.ivDoctorProfile.load(file) {
                            crossfade(true)
                            crossfade(1000)
                            transformations(CircleCropTransformation())
                        }
                    }
                } else if (data?.data != null) {
                    val imageUri = data.data ?: return@registerForActivityResult
                    val imageStream = imageUri.let {
                        requireActivity().contentResolver.openInputStream(it)
                    }
                    val selectedImage = BitmapFactory.decodeStream(imageStream)
                    val resizedBitmap = selectedImage.resizePic(1000)
                    val file = resizedBitmap.toFile(requireContext(), 20.getRandomString())
                    imageFile=file
                    binding.ivDoctorProfile.load(file) {
                        crossfade(true)
                        crossfade(1000)
                        transformations(CircleCropTransformation())
                    }
                }
            }
        }
    private var cameraOpening =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                data?.let {
                    val bitmap = data.extras?.get("data") as Bitmap
                    val resizedBitmap = bitmap.resizePic(2000)
                    val file = resizedBitmap.toFile(requireContext(), 20.getRandomString())
                    imageFile=file
                    binding.ivDoctorProfile.load(bitmap) {
                        crossfade(true)
                        crossfade(1000)
                        transformations(CircleCropTransformation())
                    }
                }
            }
        }

    private fun checkPicPermission() {
        Dexter.withContext(requireContext())
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {

                    getPicFromGallery()
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {

                    Toast.makeText(
                        requireContext(),
                        "you have denied permission for select image",
                        Toast.LENGTH_SHORT
                    ).show()
                    requireActivity().showRationalePermission()

                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    requireActivity().showRationalePermission()

                }

            }).onSameThread().check()
    }

}