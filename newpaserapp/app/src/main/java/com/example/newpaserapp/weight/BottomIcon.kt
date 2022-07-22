package com.example.newpaserapp.weight

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.newpaserapp.R

/**
 * @author xxm
 * 2022/7/21
 **/
class BottomIcon : LinearLayout {
    private lateinit var mContext: Context

    private lateinit var text: String
    private var imageResourceId: Drawable? = null

    private lateinit var imageView: ImageView
    private lateinit var textView: TextView

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        mContext = context
        LayoutInflater.from(mContext).inflate(R.layout.bottom_icon, this)
        init(attributeSet)
        initView()
        initListener()
    }

    private fun init(attributeSet: AttributeSet) {
        var array =
            mContext.obtainStyledAttributes(attributeSet, R.styleable.BottomIcon)
        text = array.getString(R.styleable.BottomIcon_icon_text).toString()
        imageResourceId = array.getDrawable(R.styleable.BottomIcon_icon_image)
        array.recycle()
    }

    private fun initView() {
        textView = findViewById(R.id.bottom_icon_text)
        imageView = findViewById(R.id.imageview_live)

        imageResourceId.let {
            imageView.background = it
        }
        text.let {
            textView.text = it
        }
    }

    private fun initListener() {
    }
}