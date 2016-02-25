package ddiehl.android.imgurtest.view.images

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import ddiehl.android.imgurtest.presenters.ImagesPresenter

class ImagesPagerAdapter(
    val fm: FragmentManager, val mPresenter: ImagesPresenter) : FragmentStatePagerAdapter(fm) {

  override fun getItem(position: Int): Fragment? {
    val image = mPresenter.getImageAt(position)
    val i = image.gifv!!.indexOf('.')
    val url =
        if (image.animated)
          image.gifv.substring(0, image.gifv.lastIndexOf('/')+1) + image.id + ".gif"
        else
          image.getLink()
    return ViewImageFragment.newInstance(url)
  }

  override fun getCount(): Int = mPresenter.getNumImages()
}