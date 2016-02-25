package ddiehl.android.imgurtest.album

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class AlbumPagerAdapter(
    val fm: FragmentManager, val mPresenter: AlbumPresenter) : FragmentStatePagerAdapter(fm) {

  override fun getItem(position: Int): Fragment? {
    val image = mPresenter.getImageAt(position)
    val url = if (image.animated) image.webm!! else image.getLink()
    return ImageFragment.newInstance(url)
  }

  override fun getCount(): Int = mPresenter.getNumImages()
}