package ddiehl.android.imgurtest.api

import android.util.Log
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.util.*

// example usage: .listener(new LoggingListener<String, GlideDrawable>())
class LoggingListener<T, R> : RequestListener<T, R> {
  override fun onException(
      e: Exception?, model: T, target: Target<R>?,
      isFirstResource: Boolean): Boolean {
    Log.d("GLIDE", String.format(Locale.ROOT,
        "onException(%s, %s, %s, %s)", e, model, target, isFirstResource), e)
    return false
  }

  override fun onResourceReady(
      resource: R, model: T, target: Target<R>?,
      isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
    Log.d("GLIDE", String.format(Locale.ROOT,
        "onResourceReady(%s, %s, %s, %s, %s)",
        resource, model, target, isFromMemoryCache, isFirstResource))
    return false
  }
}