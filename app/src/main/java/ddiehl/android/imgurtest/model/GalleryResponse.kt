package ddiehl.android.imgurtest.model

import com.google.gson.annotations.Expose

class GalleryResponse(
  @Expose val data: List<AbsGalleryItem>,
  @Expose val success: Boolean,
  @Expose val status: Int
)