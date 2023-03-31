package com.santog.wizards.presentation.view.students

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.santog.wizards.databinding.FragmentStudentsBinding
import com.santog.wizards.presentation.viewmodel.HomeScreenActions
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
            val action = StudentsFragmentDirections.actionStudentsFragmentToDetailFragment(
                    character_id, null
                )
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
        binding.rvRecyclerViewStudents.adapter = studentsAdapter
        viewModel.send(HomeScreenEvents.OnReady)
        observeState()
        observeActions()
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
                        binding.rvRecyclerViewStudents.visibility = View.VISIBLE
                        studentsAdapter.setCharactersList(results)
                    }
                }
            }
        }
    }

    private fun observeActions() {
        viewModel.actions.observe(viewLifecycleOwner) { action ->
            Timber.d(action.toString())
            when (action) {
                is HomeScreenActions.NavigateToDetail -> {
                    val directions = StudentsFragmentDirections.actionStudentsFragmentToDetailFragment(
                            action.characterId, null
                        )
                    findNavController().navigate(directions)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
