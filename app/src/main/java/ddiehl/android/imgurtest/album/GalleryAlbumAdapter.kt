package ddiehl.android.imgurtest.album

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import ddiehl.android.imgurtest.LoggingListener
import ddiehl.android.imgurtest.R
import ddiehl.android.imgurtest.model.GalleryImage
import org.jetbrains.anko.*

class GalleryAlbumAdapter(val mPresenter: AlbumPresenter) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  override fun onBindViewHolder(vh: RecyclerView.ViewHolder, position: Int) {
    (vh as GalleryAlbumImageVH)
        .bind(mPresenter.getImageAt(position))
  }

  override fun onCreateViewHolder(parent: ViewGroup, type: Int): RecyclerView.ViewHolder? =
      GalleryAlbumImageVH(
          VH_UI().createView(
              AnkoContext.create(parent.context, this)), mPresenter)

  override fun getItemCount(): Int = mPresenter.getNumImages()

  private class GalleryAlbumImageVH(val mView: View, val mPresenter: AlbumPresenter) :
      RecyclerView.ViewHolder(mView) {
    val imageView = mView.find<ImageView>(R.id.image_view)

    fun bind(image: GalleryImage) {
      val link =
          if (image.gifv != null) image.gifv.substring(0, image.gifv.length-1)
          else image.getLink()
      Glide.with(mView.context)
          .load(link)
          .listener(LoggingListener<String, GlideDrawable>())
          .into(imageView)
    }
  }

  private class VH_UI : AnkoComponent<GalleryAlbumAdapter> {
    override fun createView(ui: AnkoContext<GalleryAlbumAdapter>): View =
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