package ddiehl.android.imgurtest.view.images

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import ddiehl.android.imgurtest.R
import org.jetbrains.anko.*

class ViewImageFragment : Fragment() {
  companion object {
    private val ARG_TITLE = "arg_title"
    private val ARG_URL = "arg_url"
    fun newInstance(title: String, url: String): Fragment = ViewImageFragment().apply {
      arguments = Bundle().apply {
        putString(ARG_TITLE, title)
        putString(ARG_URL, url)
      }
    }
  }

  private lateinit var mTitleView: TextView
  private lateinit var mImageView: ImageView

  private lateinit var mTitle: String
  private lateinit var mUrl: String

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mTitle = arguments.getString(ARG_TITLE)
    mUrl = arguments.getString(ARG_URL)
  }

  override fun onResume() {
    super.onResume()
    if (TextUtils.isEmpty(mTitle)) {
      mTitleView.visibility = View.GONE
    } else {
      mTitleView.visibility = View.VISIBLE
      mTitleView.text = mTitle
    }
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
          verticalLayout {
            lparams {
              width = matchParent
              height = matchParent
            }
            mTitleView = textView {
              lparams {
                width = wrapContent
                height = wrapContent
                gravity = Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL
                margin = dimen(R.dimen.baseline_spacing)
              }
              setTextAppearance(android.R.style.TextAppearance_Large)
              textColor = ContextCompat.getColor(ui.ctx, R.color.white)
            }
            mImageView = imageView {
              lparams {
                width = matchParent
                height = dip(0)
                weight = 1.0f
              }
              gravity = Gravity.CENTER
            }
          }
        }.view
      }
    }.createView(AnkoContext.create(activity, this)) }
}