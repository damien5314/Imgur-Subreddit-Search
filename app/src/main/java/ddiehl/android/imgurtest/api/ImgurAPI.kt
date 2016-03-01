package ddiehl.android.imgurtest.api

import ddiehl.android.imgurtest.model.GalleryAlbum
import ddiehl.android.imgurtest.model.GalleryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

interface ImgurAPI {

  @GET("/3/{section}/{sort}/{page}")
  fun getGallery(
      @Path("section") section: String,
      @Path("sort") sort: String,
      @Path("page") page: Int): Observable<Response<GalleryResponse>>

  @GET("/3/gallery/album/{id}")
  fun getAlbum(
      @Path("id") id: String
  ): Observable<Response<GalleryAlbum.Response>>

  @GET("/3/gallery/r/{subreddit}/{sort}/{page}")
  fun getSubreddit(
      @Path("subreddit") subreddit: String,
      @Path("page") page: Int = 0,
      @Path("sort") sort: String = "time"
  ): Observable<Response<GalleryResponse>>
}