package com.mint.sdk

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

class MintHelloView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val measureAndLayout = Runnable {
        measure(
            MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        )
        layout(left, top, right, bottom)
    }

    init {
        setBackgroundColor(Color.parseColor("#F5F5F5"))
        buildUI()
    }

    override fun requestLayout() {
        super.requestLayout()
        post(measureAndLayout)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        post {
            val parentWidth = (parent as? View)?.width
                ?: resources.displayMetrics.widthPixels

            measure(
                MeasureSpec.makeMeasureSpec(parentWidth, MeasureSpec.AT_MOST),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            )

            if (height == 0 && measuredHeight > 0) {
                layout(left, top, left + measuredWidth, top + measuredHeight)
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val hMode = MeasureSpec.getMode(heightMeasureSpec)
        if (hMode == MeasureSpec.UNSPECIFIED || (hMode == MeasureSpec.EXACTLY && MeasureSpec.getSize(heightMeasureSpec) == 0)) {
            super.onMeasure(
                widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            )
        }
    }

    private fun buildUI() {
        val scrollView = ScrollView(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
            isFillViewport = true
        }

        val container = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            )
            val pad = dpToPx(16)
            setPadding(pad, pad, pad, pad)
        }

        container.addView(createHeaderCard())

        container.addView(createSectionCard(
            "Welcome",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
        ))

        container.addView(createStatsCard())

        container.addView(createSectionCard(
            "Features",
            "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit."
        ))

        container.addView(createSectionCard(
            "Details",
            "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Sed ut perspiciatis unde omnis."
        ))

        scrollView.addView(container)
        addView(scrollView)
    }

    private fun createHeaderCard(): View {
        val card = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            val pad = dpToPx(24)
            setPadding(pad, dpToPx(32), pad, dpToPx(32))
            background = createCardBackground("#FFFFFF")
            layoutParams = LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = dpToPx(12)
            }
            elevation = dpToPx(2).toFloat()
        }

        val iconCircle = View(context).apply {
            background = GradientDrawable().apply {
                shape = GradientDrawable.OVAL
                setColor(Color.parseColor("#E8F5E9"))
            }
            layoutParams = LinearLayout.LayoutParams(dpToPx(64), dpToPx(64)).apply {
                gravity = Gravity.CENTER
                bottomMargin = dpToPx(16)
            }
        }
        card.addView(iconCircle)

        card.addView(TextView(context).apply {
            text = "Hello from Mint!"
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f)
            setTextColor(Color.parseColor("#2E7D32"))
            setTypeface(null, Typeface.BOLD)
            gravity = Gravity.CENTER
        })

        card.addView(TextView(context).apply {
            text = "Your SDK is ready to go"
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f)
            setTextColor(Color.parseColor("#888888"))
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER
                topMargin = dpToPx(6)
            }
        })

        card.addView(TextView(context).apply {
            text = "v1.0.0"
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 11f)
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            background = GradientDrawable().apply {
                setColor(Color.parseColor("#4CAF50"))
                cornerRadius = dpToPx(12).toFloat()
            }
            val hPad = dpToPx(12)
            val vPad = dpToPx(4)
            setPadding(hPad, vPad, hPad, vPad)
            layoutParams = LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER
                topMargin = dpToPx(12)
            }
        })

        return card
    }

    private fun createSectionCard(title: String, body: String): View {
        val card = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            val pad = dpToPx(20)
            setPadding(pad, pad, pad, pad)
            background = createCardBackground("#FFFFFF")
            layoutParams = LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = dpToPx(12)
            }
            elevation = dpToPx(2).toFloat()
        }

        card.addView(TextView(context).apply {
            text = title
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            setTextColor(Color.parseColor("#333333"))
            setTypeface(null, Typeface.BOLD)
        })

        card.addView(View(context).apply {
            setBackgroundColor(Color.parseColor("#E0E0E0"))
            layoutParams = LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                dpToPx(1)
            ).apply {
                topMargin = dpToPx(10)
                bottomMargin = dpToPx(10)
            }
        })

        card.addView(TextView(context).apply {
            text = body
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
            setTextColor(Color.parseColor("#666666"))
            setLineSpacing(dpToPx(4).toFloat(), 1f)
        })

        return card
    }

    private fun createStatsCard(): View {
        val card = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            val pad = dpToPx(20)
            setPadding(pad, pad, pad, pad)
            background = createCardBackground("#FFFFFF")
            layoutParams = LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = dpToPx(12)
            }
            elevation = dpToPx(2).toFloat()
        }

        card.addView(TextView(context).apply {
            text = "Overview"
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            setTextColor(Color.parseColor("#333333"))
            setTypeface(null, Typeface.BOLD)
        })

        card.addView(View(context).apply {
            setBackgroundColor(Color.parseColor("#E0E0E0"))
            layoutParams = LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                dpToPx(1)
            ).apply {
                topMargin = dpToPx(10)
                bottomMargin = dpToPx(12)
            }
        })

        val row = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            )
        }

        row.addView(createStatItem("Status", "Active"))
        row.addView(createStatItem("Version", "1.0.0"))
        row.addView(createStatItem("Mode", "Live"))

        card.addView(row)
        return card
    }

    private fun createStatItem(label: String, value: String): View {
        val item = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                0,
                LayoutParams.WRAP_CONTENT,
                1f
            )
        }

        item.addView(TextView(context).apply {
            text = value
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            setTextColor(Color.parseColor("#2E7D32"))
            setTypeface(null, Typeface.BOLD)
            gravity = Gravity.CENTER
        })

        item.addView(TextView(context).apply {
            text = label
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 11f)
            setTextColor(Color.parseColor("#999999"))
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = dpToPx(4)
            }
        })

        return item
    }

    private fun createCardBackground(colorHex: String): GradientDrawable {
        return GradientDrawable().apply {
            setColor(Color.parseColor(colorHex))
            cornerRadius = dpToPx(12).toFloat()
        }
    }

    private fun dpToPx(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            resources.displayMetrics
        ).toInt()
    }
}
