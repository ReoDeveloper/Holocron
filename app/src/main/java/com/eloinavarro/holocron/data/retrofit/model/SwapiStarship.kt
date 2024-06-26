package com.eloinavarro.holocron.data.retrofit.model

import com.eloinavarro.holocron.domain.SWStarship
import com.eloinavarro.holocron.domain.SwLink
import com.eloinavarro.holocron.domain.SwLinkType

data class SwapiStarship(
    val name: String,
    val model: String,
    val starship_class: String,
    val manufacturer: String,
    val cost_in_credits: String,
    val length: String,
    val crew: String,
    val passengers: String,
    val max_atmosphering_speed: String,
    val hyperdrive_rating: String,
    val MGLT: String,
    val cargo_capacity: String,
    val consumables: String,
    val films: List<String>,
    val pilots: List<String>,
    val created: String,
    val edited: String,
    val url: String
)