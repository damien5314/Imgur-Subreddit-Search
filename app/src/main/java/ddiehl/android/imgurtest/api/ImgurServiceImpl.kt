package ddiehl.android.imgurtest.api

import com.google.gson.Gson
import ddiehl.android.imgurtest.model.ImageResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ImgurServiceImpl : ImgurService {
  companion object {
    private val BASE_URL = "https://api.imgur.com/"
  }

  private val mAPI: ImgurAPI

  constructor() {
    val client = OkHttpClient.Builder()
        .addInterceptor(LoggingInterceptor())
        .build()
    mAPI = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build().create(ImgurAPI::class.java)
  }

  override fun getGallery(section: String, sort: String, page: Int)
      : Observable<Response<ImageResponse>> =
      mAPI.getGallery(section, sort, page)
          .subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
}