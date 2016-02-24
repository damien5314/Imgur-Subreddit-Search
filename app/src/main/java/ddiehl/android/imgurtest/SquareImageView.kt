package ddiehl.android.imgurtest

import android.content.Context
import android.util.AttributeSet
import android.view.ViewManager
import android.widget.ImageView
import org.jetbrains.anko.custom.ankoView

class SquareImageView : ImageView {

  constructor(context: Context) : super(context) { }

  constructor(context: Context, attrs: AttributeSet) : super(context, attrs) { }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    val width = measuredWidth
    val height = measuredHeight
    setMeasuredDimension(width, width)
  }
}

inline fun ViewManager.squareImageView(): SquareImageView = squareImageView({})
inline fun ViewManager.squareImageView(init: SquareImageView.() -> Unit): SquareImageView {
  return ankoView({ ctx: Context -> SquareImageView(ctx) }) { init() }
}