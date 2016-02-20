package ddiehl.android.imgurtest.searchresults

import android.support.v7.widget.GridLayoutManager
import android.view.View
import ddiehl.android.imgurtest.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView

class SearchResultsFragment_UI : AnkoComponent<SearchResultsFragment> {
  override fun createView(ui: AnkoContext<SearchResultsFragment>): View =
    ui.apply {
      recyclerView {
        id = R.id.recycler_view
        lparams {
          width = matchParent
          height = matchParent
        }
        layoutManager = GridLayoutManager(ui.ctx, 3, GridLayoutManager.VERTICAL, false)
//        layoutManager = LinearLayoutManager(ui.ctx)
      }
    }.view
}