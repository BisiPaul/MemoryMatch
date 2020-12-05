package com.bistronic.memorymatchwevideo.core

import com.bistronic.memorymatchwevideo.data.model.LoremPicsumPhotoModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by paulbisioc on 11/29/2020.
 */
interface ApiService {
    @GET("v2/list?page=1")
    suspend fun getPhotos(
        @Query("limit") limit: Int
    ): Response<List<LoremPicsumPhotoModel>?>
}