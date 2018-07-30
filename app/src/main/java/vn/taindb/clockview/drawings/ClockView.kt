package vn.taindb.clockview.drawings

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.Log
import android.view.View
import painteffect.taindb.vn.clockview.R
import vn.taindb.clockview.clock.*


/**
 * Created by Taindb
 * on 7/28/18.
 */
class ClockView : View, ClockTimer.OnClockTimerListener {

    companion object {
        private const val TAG = "ClockView"

        private const val MARGIN = 16

        private const val MARGIN_SECOND_VIEW = 80

        private const val MARGIN_MINUTE_VIEW = 120

        private const val MARGIN_HOUR_VIEW = 160
    }

    private var gold: Int = 0

    private var pink: Int = 0

    private var purple: Int = 0

    private var red: Int = 0

    private var green: Int = 0

    private var mFinalWidth: Int = 0

    private var mFinalHeight: Int = 0

    private var mRadius: Int = 0

    private var mClockCoverPaint: Paint? = null

    private var mClockCenterPaint: Paint? = null

    private var mIntervalLinePaint: Paint? = null

    private var mIntervalNumberPaint: Paint? = null

    private var mSecondTickerPaint: Paint? = null

    private var mMinutesTickerPaint: Paint? = null

    private var mHoursTickerPaint: Paint? = null

    private var mClockOver: ClockCover? = null

    private var mClockCenter: ClockCenter? = null

    private var mMinutesTicker: MinutesTicker? = null

    private var mSecondsTicker: SecondsTicker? = null

    private var mHoursTicker: HoursTicker? = null

    private val mIntervals = ArrayList<Interval>()

    private var mSecondsRotation: Double = 0.0

    private var mMinutesRotation: Double = 0.0

    private var mHoursRotation: Double = 0.0

    private var mClockTimer: ClockTimer? = null

    constructor(context: Context?) : super(context) {
        initPaint()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initPaint()
    }

    private fun initPaint() {
        mClockTimer = ClockTimer(this)
        gold = ContextCompat.getColor(context, R.color.gold)
        purple = ContextCompat.getColor(context, R.color.purple)
        red = ContextCompat.getColor(context, R.color.red)
        pink = ContextCompat.getColor(context, R.color.live_pink)
        green = ContextCompat.getColor(context, R.color.green)

        mClockCoverPaint = Paint().apply {
            color = pink
            isAntiAlias = true
            strokeCap = Paint.Cap.ROUND
            style = Paint.Style.STROKE
            strokeWidth = 8f
        }

        mClockCenterPaint = Paint().apply {
            color = red
            isAntiAlias = true
            style = Paint.Style.FILL
            strokeWidth = 8f
        }

        mIntervalLinePaint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            strokeWidth = 8f
            strokeCap = Paint.Cap.ROUND
            color = pink
        }

        mIntervalNumberPaint = Paint().apply {
            isAntiAlias = true
            color = green
        }

        mSecondTickerPaint = Paint().apply {
            color = gold
            isAntiAlias = true
            strokeCap = Paint.Cap.ROUND
            strokeWidth = 4f
        }

        mMinutesTickerPaint = Paint().apply {
            color = red
            isAntiAlias = true
            strokeCap = Paint.Cap.ROUND
            strokeWidth = 6f
        }

        mHoursTickerPaint = Paint().apply {
            color = purple
            isAntiAlias = true
            strokeCap = Paint.Cap.ROUND
            strokeWidth = 8f
        }

