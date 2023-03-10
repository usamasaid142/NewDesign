package com.example.newdesign.fragment.loginandforgetpassword

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newdesign.R
import com.example.newdesign.databinding.OtpfragmentBinding
import com.example.newdesign.utils.Resource
import com.example.newdesign.viewmodel.RegisterViewmodel
import com.karumi.dexter.Dexter.withContext
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.internal.format

@AndroidEntryPoint
class OtpFragment : Fragment() {

    private lateinit var binding: OtpfragmentBinding
    private val args: OtpFragmentArgs by navArgs()
    private val viewmodel: RegisterViewmodel by viewModels()
    private var otpCode: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = OtpfragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (args.responsemodel?.code.toString().isNullOrEmpty()) {
            binding.tvVerification.text =
                format(getString(R.string.verification_code) + args.userforgetpassword?.phone)
        } else {
            binding.tvVerification.text =
                format(getString(R.string.verification_code) + args.user?.phone)
        }

        initButton()
        setupFocusEdittext()
        callBack()
        otpCallBack()
        timer()
    }

    private fun initButton() {

        binding.ivArrow.setOnClickListener {
            findNavController().navigate(R.id.forgetPasswordFragment)
        }
        binding.layoutHelp.setOnClickListener {
            findNavController().navigate(R.id.dialogBottomSheetFragment)
        }

        binding.btnSend.setOnClickListener {

            if (args.responsemodel?.code.toString() == getCodeFromEdittext()) {
                args.user?.let { it1 -> viewmodel.createuser("en", it1) }
                findNavController().navigate(R.id.homeFragment)
                Toast.makeText(requireContext(), "Verified", Toast.LENGTH_SHORT).show()
            } else if (otpCode == getCodeFromEdittext()) {
                args.user?.let { it1 -> viewmodel.createuser("en", it1) }
                findNavController().navigate(R.id.homeFragment)
                Toast.makeText(requireContext(), "Verified", Toast.LENGTH_SHORT).show()
            } else if (args.userforgetpassword?.code.toString() == getCodeFromEdittext()) {
                args.user?.let { it1 -> viewmodel.createuser("en", it1) }
                findNavController().navigate(R.id.changepasswordFragment)
                Toast.makeText(requireContext(), "Verified", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "invalid code", Toast.LENGTH_SHORT).show()
            }

        }

        binding.tvResendCode.setOnClickListener {
            binding.tvResendCode.visibility = View.GONE
            args.user?.let { it1 -> viewmodel.Sentotp("en", it1) }
        }


    }

    private fun setupFocusEdittext() {

        binding.etOtpFirst.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    binding.etOtpsecond.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        binding.etOtpsecond.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    binding.etOtpthird.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        binding.etOtpthird.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    binding.etOtpforth.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        binding.etOtpforth.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    binding.etOtpforth.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

    }

    private fun callBack() {
        viewmodel.createResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    //     showprogtessbar()
                }

                is Resource.sucess -> {
                    //   hideprogressbar()
                    it?.data?.let { response ->

                    }
                }
                is Resource.Error -> {
                    //   hideprogressbar()
                    it.message?.let { error ->
                        Log.e("error", error)
                    }
                }

            }
        })

    }

    private fun otpCallBack() {
        viewmodel.otpResponse.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Loading -> {
                    showprogtessbar()
                }

                is Resource.sucess -> {
                    hideprogressbar()
                    timer()
                    it?.data?.let { response ->
                        otpCode = response.data.code.toString()
                    }
                }
                is Resource.Error -> {
                    hideprogressbar()
                    it.message?.let { error ->
                        Log.e("error", error)
                    }
                }

            }

        })

    }

    private fun showprogtessbar() {
        binding.progressBar.visibility = View.VISIBLE

    }

    private fun hideprogressbar() {
        binding.progressBar.visibility = View.GONE
    }

    private fun getCodeFromEdittext(): String {

        if (binding.etOtpFirst.toString().trim().isEmpty() || binding.etOtpsecond.toString().trim()
                .isEmpty() ||
            binding.etOtpthird.toString().trim().isEmpty() || binding.etOtpforth.toString().trim()
                .isEmpty()
        ) {
            Toast.makeText(requireContext(), "please enter valid code", Toast.LENGTH_SHORT).show()
        }

        val etOtpCode = binding.etOtpFirst.text.toString() + binding.etOtpsecond.text.toString() +
                binding.etOtpthird.text.toString() + binding.etOtpforth.text.toString()

        return etOtpCode
    }

    fun timer() {
        object : CountDownTimer(120000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                binding.tvCounter.setText("(${millisUntilFinished / 1000})")

            }
            override fun onFinish() {
                binding.tvCounter.setText("(0)")
                binding.tvResendCode.visibility = View.VISIBLE
            }
        }.start()
    }

}