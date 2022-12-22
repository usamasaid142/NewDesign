package com.example.newdesign.fragment

import android.app.DialogFragment.STYLE_NO_FRAME
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.DialogFragment.STYLE_NO_FRAME
import com.example.newdesign.R
import com.example.newdesign.databinding.DialogBottomSheetfragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class DialogBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding:DialogBottomSheetfragmentBinding


    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= DialogBottomSheetfragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}