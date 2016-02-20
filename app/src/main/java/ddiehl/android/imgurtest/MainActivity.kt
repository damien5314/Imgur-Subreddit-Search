package ddiehl.android.imgurtest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    MainActivity_UI().setContentView(this)
    supportFragmentManager.beginTransaction()
        .replace(R.id.fragment, SearchResultsFragment())
        .commit()
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