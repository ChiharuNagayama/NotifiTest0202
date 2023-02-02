package com.example.notifitest0127

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime
import java.util.*


class MainActivity : AppCompatActivity() {
    @SuppressLint("UnspecifiedImmutableFlag")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnNotifi).setOnClickListener{

            //実行するクラスを指定
            val alarmMgr: AlarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val alarmIntent: PendingIntent = Intent(application, AlarmReceiver::class.java).let { intent ->

                var yosangaku =  10000
                var huku = 3000
                var mizu = 400

//                デバッグは条件変更のたびにアンスト＋通知ONすること
                var msg1 = if (yosangaku < huku+mizu){
                    "予算額を超過しているよ！"
                } else {
                    "まだまだ予算が余っているよ"
                }

                var msg2 = if (yosangaku < huku+mizu){
                    "超過"
                } else {
                    "余裕"
                }

                intent.putExtra("TITLE",msg1)
                intent.putExtra("BODY",msg2)
                PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            }

            val calendar: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.DAY_OF_MONTH,25)
            }


            //25日（指定時間）にアラームを実行、以降は1日間隔
            alarmMgr.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                alarmIntent
            )
//
//           //起動後5秒後
//            alarmMgr.set(
//                AlarmManager.ELAPSED_REALTIME_WAKEUP,
//
//
//
////                SystemClock.elapsedRealtime() + 5 * 1000,
////                alarmIntent
//            )
//
        }

    }
}