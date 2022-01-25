package acantosg.m07_uf1_prac.screens.score

import acantosg.m07_uf1_prac.R
import acantosg.m07_uf1_prac.databinding.ScoreFragmentBinding
import acantosg.m07_uf1_prac.screens.game.GameViewModel
import android.content.Context
import android.content.SharedPreferences
import android.os.Binder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection

class ScoreFragment : Fragment() {

    private lateinit var viewModel: ScoreViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = ScoreFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel = ViewModelProvider(this).get(ScoreViewModel::class.java)

        val sharedPreferences: SharedPreferences =
            requireContext().getSharedPreferences(requireContext().getPackageName() + "_preferences", Context.MODE_PRIVATE)

        //recuperamos los settings del modo de juego para mostrarlos por pantalla
        binding.gamemode = "${getString(R.string.gameModeFlavor)} ${ScoreFragmentArgs.fromBundle(requireArguments()).boardSize} (${ScoreFragmentArgs.fromBundle(requireArguments()).complexity})"

        //recuperamos el nickname para mostrarlo por pantalla
        binding.nickname = "${sharedPreferences.getString("nickname", "Nombre") ?: "Nombre"}${getString(R.string.nicknameFlavor)}"

        //recuperamos la puntuación para mostrarla por pantalla
        binding.score = ScoreFragmentArgs.fromBundle(requireArguments()).score

        //añadimos la acción de navegación al botón de volver a jugar
        binding.replayButton.setOnClickListener() {
            findNavController().navigate(ScoreFragmentDirections.actionScoreFragmentToGameFragment())
        }


        viewModel.quote.observe(viewLifecycleOwner) {
            val fadeIn: Animation = AlphaAnimation(0f, 1f)
            fadeIn.interpolator = DecelerateInterpolator()
            fadeIn.duration = 4000
            binding.quote.animation = fadeIn
            binding.quote.text = it
        }

        //y animamos el botón
        val fadeIn: Animation = AlphaAnimation(0f, 1f)
        fadeIn.interpolator = DecelerateInterpolator()
        fadeIn.duration = 4000
        binding.replayButton.animation = fadeIn

        return binding.root
    }

}
