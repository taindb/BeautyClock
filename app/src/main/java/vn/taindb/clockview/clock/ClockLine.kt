package vn.taindb.clockview.clock


/**
 * Created by Taindb
 * on 7/28/18.
 */
open class ClockLine {
    var startXY = Coordinates()

    var endXY = Coordinates()

    fun setStartXY(x: Float?, y: Float?) {
        startXY.coodX = x!!
        startXY.coodY = y!!
    }

    fun setEndXY(x: Float?, y: Float?) {
        endXY.coodX = x!!
        endXY.coodY = y!!
    }
}