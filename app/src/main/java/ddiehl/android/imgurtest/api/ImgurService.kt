package ddiehl.android.imgurtest.api

import ddiehl.android.imgurtest.model.Image
import ddiehl.android.imgurtest.model.ImageResponse
import retrofit2.Response
import rx.Observable

interface ImgurService {
  fun getGallery(section: String, sort: String, page: Int): Observable<Response<ImageResponse>>
}