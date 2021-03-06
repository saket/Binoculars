package me.saket.flick

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout

/** A ViewGroup that can be dismissed by flicking it vertically. */
open class FlickDismissLayout(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
  constructor(context: Context) : this(context, null)

  var gestureListener: FlickGestureListener? = null

  override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
    val intercepted = requireGestureListener().onTouch(this, ev)
    return intercepted || super.onInterceptTouchEvent(ev)
  }

  @SuppressLint("ClickableViewAccessibility")
  override fun onTouchEvent(event: MotionEvent): Boolean {
    return requireGestureListener().onTouch(this, event)
  }

  // These two functions are often used in combination by widgets (e.g., BaseSlider
  // in material components) to find out if they're inside a scrollable parent.
  override fun canScrollVertically(direction: Int): Boolean = true
  override fun shouldDelayChildPressedState(): Boolean = true

  private fun requireGestureListener(): FlickGestureListener {
    if (gestureListener == null) {
      throw AssertionError("Did you forget to set gestureListener?")
    }
    return gestureListener!!
  }
}
