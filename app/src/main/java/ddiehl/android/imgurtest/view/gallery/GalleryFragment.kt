package ddiehl.android.imgurtest.view.gallery

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ddiehl.android.imgurtest.R
import ddiehl.android.imgurtest.model.AbsGalleryItem
import ddiehl.android.imgurtest.model.GalleryImage
import ddiehl.android.imgurtest.presenters.GalleryPresenterImpl
import ddiehl.android.imgurtest.utils.isInPortrait
import ddiehl.android.imgurtest.utils.snack
import ddiehl.android.imgurtest.view.images.ImagesDialog
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView

class GalleryFragment : Fragment(), GalleryView {

  private val mGalleryPresenter = GalleryPresenterImpl(this)
  private val mAdapter = GalleryAdapter(mGalleryPresenter)
  private lateinit var mRecyclerView: RecyclerView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = mUI

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

  override fun showToast(stringRes: Int) {
    mUI.snack(stringRes, Snackbar.LENGTH_SHORT)
  }

  private val mUI: View by lazy {
    object: AnkoComponent<GalleryFragment> {
      override fun createView(ui: AnkoContext<GalleryFragment>): View =
          ui.apply {
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
          }.view
    }.createView(AnkoContext.create(activity, this@GalleryFragment))
  }
}
