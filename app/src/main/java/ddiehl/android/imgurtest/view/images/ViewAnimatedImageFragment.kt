package ddiehl.android.imgurtest.view.images

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.VideoView
import ddiehl.android.imgurtest.R
import org.jetbrains.anko.*

class ViewAnimatedImageFragment : Fragment() {
  companion object {
    private val ARG_TITLE = "arg_title"
    private val ARG_URL = "arg_url"
    private val ANIMATION_START_DELAY_MS = 1000L

    fun newInstance(title: String, url: String): Fragment = ViewAnimatedImageFragment().apply {
      arguments = Bundle().apply {
        putString(ARG_TITLE, title)
        putString(ARG_URL, url)
      }
    }
  }

  private lateinit var mTitleView: TextView
  private lateinit var mVideoView: VideoView
  private lateinit var mTitle: String
  private lateinit var mURL: String

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mTitle = arguments.getString(ARG_TITLE)
    mURL = arguments.getString(ARG_URL)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?): View =
      mUI.apply {
        if (TextUtils.isEmpty(mTitle)) {
          mTitleView.visibility = View.GONE
        } else {
          mTitleView.visibility = View.VISIBLE
          mTitleView.text = mTitle
        }
        mVideoView.setVideoPath(mURL)
      }

  override fun onResume() {
    super.onResume()
    Handler().postDelayed({ mVideoView.start() }, ANIMATION_START_DELAY_MS)
  }

  override fun onPause() {
    mVideoView.pause()
    super.onPause()
  }

  override fun onStop() {
    mVideoView.stopPlayback()
    super.onStop()
  }

  override fun onDestroyView() {
    mVideoView.suspend()
    super.onDestroyView()
  }

  val mUI by lazy { object: AnkoComponent<ViewAnimatedImageFragment> {
    override fun createView(ui: AnkoContext<ViewAnimatedImageFragment>): View {
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
          mVideoView = videoView {
            id = R.id.image_view
            lparams {
              width = matchParent
              height = dip(0)
              weight = 1.0f
            }
            gravity = Gravity.CENTER
            setZOrderOnTop(true)
            setOnPreparedListener { player -> player.isLooping = true }
          }
        }
      }.view
    }
  }.createView(AnkoContext.create(activity, this)) }
}