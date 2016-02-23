package ddiehl.android.imgurtest.album

import ddiehl.android.imgurtest.BasePresenter
import ddiehl.android.imgurtest.model.GalleryImage

interface AlbumPresenter : BasePresenter {
  fun getNumImages(): Int
  fun getImageAt(position: Int): GalleryImage
}