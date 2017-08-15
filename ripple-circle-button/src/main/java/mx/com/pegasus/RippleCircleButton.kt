package mx.com.pegasus

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.toObservable
import kotlinx.android.synthetic.main.circles_button_layout.view.*
import mx.com.pegasus.ripplecirclebutton.R
import mx.com.pegasus.view_utils.ViewUtils
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by angeeeld on 8/3/17.
 */
class RippleCircleButton @JvmOverloads constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int = 0, defStyleRes: Int = 0) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    //region Constants
    companion object {
        const val PROGRESSIVE_ANIMATION = 0
        const val RANDOMLY_ANIMATION = 1
        const val EXPAND_AND_DISAPPEAR_ANIMATION = 2
        const val COLLAPSE_AND_APPEAR_ANIMATION = 3
    }
    //endregion

    //region Global Variables
    //region backing properties
    private var _mainCircleSizeInDp: Float = 0f
    private var _mainCircleSize: Float = 0f
    private var _mainCircleColor: Int = 0
    private var _mainCircleBackgroundImage: Drawable? = null
    private var _mainCircleBackgroundImageSize: Float = 0f

    private var _secondaryCirclesNumber: Int = 0
    private var _secondaryCirclesColor: Int = 0

    private var _secondaryCirclesAnimation: Int = 0
    private var _animationDuration: Long = 0

    private var _circles: MutableList<View> = ArrayList()
    //endregion
    //region Getters && setters
    var mainCircleSize: Float
        get() {
            return _mainCircleSize
        }
        set(value) {
            _mainCircleSizeInDp = value
            _mainCircleSize = ViewUtils.dpToPx(context, value.toInt())
            val mainCircleLayoutParams = FrameLayout.LayoutParams(_mainCircleSize.toInt(), _mainCircleSize.toInt())
            mainCircleLayoutParams.gravity = Gravity.CENTER
            main_circle.layoutParams = mainCircleLayoutParams
        }

    var mainCircleColor: Int
        get() {
            return _mainCircleColor
        }
        set(value) {
            _mainCircleColor = value
            val mainCircleFillingDrawable = ContextCompat.getDrawable(context, R.drawable.bg_main_circle)
            mainCircleFillingDrawable.colorFilter = PorterDuffColorFilter(_mainCircleColor, PorterDuff.Mode.MULTIPLY)
            main_circle.background = mainCircleFillingDrawable
        }

    var mainCircleBackgroundImage: Drawable?
        get() {
            return _mainCircleBackgroundImage
        }
        set(value) {
            _mainCircleBackgroundImage = value
            if (_mainCircleBackgroundImage != null) {
                val mainCircleImageLayoutParams = FrameLayout.LayoutParams(_mainCircleBackgroundImageSize.toInt(), _mainCircleBackgroundImageSize.toInt())
                main_circle_image.setImageDrawable(_mainCircleBackgroundImage)
                mainCircleImageLayoutParams.gravity = Gravity.CENTER
                main_circle_image.layoutParams = mainCircleImageLayoutParams
            }
        }

    var mainCircleBackgroundImageSize: Float
        get() {
            return _mainCircleBackgroundImageSize
        }
        set(value) {
            _mainCircleBackgroundImageSize = ViewUtils.dpToPx(context, value.toInt())
            if (mainCircleBackgroundImage != null) {
                val mainCircleImageLayoutParams = FrameLayout.LayoutParams(_mainCircleBackgroundImageSize.toInt(), _mainCircleBackgroundImageSize.toInt())
                main_circle_image.setImageDrawable(_mainCircleBackgroundImage)
                mainCircleImageLayoutParams.gravity = Gravity.CENTER
                main_circle_image.layoutParams = mainCircleImageLayoutParams
            }
        }

    var secondaryCirclesNumber: Int
        get() {
            return _secondaryCirclesNumber
        }
        set(value) {
            _secondaryCirclesNumber = value
            cleanAndDrawNewCircles()
        }

    var secondaryCirclesColor: Int
        get() {
            return _secondaryCirclesColor
        }
        set(value) {
            _secondaryCirclesColor = value
        }

    var secondaryCirclesAnimation: Int
        get() {
            return _secondaryCirclesAnimation
        }
        set(value) {
            _secondaryCirclesAnimation = value
            cleanAndDrawNewCircles()
        }

    var animationDuration: Long
        get() {
            return _animationDuration
        }
        set(value) {
            _animationDuration = value
            cleanAndDrawNewCircles()
        }
    //endregion
    //region Callbacks
    private var mOnClickListener: OnMainCircleClickListener? = null
    //endregion
    //endregion

    //region inflateCircles
    /**
     * The init method is called automatically when the constructor is called
     */
    init {
        LayoutInflater.from(context).inflate(R.layout.circles_button_layout, this, true)
        context?.let {
            attrs?.let {
                val typedArray = context.obtainStyledAttributes(it, R.styleable.circlesButton, 0, 0)

                try {
                    _secondaryCirclesNumber =
                            typedArray.getInt(R.styleable.circlesButton_secondaryCirclesNumber, 1)
                    _secondaryCirclesColor =
                            typedArray.getColor(R.styleable.circlesButton_secondaryCirclesColor, Color.parseColor("#88FFFFFF"))

                    _secondaryCirclesAnimation =
                            typedArray.getInt(R.styleable.circlesButton_secondaryCirclesAnimation, EXPAND_AND_DISAPPEAR_ANIMATION)
                    _animationDuration =
                            typedArray.getInt(R.styleable.circlesButton_animationDuration, 0).toLong()

                    _mainCircleSize =
                            typedArray.getDimension(R.styleable.circlesButton_mainCircleSize, 0f)
                    _mainCircleSizeInDp = _mainCircleSizeInDp

                    _mainCircleColor =
                            typedArray.getColor(R.styleable.circlesButton_mainCircleColor, Color.parseColor("#CCFFFFFF"))

                    _mainCircleBackgroundImageSize =
                            typedArray.getDimension(R.styleable.circlesButton_mainCircleBackgroundImageSize, 0f)
                    if (typedArray.hasValue(R.styleable.circlesButton_mainCircleBackgroundImage)) {
                        _mainCircleBackgroundImage =
                                typedArray.getDrawable(R.styleable.circlesButton_mainCircleBackgroundImage)
                    }
                } finally {
                    typedArray.recycle()
                }
                inflateCircles()
            }
        }
    }
    //endregion

    //region RippleCircleButton functions

    fun startAnimation() {
        stopAnimation()
        cleanAndDrawNewCircles()
        inflateCircles()
    }

    fun stopAnimation() {
        _mainCircleSize = _mainCircleSizeInDp
        cleanViews()
    }

    private fun cleanViews() {
        inflateCirclesInterval?.let {
            inflateCirclesInterval?.dispose()
            inflateCirclesInterval = null
        }

        secondary_circle_container.removeAllViews()
        _circles = ArrayList()
    }

    private fun cleanAndDrawNewCircles() {
        cleanViews()
    }


    lateinit var mComponentViewTreeObserver: ViewTreeObserver.OnGlobalLayoutListener

    fun initializeComponentViewTreeObserver() {
        mComponentViewTreeObserver = object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                mainCircleSize = if (_mainCircleSize == 0f) (width / 3).toFloat() else _mainCircleSize

                mainCircleColor = _mainCircleColor
                mainCircleBackgroundImage = _mainCircleBackgroundImage

                initMainCircularImageView()

                when (_secondaryCirclesAnimation) {
                    PROGRESSIVE_ANIMATION -> simpleAnimation()
                    RANDOMLY_ANIMATION -> simpleAnimation()
                    EXPAND_AND_DISAPPEAR_ANIMATION -> dynamicAnimation()
                    COLLAPSE_AND_APPEAR_ANIMATION -> dynamicAnimation()
                }
            }
        }
    }

    /**
     * This function inflate the main circle and then calls another methods for create the secondary
     * circles
     */
    private fun inflateCircles() {
        initializeComponentViewTreeObserver()
        this.viewTreeObserver.addOnGlobalLayoutListener(mComponentViewTreeObserver)
    }

    private fun initMainCircularImageView() {
        main_circle_image.setOnClickListener({ mOnClickListener?.let { it.onClick() } })
        main_circle_image.setBorderColor(Color.parseColor("#00FFFFFF"))
        main_circle_image.setBorderWidth(0f)
    }

    //region PROGRESSIVE AND RANDOMLY ANIMATION

    lateinit var mCirclesContainerViewTreeObserver: ViewTreeObserver.OnGlobalLayoutListener

    private fun initializeCirclesContainerViewTreeObserver() {
        mCirclesContainerViewTreeObserver = object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                secondary_circle_container.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val maxDpis = secondary_circle_container.width
                val minDpis = main_circle.width

                for (x in 1.._secondaryCirclesNumber) {
                    val diameter = when (_secondaryCirclesAnimation) {
                        PROGRESSIVE_ANIMATION -> (((maxDpis - minDpis) / _secondaryCirclesNumber) * x) + minDpis
                        RANDOMLY_ANIMATION -> Random().nextInt(maxDpis + 1 - minDpis) + minDpis
                        else -> (((maxDpis - minDpis) / _secondaryCirclesNumber) * x) + minDpis
                    }

                    val frameParams: FrameLayout.LayoutParams = FrameLayout.LayoutParams(diameter, diameter)
                    frameParams.gravity = Gravity.CENTER

                    val circle: View = View(context)

                    val secondaryCircleFillingDrawable = ContextCompat.getDrawable(context, R.drawable.bg_secondary_circle)
                    _secondaryCirclesColor.let {
                        secondaryCircleFillingDrawable.colorFilter = PorterDuffColorFilter(it, PorterDuff.Mode.MULTIPLY)
                    }

                    circle.background = secondaryCircleFillingDrawable
                    circle.layoutParams = frameParams

                    secondary_circle_container.addView(circle)
                    _circles.add(circle)

                    simpleAnimation(circle, diameter, minDpis)
                }
            }
        }
    }

    private fun simpleAnimation() {
        initializeCirclesContainerViewTreeObserver()
        secondary_circle_container.viewTreeObserver
                .addOnGlobalLayoutListener(mCirclesContainerViewTreeObserver)
    }

    private fun simpleAnimation(view: View, maxIncrease: Int, maxDecrease: Int) {
        //animation
        val increaseAnimation = ValueAnimator.ofInt(maxIncrease, maxDecrease)
        increaseAnimation.addUpdateListener({
            val value = it.animatedValue as Int
            val layoutParams = view.layoutParams
            layoutParams.height = value
            layoutParams.width = value
            view.layoutParams = layoutParams
            val circleColor = ContextCompat.getDrawable(context, R.drawable.bg_secondary_circle)
            _secondaryCirclesColor.let {
                circleColor.colorFilter = PorterDuffColorFilter(it, PorterDuff.Mode.MULTIPLY)
            }
            view.background = circleColor
        })

        val decreaseAnimation = ValueAnimator.ofInt(maxDecrease, maxIncrease)
        decreaseAnimation.addUpdateListener({
            val value = it.animatedValue as Int
            val layoutParams = view.layoutParams
            layoutParams.height = value
            layoutParams.width = value
            view.layoutParams = layoutParams
            val circleColor = ContextCompat.getDrawable(context, R.drawable.bg_secondary_circle)
            _secondaryCirclesColor.let {
                circleColor.colorFilter = PorterDuffColorFilter(it, PorterDuff.Mode.MULTIPLY)
            }
            view.background = circleColor
        })

        val animationDuration = when (_secondaryCirclesAnimation) {
            PROGRESSIVE_ANIMATION -> if (_animationDuration.toInt() == 0) 1000 else _animationDuration
            RANDOMLY_ANIMATION -> (Random().nextInt(3000 + 1 - 500) + 500).toLong()
            else -> 1000
        }

        _animationDuration = animationDuration

        increaseAnimation.duration = _animationDuration
        decreaseAnimation.duration = _animationDuration

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

    lateinit var mDynamicCirclesContainerViewTreeObserver: ViewTreeObserver.OnGlobalLayoutListener

    private fun initializeDynamicCirclesContainerViewTreeObserver() {
        mDynamicCirclesContainerViewTreeObserver = object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                secondary_circle_container.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val maxDpis = if (_secondaryCirclesAnimation == EXPAND_AND_DISAPPEAR_ANIMATION) secondary_circle_container.width else main_circle.width
                val minDpis = if (_secondaryCirclesAnimation == EXPAND_AND_DISAPPEAR_ANIMATION) main_circle.width else secondary_circle_container.width

                for (x in 1.._secondaryCirclesNumber) {
                    val diameter = minDpis

                    val frameParams: FrameLayout.LayoutParams = FrameLayout.LayoutParams(diameter, diameter)
                    frameParams.gravity = Gravity.CENTER

                    val circle: View = View(context)
                    val secondaryCircleFillingDrawable = ContextCompat.getDrawable(context, R.drawable.bg_secondary_circle)
                    _secondaryCirclesColor.let {
                        secondaryCircleFillingDrawable.colorFilter = PorterDuffColorFilter(it, PorterDuff.Mode.MULTIPLY)
                    }

                    circle.background = secondaryCircleFillingDrawable
                    circle.layoutParams = frameParams

                    if (_secondaryCirclesAnimation == COLLAPSE_AND_APPEAR_ANIMATION) {
                        circle.background.alpha = 0
                    }

                    secondary_circle_container.addView(circle)
                    _circles.add(circle)
                }

                dynamicAnimation(minDpis, maxDpis)
            }
        }
    }

    private fun dynamicAnimation() {
        initializeDynamicCirclesContainerViewTreeObserver()
        secondary_circle_container.viewTreeObserver
                .addOnGlobalLayoutListener(mDynamicCirclesContainerViewTreeObserver)
    }

    private var inflateCirclesInterval: Disposable? = null
    private fun dynamicAnimation(minSize: Int, maxSize: Int) {
        val animationDuration = if (_animationDuration.toInt() == 0) 1000 else _animationDuration
        _animationDuration = animationDuration
        val durationBetweenAnimations = (_animationDuration / _circles.size)
        inflateCirclesInterval = Observable.zip(
                _circles.toObservable(),
                Observable.interval(durationBetweenAnimations, TimeUnit.MILLISECONDS),
                BiFunction { t1: View, t2: Long -> t1.to(View(context)) })
                .observeOn(AndroidSchedulers.mainThread())
                .map { it.first }
                .subscribe({
                    dynamicAnimation(it, minSize, maxSize)
                })
    }


    private fun dynamicAnimation(view: View, minSize: Int, maxSize: Int) {
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

            val circleColor = ContextCompat.getDrawable(context, R.drawable.bg_secondary_circle)
            _secondaryCirclesColor.let {
                circleColor.colorFilter = PorterDuffColorFilter(it, PorterDuff.Mode.MULTIPLY)
            }
            view.background = circleColor

            val zeroPercent = maxSize.toFloat() - minSize.toFloat()
            var currentPercent = ((((maxSize.toFloat() - value.toFloat()) * 700f) / zeroPercent))
            currentPercent = if (_secondaryCirclesAnimation == COLLAPSE_AND_APPEAR_ANIMATION) currentPercent - 700 else currentPercent
            currentPercent = Math.abs(currentPercent.toInt()).toFloat()

            view.background.alpha = currentPercent.toInt()
        }

        increaseAnimation.addUpdateListener(updateListener)
        increaseAgainAnimation.addUpdateListener(updateListener)
        increaseAnimation.duration = _animationDuration
        increaseAgainAnimation.duration = _animationDuration

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
    fun setOnMainCircleClickListener(onMainCircleClickListener: OnMainCircleClickListener) {
        mOnClickListener = onMainCircleClickListener
    }
    //endregion

    //region interfaces
    @FunctionalInterface
    interface OnMainCircleClickListener {
        fun onClick()
    }
    //endregion

}