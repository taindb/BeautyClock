package vn.taindb.paineffect

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import painteffect.taindb.vn.clockview.R
import vn.taindb.clockview.drawings.ClockView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var clockView = findViewById<ClockView>(R.id.clock_view)
    }


}
