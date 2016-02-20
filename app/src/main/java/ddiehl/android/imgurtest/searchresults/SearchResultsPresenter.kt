package ddiehl.android.imgurtest.searchresults

import ddiehl.android.imgurtest.BasePresenter
import ddiehl.android.imgurtest.model.Image

interface SearchResultsPresenter : BasePresenter {
  fun getNumImages(): Int
  fun getItemAt(position: Int): Image
}