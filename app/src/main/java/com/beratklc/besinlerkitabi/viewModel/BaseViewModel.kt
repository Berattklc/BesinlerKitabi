package com.beratklc.besinlerkitabi.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext


//Coroutine temellerini atacağımız sonradan diğer viewModellere uygulanacak
open class BaseViewModel(application: Application) : AndroidViewModel(application),CoroutineScope{

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    //arka plandaki işler yapılacak sonra main e dönülecek


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}