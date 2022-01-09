package acantosg.m07_uf1_prac.screens.title

import acantosg.m07_uf1_prac.R
import acantosg.m07_uf1_prac.databinding.ScoreFragmentBinding
import acantosg.m07_uf1_prac.databinding.TitleFragmentBinding
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import android.view.animation.DecelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation

class TitleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = TitleFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        //añadimos la acción de navegación al botón de empezar el juego
        binding.startButton.setOnClickListener {
            findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToGameFragment())
        }

        //y animamos el botón
        val fadeIn: Animation = AlphaAnimation(0f, 1f)
        fadeIn.interpolator = DecelerateInterpolator()
        fadeIn.duration = 1000
        binding.startButton.animation = fadeIn

        return binding.root
    }
}