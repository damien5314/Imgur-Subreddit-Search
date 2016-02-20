package ddiehl.android.imgurtest.api

import ddiehl.android.imgurtest.utils.AndroidUtils
import okhttp3.Interceptor
import okhttp3.Response

class LoggingInterceptor : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request()
    val response = chain.proceed(request)
    if (response != null) AndroidUtils.printResponseStatus(response)
    return response
  }
}
