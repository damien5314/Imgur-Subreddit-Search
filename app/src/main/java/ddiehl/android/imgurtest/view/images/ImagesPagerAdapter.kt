package ddiehl.android.imgurtest.view.images

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import ddiehl.android.imgurtest.presenters.ImagesPresenter

class ImagesPagerAdapter(
    val fm: FragmentManager, val mPresenter: ImagesPresenter) : FragmentStatePagerAdapter(fm) {

  override fun getItem(position: Int): Fragment? {
    val image = mPresenter.getImageAt(position)
    val url = if (image.animated) image.webm!! else image.getLink()
    return ViewImageFragment.newInstance(url)
  }

  override fun getCount(): Int = mPresenter.getNumImages()
}