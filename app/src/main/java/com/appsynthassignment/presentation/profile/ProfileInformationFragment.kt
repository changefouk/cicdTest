package com.appsynthassignment.presentation.profile

import android.os.Bundle
import android.view.View
import com.appsynthassignment.R
import com.appsynthassignment.databinding.FragmentProfileInformationBinding
import com.appsynthassignment.presentation.base.BaseFragment
import com.appsynthassignment.presentation.profile.adapter.ProfileInformationAdapter
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class ProfileInformationFragment : BaseFragment<FragmentProfileInformationBinding>() {

    private val vm: ProfileInformationViewModel by stateViewModel()

    override fun getLayout(): Int = R.layout.fragment_profile_information

    override fun onViewModelBinding() {
        binding.vm = this.vm
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.fetchData()
        val informationAdapter = ProfileInformationAdapter()
        binding.adapter = informationAdapter
        vm.profileInformation.observe(viewLifecycleOwner, {
            it.notification.let { notificationItems ->
                informationAdapter.setItems(notificationItems)
            }
        })

    }

    companion object {
        fun newInstance(): ProfileInformationFragment =
            ProfileInformationFragment().apply {
                arguments = Bundle().apply {
                    // bundle data
                }
            }
    }

}