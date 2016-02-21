package ddiehl.android.imgurtest.searchresults

import ddiehl.android.imgurtest.model.AbsGalleryItem

interface SearchResultsView {
  fun showImages(list: List<AbsGalleryItem>)
  fun showAlbum(albumId: String)
}