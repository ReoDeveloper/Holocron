package com.eloinavarro.holocron.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eloinavarro.holocron.R
import com.eloinavarro.holocron.databinding.CharacterItemBinding
import com.eloinavarro.holocron.domain.SWCharacter
import com.squareup.picasso.Picasso

class CharacterGridAdapter(private var items: List<SWCharacter>) :
    RecyclerView.Adapter<CharacterGridAdapter.CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val itemBinding =
            CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class CharacterViewHolder(private val itemBinding: CharacterItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: SWCharacter) {
            with(itemBinding) {
                txtCharacterName.text = item.name
                txtCharacterDescription.text = item.description
                Picasso.get().load(item.image).placeholder(R.color.gray500).into(imgCharacter)
            }
        }
    }
}