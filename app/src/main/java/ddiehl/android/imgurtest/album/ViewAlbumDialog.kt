package ddiehl.android.imgurtest.album

import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ddiehl.android.imgurtest.R
import ddiehl.android.imgurtest.model.GalleryImage
import ddiehl.android.imgurtest.utils.snack
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.support.v4.viewPager

class ViewAlbumDialog : DialogFragment(), AlbumView {
  companion object {
    private val ARG_ALBUM_ID = "arg_album_id"
    private val ARG_IMAGE = "arg_image"

    fun newInstance(albumId: String): ViewAlbumDialog =
        ViewAlbumDialog().apply {
          arguments = Bundle().apply {
            putString(ARG_ALBUM_ID, albumId)
          }
        }

    fun newInstance(image: GalleryImage): ViewAlbumDialog =
        ViewAlbumDialog().apply {
          arguments = Bundle().apply {
            putParcelable(ARG_IMAGE, image)
          }
        }
  }

  private lateinit var mAlbumPresenter: AlbumPresenter
  private lateinit var mViewPager: ViewPager
  private lateinit var mPagerAdapter: FragmentStatePagerAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    if (arguments.containsKey(ARG_ALBUM_ID)) {
      val albumId = arguments.getString(ARG_ALBUM_ID)
      mAlbumPresenter = AlbumPresenterImpl(this, albumId)
    } else if (arguments.containsKey(ARG_IMAGE)) {
//      val image = Parcels.unwrap<GalleryImage>(arguments.getParcelable(ARG_IMAGE))
      val image = arguments.getParcelable<GalleryImage>(ARG_IMAGE)
      mAlbumPresenter = AlbumPresenterImpl(this, image)
    }
    mPagerAdapter = AlbumPagerAdapter(childFragmentManager, mAlbumPresenter)
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
      Dialog(activity, R.style.DialogOverlay)

  override fun onResume() {
    super.onResume()
    mAlbumPresenter.onResume()
  }

  override fun showImages(images: List<GalleryImage>) {
    mPagerAdapter.notifyDataSetChanged()
  }

  override fun showToast(stringRes: Int) {
    view?.snack(stringRes, Snackbar.LENGTH_SHORT)
  }

  override fun onCreateView(
      inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
      object: AnkoComponent<ViewAlbumDialog> {
        override fun createView(ui: AnkoContext<ViewAlbumDialog>): View {
          return ui.apply {
            mViewPager = viewPager {
              id = R.id.view_pager
              lparams {
                width = matchParent
                height = matchParent
              }
              adapter = mPagerAdapter
            }
          }.view
        }
      }.createView(AnkoContext.create(activity, this))
}
