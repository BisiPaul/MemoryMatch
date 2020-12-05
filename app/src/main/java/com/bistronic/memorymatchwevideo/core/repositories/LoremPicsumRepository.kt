package com.bistronic.memorymatchwevideo.core.repositories

import com.bistronic.memorymatchwevideo.core.RequestResponse
import com.bistronic.memorymatchwevideo.core.RetrofitClient
import com.bistronic.memorymatchwevideo.data.model.LoremPicsumPhotoModel
import com.bistronic.memorymatchwevideo.utils.runApiCall

/**
 * Created by paulbisioc on 11/30/2020.
 */
class LoremPicsumRepository() {

    suspend fun getPhotos(photoLimit: Int): RequestResponse<List<LoremPicsumPhotoModel>?> {
        return runApiCall {
            RetrofitClient.getApiService().getPhotos(photoLimit)
        }
    }
}