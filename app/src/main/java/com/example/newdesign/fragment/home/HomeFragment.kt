package com.example.newdesign.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import com.example.newdesign.R
import com.example.newdesign.adapter.ImageVideoAdapter
import com.example.newdesign.adapter.MedicalServicesAdapter
import com.example.newdesign.adapter.ServicesAdapter
import com.example.newdesign.databinding.HomefragmentBinding
import com.example.newdesign.model.ImageServices
import com.example.newdesign.utils.Resource
import com.example.newdesign.utils.SpUtil
import com.example.newdesign.viewmodel.RegisterViewmodel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(),ServicesAdapter.Action {

    private lateinit var binding:HomefragmentBinding
    private lateinit var imageServicesAdapter:ServicesAdapter
    private lateinit var imageMedicalServicesAdapter:MedicalServicesAdapter
    private lateinit var imageVideoAdapter:ImageVideoAdapter
    @Inject
    lateinit var sp: SpUtil
    var medicalExaminatioId:Int?=null
    val viewmodel: RegisterViewmodel by viewModels()


    companion object{
        var instance: HomeFragment?=null

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= HomefragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentTime = SimpleDateFormat("HH:mm:ss a", Locale.getDefault()).format(Date())

        if (currentTime.contains("AM")){
            binding.tvWelcome.text="Good Morning"
        }else{
            binding.tvWelcome.text="Good Evening"
        }

        binding.tvPatientname.text=sp.getUser()?.name
        binding.ivPatientProfile.load("https://salamtechapi.azurewebsites.net/${sp.getUser()?.image}") {
            crossfade(true)
            crossfade(1000)
            placeholder(R.drawable.ic_profile)
            transformations(CircleCropTransformation())
        }
        instance =this
        imageVideoAdapter= ImageVideoAdapter()
        servicesRecylerview()
        medicalServicesRecylerview()
        imageServices()
        medicalServices()
        callBack()
        initButton()
    }

    private fun initButton()
    {
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

    private fun medicalServicesRecylerview(){
        imageMedicalServicesAdapter = MedicalServicesAdapter()
        binding.rvSalamtakmedicalServices.apply {
            adapter = imageMedicalServicesAdapter
            setHasFixedSize(true)
        }
    }

    private fun imageServices(){

        val list= mutableListOf<ImageServices>()

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
                getString(R.string.Home_Visit)  ,
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
    private fun medicalServices(){

        val list= mutableListOf<ImageServices>()

        list.add(
            ImageServices(
                R.drawable.ic_hospitals,
                getString(R.string.hospitals),
                R.color.color_1,
                0
            )
        )

        list.add(
            ImageServices(
                R.drawable.ic_polyclinics,
                getString(R.string.polyclinics)  ,
                R.color.color_2,0
                )
        )
        list.add(
            ImageServices(
                R.drawable.ic_pharmacies,
                getString(R.string.pharmacies),
                R.color.color_3,0
                )
        )
        list.add(
            ImageServices(
                R.drawable.ic_laboratories,
                getString(R.string.laboratories),
                R.color.color_4,0
                )
        )
        imageMedicalServicesAdapter.submitList(list)
        imageMedicalServicesAdapter.notifyDataSetChanged()

    }

   private fun callBack(){
       viewmodel.imagevedioresponse.observe(viewLifecycleOwner, Observer {response->

           when(response){

               is Resource.Loading->{
                  showprogtessbar()
               }

               is Resource.sucess->{
                  hideprogressbar()
                   response.let {
                       imageVideoAdapter.submitList(it.data?.Data)
                       binding.viewPager.adapter = imageVideoAdapter
                   }

               }

               is Resource.Error->{
                   hideprogressbar()
//                   loginresponse.data?.let {
//                       Log.e("msg : ",it.message)
//
//                   }
               }
           }

       })

       viewmodel.getHomeAdds()
   }

    private fun showprogtessbar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideprogressbar() {
        binding.progressBar.visibility = View.GONE
    }

    override fun onItemClick(servicesID: Int) {
        medicalExaminatioId=servicesID
        val action =HomeFragmentDirections.actionHomeFragmentToDialogBottomSheetFragment("services")
      findNavController().navigate(action)
    }





}