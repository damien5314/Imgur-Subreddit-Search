package ddiehl.android.imgurtest.album

import ddiehl.android.imgurtest.ImgurApplication
import ddiehl.android.imgurtest.R
import ddiehl.android.imgurtest.api.ImgurService
import ddiehl.android.imgurtest.model.GalleryImage
import timber.log.Timber
import java.util.*

class AlbumPresenterImpl(val mView: AlbumView, val mAlbumId: String) : AlbumPresenter {

  private val mImgurService: ImgurService = ImgurApplication.imgurService
  private val mData: MutableList<GalleryImage> = ArrayList()

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
              val album = response.body().data
              mData.addAll(album.images)
              mView.showImages(album.images)
            },
            { error ->
              mView.showToast(R.string.error_get_album)
              Timber.e(error, "Error occurred while retrieving album data")
            }
        )
  }

  override fun getNumImages(): Int =
      mData.size

  override fun getImageAt(position: Int): GalleryImage =
      mData[position]
}
