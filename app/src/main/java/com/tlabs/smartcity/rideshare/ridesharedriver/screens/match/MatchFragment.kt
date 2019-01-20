package com.tlabs.smartcity.rideshare.ridesharedriver.screens.match

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.tlabs.smartcity.rideshare.ridesharedriver.databinding.MatchFragmentBinding
import com.tlabs.smartcity.rideshare.ridesharedriver.screens.map.MapViewModel
import com.tlabs.smartcity.rideshare.ridesharedriver.util.AnimationUtil
import com.tlabs.smartcity.rideshare.ridesharedriver.util.ScopedFragment
import kotlinx.coroutines.launch

class MatchFragment : ScopedFragment() {
    private val viewModel: MatchViewModel by lazy {
        ViewModelProviders.of(requireActivity()).get(MatchViewModel::class.java)
    }
    private val mainModel: MapViewModel by lazy {
        ViewModelProviders.of(this.requireActivity()).get(MapViewModel::class.java)
    }


    private lateinit var binding: MatchFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MatchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.msg?.let {
            launch {
                AnimationUtil.fadeIn(content = binding.progress, transparency = 0.7f)
                viewModel.postInfo(it, mainModel.token)
                AnimationUtil.fadeOut(content = binding.progress, transparency = 0.7f)
            }
        }
    }


}