package ddiehl.android.imgurtest.view

import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import ddiehl.android.imgurtest.BuildConfig
import ddiehl.android.imgurtest.R
import ddiehl.android.imgurtest.utils.disableWebViewZoomControls
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.webView
import timber.log.Timber

class WebViewFragment : Fragment() {
  companion object {
    private val ARG_URL = "arg_url"
    fun newInstance(url: String) =
        WebViewFragment().apply {
          arguments = Bundle().apply {
            putString(ARG_URL, url)
          }
        }
  }

  private lateinit var mURL: String
  private lateinit var mWebView: WebView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      WebView.setWebContentsDebuggingEnabled(true)
    }
    mURL = arguments.getString(ARG_URL)
    Timber.d("Loading URL: " + mURL)
    activity.setTitle(R.string.app_name)
  }

  override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = mUI
    initWebView()
    return view
  }

  private fun initWebView() {
    mWebView.settings.apply {
      javaScriptEnabled = true
      useWideViewPort = true
      loadWithOverviewMode = true
      domStorageEnabled = true
    }
    mWebView.disableWebViewZoomControls()

    mWebView.setWebViewClient(object: WebViewClient() {
      override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
//        if (url.startsWith("http://m.imgur.com/")) return true
        Timber.d("Loading URL: " + url)
        return false
      }
    })

    mWebView.loadUrl(mURL)
    mWebView.setOnKeyListener { v1, keyCode, event ->
      // Check if the key event was the Back button and if there's history
      if (event.action == KeyEvent.ACTION_UP
          && keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
        mWebView.goBack()
      }
      false
    }
  }

  override fun onDestroyView() {
    (mWebView.parent as ViewGroup).removeView(mWebView)
    mWebView.removeAllViews()
    mWebView.destroy()
    super.onDestroyView()
  }

  private val mUI: View by lazy {
    object: AnkoComponent<WebViewFragment> {
      override fun createView(ui: AnkoContext<WebViewFragment>): View =
          ui.apply {
            mWebView = webView {
              lparams {
                width = matchParent
                height = matchParent
              }
            }
          }.view
    }.createView(AnkoContext.create(context, this))
  }

//  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
//      Dialog(activity, R.style.DialogOverlay).apply {
//        setContentView(object: AnkoComponent<WebViewFragment> {
//          override fun createView(ui: AnkoContext<WebViewFragment>) =
//              ui.apply {
//                mWebView = webView {
//                  lparams {
//                    width = matchParent
//                    height = matchParent
//                  }
//                }
//              }.view
//        }.createView(AnkoContext.create(context, this@WebViewFragment)))
//      }
}
