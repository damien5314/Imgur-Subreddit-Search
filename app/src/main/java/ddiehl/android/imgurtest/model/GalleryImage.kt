package ddiehl.android.imgurtest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

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
    @Expose val link: String,
    val gifv: String?,
    val mp4: String?,
    val webm: String?,
    val looping: Boolean?,
    val vote: String?,
    val favorite: Boolean,
    val nsfw: Boolean?,
    @SerializedName("comment_count") val commentCount: Int,
    @SerializedName("comment_preview") val commentPreview: Array<Comment>,
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
) : AbsGalleryItem() {
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
