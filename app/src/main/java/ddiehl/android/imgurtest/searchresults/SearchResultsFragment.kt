package ddiehl.android.imgurtest.searchresults

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ddiehl.android.imgurtest.R
import ddiehl.android.imgurtest.album.ViewAlbumDialog
import ddiehl.android.imgurtest.album.ViewImageDialog
import ddiehl.android.imgurtest.model.AbsGalleryItem
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

class SearchResultsFragment : Fragment(), SearchResultsView {

  private val mSearchResultsPresenter = SearchResultsPresenterImpl(this)
  private val mAdapter = SearchResultsAdapter(mSearchResultsPresenter)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = SearchResultsFragment_UI().createView(
        AnkoContext.create(activity, this))
    val recyclerView = view.find<RecyclerView>(R.id.recycler_view)
    recyclerView.adapter = mAdapter
    return view
  }

  override fun onResume() {
    super.onResume()
    mSearchResultsPresenter.onResume()
  }

  override fun onPause() {
    mSearchResultsPresenter.onPause()
    super.onPause()
  }

  override fun showImages(list: List<AbsGalleryItem>) {
    mAdapter.notifyItemRangeInserted(0, list.size)
  }

  override fun showAlbum(albumId: String) {
    ViewAlbumDialog.newInstance(albumId)
        .show(activity.supportFragmentManager, "album_fragment")
  }

  override fun showImage(link: String) {
    ViewImageDialog.newInstance(link)
        .show(activity.supportFragmentManager, "image_fragment")
  }
}
