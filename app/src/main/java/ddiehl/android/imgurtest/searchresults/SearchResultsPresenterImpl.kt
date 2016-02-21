package ddiehl.android.imgurtest.searchresults

import ddiehl.android.imgurtest.CustomApplication
import ddiehl.android.imgurtest.model.AbsGalleryItem
import timber.log.Timber
import java.util.*

class SearchResultsPresenterImpl(val mView: SearchResultsView) : SearchResultsPresenter {

  private val mImgurService = CustomApplication.imgurService
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
              mView.showImages(mData)},
            { error ->
              Timber.e(error, "Error occurred while loading images")
            }
        )
  }

  override fun getNumImages(): Int = mData.size
  override fun getItemAt(position: Int): AbsGalleryItem = mData[position]

  override fun onAlbumClicked(albumId: String) {
    mView.showAlbum(albumId)
  }
}