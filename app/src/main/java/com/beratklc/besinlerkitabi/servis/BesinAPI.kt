package com.beratklc.besinlerkitabi.servis

import com.beratklc.besinlerkitabi.model.Besin
import io.reactivex.Single
import retrofit2.http.GET

interface BesinAPI {

    //GET,POST

    //https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json

    // Base url -> https://raw.githubusercontent.com/
    // atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json

    @GET("atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")
    fun getBesin() : Single<List<Besin>>

}