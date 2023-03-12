package com.example.newdesign.fragment.signupanddocotorprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.newdesign.R
import com.example.newdesign.databinding.DocotorProfilefragmentBinding
import com.example.newdesign.utils.Constans
import com.example.newdesign.utils.Constans.PROFILE_STATUS
import com.example.newdesign.utils.SpUtil
import com.example.newdesign.viewmodel.SharedDataViewmodel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DocotorProfileFragment : Fragment() {

private lateinit var binding:DocotorProfilefragmentBinding
    private lateinit var navController: NavController
    val sharedDataViewmodel: SharedDataViewmodel by activityViewModels()
    @Inject
    lateinit var sp: SpUtil
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
          getProfileStatus()

    }

    private fun initButton()
    {

//        binding.btnNext.setOnClickListener {
//            findNavController().navigate(R.id.personalInfoFragment)
//        }

        binding.layoutHelp.setOnClickListener {
            val action=DocotorProfileFragmentDirections.actionDocotorProfileFragmentToDialogBottomSheetFragment("help")
            findNavController().navigate(action)
        }

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

    private fun getProfileStatus(){

        sharedDataViewmodel.profileStatus.observe(viewLifecycleOwner, Observer {
            when(it){

                1->{
                    binding.ivPlus.visibility=View.VISIBLE
                    binding.blueimageViewstep1.visibility=View.VISIBLE
                    binding.ivGraystep2.visibility=View.GONE
                    binding.layoutStep2.visibility=View.VISIBLE
                    binding.imageViewstep3.visibility=View.VISIBLE
                    binding.imageViewstep2.visibility=View.GONE
                }
                2->{
                    binding.imageViewstep3.visibility=View.GONE
                    binding.blueimageViewstep3.visibility=View.VISIBLE
                    binding.ivGraystep3.visibility=View.GONE
                    binding.layoutStep3.visibility=View.VISIBLE

                }

            }
        })
            when(sp.getProfileStatus(PROFILE_STATUS)){

                1->{
                    binding.ivPlus.visibility=View.VISIBLE
                    binding.blueimageViewstep1.visibility=View.VISIBLE
                    binding.ivGraystep2.visibility=View.GONE
                    binding.layoutStep2.visibility=View.VISIBLE
                    binding.imageViewstep3.visibility=View.VISIBLE
                    binding.imageViewstep2.visibility=View.GONE
                }
                2->{
                    binding.imageViewstep3.visibility=View.GONE
                    binding.blueimageViewstep3.visibility=View.VISIBLE
                    binding.ivGraystep3.visibility=View.GONE
                    binding.layoutStep3.visibility=View.VISIBLE

                }

            }


    }


}