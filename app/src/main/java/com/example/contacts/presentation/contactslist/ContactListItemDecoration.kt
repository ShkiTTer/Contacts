package com.example.contacts.presentation.contactslist

import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.R
import kotlinx.android.synthetic.main.item_header.view.*

class ContactListItemDecoration(private val callback: HeaderCallback) :
    RecyclerView.ItemDecoration() {
    private lateinit var headerView: View
    private lateinit var tvHeader: TextView

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        if (!this::headerView.isInitialized) {
            headerView = inflateHeader(parent)
            tvHeader = headerView.tvHeader
        }

        var prevTitle = ""

        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val pos = parent.getChildAdapterPosition(child)

            if (pos == RecyclerView.NO_POSITION) continue

            val title = callback.getHeaderName(pos)
            tvHeader.text = title

            if (!prevTitle.equals(title, true) || callback.isHeader(pos)) {
                fixLayoutSize(headerView, parent)
                drawHeader(c, child, headerView)
                prevTitle = title
            }
        }
    }

    private fun inflateHeader(view: RecyclerView): View {
        return LayoutInflater.from(view.context).inflate(R.layout.item_header, view, false)
    }

    private fun fixLayoutSize(view: View, viewGroup: ViewGroup) {
        val widthSpec = View.MeasureSpec.makeMeasureSpec(
            viewGroup.width,
            View.MeasureSpec.EXACTLY
        )
        val heightSpec = View.MeasureSpec.makeMeasureSpec(
            viewGroup.height,
            View.MeasureSpec.UNSPECIFIED
        )
        val childWidth = ViewGroup.getChildMeasureSpec(
            widthSpec,
            viewGroup.paddingLeft + viewGroup.paddingRight,
            view.layoutParams.width
        )
        val childHeight = ViewGroup.getChildMeasureSpec(
            heightSpec,
            viewGroup.paddingTop + viewGroup.paddingBottom,
            view.layoutParams.height
        )
        view.measure(childWidth, childHeight)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    }

    private fun drawHeader(c: Canvas, child: View, header: View) {
        c.save()
        c.translate(0f, 0.coerceAtLeast(child.top).toFloat())
        header.draw(c)
        c.restore()
    }

    interface HeaderCallback {
        fun isHeader(pos: Int): Boolean
        fun getHeaderName(pos: Int): String
    }
}