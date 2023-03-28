package com.santog.wizards.presentation.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.santog.wizards.R
import com.santog.wizards.databinding.ItemCharacterBinding
import com.santog.wizards.domain.model.CharacterHP
import com.santog.wizards.presentation.viewmodel.StudentsUI

class StudentsAdapter(
    val context: Context,
    val onClick: (character_id: String) -> Unit
) : RecyclerView.Adapter<StudentsAdapter.ViewHolder>() {

    private var dataSet: List<StudentsUI> = emptyList()

    class ViewHolder(binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {
        val cvCharacter: CardView
        val tvCharacterName: TextView
        val ivCharacterImage: ImageView
        val tvCharacterWizard: TextView
        init {
            cvCharacter = binding.root
            tvCharacterName = binding.tvCharacterName
            ivCharacterImage = binding.ivCharacterImage
            tvCharacterWizard = binding.tvCharacterWizard
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val character = dataSet[position]
        Glide.with(context).load(dataSet[position].image)
            .placeholder(R.drawable.character_placeholder_image)
            .circleCrop()
            .fitCenter()
            .into(viewHolder.ivCharacterImage)
        viewHolder.cvCharacter.setOnClickListener { onClick(character.id) }
        viewHolder.tvCharacterName.text = dataSet[position].characterName
        viewHolder.tvCharacterWizard.text = "Wizard: " + dataSet[position].wizard.toString()
    }

    fun setCharactersList(charactersList: List<StudentsUI>?) {
        val oldCharactersList = dataSet
        dataSet = charactersList ?: emptyList()
        notifyDataSetChanged()
    }

}
