package ddiehl.android.imgurtest.presenters

import ddiehl.android.imgurtest.model.AbsGalleryItem
import ddiehl.android.imgurtest.model.GalleryAlbum
import ddiehl.android.imgurtest.model.GalleryImage

interface GalleryPresenter : BasePresenter {
  fun getNumImages(): Int
  fun getItemAt(position: Int): AbsGalleryItem
  fun onAlbumClicked(album: GalleryAlbum)
  fun onImageClicked(image: GalleryImage)
  fun onSubredditNavigationRequested(subreddit: String)
}