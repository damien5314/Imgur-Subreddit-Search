package ddiehl.android.imgurtest.view.widgets

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import org.jetbrains.anko.custom.ankoView

class RedditNavEditText : EditText, TextWatcher {

  constructor(context: Context) : super(context) {
    addTextChangedListener(this)

    onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
      if (!hasFocus) {
        // Hide keyboard
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
      }
    }

    setOnEditorActionListener {
      textView, actionId, keyEvent ->
      actionId == EditorInfo.IME_ACTION_DONE
    }
  }

  private var before: CharSequence? = null

  override fun beforeTextChanged(s: CharSequence, p1: Int, p2: Int, p3: Int) {
    before = s.subSequence(0, s.length)
  }

  override fun onTextChanged(text: CharSequence, start: Int, lengthBefore: Int, lengthAfter: Int) {}

  override fun afterTextChanged(s: Editable) {
    if (s.length == 0) {
      // Blank field
      return
    }

    if (s.length < 3) {
      s.insert(0, "/r/")
      return
    }

    if (s.length == 3) {
      s.clear()
      return
    }

    val cs = s.subSequence(0, 3)
    if (cs.toString() != "/r/" || s.toString().contains(" ")) {
      s.replace(0, s.length, before)
    }
  }
}

fun ViewManager.redditNavEditText(): RedditNavEditText = redditNavEditText({})
inline fun ViewManager.redditNavEditText(init: RedditNavEditText.() -> Unit): RedditNavEditText {
  return ankoView({ ctx: Context -> RedditNavEditText(ctx) }) { init() }
}
