package com.mint.sdk

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView

class MintHelloView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val titleTextView: TextView
    private val subtitleTextView: TextView

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER
        setBackgroundColor(Color.parseColor("#E8F5E9"))

        val horizontalPadding = dpToPx(24)
        val verticalPadding = dpToPx(32)
        setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding)

        titleTextView = TextView(context).apply {
            text = "Hello from Mint SDK!"
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
            setTextColor(Color.parseColor("#2E7D32"))
            setTypeface(null, Typeface.BOLD)
            gravity = Gravity.CENTER
        }
        addView(titleTextView)

        subtitleTextView = TextView(context).apply {
            text = "Native Android Kotlin Component"
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
            setTextColor(Color.parseColor("#66BB6A"))
            gravity = Gravity.CENTER
            val topMargin = dpToPx(8)
            layoutParams = LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, topMargin, 0, 0)
            }
        }
        addView(subtitleTextView)
    }

    private fun dpToPx(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            resources.displayMetrics
        ).toInt()
    }
}
