package ddiehl.android.imgurtest.view.gallery

import ddiehl.android.imgurtest.model.AbsGalleryItem
import ddiehl.android.imgurtest.model.GalleryImage
import ddiehl.android.imgurtest.view.BaseView

interface GalleryView : BaseView {
  fun showImages(list: List<AbsGalleryItem>)
  fun showAlbum(albumId: String)
  fun showImage(link: String)
  fun showImage(image: GalleryImage)
  fun notifyDataCleared(numItems: Int)
}