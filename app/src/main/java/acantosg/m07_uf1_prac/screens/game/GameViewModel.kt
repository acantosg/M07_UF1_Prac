package acantosg.m07_uf1_prac.screens.game

import acantosg.m07_uf1_prac.screens.settings.countertype
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel(
    //tamaño deaseado de la matriz de luces
    val boardSize: Int,
    //complejidad del tablero, (afecta el número de luces activadas aleatoriamente el inicio de la partida)
    val complexity: String) : ViewModel() {
    private var complexitySetting = when (complexity) {
        "Baja" -> 12
        "Alta" -> 4
        else -> 8
    }
    private val complexityFactor = ((boardSize * boardSize) / complexitySetting) + 1

    var lights: MutableList<MutableList<MutableLiveData<Boolean>>> = mutableListOf() //matriz de luces

    //String para mostrar el tiempo de juego
    private val _timerText = MutableLiveData<String>()
    val timerText: LiveData<String> = _timerText

    //tiempo de juego
    private var time = 0

    //contador para incrementar el tiempo de juego
    private val timer: CountDownTimer
    private val SECOND = 1000L //milisegundos por cada segundo

    //livedata para reaccionar al fin del juego
    private val _finish = MutableLiveData(false)
    val finish: LiveData<Boolean> = _finish

    init {
        //por cada posición de la matriz, creamos un boolean
        //también tendremos una lista de todas las posiciones de la matriz
        val set: MutableList<Pair<Int, Int>> = mutableListOf()
        for (i in 0 until boardSize) {
            val lightRow = mutableListOf<MutableLiveData<Boolean>>()
            for (j in 0 until boardSize) {
                set.add(Pair(j, i))
                lightRow.add(MutableLiveData<Boolean>(true))
            }
            lights.add(lightRow)
        }

        //simulamos un click en unas cuantas posiciones aleatorias de la matriz
        //esto genera un tablero que siempre posee la misma complejidad y es posible resolver
        for (i in 0 until complexityFactor) {
            val rand = (Math.random() * set.size).toInt()
            val tile = set[rand]
            onClick(tile.first, tile.second)
            //quitamos cada posición utilizada, para evitar que se use otra vez
            set.removeAt(rand)
        }

        //inicializamos el string del tiempo
        updateTimerText()
        timer = object : CountDownTimer(Long.MAX_VALUE, SECOND) {
            //cada segundo, incrementamos el tiempo y actualizamos su string
            override fun onTick(m: Long) {
                time += 1
                updateTimerText()
            }
            override fun onFinish() {
            }
        }
        timer.start()
    }

    //pasamos los segundos a String, si es más de 99:59, lo dejamos en --:--
    fun updateTimerText() {
        if (countertype) {
            if (time >= 60 * 100) {
                _timerText.value = "--:--"
            } else {
                val min: Int = time / 60
                val sec: Int = time % 60
                _timerText.value = String.format("%02d:%02d", min, sec)
            }
        } else {
            if (time >= 60 * 100) {
                _timerText.value = "-----"
            } else {
                _timerText.value = String.format("%05d", time)
            }
        }
    }

    //al hacer click en una luz...
    fun onClick(x: Int, y: Int) {
        //por cada posición adyacente (y la propia posición)
        val set: Set<Pair<Int, Int>> = setOf(
            Pair(x, y-1),
            Pair(x-1, y),
            Pair(x, y),
            Pair(x+1, y),
            Pair(x, y+1)
        )
        for (pair in set) {
            //si la posición no está fuera de la matriz, invertimos la luz
            if (pair.first >= 0 && pair.second >= 0 && pair.first < boardSize && pair.second < boardSize) {
                lights[pair.second][pair.first].value = !(lights[pair.second][pair.first].value ?: false)
            }
        }
        //comprobamos si el juego ha finalizado
        checkCompletion()
        //indicamos que el tablero ha cambiado
    }

    fun checkCompletion() {
        var boardCompleted = true
        //comprobamos todas las luces
        for (lightRow in lights) {
            for (lightTile in lightRow) {
                if (lightTile.value == false) {
                    //si está apagada, no hemos completado el tablero
                    boardCompleted = false
                }
            }
        }
        //si no había ninguna luz apagada, se acaba el juego
        if (boardCompleted) {
            _finish.value = true
        }
    }

}