package ddiehl.android.imgurtest.view.images

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import ddiehl.android.imgurtest.R
import org.jetbrains.anko.*

class ViewAnimatedImageFragment : Fragment() {
  companion object {
    private val ARG_URL = "arg_url"
    private val ANIMATION_START_DELAY_MS = 1000L

    fun newInstance(url: String): Fragment = ViewAnimatedImageFragment().apply {
      arguments = Bundle().apply {
        putString(ARG_URL, url)
      }
    }
  }

  private lateinit var mVideoView: VideoView
  private lateinit var mURL: String

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mURL = arguments.getString(ARG_URL)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?): View =
      mUI.apply { mVideoView.setVideoPath(mURL) }

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
        frameLayout {
          lparams {
            width = matchParent
            height = matchParent
          }
          mVideoView = videoView {
            id = R.id.image_view
            lparams {
              width = matchParent
              height = matchParent
              gravity = Gravity.CENTER
            }
            setZOrderOnTop(true)
            setOnPreparedListener { player -> player.isLooping = true }
          }
        }
      }.view
    }
  }.createView(AnkoContext.create(activity, this)) }
}