package com.bistronic.memorymatchwevideo.data.model.response

import com.bistronic.memorymatchwevideo.data.model.LoremPicsumPhotoModel
import com.squareup.moshi.JsonClass

/**
 * Created by paulbisioc on 11/30/2020.
 */

@JsonClass(generateAdapter = true)
data class GetPicturesResponseModel(
    val photos: List<LoremPicsumPhotoModel>
)