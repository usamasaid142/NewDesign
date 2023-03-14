package com.example.newdesign.fragment.signupanddocotorprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.findFragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
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
  //  private lateinit var navController: NavController
    val sharedDataViewmodel: SharedDataViewmodel by activityViewModels()
    var profileStatus=0
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

         //setupNavigationBottom()
          initButton()
          getProfileStatus()

    }

    private fun initButton()
    {

        binding.tvSkip.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }

        binding.layoutHelp.setOnClickListener {
            val action=DocotorProfileFragmentDirections.actionDocotorProfileFragmentToDialogBottomSheetFragment("help")
            findNavController().navigate(action)
        }

    }

    private fun setupNavigationBottom(){
//        val navHostFragment =
//            requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentnav_Streper) as NavHostFragment
//        navController = navHostFragment.navController
//
//        var navGraph = navController.navInflater.inflate(R.navigation.nav_streper)
//        navGraph.id =
//            if (profileStatus==1) {
//                R.id.locationFragment
//            } else {
//                R.id.medicalStateFragment
//            }
//
//        navController.graph = navGraph

        val nextDestination = if (profileStatus==1) {
            R.id.locationFragment
        } else {
            R.id.medicalStateFragment
        }

        val options = NavOptions.Builder()
            .setPopUpTo(R.id.fragmentnav_Streper, true)
            .build()

       findNavController().navigate(nextDestination, null, options)

    }
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
                    profileStatus=it
                    binding.ivPlus.visibility=View.VISIBLE
                    binding.blueimageViewstep1.visibility=View.VISIBLE
                    binding.ivGraystep2.visibility=View.GONE
                    binding.layoutStep2.visibility=View.VISIBLE
                    binding.imageViewstep3.visibility=View.VISIBLE
                    binding.imageViewstep2.visibility=View.GONE

                }
                2->{
                    profileStatus=it
                    binding.ivPlus.visibility=View.VISIBLE
                    binding.blueimageViewstep1.visibility=View.VISIBLE
                    binding.ivPlusstep2.visibility=View.VISIBLE
                    binding.imageViewstep2.visibility=View.GONE
                    binding.ivGraystep2.visibility=View.GONE
                    binding.imageViewstep3.visibility=View.VISIBLE
                    binding.blueimageViewstep3.visibility=View.VISIBLE
                    binding.ivGraystep3.visibility=View.GONE
                    binding.layoutStep2.visibility=View.VISIBLE
                    binding.layoutStep3.visibility=View.VISIBLE
                    binding.tvSkip.visibility=View.VISIBLE
                }
                3->{
                    binding.ivPlus.visibility=View.VISIBLE
                    binding.blueimageViewstep1.visibility=View.VISIBLE
                    binding.ivPlusstep2.visibility=View.VISIBLE
                    binding.ivPlusstep3.visibility=View.VISIBLE
                    binding.imageViewstep2.visibility=View.GONE
                    binding.ivGraystep2.visibility=View.GONE
                    binding.imageViewstep3.visibility=View.VISIBLE
                    binding.blueimageViewstep3.visibility=View.VISIBLE
                    binding.ivGraystep3.visibility=View.GONE
                    binding.layoutStep2.visibility=View.VISIBLE
                    binding.layoutStep3.visibility=View.VISIBLE

                }

            }
        })



    }


}