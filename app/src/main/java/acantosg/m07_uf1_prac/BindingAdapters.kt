package acantosg.m07_uf1_prac

import acantosg.m07_uf1_prac.R
import android.widget.ImageView
import androidx.databinding.BindingAdapter

//binding para enlazar el valor de la luz en la matriz con una imagen correspondiente
@BindingAdapter("on")
fun bindOnOff(tile: ImageView, on: Boolean) {
    if (on) {
        tile.setImageResource(R.drawable.green)
    } else {
        tile.setImageResource(R.drawable.red)
    }
}
