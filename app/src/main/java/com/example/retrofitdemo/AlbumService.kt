package com.example.retrofitdemo

import androidx.lifecycle.LiveData
import retrofit2.Response
import retrofit2.http.*

//用來抓data 的interface
interface AlbumService {

//    @GET("/albums")//只用後綴 因為baseURL會放在instance讓其他interface共用
//    //加上suspend是為了要使用coroutine
//    //<Albums>包含整個Response json object
//    suspend fun getAlbums() : Response<Albums>
    @GET("/albums")
    suspend fun getAlbums() :Response<Albums>

    @GET("/albums")
    suspend fun getSortedAlbums(@Query("userId") userId: Int) :Response<Albums>

    @GET("/albums/{id}")
    suspend fun getAlbum(@Path(value = "id")albumId: Int): Response<AlbumsItem>

    @POST("/albums")
    suspend fun uploadAlbum(@Body album:AlbumsItem) : Response<AlbumsItem>


}