package com.example.projectg17.networking

import com.example.projectg17.models.RandomUserReponseObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    // TODO: https://randomuser.me/api/?results=5
    // This will always return 5 results
    @GET("api/?results=5")
    suspend fun getRandomUsers(): Response<RandomUserReponseObject>

    // Use this endpoint if you want to control the number of results
//    @GET("api/")
//    suspend fun getRandomUsers(@Query("results") numResults:Int): Response<RandomUserReponseObject>

}
