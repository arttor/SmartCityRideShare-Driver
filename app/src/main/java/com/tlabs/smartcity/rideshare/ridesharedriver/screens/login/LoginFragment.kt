package com.tlabs.smartcity.rideshare.ridesharedriver.screens.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.tlabs.smartcity.rideshare.ridesharedriver.MainViewModel
import com.tlabs.smartcity.rideshare.ridesharedriver.R
import com.tlabs.smartcity.rideshare.ridesharedriver.databinding.LoginFragmentBinding
import com.tlabs.smartcity.rideshare.ridesharedriver.util.ScopedFragment

class LoginFragment : ScopedFragment() {
    private val vm: MainViewModel by lazy {
        ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)
    }


    private lateinit var binding: LoginFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginButton.setOnClickListener {
            if (binding.tokenInput.text.toString() != null) {
                vm.token = binding.tokenInput.text.toString()
                findNavController().navigate(R.id.mapFragment)
            }
        }

    }
}