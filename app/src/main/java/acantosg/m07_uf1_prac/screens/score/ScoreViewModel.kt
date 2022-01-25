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
            try {
                val url = URL("https://zenquotes.io/api/random/")
                with(url.openConnection() as HttpURLConnection) {
                    var result = inputStream.bufferedReader().readLine()
                    result = result.replace(".*q\":\"".toRegex(), "")
                    result = result.replace("\",\"h.*".toRegex(), "")
                    val finalResult = result.split("\",\"a\":\"".toRegex())
                    _quote.value = "${finalResult[0]}\n\t-${finalResult[1]}"
                }
            } catch (ex: Exception) {
                //si falla la llamada, no pasa nada, simplemente no mostraremos el texto
            }
        }
    }

}