        mClockOver = ClockCover()
        mClockCenter = ClockCenter(ClockCostants.CENTER_RADIUS)
        mSecondsTicker = SecondsTicker()
        mMinutesTicker = MinutesTicker()
        mHoursTicker = HoursTicker()

    }

    private fun initBaseParameter() {
        mClockOver?.apply {
            setRect(MARGIN, MARGIN, mFinalWidth.minus(MARGIN), mFinalHeight.minus(MARGIN))//padding
            setPath()
        }

        mClockCenter?.apply {
            coordinates = Coordinates(mFinalWidth.div(2f), mFinalHeight.div(2f))
        }

        mSecondsRotation = ClockGeometry.getRadianAngle(-ClockCostants.OFFSET_INTERVAL_ANGLE)
        val coordinatesCenter = mClockCenter?.coordinates
        mSecondsTicker?.apply {
            setStartXY(coordinatesCenter?.coodX, coordinatesCenter?.coodY)
            setEndXY(
                    ClockGeometry.calculateX(coordinatesCenter?.coodX, mRadius.minus(MARGIN_SECOND_VIEW), mSecondsRotation),
                    ClockGeometry.calculateY(coordinatesCenter?.coodY!!, mRadius.minus(MARGIN_SECOND_VIEW), mSecondsRotation)
            )
        }

        mMinutesTicker?.apply {
            setStartXY(coordinatesCenter?.coodX, coordinatesCenter?.coodY)
            setEndXY(
                    ClockGeometry.calculateX(coordinatesCenter?.coodX, mRadius.minus(MARGIN_MINUTE_VIEW), mSecondsRotation),
                    ClockGeometry.calculateY(coordinatesCenter?.coodY!!, mRadius.minus(MARGIN_MINUTE_VIEW), mSecondsRotation)
            )
        }

        mHoursTicker?.apply {
            setStartXY(coordinatesCenter?.coodX, coordinatesCenter?.coodY)
            setEndXY(
                    ClockGeometry.calculateX(coordinatesCenter?.coodX, mRadius.minus(MARGIN_HOUR_VIEW), mSecondsRotation),
                    ClockGeometry.calculateY(coordinatesCenter?.coodY!!, mRadius.minus(MARGIN_HOUR_VIEW), mSecondsRotation)
            )
        }

        mIntervalNumberPaint?.textSize = 0.08f * mFinalWidth
        if (mIntervals.isEmpty()) {
            for (i in 0 until ClockCostants.INTERVALS_COUNT) {
                val intervalLine = IntervalLine()
                val intervalNumber = IntervalNumber()

                mSecondsRotation = ClockGeometry.getRadianAngle(
                        -ClockCostants.OFFSET_INTERVAL_ANGLE + ClockCostants.INTERVAL_ANGLE + (i * ClockCostants.INTERVAL_ANGLE))

                val coordinatesCenter = mClockCenter?.coordinates

                // prepare coordinates for Interval line
                intervalLine.setStartXY(
                        ClockGeometry.calculateX(coordinatesCenter?.coodX!!, mRadius.minus(MARGIN), mSecondsRotation),
                        ClockGeometry.calculateY(coordinatesCenter.coodY, mRadius.minus(MARGIN), mSecondsRotation)
                )

                intervalLine.setEndXY(
                        ClockGeometry.calculateX(coordinatesCenter.coodX, mRadius.minus(26), mSecondsRotation),
                        ClockGeometry.calculateY(coordinatesCenter.coodY, mRadius.minus(26), mSecondsRotation)
                )

                // prepare coordinates for number text
                intervalNumber.number = (i + 1).toString()
                intervalNumber.setCoordinates(
                        ClockGeometry.calculateX(
                                coordinatesCenter.coodX,
                                mRadius.minus(50),
                                mSecondsRotation).minus((mIntervalNumberPaint?.measureText(intervalNumber.number)?.div(2)!!)),
                        ClockGeometry.calculateY(
                                coordinatesCenter.coodX,
                                mRadius.minus(50),
                                mSecondsRotation).minus((mIntervalNumberPaint?.descent()!!.plus(mIntervalNumberPaint!!.ascent().div(2))))
                )
                mIntervals.add(Interval(intervalLine, intervalNumber))
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mFinalWidth = measuredWidth / 2
        mFinalHeight = measuredWidth / 2
        mRadius = mFinalWidth.div(2)
        setMeasuredDimension(mFinalWidth, mFinalHeight)
        Log.d(TAG, "onMeasure")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        initBaseParameter()
        start()
        Log.d(TAG, "onLayout")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.d(TAG, "onDraw")
        // Drawing the cover circle for clock
        canvas?.drawPath(mClockOver?.path, mClockCoverPaint)

        mClockCenter?.coordinates?.let {
            canvas?.drawCircle(
                    it.coodX,
                    it.coodY,
                    mClockCenter?.radius!!.toFloat(),
                    mClockCenterPaint)
        }

        mIntervals.forEach {
            val intervalNumber = it.intervalNumber
            val intervalLine = it.intervalLine

            // Finally drawing the interval lines
            canvas?.drawLine(
                    intervalLine.startXY.coodX,
                    intervalLine.startXY.coodY,
                    intervalLine.endXY.coodX,
                    intervalLine.endXY.coodY,
                    mIntervalLinePaint
            )

            // Drawing the text with interval
            canvas?.drawText(
                    intervalNumber.number,
                    intervalNumber.coordinates.coodX,
                    intervalNumber.coordinates.coodY,
                    mIntervalNumberPaint
            )
        }

        // Drawing the second ticker
        canvas?.drawLine(
                mSecondsTicker?.startXY?.coodX!!,
                mSecondsTicker?.startXY?.coodY!!,
                mSecondsTicker?.endXY?.coodX!!,
                mSecondsTicker?.endXY?.coodY!!,
                mSecondTickerPaint
        )

        // Drawing the minutes ticker
        canvas?.drawLine(
                mMinutesTicker?.startXY?.coodX!!,
                mMinutesTicker?.startXY?.coodY!!,
                mMinutesTicker?.endXY?.coodX!!,
                mMinutesTicker?.endXY?.coodY!!,
                mMinutesTickerPaint
        )

        // Drawing the hours ticker
        canvas?.drawLine(
                mHoursTicker?.startXY?.coodX!!,
                mHoursTicker?.startXY?.coodY!!,
                mHoursTicker?.endXY?.coodX!!,
                mHoursTicker?.endXY?.coodY!!,
                mHoursTickerPaint
        )
    }

    override fun onNextSecond(second: Int) {
        mSecondsRotation = ClockGeometry.getRadianAngle(
                second.times(ClockCostants.MIN_ROTATION_ANGLE) - ClockCostants.OFFSET_INTERVAL_ANGLE
        )
        mSecondsTicker?.apply {
            setEndXY(
                    ClockGeometry.calculateX(mClockCenter?.coordinates?.coodX, mRadius.minus(MARGIN_SECOND_VIEW), mSecondsRotation),
                    ClockGeometry.calculateY(mClockCenter?.coordinates?.coodY!!, mRadius.minus(MARGIN_SECOND_VIEW), mSecondsRotation)
            )
        }
    }

    override fun onNextMinute(minute: Int) {
        mMinutesRotation = ClockGeometry.getRadianAngle(
                minute.times(ClockCostants.MIN_ROTATION_ANGLE) - ClockCostants.OFFSET_INTERVAL_ANGLE
        )
        mMinutesTicker?.apply {
            setEndXY(
                    ClockGeometry.calculateX(mClockCenter?.coordinates?.coodX, mRadius.minus(MARGIN_MINUTE_VIEW), mMinutesRotation),
                    ClockGeometry.calculateY(mClockCenter?.coordinates?.coodY!!, mRadius.minus(MARGIN_MINUTE_VIEW), mMinutesRotation)
            )
        }
    }

    override fun onNextHour(hour: Float) {
        mHoursRotation = ClockGeometry.getRadianAngle(
                hour.times(ClockCostants.MIN_HOUR_ROTATION_ANGLE) - ClockCostants.OFFSET_INTERVAL_ANGLE
        )
        mHoursTicker?.apply {
            setEndXY(
                    ClockGeometry.calculateX(mClockCenter?.coordinates?.coodX, mRadius.minus(MARGIN_HOUR_VIEW), mHoursRotation),
                    ClockGeometry.calculateY(mClockCenter?.coordinates?.coodY!!, mRadius.minus(MARGIN_HOUR_VIEW), mHoursRotation)
            )
        }
    }

    override fun onShouldUpdateClock() {
        invalidate()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stop()
    }

    fun start() {
        mClockTimer?.start()
    }

    fun stop() {
        mClockTimer?.stop()
    }
}