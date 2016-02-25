package ddiehl.android.imgurtest.searchresults

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ddiehl.android.imgurtest.R
import ddiehl.android.imgurtest.album.ViewAlbumDialog
import ddiehl.android.imgurtest.model.AbsGalleryItem
import ddiehl.android.imgurtest.model.GalleryImage
import ddiehl.android.imgurtest.utils.snack
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView

class SearchResultsFragment : Fragment(), SearchResultsView {

  private val mSearchResultsPresenter = SearchResultsPresenterImpl(this)
  private val mAdapter = SearchResultsAdapter(mSearchResultsPresenter)
  private lateinit var mRecyclerView: RecyclerView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = mUI

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
    ViewAlbumDialog.newInstance(link)
        .show(activity.supportFragmentManager, "image_fragment")
  }

  override fun showImage(image: GalleryImage) {
    ViewAlbumDialog.newInstance(image)
        .show(activity.supportFragmentManager, "image_fragment")
  }

  override fun showToast(stringRes: Int) {
    activity.window.decorView.snack(stringRes, Snackbar.LENGTH_SHORT)
  }

  private val mUI: View by lazy {
    object: AnkoComponent<SearchResultsFragment> {
      override fun createView(ui: AnkoContext<SearchResultsFragment>): View =
          ui.apply {
            mRecyclerView = recyclerView {
              id = R.id.recycler_view
              lparams {
                width = matchParent
                height = matchParent
              }
              layoutManager = GridLayoutManager(ui.ctx, 3, GridLayoutManager.VERTICAL, false)
              adapter = mAdapter
              //        layoutManager = LinearLayoutManager(ui.ctx)
            }
          }.view
    }.createView(AnkoContext.create(activity, this@SearchResultsFragment))
  }
}
