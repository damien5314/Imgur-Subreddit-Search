package ddiehl.android.imgurtest.view.images

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import ddiehl.android.imgurtest.R
import org.jetbrains.anko.*

class ViewImageFragment : Fragment() {
  companion object {
    private val ARG_URL = "arg_url"
    fun newInstance(url: String): Fragment = ViewImageFragment().apply {
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

  override fun onResume() {
    super.onResume()
    Glide.with(this)
        .load(mUrl)
        .fitCenter()
        .into(mImageView)
  }

  override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return object: AnkoComponent<ViewImageFragment> {
      override fun createView(ui: AnkoContext<ViewImageFragment>): View {
        return ui.apply {
          frameLayout {
            lparams {
              width = matchParent
              height = matchParent
            }
            mImageView = imageView {
              id = R.id.image_view
              lparams {
                width = matchParent
                height = matchParent
              }
            }
          }
        }.view
      }
    }.createView(AnkoContext.create(activity, this)) }
}