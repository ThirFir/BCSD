package com.bcsd.bcsd_week3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bcsd.bcsd_week3.databinding.ActivityWeek11Binding
import com.bcsd.bcsd_week3.databinding.ItemTimeRecordBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class Week11Activity : AppCompatActivity() {
    private lateinit var binding: ActivityWeek11Binding

    private lateinit var onStartClickListener: OnClickListener
    private lateinit var onPauseClickListener: OnClickListener
    private lateinit var onResetClickListener: OnClickListener
    private lateinit var onRecordClickListener: OnClickListener

    private val mainDispatcher = Dispatchers.Main
    private val uiScope = CoroutineScope(mainDispatcher)

    private var isRunning = false
    private var displayTime = 0L
    private var startTime = 0L
    private var prevSectionTime = 0L

    private var stopWatchJob: Job? = null

    private val timeRecordAdapter = TimeRecordAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeek11Binding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()

        binding.run {
            buttonStart.setOnClickListener(onStartClickListener)
            buttonPause.setOnClickListener(onPauseClickListener)
            buttonReset.setOnClickListener(onResetClickListener)
            buttonRecord.setOnClickListener(onRecordClickListener)

            recyclerViewRecords.layoutManager = LinearLayoutManager(this@Week11Activity)
            recyclerViewRecords.adapter = timeRecordAdapter
        }

    }

    private fun initListeners() {
        onStartClickListener = OnClickListener {
            showStopButton()
            showRecordButton()
            startStopWatch()
        }
        onPauseClickListener = OnClickListener {
            showStartButton()
            showResetButton()
            pauseStopWatch()
        }
        onResetClickListener = OnClickListener {
            resetStopWatch()
        }
        onRecordClickListener = OnClickListener {
            recordTime()
        }
    }

    private fun startStopWatch() {
        isRunning = true
        startTime = SystemClock.elapsedRealtime() - displayTime
        stopWatchJob = uiScope.launch {
            while (isRunning) {
                displayTime = SystemClock.elapsedRealtime() - startTime
                binding.textViewTime.text = displayTime.toTimerString()
                delay(10)
            }
        }
    }

    private fun pauseStopWatch() {
        isRunning = false
        stopWatchJob?.cancel()
    }

    private fun resetStopWatch() {
        timeRecordAdapter.clearTimeRecords()
        displayTime = 0L
        prevSectionTime = 0L
        binding.textViewTime.text = displayTime.toTimerString()
    }

    private fun recordTime() {
        timeRecordAdapter.addTimeRecord(
            TimeRecord(
                timeRecordAdapter.itemCount + 1,
                displayTime - prevSectionTime,
                displayTime
            )
        )
        prevSectionTime = displayTime
    }

    private fun showStartButton() {
        binding.buttonPause.visibility = View.GONE
        binding.buttonStart.visibility = View.VISIBLE
    }

    private fun showStopButton() {
        binding.buttonStart.visibility = View.GONE
        binding.buttonPause.visibility = View.VISIBLE
    }

    private fun showResetButton() {
        binding.buttonRecord.visibility = View.GONE
        binding.buttonReset.visibility = View.VISIBLE
    }

    private fun showRecordButton() {
        binding.buttonReset.visibility = View.GONE
        binding.buttonRecord.visibility = View.VISIBLE
    }

    private class TimeRecordAdapter(private val records: MutableList<TimeRecord>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private inner class TimeRecordViewHolder(val binding : ItemTimeRecordBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            TimeRecordViewHolder(
                ItemTimeRecordBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

        override fun getItemCount(): Int = records.size

        fun addTimeRecord(timeRecord: TimeRecord) {
            records.add(0, timeRecord)
            notifyItemInserted(0)
        }

        fun clearTimeRecords() {
            records.clear()
            notifyDataSetChanged()
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            (holder as TimeRecordViewHolder).binding.run {
                textViewSection.text = DecimalFormat("00").format(records[position].section)
                textViewSectionRecordTime.text = records[position].sectionTime.toTimerString()
                textViewTotalTime.text = records[position].totalTime.toTimerString()
            }
        }
    }

    private data class TimeRecord(val section: Int, val sectionTime: Long, val totalTime: Long)
}

private fun Long.toTimerString() = SimpleDateFormat("mm : ss . SS").format(this)