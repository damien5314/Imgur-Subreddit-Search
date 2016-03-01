package ddiehl.android.imgurtest.view

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import ddiehl.android.imgurtest.R
import ddiehl.android.imgurtest.view.gallery.GalleryFragment
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity(), MainView {

  private val mProgressDialog: ProgressDialog by lazy {
    ProgressDialog(this, R.style.ProgressDialog).apply {
      setCancelable(false)
      setProgressStyle(ProgressDialog.STYLE_SPINNER)
    }
  }

  override fun showSpinner() {
    mProgressDialog.show()
  }

  override fun dismissSpinner() {
    if (mProgressDialog.isShowing) mProgressDialog.dismiss()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    MainActivity_UI().setContentView(this)
    val fragment = supportFragmentManager.findFragmentById(R.id.fragment)
    if (fragment == null) {
      supportFragmentManager.beginTransaction()
          .replace(R.id.fragment, GalleryFragment(this))
          .commit()
    }
  }

  private class MainActivity_UI : AnkoComponent<MainActivity> {
    override fun createView(ui: AnkoContext<MainActivity>): View =
        ui.apply {
          frameLayout {
            lparams {
              id = R.id.fragment
              width = matchParent
              height = matchParent
            }
          }
        }.view
  }
}

interface MainView {
  fun showSpinner()
  fun dismissSpinner()
}
