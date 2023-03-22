package com.example.newdesign.fragment.healthy

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newdesign.R
import com.example.newdesign.adapter.MedicalHealthAdapter
import com.example.newdesign.adapter.MedicalServicesAdapter
import com.example.newdesign.databinding.HealthEntityfragmentBinding
import com.example.newdesign.utils.Constans
import com.example.newdesign.utils.DateUtils
import com.example.newdesign.utils.Resource
import com.example.newdesign.viewmodel.DialogBottomSheetViewmodel
import com.example.newdesign.viewmodel.SharedDataViewmodel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HealthEntityFragment : Fragment(),MedicalHealthAdapter.Action {

   private lateinit var binding:HealthEntityfragmentBinding
    private lateinit var bottomsheetbeahavoir: BottomSheetBehavior<ConstraintLayout>
    private val viewmodel: DialogBottomSheetViewmodel by viewModels()
    val sharedDataViewmodel: SharedDataViewmodel by activityViewModels()
    private val args:HealthEntityFragmentArgs by navArgs()
    private lateinit var healthAdapter: MedicalHealthAdapter
    private var cityId=0
    private var areaId=0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=HealthEntityfragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
     binding.tvHealth.text=args.healthname

        bottomsheetbeahavoir =
            BottomSheetBehavior.from(binding.layoutBottomsheetpersistant.MedicalServices)
        bottomsheetbeahavoir.state = BottomSheetBehavior.STATE_HIDDEN
        callBack()
        medicalServicesRecylerview()
        bindDataToUi()
        initButton()
    }

    private fun medicalServicesRecylerview(){
        healthAdapter = MedicalHealthAdapter(this)
        binding.rvDoctors.apply {
            adapter = healthAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun initButton(){
        binding.ivArrow.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }

        binding.layoutBottomsheetpersistant.btnApply.setOnClickListener {
            viewmodel.getHealthEntityPagedList(cityId,areaId,args.healthtype,15,0)
            bottomsheetbeahavoir.state = BottomSheetBehavior.STATE_HIDDEN
        }

        binding.layoutFilter.setOnClickListener {
            bottomsheetbeahavoir.state =
                if (bottomsheetbeahavoir.state == BottomSheetBehavior.STATE_EXPANDED)
                    BottomSheetBehavior.STATE_HIDDEN else BottomSheetBehavior.STATE_EXPANDED
        }

        binding.layoutBottomsheetpersistant.layoutChooseCity.setOnClickListener {

            val action=HealthEntityFragmentDirections.actionHealthEntityFragmentToDialogBottomSheetFragment("AllCity")
            findNavController().navigate(action)
        }

        binding.layoutBottomsheetpersistant.layoutChooseArea.setOnClickListener {

            val action=HealthEntityFragmentDirections.actionHealthEntityFragmentToDialogBottomSheetFragment("AllArea")
            findNavController().navigate(action)
        }
    }

    private fun callBack(){

        viewmodel.healthyResponse.observe(viewLifecycleOwner, Observer {response->
            when(response){

                is Resource.Loading->{
                   binding.progressBar.visibility=View.VISIBLE
                }

                is Resource.sucess->{
                    binding.progressBar.visibility=View.GONE
                    healthAdapter.submitList(response.data?.data?.items)
                    healthAdapter.notifyDataSetChanged()
                }

                is Resource.Error->{
                    binding.progressBar.visibility=View.GONE
                    Snackbar.make(requireView(), "${response.message}", Snackbar.LENGTH_SHORT).show()
                }
            }

        })
        viewmodel.getHealthEntityPagedList(cityId,areaId,args.healthtype,10,0)

    }

    private fun bindDataToUi(){

        sharedDataViewmodel.getCity.observe(viewLifecycleOwner, Observer {
            binding.layoutBottomsheetpersistant.etChooseCity.setText(it.name)
            cityId=it.id
        })

        sharedDataViewmodel.getArea.observe(viewLifecycleOwner, Observer {
            binding.layoutBottomsheetpersistant.etChooseArea.setText(it.name)
            areaId=it.id
        })

    }


    override fun onItemClick(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
        this.startActivity(intent)
    }


}