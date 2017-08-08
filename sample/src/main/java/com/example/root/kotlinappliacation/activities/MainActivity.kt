package com.example.root.kotlinappliacation.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import com.example.root.kotlinappliacation.R

class MainActivity : AppCompatActivity() {

    //region Global Variables

    //endregion

    //region Activity Methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_main)

//        simpleAnimation(3)
    }
    //endregion


}
