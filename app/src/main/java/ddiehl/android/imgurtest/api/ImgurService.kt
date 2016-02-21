package ddiehl.android.imgurtest.api

import ddiehl.android.imgurtest.model.AbsGalleryItem
import ddiehl.android.imgurtest.model.GalleryAlbum
import retrofit2.Response
import rx.Observable

interface ImgurService {

  fun getGallery(section: String, sort: String, page: Int)
      : Observable<Response<AbsGalleryItem.Response>>

  fun getAlbum(id: String) : Observable<Response<GalleryAlbum.Response>>

}