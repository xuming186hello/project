package com.example.newpaserapp.weight

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.newpaserapp.R
import com.example.newpaserapp.favorite.FavoriteActivity


/**
 * @author xxm
 * 2022/7/20
 **/
class TopBar : RelativeLayout {
    lateinit var textView: TextView
    lateinit var imageView: ImageView

    var mContext: Context? = null

    private var textString: String? = null
    var icShow = false

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        mContext = context
        LayoutInflater.from(mContext).inflate(R.layout.top_bar, this)
        initAttrs(attributeSet)
        initView()
        initListener()
    }

    private fun initAttrs(attributeSet: AttributeSet) {
        val tyList =
            mContext!!.obtainStyledAttributes(attributeSet, R.styleable.TopBar)
        textString = tyList.getString(R.styleable.TopBar_title_text)
        icShow = tyList.getBoolean(R.styleable.TopBar_icon_back_show, false)
        tyList.recycle()
    }

    private fun initView() {
        textView = findViewById(R.id.textview_top_bar_title)
        imageView = findViewById(R.id.image_top_bar_back)

        showBackIcon(icShow)
        textString.let {
            textView.text = it
        }
    }

    private fun initListener() {
    }

    fun setBackIconEvent(backEvent: FavoriteActivity.IconBackEvent) {
        imageView.setOnClickListener {
            backEvent.back()
        }
    }

    private fun showBackIcon(showIcon: Boolean) {
        imageView.let {
            if (showIcon) {
                imageView.visibility = View.VISIBLE
            } else {
                imageView.visibility = View.INVISIBLE
            }
        }
    }

    fun setTextFont(font: Typeface) {
        textView?.let {
            it.typeface = font
        }
    }
}