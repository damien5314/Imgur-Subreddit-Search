package ddiehl.android.imgurtest.searchresults

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import ddiehl.android.imgurtest.CustomApplication
import ddiehl.android.imgurtest.R
import ddiehl.android.imgurtest.model.Image
import org.jetbrains.anko.*

class SearchResultsAdapter(val presenter: SearchResultsPresenter)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  override fun onBindViewHolder(vh: RecyclerView.ViewHolder, position: Int) {
    (vh as VH).bind(presenter.getItemAt(position))
  }

  override fun getItemCount(): Int = presenter.getNumImages()

  override fun onCreateViewHolder(parent: ViewGroup, type: Int): RecyclerView.ViewHolder {
    return VH(VH_UI().createView(
        AnkoContext.create(parent.context, this)))
  }

  private class VH(val view: View) : RecyclerView.ViewHolder(view) {
    fun bind(image: Image) {
      val imageView = view.find<ImageView>(R.id.image_view)
      Picasso.with(CustomApplication.context)
          .load(image.link)
          .fit()
          .centerCrop()
          .into(imageView)
    }
  }

  private class VH_UI : AnkoComponent<SearchResultsAdapter> {
    override fun createView(ui: AnkoContext<SearchResultsAdapter>): View =
        ui.apply {
          frameLayout {
            lparams {
              width = matchParent
              height = wrapContent
            }
            imageView {
              id = R.id.image_view
              lparams {
                width = matchParent
                height = dip(128)
              }
            }
          }
        }.view
  }
}