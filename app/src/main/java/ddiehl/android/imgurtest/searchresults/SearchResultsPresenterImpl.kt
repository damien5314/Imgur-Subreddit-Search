package ddiehl.android.imgurtest.searchresults

import ddiehl.android.imgurtest.CustomApplication
import ddiehl.android.imgurtest.model.Image
import timber.log.Timber
import java.util.*

class SearchResultsPresenterImpl(val view: SearchResultsView) : SearchResultsPresenter {

  private val mImgurService = CustomApplication.imgurService
  private val mData: MutableList<Image> = ArrayList()
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
              mData.addAll(imageResponse.body().data
                  .filter { !it.isAlbum }
              )
              view.showImages(mData)},
            { error ->
              Timber.e(error, "Error occurred while loading images")
            }
        )
  }

  override fun getNumImages(): Int = mData.size
  override fun getItemAt(position: Int): Image = mData[position]
}