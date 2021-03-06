package ddiehl.android.imgurtest.utils

import android.content.Context
import android.content.res.Configuration
import android.graphics.PorterDuff
import android.os.Build
import android.os.Parcel
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.View
import android.webkit.WebView
import android.widget.ZoomButtonsController
import okhttp3.Response
import timber.log.Timber

fun printResponseStatus(response: Response) {
  Timber.d(response.toString())
}

fun View.snack(@StringRes resId: Int, duration: Int) = Snackbar.make(this, resId, duration).show()

fun WebView.disableWebViewZoomControls() {
  settings.setSupportZoom(true)
  settings.builtInZoomControls = true
  // Use the API 11+ calls to disable the controls
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
    settings.displayZoomControls = false
  } else {
    try {
      val zoom_control: ZoomButtonsController
      zoom_control = javaClass.getMethod("getZoomButtonsController")
          .invoke(this, null) as ZoomButtonsController
      zoom_control.container.visibility = View.GONE
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }
}

fun Parcel.readBoolean(): Boolean = readInt() == 1
fun Parcel.writeBoolean(b: Boolean) {
  writeInt(if (b) 1 else 0)
}

fun isInLandscape(ctx: Context) =
    ctx.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

fun isInPortrait(ctx: Context) =
    ctx.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

fun Context.dip2px(dip: Int): Float = dip.toFloat() * resources.displayMetrics.density
fun Context.sp2px(sp: Int): Float = sp.toFloat() * resources.displayMetrics.scaledDensity

fun getTintedDrawable(context: Context, @DrawableRes id: Int, @ColorRes color: Int) =
    context.getDrawable(id).apply {
      setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_IN)
    }