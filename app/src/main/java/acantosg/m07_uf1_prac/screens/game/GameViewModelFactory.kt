package acantosg.m07_uf1_prac.screens.game

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GameViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            //para generar el viewModel, recuperamos las preferencias
            var sharedPreferences: SharedPreferences =
                application.applicationContext.getSharedPreferences(application.applicationContext.getPackageName() + "_preferences", Context.MODE_PRIVATE)
            val boardSize = sharedPreferences.getString("boardSize", "6")?.toInt() ?: 6
            var complexity = sharedPreferences.getString("complexity", "Normal") ?: "Normal"
            return GameViewModel(boardSize, complexity) as T
        }
        throw IllegalArgumentException("Clase de ViewModel desconocida, esta Factory es exclusiva para GameViewModel")
    }

}