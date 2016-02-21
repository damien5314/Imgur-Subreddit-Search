package ddiehl.android.imgurtest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

abstract class AbsGalleryItem(
    @Expose @SerializedName("is_album") val isAlbum: Boolean = false
) {
  class Response(
      @Expose val data: List<AbsGalleryItem>,
      @Expose val success: Boolean,
      @Expose val code: Int
  )
}