package ddiehl.android.imgurtest.utils

import android.os.Build
import android.os.Parcel
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
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
