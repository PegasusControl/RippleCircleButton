package mx.com.pegasus

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import com.example.root.kotlinappliacation.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //region Global Variables

    //endregion

    //region Activity Methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_main)

        Handler().postDelayed({
            rippleCircleButton.stopAnimation()
        }, 100)

        Handler().postDelayed({
            rippleCircleButton.stopAnimation()
            rippleCircleButton.secondaryCirclesAnimation = RippleCircleButton.COLLAPSE_AND_APPEAR_ANIMATION
            rippleCircleButton.secondaryCirclesNumber = 3
            rippleCircleButton.animationDuration = 1500
            rippleCircleButton.startAnimation()
            Handler().postDelayed({
                rippleCircleButton.stopAnimation()
                rippleCircleButton.secondaryCirclesAnimation = RippleCircleButton.PROGRESSIVE_ANIMATION
                rippleCircleButton.secondaryCirclesNumber = 7
                rippleCircleButton.animationDuration = 4000
                rippleCircleButton.startAnimation()
                Handler().postDelayed({
                    rippleCircleButton.stopAnimation()
                    rippleCircleButton.secondaryCirclesAnimation = RippleCircleButton.EXPAND_AND_DISAPPEAR_ANIMATION
                    rippleCircleButton.secondaryCirclesNumber = 7
                    rippleCircleButton.animationDuration = 4000
                    rippleCircleButton.startAnimation()
                }, 10000)
            }, 10000)
        }, 10000)

    }
    //endregion


}
