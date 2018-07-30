package vn.taindb.clockview.clock


/**
 * Created by Taindb
 * on 7/28/18.
 */
class IntervalNumber {

    var number: String = ""

    var coordinates = Coordinates()

    fun setCoordinates(x: Float?, y: Float?) {
        this.coordinates.coodX = x!!
        this.coordinates.coodY = y!!
    }
}