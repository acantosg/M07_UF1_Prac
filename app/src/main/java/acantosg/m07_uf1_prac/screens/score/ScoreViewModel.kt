package acantosg.m07_uf1_prac.screens.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL

class ScoreViewModel : ViewModel() {

    var job: Job? = null

    private val _quote = MutableLiveData<String>("")
    val quote: LiveData<String> = _quote

    init {
        getQuote()
    }

    private fun getQuote() {
        job = viewModelScope.launch(Dispatchers.IO) {
            val url = URL("https://zenquotes.io/api/random/")
            with(url.openConnection() as HttpURLConnection) {
                var result = ""
                // !! simplificar a leer solo una linea
                inputStream.bufferedReader().use {
                    it.lines().forEach { line ->
                        result = line
                    }
                }
                result = result.replace(".*q\":\"".toRegex(), "")
                result = result.replace("\",\"h.*".toRegex(), "")
                val finalResult = result.split("\",\"a\":\"".toRegex())
                _quote.postValue("${finalResult[0]}\n\t-${finalResult[1]}")
            }
        }
    }

}