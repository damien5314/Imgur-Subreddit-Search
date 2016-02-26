package ddiehl.android.imgurtest.view.gallery

import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import ddiehl.android.imgurtest.R
import ddiehl.android.imgurtest.model.GalleryAlbum
import ddiehl.android.imgurtest.model.GalleryImage
import ddiehl.android.imgurtest.presenters.GalleryPresenter
import ddiehl.android.imgurtest.view.widgets.squareImageView
import org.jetbrains.anko.*
import timber.log.Timber

class GalleryAdapter(val mPresenter: GalleryPresenter)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
  companion object {
    private val TYPE_IMAGE = 0
    private val TYPE_ALBUM = 1
  }

  override fun getItemViewType(position: Int): Int {
    val item = mPresenter.getItemAt(position)
    if (item is GalleryImage) return TYPE_IMAGE
    else if (item is GalleryAlbum) return TYPE_ALBUM
    else return Int.MIN_VALUE
  }

  override fun onBindViewHolder(vh: RecyclerView.ViewHolder, position: Int) {
    val item = mPresenter.getItemAt(position)
    if (vh is GalleryImageVH) vh.bind(item as GalleryImage)
    else if (vh is GalleryAlbumVH) vh.bind(item as GalleryAlbum)
    else Timber.w("Need a bind function for this type: " + vh.javaClass.simpleName)
  }

  override fun getItemCount(): Int = mPresenter.getNumImages()

  override fun onCreateViewHolder(parent: ViewGroup, type: Int): RecyclerView.ViewHolder? {
    when (type) {
      TYPE_IMAGE -> return GalleryImageVH(VH_UI().createView(
          AnkoContext.create(parent.context, this)), mPresenter)
      TYPE_ALBUM -> return GalleryAlbumVH(VH_UI().createView(
          AnkoContext.create(parent.context, this)), mPresenter)
    }
    return null
  }

  private class GalleryImageVH(val mView: View, val mPresenter: GalleryPresenter) :
      RecyclerView.ViewHolder(mView) {
    val imageView: ImageView = mView.find<ImageView>(R.id.image_view)
    val imagesCount: TextView = mView.find<TextView>(R.id.images_count)

    fun bind(image: GalleryImage) {
      imageView.setOnClickListener { mPresenter.onImageClicked(image) }
      imagesCount.visibility = View.GONE
      Glide.with(mView.context)
          .load(image.getMediumThumbnailLink())
          .centerCrop()
          .into(imageView)
    }
  }

  private class GalleryAlbumVH(val mView: View, val mPresenter: GalleryPresenter) :
      RecyclerView.ViewHolder(mView) {
    val imageView: ImageView = mView.find<ImageView>(R.id.image_view)
    val imagesCount: TextView = mView.find<TextView>(R.id.images_count)

    fun bind(album: GalleryAlbum) {
      imageView.setOnClickListener { mPresenter.onAlbumClicked(album) }
      imagesCount.text = album.imagesCount.toString()
      Glide.with(mView.context)
          .load(album.getLargeCover())
          .centerCrop()
          .into(imageView)
    }
  }

  private class VH_UI : AnkoComponent<GalleryAdapter> {
    override fun createView(ui: AnkoContext<GalleryAdapter>): View =
        ui.apply {
          frameLayout {
            lparams {
              width = matchParent
              height = wrapContent
              margin = dimen(R.dimen.gallery_image_margin)
            }
            padding = dimen(R.dimen.gallery_image_padding)
            backgroundResource = R.drawable.gallery_image_bg
            squareImageView {
              id = R.id.image_view
              lparams {
                width = matchParent
                height = dip(0)
              }
            }
            textView {
              id = R.id.images_count
              lparams {
                gravity = Gravity.BOTTOM or Gravity.LEFT
                margin = dip(8)
              }
              gravity = Gravity.CENTER
              setTextAppearance(android.R.style.TextAppearance_Small)
              backgroundResource = R.drawable.images_count_bg
            }
          }
        }.view
  }
}