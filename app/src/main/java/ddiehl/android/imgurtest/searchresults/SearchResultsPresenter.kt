package ddiehl.android.imgurtest.searchresults

import ddiehl.android.imgurtest.BasePresenter
import ddiehl.android.imgurtest.model.AbsGalleryItem

interface SearchResultsPresenter : BasePresenter {
  fun getNumImages(): Int
  fun getItemAt(position: Int): AbsGalleryItem
  fun onAlbumClicked(albumId: String)
}