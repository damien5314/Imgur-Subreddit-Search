package ddiehl.android.imgurtest.view

import android.support.annotation.StringRes

interface BaseView {
  fun showToast(@StringRes stringRes: Int)
}