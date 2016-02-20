package ddiehl.android.imgurtest.searchresults

import ddiehl.android.imgurtest.model.Image

interface SearchResultsView {
  fun showImages(list: List<Image>)
}