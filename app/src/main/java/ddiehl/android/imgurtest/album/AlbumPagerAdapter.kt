package ddiehl.android.imgurtest.album

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class AlbumPagerAdapter(
    val fm: FragmentManager, val mPresenter: AlbumPresenter) : FragmentStatePagerAdapter(fm) {

  override fun getItem(position: Int): Fragment? =
      ImageFragment.newInstance(
          mPresenter.getImageAt(position).getLink())

  override fun getCount(): Int = mPresenter.getNumImages()
}