package ddiehl.android.imgurtest.presenters

import ddiehl.android.imgurtest.ImgurApplication
import ddiehl.android.imgurtest.R
import ddiehl.android.imgurtest.model.AbsGalleryItem
import ddiehl.android.imgurtest.model.GalleryAlbum
import ddiehl.android.imgurtest.model.GalleryImage
import ddiehl.android.imgurtest.view.gallery.GalleryView
import timber.log.Timber
import java.util.*

class GalleryPresenterImpl(val mView: GalleryView) : GalleryPresenter {

  private val mImgurService = ImgurApplication.imgurService
  private val mData: MutableList<AbsGalleryItem> = ArrayList()
  private var mPage = 0

  override fun onResume() {
    if (mData.isEmpty()) {
      refreshData()
    }
  }

  override fun onPause() {

  }

  private fun refreshData() {
    mPage = 0
    mImgurService.getGallery("gallery", "hot", mPage)
        .subscribe(
            { imageResponse ->
              mData.addAll(imageResponse.body().data)
              mView.showImages(mData)
            },
            { error ->
              mView.showToast(R.string.error_loading_gallery)
              Timber.e(error, "Error occurred while loading images")
            }
        )
  }

  override fun getNumImages(): Int = mData.size
  override fun getItemAt(position: Int): AbsGalleryItem = mData[position]

  override fun onAlbumClicked(album: GalleryAlbum) {
    mView.showAlbum(album.id)
  }

  override fun onImageClicked(image: GalleryImage) {
    mView.showImage(image)
  }
}