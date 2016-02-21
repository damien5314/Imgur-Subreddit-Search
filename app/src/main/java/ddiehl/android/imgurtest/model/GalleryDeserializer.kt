package ddiehl.android.imgurtest.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class GalleryDeserializer : JsonDeserializer<AbsGalleryItem> {
  override fun deserialize(
      json: JsonElement, type: Type, ctx: JsonDeserializationContext): AbsGalleryItem {
    val obj = json.asJsonObject
    val isAlbum = obj.get("is_album").asBoolean
    if (isAlbum) return ctx.deserialize(json, GalleryAlbum::class.java)
    else return ctx.deserialize(json, GalleryImage::class.java)
  }
}