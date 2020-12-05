package com.bistronic.memorymatchwevideo.core

import retrofit2.Response

/**
 * Created by paulbisioc on 11/30/2020.
 */

/* Class that pareses the retrofit response into an object that holds both the data (if available)
 and the request result. */

sealed class RequestResponse<out R> {

    companion object {
        fun <R> Response<R>.createRequestResponse(): RequestResponse<R> =
            if (this.isSuccessful)
                Success(this.body())
            else
                Error(
                    this.errorBody(),
                    this.errorBody()?.string(),
                    this.message(),
                    this.code()
                )
    }

    data class Success<R>(
        var body: R?
    ) : RequestResponse<R>()

    data class Error<R>(
        val errorBody: Any?,
        val errorJsonString: String?,
        val errorMessage: String,
        val errorCode: Int?
    ) : RequestResponse<R>()

}