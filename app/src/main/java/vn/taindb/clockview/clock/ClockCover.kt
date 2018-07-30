package vn.taindb.clockview.clock

import android.graphics.Path
import android.graphics.RectF
import vn.taindb.clockview.drawings.ClockCostants


/**
 * Created by Taindb
 * on 7/28/18.
 */
class ClockCover {
    // The border of the circle
    val path = Path()

    // Rectangle inside which we'll draw this circle
    private val rectF = RectF()

    fun setRect(left: Int, top: Int, bottom: Int, right: Int) {
        rectF.set(left.toFloat(), top.toFloat(), bottom.toFloat(), right.toFloat())
    }

    // Should be called after setRect()
    fun setPath() {
        path.arcTo(rectF, ClockCostants.MIN_DEGREES.toFloat(), (ClockCostants.MAX_DEGREES - 1).toFloat(), true)
    }

}

