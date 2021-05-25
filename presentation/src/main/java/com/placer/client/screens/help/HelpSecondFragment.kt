package com.placer.client.screens.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.placer.client.Constants
import com.placer.client.R
import com.placer.client.databinding.FragmentHelpSecondBinding
import com.placer.client.navigation.HelpThirdTransaction

internal class HelpSecondFragment : Fragment(), HelpThirdTransaction {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentHelpSecondBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_help_second, container, false
        )
        (requireActivity() as AppCompatActivity?)?.supportActionBar?.elevation = Constants.ACTION_BAR_ELEVATION
        (requireActivity() as AppCompatActivity?)?.supportActionBar?.show()
        binding.button.setOnClickListener {
            setThirdHelpFragment()
        }
        return binding.root
    }

    override fun setThirdHelpFragment() {
        findNavController().navigate(HelpSecondFragmentDirections.actionHelpSecondFragmentToHelpThirdFragment())
    }
}