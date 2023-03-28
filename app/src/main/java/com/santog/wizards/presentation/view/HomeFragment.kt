package com.santog.wizards.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.santog.wizards.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        val view = binding.root
        val buttonStaff = binding.btHogwartsStaff
        buttonStaff.setOnClickListener{
            val action = HomeFragmentDirections.actionHomeFragmentToStaffFragment()
            findNavController().navigate(action)
        }
        val buttonStudents = binding.btHogwartsStudents
        buttonStudents.setOnClickListener{
            val action = HomeFragmentDirections.actionHomeFragmentToStudentsFragment()
            findNavController().navigate(action)
        }
        return view
    }

}
