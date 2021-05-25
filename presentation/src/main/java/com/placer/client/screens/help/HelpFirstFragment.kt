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
import com.placer.client.databinding.FragmentHelpFirstBinding
import com.placer.client.navigation.HelpSecondTransaction

internal class HelpFirstFragment : Fragment(), HelpSecondTransaction {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentHelpFirstBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_help_first, container, false
        )
        (requireActivity() as AppCompatActivity?)?.supportActionBar?.elevation = Constants.ACTION_BAR_ELEVATION
        (requireActivity() as AppCompatActivity?)?.supportActionBar?.show()
        binding.button.setOnClickListener {
            setSecondHelpFragment()
        }
        return binding.root
    }

    override fun setSecondHelpFragment() {
        findNavController().navigate(HelpFirstFragmentDirections.actionHelpFirstFragmentToHelpSecondFragment())
    }
}