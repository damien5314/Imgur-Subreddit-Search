package ddiehl.android.imgurtest

import android.app.Application
import android.content.Context
import com.squareup.picasso.Picasso
import ddiehl.android.imgurtest.api.ImgurServiceImpl
import timber.log.Timber

class ImgurApplication : Application {
  companion object {
    var context: Context? = null
    val imgurService = ImgurServiceImpl()
  }

  constructor() {
    context = this
  }

  override fun onCreate() {
    super.onCreate()
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
      Picasso.setSingletonInstance(
          Picasso.Builder(this)
//              .indicatorsEnabled(true)
//              .loggingEnabled(true)
              .build())
    }
  }
}