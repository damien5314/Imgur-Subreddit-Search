package ddiehl.android.imgurtest.api

import com.google.gson.GsonBuilder
import ddiehl.android.imgurtest.BuildConfig
import ddiehl.android.imgurtest.model.AbsGalleryItem
import ddiehl.android.imgurtest.model.GalleryAlbum
import ddiehl.android.imgurtest.model.GalleryDeserializer
import ddiehl.android.imgurtest.model.GalleryResponse
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
    val gson = GsonBuilder()
        .registerTypeAdapter(AbsGalleryItem::class.java, GalleryDeserializer())
        .create()
    val client = OkHttpClient.Builder()
        .addInterceptor(LoggingInterceptor())
        .addInterceptor {
          chain ->
          chain.proceed(
              chain.request().newBuilder()
                  .removeHeader("Authorization")
                  .addHeader("Authorization", "Client-ID " + BuildConfig.IMGUR_CLIENT_ID)
                  .build())
        }
        .build()
    mAPI = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build().create(ImgurAPI::class.java)
  }

  override fun getGallery(section: String, sort: String, page: Int)
      : Observable<Response<GalleryResponse>> =
      mAPI.getGallery(section, sort, page)
          .subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())

  override fun getAlbum(id: String): Observable<Response<GalleryAlbum.Response>> =
      mAPI.getAlbum(id)
          .subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())

  override fun getSubreddit(subreddit: String, page: Int): Observable<Response<GalleryResponse>> =
      mAPI.getSubreddit(subreddit, page)
          .subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
}