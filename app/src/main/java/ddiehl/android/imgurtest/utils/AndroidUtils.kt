package ddiehl.android.imgurtest.utils

import okhttp3.Response
import timber.log.Timber

class AndroidUtils {
  companion object {
    fun printResponseStatus(response: Response) {
      Timber.d(response.toString())
    }
  }
}