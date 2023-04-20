package com.example.projectg17.networking

import com.example.projectg17.models.ParkByStateReponseObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("api/v1/parks/")
    suspend fun getParkList(@Query("stateCode") state:String, @Query("api_key") key:String)
        : Response<ParkByStateReponseObject>

}
