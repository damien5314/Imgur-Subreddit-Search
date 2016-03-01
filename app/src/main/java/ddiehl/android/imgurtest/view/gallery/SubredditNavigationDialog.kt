package ddiehl.android.imgurtest.view.gallery

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import ddiehl.android.imgurtest.R
import ddiehl.android.imgurtest.utils.getTintedDrawable
import ddiehl.android.imgurtest.view.widgets.RedditNavEditText
import ddiehl.android.imgurtest.view.widgets.redditNavEditText
import org.jetbrains.anko.*

class SubredditNavigationDialog(val mListener: Callbacks) : DialogFragment() {

  private lateinit var mRedditNavEditText: RedditNavEditText
  private lateinit var mRedditNavEditTextGo: ImageView

  interface Callbacks {
    fun onSubredditNavigationConfirmed(subreddit: String)
    fun onSubredditNavigationCancelled()
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val dialog = Dialog(activity)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(mUI)
        mRedditNavEditTextGo.setOnClickListener{ v: View ->
          val vInput = mRedditNavEditText
          var inputSubreddit = vInput.text.toString()
          if (inputSubreddit != "") {
            inputSubreddit = inputSubreddit.substring(3)
            inputSubreddit = inputSubreddit.trim()
            vInput.setText("")
            dialog.dismiss()
            mListener.onSubredditNavigationConfirmed(inputSubreddit)
          }
        }
    return dialog
  }

  private val mUI by lazy {
    object: AnkoComponent<SubredditNavigationDialog> {
      override fun createView(ui: AnkoContext<SubredditNavigationDialog>): View =
          ui.apply {
            linearLayout {
              lparams {
                width = matchParent
                height = dip(48)
              }
              setPadding(dip(16), 0, dip(16), 0)

              mRedditNavEditText = redditNavEditText {
                lparams {
                  width = dip(240)
                  height = wrapContent
                  gravity = Gravity.CENTER_VERTICAL
                }
                setTextAppearance(android.R.style.TextAppearance_Medium)
                textAlignment = View.TEXT_ALIGNMENT_VIEW_START
                gravity = Gravity.START
                imeOptions = EditorInfo.IME_ACTION_DONE
                inputType = InputType.TYPE_CLASS_TEXT
                singleLine = true
                background = null
                hint = getString(R.string.subreddit_nav_hint)
              }

              mRedditNavEditTextGo = imageView {
                lparams {
                  gravity = Gravity.CENTER_VERTICAL
                }
                background = getTintedDrawable(ui.ctx, R.drawable.ic_go, R.color.accent)
              }
            }
          }.view
    }.createView(AnkoContext.create(context, this))
  }
}
