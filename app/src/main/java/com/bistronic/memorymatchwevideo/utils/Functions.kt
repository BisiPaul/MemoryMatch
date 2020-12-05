package com.bistronic.memorymatchwevideo.utils

import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.bistronic.memorymatchwevideo.core.RequestResponse
import com.bistronic.memorymatchwevideo.core.RequestResponse.Companion.createRequestResponse
import com.bistronic.memorymatchwevideo.core.ViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response

/**
 * Created by paulbisioc on 11/28/2020.
 */
@MainThread
inline fun <reified VM : ViewModel> Fragment.fragmentViewModel() =
    createViewModelLazy(
        VM::class,
        { this.viewModelStore },
        {
            ViewModelFactory(requireContext().applicationContext)
        }
    )

suspend fun <T> runApiCall(call: suspend () -> Response<T>): RequestResponse<T> =
    try {
        call().createRequestResponse()
    } catch (e: Exception) {
        e.printStackTrace()
        RequestResponse.Error(
            e.message,
            null,
            e.localizedMessage ?: e.javaClass.name,
            0
        )
    }

// Delay block of code with coroutines - way way better and safer than Handler().postDelayed()
fun AppCompatActivity.coroutineDelay(timeInMills: Long, callback: suspend () -> Unit) {
    this.lifecycleScope.launch {
        delay(timeInMills)
        callback.invoke()
    }
}

// Delay block of code with coroutines - way way better and safer than Handler().postDelayed()
fun Fragment.coroutineDelay(timeInMills: Long, callback: suspend () -> Unit) {
    this.lifecycleScope.launch {
        delay(timeInMills)
        callback.invoke()
    }
}

// Delay block of code with coroutines - way way better and safer than Handler().postDelayed()
fun ViewModel.coroutineDelay(timeInMills: Long, callback: suspend () -> Unit) {
    this.viewModelScope.launch {
        delay(timeInMills)
        callback.invoke()
    }
}