package ddiehl.android.imgurtest.album

import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import ddiehl.android.imgurtest.R
import ddiehl.android.imgurtest.model.GalleryImage
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView

class ViewAlbumDialog : DialogFragment(), AlbumView {
  companion object {
    private val ARG_ALBUM_ID = "arg_album_id"

    fun newInstance(albumId: String): ViewAlbumDialog {
      val args = Bundle()
      args.putString(ARG_ALBUM_ID, albumId)
      val fragment = ViewAlbumDialog()
      fragment.arguments = args
      return fragment
    }
  }

  private lateinit var mRecyclerView: RecyclerView
  private lateinit var mAlbumPresenter: AlbumPresenter
  private lateinit var mAlbumAdapter: GalleryAlbumAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val albumId = arguments.getString(ARG_ALBUM_ID)
    mAlbumPresenter = AlbumPresenterImpl(this, albumId)
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    return Dialog(activity, R.style.DialogOverlay).apply {
      setContentView(mView)
      mAlbumAdapter = GalleryAlbumAdapter(mAlbumPresenter)
      mRecyclerView.adapter = mAlbumAdapter
    }
  }

  override fun onResume() {
    super.onResume()
    mAlbumPresenter.onResume()
  }

  override fun onPause() {
    super.onPause()
  }

  override fun showImages(images: Array<GalleryImage>) {
    mAlbumAdapter.notifyDataSetChanged()
  }

  override fun showToast(stringRes: Int) {
    Snackbar.make(mView, stringRes, Snackbar.LENGTH_SHORT).show()
  }

  private val mView: View by lazy { object: AnkoComponent<ViewAlbumDialog> {
    override fun createView(ui: AnkoContext<ViewAlbumDialog>): View {
      return ui.apply {
        mRecyclerView = recyclerView {
          id = R.id.recycler_view
          lparams {
            width = matchParent
            height = matchParent
          }
          layoutManager = LinearLayoutManager(ui.ctx)
        }
      }.view
    }
  }.createView(AnkoContext.create(activity, this)) }
}
