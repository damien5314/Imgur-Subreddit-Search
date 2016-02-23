package ddiehl.android.imgurtest.searchresults

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import ddiehl.android.imgurtest.R
import org.jetbrains.anko.*

class ViewImageDialog() : DialogFragment() {
  companion object {
    private val ARG_URL = "arg_url"
    fun newInstance(url: String): DialogFragment =
        ViewImageDialog().apply {
          arguments = Bundle().apply {
            putString(ARG_URL, url)
          }
        }
  }

  private lateinit var mImageView: ImageView
  private lateinit var mUrl: String

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mUrl = arguments.getString(ARG_URL)
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    return Dialog(activity, R.style.DialogOverlay).apply {
      setContentView(mView)
    }
  }

  override fun onResume() {
    super.onResume()
    Picasso.with(mView.context)
        .load(mUrl)
        .into(mImageView)
  }

  private val mView: View by lazy { object: AnkoComponent<ViewImageDialog> {
    override fun createView(ui: AnkoContext<ViewImageDialog>): View {
      return ui.apply {
        frameLayout {
          mImageView = imageView {
            id = R.id.image_view
            lparams {
              width = matchParent
              height = wrapContent
            }
          }
        }
      }.view
    }
  }.createView(AnkoContext.create(activity, this)) }
}