package com.myapplication.services

import com.myapplication.model.user
import retrofit2.Call
import retrofit2.http.*

interface UserService {

    @GET("destination")
    fun getUserList(@QueryMap filter: HashMap<String, String>): Call<List<user>>

    @GET("destination/{id}")
    fun getUser(@Path("id") id: Int): Call<user>

    @POST("destination")
    fun addUser(@Body newDestination: user): Call<user>

    @FormUrlEncoded
    @PUT("destination/{id}")
    fun updateUser(
        @Path("id") id: Int,
        @Field("mdp") mdp: String,
        @Field("nom") nom: String,
        @Field("prenom") prenom: String
    ): Call<user>

    @DELETE("destination/{id}")
    fun deleteUser(@Path("id") id: Int): Call<Unit>
}