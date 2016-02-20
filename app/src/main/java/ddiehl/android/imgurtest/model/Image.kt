package ddiehl.android.imgurtest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Image(
    @Expose val link: String?,
    @Expose @SerializedName("is_album") val isAlbum: Boolean = false
)

data class ImageResponse(
    @Expose val data: List<Image>
)