package ddiehl.android.imgurtest.searchresults

import ddiehl.android.imgurtest.BasePresenter
import ddiehl.android.imgurtest.model.AbsGalleryItem
import ddiehl.android.imgurtest.model.GalleryAlbum
import ddiehl.android.imgurtest.model.GalleryImage

interface SearchResultsPresenter : BasePresenter {
  fun getNumImages(): Int
  fun getItemAt(position: Int): AbsGalleryItem
  fun onAlbumClicked(album: GalleryAlbum)
  fun onImageClicked(image: GalleryImage)
}