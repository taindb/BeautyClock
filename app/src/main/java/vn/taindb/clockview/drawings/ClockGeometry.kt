package vn.taindb.clockview.drawings


/**
 * Created by Taindb
 * on 7/28/18.
 */

object ClockGeometry {

    fun getRadianAngle(angle: Float): Double {
        return angle * (Math.PI / 180)
    }

    fun getRadianAngle(angle: Int): Double {
        return angle * (Math.PI / 180)
    }

    fun calculateX(coodX: Float?, radius: Int, rotationRadian: Double): Float {
        return ((coodX!! + (radius * Math.cos(rotationRadian))).toFloat())
    }

    fun calculateY(coodY: Float, radius: Int, rotationRadian: Double): Float {
        return ((coodY + (radius.times(Math.sin(rotationRadian)))).toFloat())
    }
}