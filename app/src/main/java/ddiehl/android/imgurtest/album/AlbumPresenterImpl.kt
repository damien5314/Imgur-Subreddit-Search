package ddiehl.android.imgurtest.album

import ddiehl.android.imgurtest.CustomApplication
import ddiehl.android.imgurtest.R
import ddiehl.android.imgurtest.api.ImgurService
import ddiehl.android.imgurtest.model.GalleryAlbum
import java.util.*

class AlbumPresenterImpl(val mView: AlbumView, val mAlbumId: String) : AlbumPresenter {

  private val mImgurService: ImgurService = CustomApplication.imgurService
  private val mData: MutableList<GalleryAlbum> = ArrayList()

  override fun onResume() {
    if (mData.isEmpty()) {
      refreshData()
    }
  }

  override fun onPause() {
  }

  private fun refreshData() {
    mImgurService.getAlbum(mAlbumId)
        .subscribe(
            { response ->
              val images = response.body().data
              mData.addAll(images)
            }, { error -> mView.showToast(R.string.error_get_album) }
        )
  }
}
