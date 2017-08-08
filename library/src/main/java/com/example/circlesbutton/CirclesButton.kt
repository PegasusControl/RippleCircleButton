package com.example.circlesbutton

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.toObservable
import kotlinx.android.synthetic.main.circles_button_layout.view.*
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by root on 8/3/17.
 */
class CirclesButton @JvmOverloads constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int = 0, defStyleRes: Int = 0) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    //region Constants
    companion object {
        const val PROGRESSIVE_ANIMATION = 0
        const val RANDOMLY_ANIMATION = 1
        const val EXPANDING_AND_DISAPPEARING_ANIMATION = 2
        const val COLLAPSING_AND_APPEARING_ANIMATION = 3
    }
    //endregion

    //region Global Variables
    var mMainCircleSize: Float = 0f
    var mMainCircleColor: Int = 0
    var mainCircleBackgroundImage: Drawable? = null
    var mainCircleBackgroundImageSize: Float = 0f

    var mSecondaryCirclesNumber: Int = 0
    var mSecondaryCirclesColor: Int = 0

    var mSecondaryCirclesAnimation: Int = 0
    var mAnimationDuration: Long = 0

    val mCircles: MutableList<View> = ArrayList()
    var mCirclesLoaded: Boolean = false

    //region Views
    lateinit var mMainCircle: View
    //endregion

    //endregion

    //region inflateCircles
    init {
        LayoutInflater.from(context).inflate(R.layout.circles_button_layout, this, true)
        context?.let {
            attrs?.let {
                val typedArray = context.obtainStyledAttributes(it, R.styleable.circlesButton, 0, 0)

                try {
                    mSecondaryCirclesNumber = typedArray.getInt(R.styleable.circlesButton_secondaryCirclesNumber, 0)
                    mSecondaryCirclesColor = typedArray.getColor(R.styleable.circlesButton_secondaryCirclesColor, 0)

                    mSecondaryCirclesAnimation = typedArray.getInt(R.styleable.circlesButton_secondaryCirclesAnimation, 0)
                    mAnimationDuration = typedArray.getInt(R.styleable.circlesButton_animationDuration, 0).toLong()

                    mMainCircleSize = typedArray.getDimension(R.styleable.circlesButton_mainCircleSize, 0f)
                    mMainCircleColor = typedArray.getColor(R.styleable.circlesButton_mainCircleColor, 0)

                    mainCircleBackgroundImageSize = typedArray.getDimension(R.styleable.circlesButton_mainCircleBackgroundImageSize, 0f)
                    if (typedArray.hasValue(R.styleable.circlesButton_mainCircleBackgroundImage)) {
                        mainCircleBackgroundImage = typedArray.getDrawable(R.styleable.circlesButton_mainCircleBackgroundImage)
                    }
                } finally {
                    typedArray.recycle()
                }
                inflateCircles()
            }
        }
    }
    //endregion

    //region CirclesButton functions
    fun inflateCircles() {
        val mainCircleLayoutParams = FrameLayout.LayoutParams(mMainCircleSize.toInt(), mMainCircleSize.toInt())
        mainCircleLayoutParams.gravity = Gravity.CENTER

        val mainCircleFillingDrawable = ContextCompat.getDrawable(context, R.drawable.bg_main_circle)
        mMainCircleColor.let {
            mainCircleFillingDrawable.colorFilter = PorterDuffColorFilter(it, PorterDuff.Mode.MULTIPLY)
        }

        main_circle.background = mainCircleFillingDrawable
        main_circle.layoutParams = mainCircleLayoutParams

        if (mainCircleBackgroundImage != null) {
            val mainCircleImageLayoutParams = FrameLayout.LayoutParams(mainCircleBackgroundImageSize.toInt(), mainCircleBackgroundImageSize.toInt())
            main_circle_image.setImageDrawable(mainCircleBackgroundImage)
            mainCircleImageLayoutParams.gravity = Gravity.CENTER
            main_circle_image.layoutParams = mainCircleImageLayoutParams
        }

        when (mSecondaryCirclesAnimation) {
            PROGRESSIVE_ANIMATION -> simpleAnimation(mSecondaryCirclesNumber)
            RANDOMLY_ANIMATION -> simpleAnimation(mSecondaryCirclesNumber)
            EXPANDING_AND_DISAPPEARING_ANIMATION -> dynamicAnimation(mSecondaryCirclesNumber)
            COLLAPSING_AND_APPEARING_ANIMATION -> dynamicAnimation(mSecondaryCirclesNumber)
        }
    }

    //region PROGRESSIVE AND RANDOMLY ANIMATION
    fun simpleAnimation(circles: Int) {
        secondary_circle_container.viewTreeObserver.addOnGlobalLayoutListener {
            if (!mCirclesLoaded) {
                mCirclesLoaded = true

                val maxDpis = secondary_circle_container.width
                val minDpis = main_circle.width

                for (x in 1..circles) {
                    val diameter = when (mSecondaryCirclesAnimation) {
                        PROGRESSIVE_ANIMATION -> (((maxDpis - minDpis) / circles) * x) + minDpis
                        RANDOMLY_ANIMATION -> Random().nextInt(maxDpis + 1 - minDpis) + minDpis
                        else -> (((maxDpis - minDpis) / circles) * x) + minDpis
                    }

                    val frameParams: FrameLayout.LayoutParams = FrameLayout.LayoutParams(diameter, diameter)
                    frameParams.gravity = Gravity.CENTER

                    val circle: View = View(context)

                    val secondaryCircleFillingDrawable = ContextCompat.getDrawable(context, R.drawable.bg_secondary_circle)
                    mSecondaryCirclesColor.let {
                        secondaryCircleFillingDrawable.colorFilter = PorterDuffColorFilter(it, PorterDuff.Mode.MULTIPLY)
                    }

                    circle.background = secondaryCircleFillingDrawable
                    circle.layoutParams = frameParams

                    secondary_circle_container.addView(circle)
                    mCircles.add(circle)

                    simpleAnimation(circle, diameter, minDpis)
                }
            }
        }
    }

    fun simpleAnimation(view: View, maxIncrease: Int, maxDecrease: Int) {
        //animation
        val increaseAnimation = ValueAnimator.ofInt(maxIncrease, maxDecrease)
        increaseAnimation.addUpdateListener({
            val value = it.animatedValue as Int
            val layoutParams = view.layoutParams
            layoutParams.height = value
            layoutParams.width = value
            view.layoutParams = layoutParams
        })

        val decreaseAnimation = ValueAnimator.ofInt(maxDecrease, maxIncrease)
        decreaseAnimation.addUpdateListener({
            val value = it.animatedValue as Int
            val layoutParams = view.layoutParams
            layoutParams.height = value
            layoutParams.width = value
            view.layoutParams = layoutParams
        })

        val animationDuration = when (mSecondaryCirclesAnimation) {
            PROGRESSIVE_ANIMATION -> if (mAnimationDuration.toInt() == 0) 1000 else mAnimationDuration
            RANDOMLY_ANIMATION -> (Random().nextInt(3000 + 1 - 500) + 500).toLong()
            else -> 1000
        }

        mAnimationDuration = animationDuration

        increaseAnimation.duration = mAnimationDuration
        decreaseAnimation.duration = mAnimationDuration

        increaseAnimation.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {}

            override fun onAnimationCancel(p0: Animator?) {}

            override fun onAnimationRepeat(p0: Animator?) {}

            override fun onAnimationEnd(animator: Animator) {
                decreaseAnimation.start()
            }
        })

        decreaseAnimation.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {}

            override fun onAnimationCancel(p0: Animator?) {}

            override fun onAnimationRepeat(p0: Animator?) {}

            override fun onAnimationEnd(animator: Animator) {
                increaseAnimation.start()
            }
        })

        increaseAnimation.start()
    }
    //endregion

    //region DISAPPEARING_ANIMATION
    fun dynamicAnimation(circles: Int) {
        secondary_circle_container.viewTreeObserver.addOnGlobalLayoutListener {
            if (!mCirclesLoaded) {
                mCirclesLoaded = true

                val maxDpis = if (mSecondaryCirclesAnimation == EXPANDING_AND_DISAPPEARING_ANIMATION) secondary_circle_container.width else main_circle.width
                val minDpis = if (mSecondaryCirclesAnimation == EXPANDING_AND_DISAPPEARING_ANIMATION) main_circle.width else secondary_circle_container.width

                for (x in 1..circles) {
                    val diameter = minDpis

                    val frameParams: FrameLayout.LayoutParams = FrameLayout.LayoutParams(diameter, diameter)
                    frameParams.gravity = Gravity.CENTER

                    val circle: View = View(context)
                    val secondaryCircleFillingDrawable = ContextCompat.getDrawable(context, R.drawable.bg_secondary_circle)
                    mSecondaryCirclesColor.let {
                        secondaryCircleFillingDrawable.colorFilter = PorterDuffColorFilter(it, PorterDuff.Mode.MULTIPLY)
                    }

                    circle.background = secondaryCircleFillingDrawable
                    circle.layoutParams = frameParams

                    if (mSecondaryCirclesAnimation == COLLAPSING_AND_APPEARING_ANIMATION) {
                        circle.background.alpha = 0
                    }

                    secondary_circle_container.addView(circle)
                    mCircles.add(circle)
                }

                dynamicAnimation(minDpis, maxDpis)
            }
        }
    }

    fun dynamicAnimation(minSize: Int, maxSize: Int) {
        val animationDuration = if (mAnimationDuration.toInt() == 0) 1000 else mAnimationDuration
        mAnimationDuration = animationDuration
        val durationBetweenAnimations = (mAnimationDuration / mCircles.size)
        Observable.zip(
                mCircles.toObservable(),
                Observable.interval(durationBetweenAnimations, TimeUnit.MILLISECONDS),
                BiFunction { t1: View, t2: Long -> t1.to(View(context)) })
                .observeOn(AndroidSchedulers.mainThread())
                .map { it.first }
                .subscribe({
                    dynamicAnimation(it, minSize, maxSize)
                })
    }


    fun dynamicAnimation(view: View, minSize: Int, maxSize: Int) {
        //animation
        val increaseAnimation = ValueAnimator.ofInt(minSize, maxSize)
        increaseAnimation.interpolator = AccelerateDecelerateInterpolator()
        val increaseAgainAnimation = ValueAnimator.ofInt(minSize, maxSize)
        increaseAgainAnimation.interpolator = AccelerateDecelerateInterpolator()

        val updateListener = ValueAnimator.AnimatorUpdateListener {
            valueAnimator ->
            val value = valueAnimator.animatedValue as Int
            val layoutParams = view.layoutParams
            layoutParams.height = value
            layoutParams.width = value
            view.layoutParams = layoutParams

            val zeroPercent = maxSize.toFloat() - minSize.toFloat()
            var currentPercent = ((((maxSize.toFloat() - value.toFloat()) * 700f) / zeroPercent))
            currentPercent = if (mSecondaryCirclesAnimation == COLLAPSING_AND_APPEARING_ANIMATION) currentPercent - 700 else currentPercent
            currentPercent = Math.abs(currentPercent.toInt()).toFloat()

            view.background.alpha = currentPercent.toInt()
        }

        increaseAnimation.addUpdateListener(updateListener)
        increaseAgainAnimation.addUpdateListener(updateListener)
        increaseAnimation.duration = mAnimationDuration
        increaseAgainAnimation.duration = mAnimationDuration

        increaseAnimation.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {}
            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationRepeat(p0: Animator?) {}
            override fun onAnimationEnd(animator: Animator) {
                increaseAgainAnimation.start()
            }
        })

        increaseAgainAnimation.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {}
            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationRepeat(p0: Animator?) {}
            override fun onAnimationEnd(animator: Animator) {
                increaseAnimation.start()
            }
        })

        increaseAnimation.start()
    }
    //endregion

    //endregion

    //region Getter & Setter

    //endregion

}