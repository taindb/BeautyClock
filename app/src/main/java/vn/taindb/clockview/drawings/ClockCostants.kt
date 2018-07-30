package vn.taindb.clockview.drawings


/**
 * Created by Taindb
 * on 7/28/18.
 */
object ClockCostants {
    /* The arc default starting angle is 0 degree on x-axis. Without having an offset angle the counting start
            * from the x-axis and clock seems to be leading by 90 degrees.
            * To match it with real clock structure, we need to shift this angle by 90 backward.
            * Hence, taking an offset value of 90.*/
    const val OFFSET_INTERVAL_ANGLE = 90

    // It is the maximum interval we want in the clock. For now we're taking it as 12.
    const val INTERVALS_COUNT = 12

    const val MAX_DEGREES = 360

    const val MIN_DEGREES = 0

    // Minimum rotation angle for a clock, 60 = minute in an hour, seconds in a minute
    const val MIN_ROTATION_ANGLE = MAX_DEGREES / 60

    // Minimum rotation angle for a clock, 60 = minute in an hour, seconds in a minute
    const val MIN_HOUR_ROTATION_ANGLE = MAX_DEGREES / 12

    // It depends on the INTERVALS_COUNT. This ia the angle by which every ticking of clock pointer is defined
    // e.g. if INTERVALS_COUNT = 12 then each tick would require to move by 30 degrees to go to next interval
    const val INTERVAL_ANGLE = MAX_DEGREES / INTERVALS_COUNT

    // Default center circle radius
    const val CENTER_RADIUS = 5

    // Minimum ticking interval for clock when in auto mode
    // 1 second in milliseconds
    const val CLOCK_TICK_AUTO_INTERVAL = 200

    // Time for ticker moving animation
    // In milliseconds
    const val CLOCK_TICK_ANIMATION_TIME = 100
}