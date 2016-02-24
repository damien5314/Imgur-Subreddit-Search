package ddiehl.android.imgurtest.searchresults

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import ddiehl.android.imgurtest.LoggingListener
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
    return Dialog(activity, android.R.style.Theme_Black_NoTitleBar_Fullscreen).apply {
      setContentView(UI().createView(AnkoContext.create(context, this@ViewImageDialog)))
      mImageView = findViewById(R.id.image_view) as ImageView
    }
  }

  override fun onResume() {
    super.onResume()
    Glide.with(this)
        .load(mUrl)
        .fitCenter()
        .error(R.drawable.ic_alert_error)
        .listener(LoggingListener<String, GlideDrawable>())
        .into(mImageView)
  }

  private class UI: AnkoComponent<ViewImageDialog> {
    override fun createView(ui: AnkoContext<ViewImageDialog>): View {
      return ui.apply {
        frameLayout {
          lparams {
            width = matchParent
            height = matchParent
          }
          imageView {
            id = R.id.image_view
            lparams {
              width = matchParent
              height = matchParent
            }
          }
        }
      }.view
    }
  }
}