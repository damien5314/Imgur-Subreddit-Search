package ddiehl.android.imgurtest.view.images

import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ddiehl.android.imgurtest.R
import ddiehl.android.imgurtest.model.GalleryImage
import ddiehl.android.imgurtest.presenters.ImagesPresenter
import ddiehl.android.imgurtest.presenters.ImagesPresenterImpl
import ddiehl.android.imgurtest.utils.snack
import ddiehl.android.imgurtest.view.MainView
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.support.v4.viewPager
import org.parceler.Parcels

class ImagesDialog : DialogFragment(), ImagesView {
  companion object {
    private val ARG_ALBUM_ID = "arg_album_id"
    private val ARG_IMAGE = "arg_image"

    fun newInstance(albumId: String): ImagesDialog =
        ImagesDialog().apply {
          arguments = Bundle().apply {
            putString(ARG_ALBUM_ID, albumId)
          }
        }

    fun newInstance(image: GalleryImage): ImagesDialog =
        ImagesDialog().apply {
          arguments = Bundle().apply {
            putParcelable(ARG_IMAGE, Parcels.wrap(image))
          }
        }
  }

  private lateinit var mImagesPresenter: ImagesPresenter
  private lateinit var mViewPager: ViewPager
  private lateinit var mPagerAdapter: ImagesPagerAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    if (arguments.containsKey(ARG_ALBUM_ID)) {
      val albumId = arguments.getString(ARG_ALBUM_ID)
      mImagesPresenter = ImagesPresenterImpl(activity as MainView, this, albumId)
    } else if (arguments.containsKey(ARG_IMAGE)) {
      val image = Parcels.unwrap<GalleryImage>(arguments.getParcelable(ARG_IMAGE))
      mImagesPresenter = ImagesPresenterImpl(activity as MainView, this, image)
    }
    mPagerAdapter = ImagesPagerAdapter(childFragmentManager, mImagesPresenter)
  }

  override fun onCreateView(
      inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? = mUI

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
      Dialog(activity, R.style.DialogOverlay)

  override fun onResume() {
    super.onResume()
    mImagesPresenter.onResume()
  }

  override fun showImages(images: List<GalleryImage>) {
    mPagerAdapter.notifyDataSetChanged()
  }

  override fun showToast(stringRes: Int) {
    view?.snack(stringRes, Snackbar.LENGTH_SHORT)
  }

  private val mUI by lazy { object: AnkoComponent<ImagesDialog> {
    override fun createView(ui: AnkoContext<ImagesDialog>): View {
      return ui.apply {
        mViewPager = viewPager {
          id = R.id.view_pager
          lparams {
            width = matchParent
            height = matchParent
          }
          adapter = mPagerAdapter
          addOnPageChangeListener(object: ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
              mImagesPresenter.onImageDisplayed(position)
            }
          })
        }
      }.view
    }
  }.createView(AnkoContext.create(activity, this)) }
}
