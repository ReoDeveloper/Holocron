package com.eloinavarro.holocron.domain

data class SWCharacter(
    override val id: Int,
    override val name: String,
    override val image: String,
    val description: String,
    val bornDate: String,
    val height: Float?,
    val weight: Int?,
    val skinColors: List<String>,
    val eyeColors: List<String>,
    val hairColors: List<String>,
    override var links:List<SwLinkList>,
    override val url: String,
    override var isFavorite: Boolean
):SWItem