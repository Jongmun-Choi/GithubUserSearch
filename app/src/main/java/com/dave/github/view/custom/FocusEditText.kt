package com.dave.github.view.custom

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.dave.github.R
import com.dave.github.util.Utils.toEditable


class FocusEditText :
    androidx.appcompat.widget.AppCompatEditText, View.OnFocusChangeListener, View.OnTouchListener {

    private var clearDrawable : Drawable
    private var isParentBackground = false
    private var isUseBackground = false
    private var isErrorBackground = false
    private var clearClickListener : ClearClickListener?= null
    private var alwaysUseClear = false
    private var isValidation = true
    private var isRemoveBlank = true
    interface ClearClickListener {
        fun onClick()
    }

    constructor(context: Context, attr: AttributeSet?) : super(context, attr) {

        setOnTouchListener(this)

        val ta: TypedArray = context.obtainStyledAttributes(attr, R.styleable.FocusEditText)
        isParentBackground = ta.getBoolean(R.styleable.FocusEditText_isParentBackground,false)

        isUseBackground = ta.getBoolean(R.styleable.FocusEditText_isUseBackground, true)
        if(isUseBackground) onFocusChangeListener = this

        alwaysUseClear = ta.getBoolean(R.styleable.FocusEditText_alwaysUseClear, false)

        isErrorBackground = ta.getBoolean(R.styleable.FocusEditText_isErrorBackground, false)

        isRemoveBlank = ta.getBoolean(R.styleable.FocusEditText_isRemoveBlank, true)

        val tempDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_clear)
        clearDrawable = tempDrawable?.let { DrawableCompat.wrap(it) }!!
        clearDrawable.setBounds(0, 0, clearDrawable.intrinsicWidth, clearDrawable.intrinsicHeight)

        setClearIconVisible(alwaysUseClear)

        if(inputType == ( EditorInfo.TYPE_CLASS_TEXT + EditorInfo.TYPE_TEXT_VARIATION_PASSWORD ))
            transformationMethod = BiggerDotPasswordTransformationMethod

        includeFontPadding = false
    }

    fun setVisiblePassWord(visible: Boolean) {
        if(visible) {
            inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            transformationMethod = null
        }else {
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            transformationMethod = BiggerDotPasswordTransformationMethod
        }
    }

    fun setClearButtonClickListener(listener : ClearClickListener) {
        clearClickListener = listener
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val clearButtonHeightSpec = MeasureSpec.makeMeasureSpec(paddingTop + paddingBottom+clearDrawable.intrinsicHeight, MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, clearButtonHeightSpec)
    }

    fun setClearIconVisible(visible : Boolean) {
        if(clearDrawable != null) {
            clearDrawable.setVisible(visible, false)
            setCompoundDrawables(null, null, if (visible) clearDrawable else null, null)
        }
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        val targetView : View = if(isParentBackground) {
            parent as View
        }else {
            this
        }

        if(hasFocus || alwaysUseClear) {
            setClearIconVisible(hasFocus && !text.isNullOrEmpty())
        }else {
            if (isRemoveBlank) text = text?.trim().toString().toEditable()
            setClearIconVisible(false)
        }
        if (isUseBackground) {
            if (isErrorBackground) setInputErrorBoxResource(isValidation, hasFocus, targetView) else setInputBoxResource(hasFocus, targetView)
        }
    }

    override fun onTouch(p0: View?, motionEvent: MotionEvent?): Boolean {
        if(motionEvent == null) return false
        val x = motionEvent.x.toInt()
        if (clearDrawable.isVisible && x> width - paddingRight - clearDrawable.intrinsicWidth) {
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                text?.clear()
                clearClickListener?.onClick()
            }
            return true
        }
        return false
    }

    fun setIsValidation(isValid: Boolean){
        isValidation = isValid
    }

     fun setInputErrorBoxResource(isValidation: Boolean, hasFocus: Boolean, bgView : View) {
        if(isValidation) {
            setInputBoxResource(hasFocus, bgView)
        } else {
            bgView.setBackgroundResource(R.drawable.drawable_round_btn_white_invalidate)
        }
    }

    fun setInputBoxResource(hasFocus: Boolean, bgView : View) {
        if(hasFocus) {
            bgView.setBackgroundResource(R.drawable.drawable_round_btn_white_focus)
        } else {
            bgView.setBackgroundResource(R.drawable.drawable_filter_background)
        }
    }

    override fun onTextChanged(
        text: CharSequence,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        setClearIconVisible((isFocused || alwaysUseClear) && text.isNotEmpty())
    }

    private object BiggerDotPasswordTransformationMethod : PasswordTransformationMethod() {

        override fun getTransformation(source: CharSequence, view: View): CharSequence {
            return PasswordCharSequence(super.getTransformation(source, view))
        }

        private class PasswordCharSequence(
            val transformation: CharSequence
        ) : CharSequence by transformation {
            override fun get(index: Int): Char = if (transformation[index] == DOT) {
                BIGGER_DOT
            } else {
                transformation[index]
            }
        }

        private const val DOT = '\u2022'
        private const val BIGGER_DOT = '●'
    }

}