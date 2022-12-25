package com.example.newdesign.fragment.signupanddocotorprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import com.example.newdesign.databinding.DocotorProfilefragmentBinding


class DocotorProfileFragment : Fragment() {

private lateinit var binding:DocotorProfilefragmentBinding
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= DocotorProfilefragmentBinding.inflate(layoutInflater,container,false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        setupNavigationBottom()
          initButton()


    }

    private fun initButton()
    {

//        binding.btnNext.setOnClickListener {
//            findNavController().navigate(R.id.personalInfoFragment)
//        }

    }

//    private fun setupNavigationBottom(){
//
//
//    }
private fun getVisibleFragment(): Fragment? {
    val fragmentManager: FragmentManager = requireActivity().getSupportFragmentManager()
    val fragments: List<Fragment> = fragmentManager.getFragments()
    for (fragment in fragments) {
        if (fragment != null && fragment.isVisible) return fragment
    }
    return null
}




}