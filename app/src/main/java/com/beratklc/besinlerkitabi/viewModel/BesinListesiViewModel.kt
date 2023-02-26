package com.beratklc.besinlerkitabi.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beratklc.besinlerkitabi.model.Besin
import com.beratklc.besinlerkitabi.servis.BesinAPIServis
import com.beratklc.besinlerkitabi.servis.BesinDatabase
import com.beratklc.besinlerkitabi.util.OzelSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class BesinListesiViewModel(application: Application) : BaseViewModel(application) {
    val besinler = MutableLiveData<List<Besin>>()
    val besinHataMesaji = MutableLiveData<Boolean>()
    val besinYukleniyor = MutableLiveData<Boolean>()
    private var guncellemeZamani = 10 * 60 * 1000 * 1000 * 1000L

    private val besinApiServis = BesinAPIServis()
    //Tek kullanımlık istek
    private val disposable = CompositeDisposable()//Rxjava sayesinde
    private  val ozelSharedPreferences = OzelSharedPreferences(getApplication())

    fun refreshData(){

        val kaydedilmeZamani = ozelSharedPreferences.zamaniAl()
        if(kaydedilmeZamani != null && kaydedilmeZamani != 0L && System.nanoTime() - kaydedilmeZamani < guncellemeZamani){
            //Sqlite 'dan çek
            verileriSQLitetanAl()
        }else{
            verileriInternettenAl()
        }

    }

    fun refreshFromInternet(){
        verileriInternettenAl()
    }

    private fun verileriSQLitetanAl(){

        besinYukleniyor.value = true

       launch {
           val besinListesi = BesinDatabase(getApplication()).besinDao().getAllBesin()
           besinleriGoster(besinListesi)
           Toast.makeText(getApplication(),"Besinleri Roomdan aldık",Toast.LENGTH_LONG).show()
       }

    }

    private fun verileriInternettenAl(){
        besinYukleniyor.value = true

        disposable.add(
                besinApiServis.getData()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<List<Besin>>(){
                        override fun onSuccess(t: List<Besin>) {
                                sqliteSakla(t)
                            Toast.makeText(getApplication(),"Besinleri Internetten aldık",Toast.LENGTH_LONG).show()
                        }

                        override fun onError(e: Throwable) {
                            besinHataMesaji.value = true
                            besinYukleniyor.value = false
                            e.printStackTrace()

                        }

                    })
        )

    }

    private fun besinleriGoster(besinlerListesi : List<Besin>){
        besinler.value = besinlerListesi
        besinHataMesaji.value = false
        besinYukleniyor.value = false

    }
    private fun sqliteSakla (besinListesi : List<Besin>){

        launch {
                val dao = BesinDatabase(getApplication()).besinDao()
                dao.deleteAllBesin()
            val uuidListesi = dao.insertAll(*besinListesi.toTypedArray())//Listeyi tekil hale getiriyor
            var i = 0
            while (i<besinListesi.size){
                besinListesi[i].uuid = uuidListesi[i].toInt()
                i += 1
                //Sqlite' da oluşturulan veri id'lerini modelimizin içerisine koyuyoruz
            }
            besinleriGoster(besinListesi)
        }

        ozelSharedPreferences.zamaniKaydet(System.nanoTime())
    }

}