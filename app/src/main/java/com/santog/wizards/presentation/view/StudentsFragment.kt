package com.santog.wizards.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.santog.wizards.databinding.FragmentStudentsBinding
import com.santog.wizards.presentation.viewmodel.HomeScreenEvents
import com.santog.wizards.presentation.viewmodel.HomeScreenStates
import com.santog.wizards.presentation.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class StudentsFragment : Fragment() {
    private var _binding: FragmentStudentsBinding? = null
    private val binding get() = _binding!!
    private val studentsAdapter by lazy {
        StudentsAdapter(context = requireContext()) { character_id ->
            val action = StudentsFragmentDirections.actionStudentsFragmentToDetailFragment(character_id)
            findNavController().navigate(action)
        }
    }
    private val viewModel by viewModel<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStudentsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvRecyclerView.adapter = studentsAdapter
        // TODO send character id to fetch character data for detail screen
        viewModel.send(HomeScreenEvents.OnReady)
        observeState()
    }

    private fun observeState() {
        viewModel.states.observe(viewLifecycleOwner) { state ->
            when (state) {
                is HomeScreenStates.Loading -> { /* DO NOOP */ }
                is HomeScreenStates.Error -> { /* DO NOOP */ }
                is HomeScreenStates.Content -> {
                    val results = state.generalContent.studentsList
                    if(results.isEmpty()) {
                        Timber.d("No students found")
                        Toast.makeText(context, "No students found", Toast.LENGTH_SHORT).show()
                    } else {
                        binding.rvRecyclerView.visibility = View.VISIBLE
                        studentsAdapter.setCharactersList(results)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
