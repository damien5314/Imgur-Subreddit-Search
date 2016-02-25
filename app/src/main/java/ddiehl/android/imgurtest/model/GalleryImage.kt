package ddiehl.android.imgurtest.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ddiehl.android.imgurtest.utils.readBoolean
import ddiehl.android.imgurtest.utils.writeBoolean
import java.util.*

class GalleryImage(
    val id: String,
    val title: String,
    val description: String,
    val datetime: Long,
    val type: String,
    val animated: Boolean,
    val width: Int,
    val height: Int,
    val size: Int,
    val views: Int,
    val bandwidth: Long,
    val deletehash: String?,
    private @Expose val link: String,
    val gifv: String?,
    val mp4: String?,
    val webm: String?,
    val looping: Boolean?,
    val vote: String?,
    val favorite: Boolean,
    val nsfw: Boolean?,
    @SerializedName("comment_count") val commentCount: Int,
    @SerializedName("comment_preview") val commentPreview: List<Comment>,
    val topic: String,
    @SerializedName("topic_id") val topicId: Int,
    val section: String,
    @SerializedName("account_url") val accountUrl: String,
    @SerializedName("account_id") val accountId: Int,
    val ups: Int,
    val downs: Int,
    val points: Int,
    val score: Int
//    @Expose @SerializedName("is_album") val isAlbum: Boolean = false
) : AbsGalleryItem(), Parcelable {

  fun getLink() = String.format("http://i.imgur.com/%s.jpg", id)
  fun getSmallThumbnailLink() = getThumbnailLink("t")
  fun getMediumThumbnailLink() = getThumbnailLink("m")
  fun getLargeThumbnailLink() = getThumbnailLink("l")
  fun getHugeThumbnailLink() = getThumbnailLink("h")

  private fun getThumbnailLink(s: String): String? {
    val i = link.lastIndexOf('/')
    val j = link.lastIndexOf('.')
    return link.substring(0, i + 8) + s + link.substring(j)
  }

  override fun writeToParcel(dest: Parcel, flags: Int) {
    dest.writeString(id)
    dest.writeString(title)
    dest.writeString(description)
    dest.writeLong(datetime)
    dest.writeString(type)
    dest.writeBoolean(animated)
    dest.writeInt(width)
    dest.writeInt(height)
    dest.writeInt(size)
    dest.writeInt(views)
    dest.writeLong(bandwidth)
    dest.writeString(deletehash)
    dest.writeString(link)
    dest.writeString(gifv)
    dest.writeString(mp4)
    dest.writeString(webm)
    dest.writeBoolean(looping ?: false)
    dest.writeString(vote)
    dest.writeBoolean(favorite)
    dest.writeBoolean(nsfw ?: false)
    dest.writeInt(commentCount)
    ArrayList<Comment>()
    dest.writeString(topic)
    dest.writeInt(topicId)
    dest.writeString(section)
    dest.writeString(accountUrl)
    dest.writeInt(accountId)
    dest.writeInt(ups)
    dest.writeInt(downs)
    dest.writeInt(points)
    dest.writeInt(score)
  }

  override fun describeContents(): Int = 0

  val CREATOR = object: Parcelable.Creator<GalleryImage> {
    override fun newArray(size: Int): Array<out GalleryImage?>? = null

    override fun createFromParcel(p: Parcel): GalleryImage =
        GalleryImage(
            p.readString(),
            p.readString(),
            p.readString(),
            p.readLong(),
            p.readString(),
            p.readBoolean(),
            p.readInt(),
            p.readInt(),
            p.readInt(),
            p.readInt(),
            p.readLong(),
            p.readString(),
            p.readString(),
            p.readString(),
            p.readString(),
            p.readString(),
            p.readBoolean(),
            p.readString(),
            p.readBoolean(),
            p.readBoolean(),
            p.readInt(),
            ArrayList<Comment>(),
            p.readString(),
            p.readInt(),
            p.readString(),
            p.readString(),
            p.readInt(),
            p.readInt(),
            p.readInt(),
            p.readInt(),
            p.readInt()
        )
  }
}
