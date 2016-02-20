package ddiehl.android.imgurtest.api

import ddiehl.android.imgurtest.BuildConfig
import ddiehl.android.imgurtest.model.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import rx.Observable

interface ImgurAPI {

  @GET("/3/{section}/{sort}/{page}")
  @Headers("Authorization: Client-ID " + BuildConfig.IMGUR_CLIENT_ID)
  fun getGallery(
      @Path("section") section: String,
      @Path("sort") sort: String,
      @Path("page") page: Int): Observable<Response<ImageResponse>>

}