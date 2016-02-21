package ddiehl.android.imgurtest

import android.support.annotation.StringRes

interface BaseView {
  fun showToast(@StringRes stringRes: Int)
}