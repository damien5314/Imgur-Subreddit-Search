package ddiehl.android.imgurtest.presenters

import ddiehl.android.imgurtest.presenters.BasePresenter
import ddiehl.android.imgurtest.model.GalleryImage

interface ImagesPresenter : BasePresenter {
  fun getNumImages(): Int
  fun getImageAt(position: Int): GalleryImage
}