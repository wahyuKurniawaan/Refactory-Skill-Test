package com.task.mycalendar.taskscreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.michalsvec.singlerowcalendar.calendar.CalendarChangesObserver
import com.michalsvec.singlerowcalendar.calendar.CalendarViewManager
import com.michalsvec.singlerowcalendar.calendar.SingleRowCalendarAdapter
import com.michalsvec.singlerowcalendar.selection.CalendarSelectionManager
import com.michalsvec.singlerowcalendar.utils.DateUtils
import com.task.mycalendar.R
import com.task.mycalendar.databinding.FragmentTaskBinding
import com.task.mycalendar.databinding.ItemCalendarSelectBinding

import java.util.*


class TaskFragment : Fragment() {
    private val calendar = Calendar.getInstance()
    private var currentMonth = 0
    private lateinit var bind: FragmentTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentTaskBinding.inflate(inflater, container, false)
        calendar.time = Date()
        currentMonth = calendar[Calendar.MONTH]

        if (calendar.time == Date()) {
            val getDate = "${DateUtils.getDayName(Date())}, ${DateUtils.getMonth3LettersName(Date())} ${
                DateUtils.getDayNumber(Date())
            } ${DateUtils.getYear(Date())} "
            bind.tvCurrentDate.text = getDate
        }

        val myCalendarViewManager = object : CalendarViewManager {
            @SuppressLint("SetTextI18n")
            override fun setCalendarViewResourceId(
                position: Int,
                date: Date,
                isSelected: Boolean
            ): Int {
                val cal = Calendar.getInstance()

                cal.time = date
                return if (isSelected) {
                    when (cal[Calendar.DAY_OF_WEEK]) {
                        Calendar.MONDAY -> R.layout.special_item_calendar_selected
                        Calendar.WEDNESDAY -> R.layout.special_item_calendar_selected
                        Calendar.FRIDAY -> R.layout.special_item_calendar_selected
                        else -> R.layout.item_calendar_select
                    }
                } else {
                    when (cal[Calendar.DAY_OF_WEEK]) {
                        Calendar.MONDAY -> R.layout.special_item_calendar_unselected
                        Calendar.WEDNESDAY -> R.layout.special_item_calendar_unselected
                        Calendar.FRIDAY -> R.layout.special_item_calendar_unselected
                        else -> R.layout.item_calendar_unselect
                    }
                }
            }
            override fun bindDataToCalendarView(
                holder: SingleRowCalendarAdapter.CalendarViewHolder,
                date: Date,
                position: Int,
                isSelected: Boolean
            ) {
                val bind = ItemCalendarSelectBinding.bind(holder.itemView)
                bind.tvDateCalendarItem.text = DateUtils.getDayNumber(date)
                bind.tvDayCalendarItem.text = DateUtils.getDay1LetterName(date)
                bind.root
            }
        }

        val myCalendarChangesObserver = object : CalendarChangesObserver {
            override fun whenSelectionChanged(isSelected: Boolean, position: Int, date: Date) {
                val setDate = "${DateUtils.getDayName(date)}, ${DateUtils.getMonth3LettersName(date)} ${
                    DateUtils.getDayNumber(date)
                } ${DateUtils.getYear(date)} "
                bind.tvCurrentDate.text = setDate

                super.whenSelectionChanged(isSelected, position, date)
            }
        }

        val mySelectionManager = object : CalendarSelectionManager {
            override fun canBeItemSelected(position: Int, date: Date): Boolean {
                return true
            }
        }

        bind.calendarView.apply {
            calendarViewManager = myCalendarViewManager
            calendarChangesObserver = myCalendarChangesObserver
            calendarSelectionManager = mySelectionManager
            includeCurrentDate = true
            setDates(getFutureDatesOfCurrentMonth())
            init()
        }
        return bind.root
    }


    private fun getFutureDatesOfCurrentMonth(): List<Date> {
        // get all next dates of current month
        currentMonth = calendar[Calendar.MONTH]
        return getDates(mutableListOf())
    }

    private fun getDates(list: MutableList<Date>): List<Date> {
        // load dates of whole month
        calendar.set(Calendar.MONTH, currentMonth)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        list.add(calendar.time)
        while (currentMonth == calendar[Calendar.MONTH]) {
            calendar.add(Calendar.DATE, +1)
            if (calendar[Calendar.MONTH] == currentMonth)
                list.add(calendar.time)
        }
        calendar.add(Calendar.DATE, -1)
        return list
    }


}

