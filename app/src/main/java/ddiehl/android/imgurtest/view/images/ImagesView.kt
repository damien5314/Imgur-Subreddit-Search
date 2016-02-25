package ddiehl.android.imgurtest.view.images

import ddiehl.android.imgurtest.view.BaseView
import ddiehl.android.imgurtest.model.GalleryImage

interface ImagesView : BaseView {
  fun showImages(images: List<GalleryImage>)
}