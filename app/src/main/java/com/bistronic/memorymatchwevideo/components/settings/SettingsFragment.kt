package com.bistronic.memorymatchwevideo.components.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bistronic.memorymatchwevideo.R
import com.bistronic.memorymatchwevideo.core.ViewModelFactory
import com.bistronic.memorymatchwevideo.databinding.FragmentSettingsBinding
import com.bistronic.memorymatchwevideo.utils.SharedPreferencesUtils
import com.google.android.material.slider.Slider

/**
 * Created by paulbisioc on 11/30/2020.
 */
class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        viewModel = ViewModelProvider(this, ViewModelFactory(requireContext())).get(SettingsViewModel::class.java)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        configView()
        setControls()
        observe()
    }

    private fun configView() {
        binding.settingsGridSizeTV.text = resources.getString(R.string.settings_grid_size, SharedPreferencesUtils.gridSize, SharedPreferencesUtils.gridSize)
        binding.settingsSlider.value = SharedPreferencesUtils.gridSize
    }

    private fun setControls() {
        binding.settingsSlider.addOnChangeListener { slider, value, fromUser ->
            binding.settingsGridSizeTV.text = resources.getString(R.string.settings_grid_size, value, value)
            SharedPreferencesUtils.gridSize = value
        }
    }

    private fun observe() = with(viewModel) {

    }
}