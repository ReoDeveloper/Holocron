package com.eloinavarro.holocron.data.retrofit

import com.eloinavarro.holocron.data.toDomainModel
import com.eloinavarro.holocron.domain.SWCharacter
import com.eloinavarro.holocron.domain.SWItem
import com.eloinavarro.holocron.domain.SWMovie
import com.eloinavarro.holocron.domain.SWPlanet
import com.eloinavarro.holocron.domain.SWSpecie
import com.eloinavarro.holocron.domain.SWStarship
import com.eloinavarro.holocron.domain.SWVehicle
import com.eloinavarro.holocron.domain.SwLinkList
import com.eloinavarro.holocron.domain.SwLinkType

class SwapiRetrofitDatasource {

    private val url = "https://swapi.dev/api/"

    private fun getApi(): Swapi {
        return RetrofitClient.getRetrofit(url)!!.create(Swapi::class.java)
    }

    suspend fun getAllCharacters(page: Int): Result<List<SWCharacter>> {
        val response = getApi().getAllCharacters(page)
        return if(response.isSuccessful && response.body() != null) {
            Result.success(response.body()!!.results.map { it.toDomainModel() })
        } else {
            Result.failure(Exception("Error getAllCharacters"))
        }
    }

    suspend fun getCharacterById(id: Int, recursive: Boolean = true):Result<SWCharacter> {
        val response = getApi().getCharacterById(id)
        return if(response.isSuccessful && response.body() != null) {
            val successResult = response.body()!!.toDomainModel()
            if(recursive) successResult.links = populateLinks(successResult.links)
            Result.success(successResult)
        } else {
            Result.failure(Exception("Error getCharacterById($id)"))
        }
    }

    suspend fun getAllPlanets(page: Int): Result<List<SWPlanet>> {
        val response = getApi().getAllPlanets(page)
        return if(response.isSuccessful && response.body() != null) {
            Result.success(response.body()!!.results.map { it.toDomainModel() })
        } else {
            Result.failure(Exception("Error getAllPlanets"))
        }
    }

    suspend fun getPlanetById(id: Int, recursive: Boolean = true):Result<SWPlanet> {
        val response = getApi().getPlanetById(id)
        return if(response.isSuccessful && response.body() != null) {
            val successResult = response.body()!!.toDomainModel()
            if(recursive) successResult.links = populateLinks(successResult.links)
            Result.success(successResult)
        } else {
            Result.failure(Exception("Error getPlanetById($id)"))
        }
    }

    suspend fun getAllStarships(page: Int): Result<List<SWStarship>> {
        val response = getApi().getAllStarships(page)
        return if(response.isSuccessful && response.body() != null) {
            Result.success(response.body()!!.results.map { it.toDomainModel() })
        } else {
            Result.failure(Exception("Error getAllStarships"))
        }
    }

    suspend fun getStarshipById(id: Int, recursive: Boolean = true):Result<SWStarship> {
        val response = getApi().getStarshipById(id)
        return if(response.isSuccessful && response.body() != null) {
            val successResult = response.body()!!.toDomainModel()
            if(recursive) successResult.links = populateLinks(successResult.links)
            Result.success(successResult)
        } else {
            Result.failure(Exception("Error getStarshipById($id)"))
        }
    }

    suspend fun getAllVehicles(page: Int): Result<List<SWVehicle>> {
        val response = getApi().getAllVehicles(page)
        return if(response.isSuccessful && response.body() != null) {
            Result.success(response.body()!!.results.map { it.toDomainModel() })
        } else {
            Result.failure(Exception("Error getAllStarships"))
        }
    }

    suspend fun getVehicleById(id: Int, recursive: Boolean = true):Result<SWVehicle> {
        val response = getApi().getVehicleById(id)
        return if(response.isSuccessful && response.body() != null) {
            val successResult = response.body()!!.toDomainModel()
            if(recursive) successResult.links = populateLinks(successResult.links)
            Result.success(successResult)
        } else {
            Result.failure(Exception("Error getStarshipById($id)"))
        }
    }

    suspend fun getAllSpecies(page: Int): Result<List<SWSpecie>> {
        val response = getApi().getAllSpecies(page)
        return if(response.isSuccessful && response.body() != null) {
            Result.success(response.body()!!.results.map { it.toDomainModel() })
        } else {
            Result.failure(Exception("Error getAllStarships"))
        }
    }

    suspend fun getSpecieById(id: Int, recursive: Boolean = true):Result<SWSpecie> {
        val response = getApi().getSpecieById(id)
        return if(response.isSuccessful && response.body() != null) {
            val successResult = response.body()!!.toDomainModel()
            if(recursive) successResult.links = populateLinks(successResult.links)
            Result.success(successResult)
        } else {
            Result.failure(Exception("Error getStarshipById($id)"))
        }
    }

    suspend fun getAllMovies(page: Int): Result<List<SWMovie>> {
        val response = getApi().getAllMovies(page)
        return if(response.isSuccessful && response.body() != null) {
            Result.success(response.body()!!.results.map { it.toDomainModel() })
        } else {
            Result.failure(Exception("Error getAllStarships"))
        }
    }

    suspend fun getMovieById(id: Int, recursive: Boolean = true):Result<SWMovie> {
        val response = getApi().getMovieById(id)
        return if(response.isSuccessful && response.body() != null) {
            val successResult = response.body()!!.toDomainModel()
            if(recursive) successResult.links = populateLinks(successResult.links)
            Result.success(successResult)
        } else {
            Result.failure(Exception("Error getStarshipById($id)"))
        }
    }

    private suspend fun populateLinks(items: List<SwLinkList>): List<SwLinkList> {
        items.forEach { linkList ->
            val currentType = linkList.type
            linkList.links.forEach { link ->
                link.name = when (currentType) {
                    SwLinkType.PLANET -> getPlanetData(link.id) ?: "Not found"
                    SwLinkType.CHARACTER -> getCharacterData(link.id) ?: "Not found"
                    SwLinkType.SPECIE -> getSpecieData(link.id) ?: "Not found"
                    SwLinkType.VEHICLE -> getVehicleData(link.id) ?: "Not found"
                    SwLinkType.STARSHIP -> getStarshipData(link.id) ?: "Not found"
                    SwLinkType.MOVIE -> getMovieData(link.id) ?: "Not found"
                }
            }
        }
        return items
    }

    private suspend fun getPlanetData(id: Int):String? = getPlanetById(id, false).getOrNull()?.name
    private suspend fun getCharacterData(id: Int):String? = getCharacterById(id, false).getOrNull()?.name
    private suspend fun getSpecieData(id: Int):String? = getSpecieById(id, false).getOrNull()?.name
    private suspend fun getVehicleData(id: Int):String? = getVehicleById(id, false).getOrNull()?.name
    private suspend fun getStarshipData(id: Int):String? = getStarshipById(id, false).getOrNull()?.name
    private suspend fun getMovieData(id: Int):String? = getMovieById(id, false).getOrNull()?.name

}