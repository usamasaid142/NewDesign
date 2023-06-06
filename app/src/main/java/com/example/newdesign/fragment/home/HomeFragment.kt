package com.example.newdesign.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import com.example.newdesign.R
import com.example.newdesign.adapter.*
import com.example.newdesign.databinding.HomefragmentBinding
import com.example.newdesign.model.ImageServices
import com.example.newdesign.model.populardoctors.HealthData
import com.example.newdesign.utils.Constans
import com.example.newdesign.utils.Resource
import com.example.newdesign.utils.SpUtil
import com.example.newdesign.viewmodel.DialogBottomSheetViewmodel
import com.example.newdesign.viewmodel.RegisterViewmodel
import com.example.newdesign.viewmodel.SharedDataViewmodel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.intellij.lang.annotations.Language
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), ServicesAdapter.Action,PopularDoctorsAdapter.Action,HealthTopicsAdapter.Action,SpotlightAdapter.Action{

    private lateinit var binding: HomefragmentBinding
    private lateinit var imageServicesAdapter: ServicesAdapter
    private lateinit var imageMedicalServicesAdapter: MedicalServicesAdapter
    private lateinit var imageVideoAdapter: ImageVideoAdapter
    private lateinit var popularDoctorsAdapter: PopularDoctorsAdapter
    private lateinit var healthTopicsAdapter: HealthTopicsAdapter
    private lateinit var spotlightAdapter: SpotlightAdapter
    val sharedDataViewmodel: SharedDataViewmodel by activityViewModels()
    @Inject
    lateinit var sp: SpUtil
    var medicalExaminatioId: Int? = null
    val regviewmodel: RegisterViewmodel by viewModels()
    val viewmodel: DialogBottomSheetViewmodel by viewModels()


    companion object {
        var instance: HomeFragment? = null

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = HomefragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        instance = this
        bindDataToViews()
        imageVideoAdapter = ImageVideoAdapter()
        servicesRecylerview()
        medicalServicesRecylerview()
        popularDoctorsRecylerview()
        healthTopicsRecylerview()
        spotlightRecylerview()
        imageServices()
        medicalServices()
        callBack()
        callBackPopularDoctors()
        callBackDoctorHealthTopics()
        callBackSpotlight()
        initButton()
    }

    private fun initButton() {
        binding.etSearch.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }
    }

    private fun servicesRecylerview() {
        imageServicesAdapter = ServicesAdapter(this)
        binding.rvSalamtakServices.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = imageServicesAdapter
            setHasFixedSize(true)
        }

    }

    private fun medicalServicesRecylerview() {
        imageMedicalServicesAdapter = MedicalServicesAdapter()
        binding.rvSalamtakmedicalServices.apply {
            adapter = imageMedicalServicesAdapter
            setHasFixedSize(true)
        }
    }

    private fun popularDoctorsRecylerview() {
        popularDoctorsAdapter = PopularDoctorsAdapter(this)
        binding.rvPopularDoctors.apply {
            adapter = popularDoctorsAdapter
            setHasFixedSize(true)
        }
    }

    private fun healthTopicsRecylerview() {
        healthTopicsAdapter = HealthTopicsAdapter(this)
        binding.rvHealthTopics.apply {
            adapter = healthTopicsAdapter
            setHasFixedSize(true)
        }
    }

    private fun spotlightRecylerview() {
        spotlightAdapter = SpotlightAdapter(this)
        binding.rvSpotlight.apply {
            adapter = spotlightAdapter
            setHasFixedSize(true)
        }
    }

    private fun imageServices() {

        val list = mutableListOf<ImageServices>()

        list.add(
            ImageServices(
                R.drawable.ic_clinic_booking,
                getString(R.string.Clinic_Booking),
                Id = 1
            )
        )

        list.add(
            ImageServices(
                R.drawable.ic_hom_visit,
                getString(R.string.Home_Visit),
                Id = 2

            )
        )
        list.add(
            ImageServices(
                R.drawable.ic_chat,
                getString(R.string.Chat),
                Id = 3
            )
        )
        list.add(
            ImageServices(
                R.drawable.ic_call,
                getString(R.string.Call),
                Id = 4
            )
        )
        list.add(
            ImageServices(
                R.drawable.ic_videocall,
                getString(R.string.Video_Call),
                Id = 5
            )
        )
        imageServicesAdapter.submitList(list)
        imageServicesAdapter.notifyDataSetChanged()

    }

    private fun medicalServices() {

        val list = mutableListOf<ImageServices>()

        list.add(
            ImageServices(
                R.drawable.ic_hospitals,
                getString(R.string.hospitals),
                R.color.color_1,
                2
            )
        )

        list.add(
            ImageServices(
                R.drawable.ic_polyclinics,
                getString(R.string.polyclinics),
                R.color.color_2, 6
            )
        )
        list.add(
            ImageServices(
                R.drawable.ic_pharmacies,
                getString(R.string.pharmacies),
                R.color.color_3, 5
            )
        )
        list.add(
            ImageServices(
                R.drawable.ic_laboratories,
                getString(R.string.laboratories),
                R.color.color_4, 3
            )
        )
        imageMedicalServicesAdapter.submitList(list)
        imageMedicalServicesAdapter.notifyDataSetChanged()

    }

    private fun callBack() {
        regviewmodel.imagevedioresponse.observe(viewLifecycleOwner, Observer { response ->

            when (response) {

                is Resource.Loading -> {
                    showprogtessbar()
                }

                is Resource.sucess -> {
                    hideprogressbar()
                    response.let {
                        imageVideoAdapter.submitList(it.data?.Data)
                        binding.viewPager.adapter = imageVideoAdapter
                    }

                }

                is Resource.Error -> {
                    hideprogressbar()
//                   loginresponse.data?.let {
//                       Log.e("msg : ",it.message)
//
//                   }
                }
            }

        })

        regviewmodel.getHomeAdds()
    }

    private fun callBackPopularDoctors() {
        viewmodel.popularResponse.observe(viewLifecycleOwner, Observer { response ->

            when (response) {

                is Resource.Loading -> {
                    showprogtessbar()
                }

                is Resource.sucess -> {
                    hideprogressbar()
                    response.let {
                        popularDoctorsAdapter.submitList(it.data)
                        popularDoctorsAdapter.notifyDataSetChanged()
                    }

                }

                is Resource.Error -> {
                    hideprogressbar()
                }
            }

        })

        viewmodel.getPopularDoctors()
    }

    private fun callBackDoctorHealthTopics() {
        viewmodel.healthTopicsResponse.observe(viewLifecycleOwner, Observer { response ->

            when (response) {

                is Resource.Loading -> {
                    binding.healthProgressBar.visibility = View.VISIBLE
                }

                is Resource.sucess -> {
                    binding.healthProgressBar.visibility = View.GONE

                    response.let {
                        healthTopicsAdapter.submitList(it.data?.data)
                        healthTopicsAdapter.notifyDataSetChanged()
                    }
                }
                is Resource.Error -> {
                    binding.healthProgressBar.visibility = View.GONE
                    Snackbar.make(requireView(), "${response.message}", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }

        })

        viewmodel.getDoctorHealthTopics()
    }

    private fun callBackSpotlight() {
        viewmodel.doctorSpotLightResponse.observe(viewLifecycleOwner, Observer { response ->

            when (response) {

                is Resource.Loading -> {
                    binding.healthProgressBar.visibility = View.VISIBLE
                }

                is Resource.sucess -> {
                    binding.healthProgressBar.visibility = View.GONE

                    response.let {
                        spotlightAdapter.submitList(it.data?.data)
                        spotlightAdapter.notifyDataSetChanged()
                    }
                }
                is Resource.Error -> {
                    binding.healthProgressBar.visibility = View.GONE
                    Snackbar.make(requireView(), "${response.message}", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }

        })

        viewmodel.getDoctorSpotLight()
    }

    private fun showprogtessbar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideprogressbar() {
        binding.progressBar.visibility = View.GONE
    }

    override fun onItemClick(servicesID: Int) {
        medicalExaminatioId = servicesID
        val action =
            HomeFragmentDirections.actionHomeFragmentToDialogBottomSheetFragment("services")
        findNavController().navigate(action)
    }




    private fun bindDataToViews() {

        val currentTime = SimpleDateFormat("HH:mm:ss a", Locale.getDefault()).format(Date())

        if (sp.getUserLang(Constans.Language) == "En") {
            if (currentTime.contains("pm") || currentTime.contains("PM")) {
                binding.tvWelcome.text = getString(R.string.good_evening)
            } else {
                binding.tvWelcome.text = getString(R.string.good_morning)
            }
            binding.tvPatientname.text = sp.getUser()?.name
        } else {
            if (currentTime.contains("pm") || currentTime.contains("PM")) {
                binding.tvWelcome.text = getString(R.string.good_evening)
            } else {
                binding.tvWelcome.text = getString(R.string.good_morning)
            }
            binding.tvPatientname.text = sp.getUser()?.NameAR
        }

        binding.ivPatientProfile.load("https://salamtechapi.azurewebsites.net/${sp.getUser()?.image}") {
            crossfade(true)
            crossfade(1000)
            placeholder(R.drawable.ic_profile)
            transformations(CircleCropTransformation())
        }

    }

    override fun onItemClickDoctorsDetails(DoctorId: Int) {
        sharedDataViewmodel.getDocotorId(DoctorId)
       val action=HomeFragmentDirections.actionHomeFragmentToBookingAppointmentFragment()
        findNavController().navigate(action)
    }

    override fun onItemClickHealthTopicsDetails(healthTopicsData: HealthData) {
        val action=HomeFragmentDirections.actionHomeFragmentToHealthyTopicsFragment(healthTopicsData)
        findNavController().navigate(action)
    }

    override fun onItemClickSpotLightDetails(healthTopicsData: HealthData) {
        val action=HomeFragmentDirections.actionHomeFragmentToHealthyTopicsFragment(healthTopicsData)
        findNavController().navigate(action)
    }


}