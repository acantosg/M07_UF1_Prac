package acantosg.m07_uf1_prac.screens.score

import acantosg.m07_uf1_prac.databinding.ScoreFragmentBinding
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import androidx.navigation.fragment.findNavController

class ScoreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ScoreFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        //recuperamos la puntuación para mostrarla por pantalla
        binding.score = ScoreFragmentArgs.fromBundle(requireArguments()).score

        //añadimos la acción de navegación al botón de volver a jugar
        binding.replayButton.setOnClickListener() {
            findNavController().navigate(ScoreFragmentDirections.actionScoreFragmentToGameFragment())
        }

        //y animamos el botón
        val fadeIn: Animation = AlphaAnimation(0f, 1f)
        fadeIn.interpolator = DecelerateInterpolator()
        fadeIn.duration = 5000
        binding.replayButton.animation = fadeIn

        return binding.root
    }

}