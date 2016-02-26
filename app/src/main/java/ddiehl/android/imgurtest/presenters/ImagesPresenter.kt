package ddiehl.android.imgurtest.presenters

import ddiehl.android.imgurtest.model.GalleryImage

interface ImagesPresenter : BasePresenter {
  fun getNumImages(): Int
  fun getImageAt(position: Int): GalleryImage
  fun onImageDisplayed(position: Int)
}