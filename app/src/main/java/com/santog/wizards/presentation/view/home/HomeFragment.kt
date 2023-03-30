package com.santog.wizards.presentation.view.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.santog.wizards.R
import com.santog.wizards.databinding.FragmentHomeBinding
import com.santog.wizards.presentation.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<HomeViewModel>()

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
        buttonStaff.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToStaffFragment()
            findNavController().navigate(action)
        }
        val buttonStudents = binding.btHogwartsStudents
        buttonStudents.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToStudentsFragment()
            findNavController().navigate(action)
        }

        val buttonSearch = binding.btSearchCharacter
        buttonSearch.setOnClickListener {
            val dialog: AlertDialog.Builder = AlertDialog.Builder(this.requireContext())
            val inflater = this.layoutInflater
            val dialogView: View = inflater.inflate(R.layout.fragment_search, null)
            dialog.setView(dialogView)
            val editText = dialogView.findViewById<View>(R.id.et_character_search_text) as EditText
            val positiveButton = dialogView.findViewById<Button>(R.id.bt_search_ok)
            val negativeButton = dialogView.findViewById<Button>(R.id.bt_search_cancel)
            val alertDialog: AlertDialog = dialog.create()
            positiveButton.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToDetailFragment(null, editText.text.toString())
                findNavController().navigate(action)
                alertDialog.dismiss()
            }

            negativeButton.setOnClickListener {
                alertDialog.dismiss()
            }

            alertDialog.show()
        }

        return view
    }

}
