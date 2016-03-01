package ddiehl.android.imgurtest.view.gallery

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ddiehl.android.imgurtest.R
import ddiehl.android.imgurtest.model.AbsGalleryItem
import ddiehl.android.imgurtest.model.GalleryImage
import ddiehl.android.imgurtest.presenters.GalleryPresenterImpl
import ddiehl.android.imgurtest.utils.dip2px
import ddiehl.android.imgurtest.utils.isInPortrait
import ddiehl.android.imgurtest.utils.snack
import ddiehl.android.imgurtest.view.MainView
import ddiehl.android.imgurtest.view.images.ImagesDialog
import org.jetbrains.anko.*
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.recyclerview.v7.recyclerView
import timber.log.Timber

class GalleryFragment(private val mMainView: MainView) :
    Fragment(), GalleryView, SubredditNavigationDialog.Callbacks {

  private val mGalleryPresenter = GalleryPresenterImpl(mMainView, this)
  private val mAdapter = GalleryAdapter(mGalleryPresenter)
  private lateinit var mRecyclerView: RecyclerView
  private lateinit var mSearchButton: FloatingActionButton

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    retainInstance = true
  }

  override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
      mUI().apply {
        mSearchButton.setOnClickListener {
          SubredditNavigationDialog(this@GalleryFragment)
              .show(activity.supportFragmentManager, "dialog_navigate")
        }
      }

  override fun onResume() {
    super.onResume()
    mGalleryPresenter.onResume()
  }

  override fun onPause() {
    mGalleryPresenter.onPause()
    super.onPause()
  }

  override fun showImages(list: List<AbsGalleryItem>) {
    mAdapter.notifyItemRangeInserted(0, list.size)
  }

  override fun showAlbum(albumId: String) {
    ImagesDialog.newInstance(albumId)
        .show(activity.supportFragmentManager, "album_fragment")
  }

  override fun showImage(link: String) {
    ImagesDialog.newInstance(link)
        .show(activity.supportFragmentManager, "image_fragment")
  }

  override fun showImage(image: GalleryImage) {
    ImagesDialog.newInstance(image)
        .show(activity.supportFragmentManager, "image_fragment")
  }

  override fun onSubredditNavigationConfirmed(subreddit: String) {
    Timber.d("Subreddit navigation requested: " + subreddit)
    mGalleryPresenter.onSubredditNavigationRequested(subreddit)
  }

  override fun onSubredditNavigationCancelled() { }

  override fun showToast(stringRes: Int) {
    mRecyclerView.snack(stringRes, Snackbar.LENGTH_SHORT)
  }

  override fun notifyDataCleared(numItems: Int) {
    mAdapter.notifyItemRangeRemoved(0, numItems)
  }

  private fun mUI(): View {
    return object: AnkoComponent<GalleryFragment> {
      override fun createView(ui: AnkoContext<GalleryFragment>): View =
          ui.apply {
            coordinatorLayout {
              mRecyclerView = recyclerView {
                id = R.id.recycler_view
                lparams {
                  width = matchParent
                  height = matchParent
                }
                val columns = if (isInPortrait(activity)) 3 else 5
                layoutManager = GridLayoutManager(ui.ctx, columns, GridLayoutManager.VERTICAL, false)
                adapter = mAdapter
              }
              mSearchButton = floatingActionButton {
                elevation = ui.ctx.dip2px(8)
                setImageResource(R.drawable.ic_search_white_24dp)
                lparams {
                  margin = dimen(R.dimen.activity_horizontal_margin)
                  gravity = Gravity.BOTTOM or Gravity.RIGHT
                }
              }
            }
          }.view
    }.createView(AnkoContext.create(activity, this@GalleryFragment))
  }
}
