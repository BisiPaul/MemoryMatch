package com.bistronic.memorymatchwevideo.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by paulbisioc on 11/30/2020.
 */

@JsonClass(generateAdapter = true)
data class LoremPicsumPhotoModel(
    @field:Json(name = "id") var id: String?,
    @field:Json(name = "author") var author: String?,
    @field:Json(name = "width") var width: Int?,
    @field:Json(name = "height") var height: Int?,
    @field:Json(name = "url") var url: String?,
    @field:Json(name = "download_url") var download_url: String?
)