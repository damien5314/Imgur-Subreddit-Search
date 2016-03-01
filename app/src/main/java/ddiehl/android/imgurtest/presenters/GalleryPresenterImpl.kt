package ddiehl.android.imgurtest.presenters

import ddiehl.android.imgurtest.ImgurApplication
import ddiehl.android.imgurtest.R
import ddiehl.android.imgurtest.model.AbsGalleryItem
import ddiehl.android.imgurtest.model.GalleryAlbum
import ddiehl.android.imgurtest.model.GalleryImage
import ddiehl.android.imgurtest.model.GalleryResponse
import ddiehl.android.imgurtest.view.MainView
import ddiehl.android.imgurtest.view.gallery.GalleryView
import retrofit2.Response
import rx.functions.Action1
import timber.log.Timber
import java.util.*

class GalleryPresenterImpl(
    private val mMainView: MainView, private val mView: GalleryView) : GalleryPresenter {

  private val mImgurService = ImgurApplication.imgurService
  private val mData: MutableList<AbsGalleryItem> = ArrayList()
  private var mPage = 0
  private var mSubreddit: String? = null

  override fun onResume() {
    if (mData.isEmpty()) {
      refreshData()
    }
  }

  override fun onPause() {

  }

  private fun refreshData() {
    if (mData.size > 0) {
      val numItems = mData.size
      mData.clear()
      mView.notifyDataCleared(numItems)
    }
    mPage = 0
    val subreddit = mSubreddit
    if (subreddit == null) {
      mImgurService.getGallery("gallery", "hot", mPage)
          .doOnSubscribe { mMainView.showSpinner() }
          .doOnTerminate { mMainView.dismissSpinner() }
          .subscribe(onImagesLoaded(), onError())
    } else {
      mImgurService.getSubreddit(subreddit, mPage)
          .doOnSubscribe { mMainView.showSpinner() }
          .doOnTerminate { mMainView.dismissSpinner() }
          .subscribe(onImagesLoaded(), onError())
    }
  }

  private fun onImagesLoaded(): Action1<Response<GalleryResponse>> {
    return Action1 { imageResponse ->
      if (imageResponse.body() != null && imageResponse.body().status == 200) {
        mData.addAll(imageResponse.body().data)
        mView.showImages(mData)
      } else { onError().call(null) }
    }
  }

  private fun onError(): Action1<Throwable> {
    return Action1 { error ->
      mView.showToast(R.string.error_loading_gallery)
      Timber.e(error, "Error occurred while loading images")
    }
  }

  override fun getNumImages(): Int = mData.size
  override fun getItemAt(position: Int): AbsGalleryItem = mData[position]

  override fun onAlbumClicked(album: GalleryAlbum) {
    mView.showAlbum(album.id)
  }

  override fun onImageClicked(image: GalleryImage) {
    mView.showImage(image)
  }

  override fun onSubredditNavigationRequested(subreddit: String) {
    mSubreddit = subreddit
    refreshData()
  }
}
