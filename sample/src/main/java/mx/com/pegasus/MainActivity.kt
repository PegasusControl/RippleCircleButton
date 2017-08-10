package mx.com.pegasus

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
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

        rippleCircleButton.mainCircleSize = 100f //Value is in DPs
        rippleCircleButton.mainCircleColor = Color.parseColor("#CCFFFFFF")

        rippleCircleButton.mainCircleBackgroundImage = ContextCompat.getDrawable(baseContext, R.drawable.ic_power_settings_new_blue_700_48dp)
        rippleCircleButton.mainCircleBackgroundImageSize = 100f //Value is in DPs

        rippleCircleButton.secondaryCirclesNumber = 3
        rippleCircleButton.secondaryCirclesColor = Color.parseColor("#88FFFFFF")

        rippleCircleButton.animationDuration = 1700 //Value is in Milliseconds
        rippleCircleButton.secondaryCirclesAnimation = RippleCircleButton.EXPAND_AND_DISAPPEAR_ANIMATION


//        Observable.zip(
//                Observable.range(0, 10),
//                Observable.interval(6000, TimeUnit.MILLISECONDS),
//                BiFunction { t1: Int, t2: Long -> t1 })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    if(it == 1) rippleCircleButton.startAnimation() else rippleCircleButton.stopAnimation()
//                    if(it == 2) rippleCircleButton.secondaryCirclesColor = Color.parseColor("#66FF0000")
//                    if(it == 3) rippleCircleButton.secondaryCirclesColor = Color.parseColor("#FFFFFF")
//                    if(it == 4) rippleCircleButton.mainCircleSize = baseContext.resources.displayMetrics.density * 200 + 0.5f
//                    if(it == 5) rippleCircleButton.secondaryCirclesNumber = 5
////                    if(it == 6) rippleCircleButton.mainCircleSize = baseContext.resources.displayMetrics.density * 200 + 0.5f
////                    if(it == 7) rippleCircleButton.mainCircleSize = baseContext.resources.displayMetrics.density * 200 + 0.5f
////                    if(it == 8) rippleCircleButton.mainCircleSize = baseContext.resources.displayMetrics.density * 200 + 0.5f
//                    if(it == 10) rippleCircleButton.stopAnimation()
//                })
    }
    //endregion


}
