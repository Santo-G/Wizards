package com.santog.wizards.presentation.view.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.santog.wizards.R
import com.santog.wizards.databinding.FragmentDetailBinding
import com.santog.wizards.presentation.viewmodel.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel by viewModel<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val characterId = args.characterId
        val characterSearchName = args.characterSearchName
        if (!characterId.isNullOrBlank()) {
            viewModel.send(DetailScreenEvents.OnReady(characterId))
        } else if (!characterSearchName.isNullOrBlank()) {
            viewModel.send(DetailScreenEvents.OnCharacterSearch(characterSearchName))
        }
        observeState()
    }

    private fun observeState() {
        viewModel.states.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DetailScreenStates.Loading -> { /* DO NOOP */ }
                is DetailScreenStates.Error -> { Toast.makeText(context, "No Character Found", Toast.LENGTH_LONG).show() }
                is DetailScreenStates.Content -> {
                    val character = state.detailContent.character
                    if(character == null) {
                        Timber.d("No character found")
                        Toast.makeText(context, "No character found", Toast.LENGTH_SHORT).show()
                    } else {
                        setContentView(character)
                    }
                }
            }
        }
    }

    private fun setContentView(character: DetailCharacterUI) {
        binding.tvDetailName.text = character.name
        binding.tvDetailActor.text = "Actor: " + character.actor
        binding.tvDetailAlive.text = "Alive: " + character.alive.toString()
        binding.tvDetailAncestry.text = "Ancestry: " + character.ancestry
        binding.tvDetailBirth.text = "Birth: " + character.dateOfBirth
        binding.tvDetailEyeColour.text = "Eye colour: " + character.eyeColour
        binding.tvDetailGender.text = "Gender: " + character.gender
        binding.tvDetailHairColour.text = "Hair colour: " + character.hairColour
        binding.tvDetailHogwartsStaff.text = "Staff: " + character.hogwartsStaff.toString()
        binding.tvDetailHogwartsStudent.text = "Student: " + character.hogwartsStudent.toString()
        binding.tvDetailHouse.text = "House: " + character.house
        Glide.with(requireContext()).load(character.image)
            .placeholder(R.drawable.character_placeholder_image)
            .circleCrop()
            .fitCenter()
            .into(binding.ivDetailImage)
        binding.tvDetailPatronus.text = "Patronus: " + character.patronus
        binding.tvDetailSpecies.text = "Species: " + character.species
        binding.tvDetailWizard.text = "Wizard: " + character.wizard.toString()
        binding.tvDetailYearBirth.text = "Year birth: " + character.yearOfBirth.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
