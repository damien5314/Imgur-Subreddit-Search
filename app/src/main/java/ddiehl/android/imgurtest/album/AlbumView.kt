package ddiehl.android.imgurtest.album

import ddiehl.android.imgurtest.BaseView
import ddiehl.android.imgurtest.model.GalleryImage

interface AlbumView : BaseView {
  fun showImages(images: Array<GalleryImage>)
}