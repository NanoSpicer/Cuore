package com.nanospicer.cuore

import android.app.AlarmManager
import android.app.Application
import androidx.core.app.AlarmManagerCompat
import androidx.core.content.getSystemService
import java.util.*

class CuoreApp : Application() {

    val alarmManager: AlarmManager?
        get() = getSystemService()

    override fun onCreate() {
        super.onCreate()

        /*AlarmManagerCompat
            .setAndAllowWhileIdle(
                alarmManager
            )*/

    }


    fun time(dayOfMonth: Int, month: Int, year: Int): Long =
        Calendar
            .getInstance()
            .apply {
                set(year, month, dayOfMonth)
            }.timeInMillis
}