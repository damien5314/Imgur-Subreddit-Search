package ddiehl.android.imgurtest.api

import ddiehl.android.imgurtest.model.GalleryAlbum
import ddiehl.android.imgurtest.model.GalleryResponse
import retrofit2.Response
import rx.Observable

interface ImgurService {

  fun getGallery(section: String, sort: String, page: Int)
      : Observable<Response<GalleryResponse>>

  fun getAlbum(id: String) : Observable<Response<GalleryAlbum.Response>>

}