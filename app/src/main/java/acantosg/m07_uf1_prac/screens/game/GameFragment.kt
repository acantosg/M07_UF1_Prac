package acantosg.m07_uf1_prac.screens.game

import acantosg.m07_uf1_prac.databinding.GameFragmentBinding
import acantosg.m07_uf1_prac.databinding.LightFragmentBinding
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.widget.TableRow
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController


class GameFragment : Fragment() {

    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(this).get(GameViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = GameFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        //generamos un light_fragment por cada posición de la matriz
        val size = viewModel.size
        for (i in 0 until size) {
            val row = TableRow(context);
            for (j in 0 until size) {
                val lightBinding = LightFragmentBinding.inflate(inflater)
                lightBinding.lifecycleOwner = viewLifecycleOwner
                lightBinding.viewModel = viewModel
                //indicamos la posición del fragmento en la matriz
                lightBinding.y = i
                lightBinding.x = j
                row.addView(lightBinding.root)
            }
            binding.lightTable.addView(row)
        }

        //observador para finalizar el juego
        viewModel.finish.observe(viewLifecycleOwner) { finished ->
            if (finished) {
                gameFinished()
            }
        }

        //añadimos la acción de navegación al botón de volver al título
        binding.giveUpButton.setOnClickListener() {
            findNavController().navigate(GameFragmentDirections.actionGameFragmentToTitleFragment())
        }

        //y animamos el botón
        val fadeIn: Animation = AlphaAnimation(0f, 1f)
        fadeIn.interpolator = DecelerateInterpolator()
        fadeIn.duration = 5000
        binding.giveUpButton.animation = fadeIn

        return binding.root
    }

    //terminamos el juego, pasando un String con la puntuación final
    private fun gameFinished() {
        val action = GameFragmentDirections.actionGameFragmentToScoreFragment()
        action.score = viewModel.timerText.value ?: "--:--"
        NavHostFragment.findNavController(this).navigate(action)
    }

}