package ddiehl.android.imgurtest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.parceler.Parcel

// Parceler notes
// Use Parcel.Serialization.BEAN to cause Parceler to use getters & setters to modify fields
// This is required for Kotlin classes because fields are private and accessed by properties
// Apply default values in the primary constructor to remove the need to annotate with @ParcelProperty
@Suppress("unused")
@Parcel(Parcel.Serialization.BEAN)
class GalleryImage(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val datetime: Long = 0L,
    val type: String = "",
    val animated: Boolean = false,
    val width: Int = 0,
    val height: Int = 0,
    val size: Int = 0,
    val views: Int = 0,
    val bandwidth: Long = 0L,
    val deletehash: String? = null,
    @Expose
    private val link: String = "",
    val gifv: String? = null,
    val mp4: String? = null,
    val webm: String? = null,
    val looping: Boolean? = false,
    val vote: String? = "",
    val favorite: Boolean = false,
    val nsfw: Boolean? = false,
    @SerializedName("comment_count")
    val commentCount: Int = 0,
    @SerializedName("comment_preview")
    val commentPreview: List<Comment> = emptyList(),
    val topic: String = "",
    @SerializedName("topic_id")
    val topicId: Int = 0,
    val section: String = "",
    @SerializedName("account_url")
    val accountUrl: String = "",
    @SerializedName("account_id")
    val accountId: Int = 0,
    val ups: Int = 0,
    val downs: Int = 0,
    val points: Int = 0,
    val score: Int = 0
) : AbsGalleryItem() {
  
  constructor() : this("")

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
}
