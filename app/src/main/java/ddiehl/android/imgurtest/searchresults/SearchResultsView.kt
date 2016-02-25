package ddiehl.android.imgurtest.searchresults

import ddiehl.android.imgurtest.BaseView
import ddiehl.android.imgurtest.model.AbsGalleryItem
import ddiehl.android.imgurtest.model.GalleryImage

interface SearchResultsView : BaseView {
  fun showImages(list: List<AbsGalleryItem>)
  fun showAlbum(albumId: String)
  fun showImage(link: String)
  fun showImage(image: GalleryImage)
}