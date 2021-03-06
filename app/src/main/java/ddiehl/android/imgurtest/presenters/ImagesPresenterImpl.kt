package ddiehl.android.imgurtest.presenters

import android.text.TextUtils
import ddiehl.android.imgurtest.ImgurApplication
import ddiehl.android.imgurtest.R
import ddiehl.android.imgurtest.api.ImgurService
import ddiehl.android.imgurtest.model.GalleryImage
import ddiehl.android.imgurtest.view.MainView
import ddiehl.android.imgurtest.view.images.ImagesView
import timber.log.Timber
import java.util.*

class ImagesPresenterImpl : ImagesPresenter {

  private val mImgurService: ImgurService = ImgurApplication.imgurService
  private val mData: MutableList<GalleryImage> = ArrayList()

  private lateinit var mMainView: MainView
  private lateinit var mView: ImagesView
  private lateinit var mURL: String

  private constructor()

  constructor(mainView: MainView, view: ImagesView, url: String) : this() {
    mMainView = mainView
    mView = view
    mURL = url
  }

  constructor(mainView: MainView, view: ImagesView, image: GalleryImage)
      : this(mainView, view, "") {
    mData.add(image)
  }

  override fun onResume() {
    if (mData.isEmpty()) {
      refreshData()
    } else {
      mView.showImages(mData)
    }
  }

  override fun onPause() {
  }

  private fun refreshData() {
    if (!TextUtils.isEmpty(mURL)) {
      refreshData_album()
    } else {
      refreshData_image()
    }
  }

  private fun refreshData_album() {
    mImgurService.getAlbum(mURL)
        .doOnSubscribe { mMainView.showSpinner() }
        .doOnTerminate { mMainView.dismissSpinner() }
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

  private fun refreshData_image() {

  }

  override fun getNumImages(): Int =
      mData.size

  override fun getImageAt(position: Int): GalleryImage =
      mData[position]

  override fun onImageDisplayed(position: Int) {

  }
}
