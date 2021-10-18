/**
 * 커스텀 캘린더뷰 클래스
 * 인데 시간 남으면 구현하도록 하겠습니다 일단 기본 material date picker 사용
 * */
package com.example.a2021ictproject.fragment

import android.icu.util.Calendar
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.a2021ictproject.R
import com.example.a2021ictproject.databinding.CalendarDialogFragmentBinding
import com.prolificinteractive.materialcalendarview.*
import java.util.*

class CalendarDialogFragment : DialogFragment() {

    init {
        isCancelable = false
    }

    private lateinit var binding: CalendarDialogFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.calendar_dialog_fragment, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCalendar()

        binding.btnCloseCalendar.setOnClickListener {
            this.dismiss()
        }

        binding.btnConfirmCalendar.setOnClickListener {
            this.dismiss()
        }
    }

    private fun setCalendar() {
        val calendar = Calendar.getInstance()

        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DATE)

        val today = CalendarDay.from(currentYear, currentMonth, currentDay)

        binding.calendarViewCalendar.state().edit()
            .setFirstDayOfWeek(Calendar.SUNDAY)
            .setMinimumDate(CalendarDay.from(currentYear, currentMonth, 1))
            .commit()

        binding.calendarViewCalendar.addDecorators(
            MiniDecorator(today),
            TodayDecorator(today)
        )



    }

    /**
     * 대회를 생성하는 날짜보다 이전의 날짜를 선택하는 것을 막기 위한 decorator 클래스
     * */
    inner class MiniDecorator(private val minDay: CalendarDay) : DayViewDecorator {
        /* minDay 보다 이전의 날짜에 적용 */
        override fun shouldDecorate(day: CalendarDay?): Boolean =
            (day?.month == minDay.month && day.day < minDay.day)
                    || (day!!.month < minDay.month)

        override fun decorate(view: DayViewFacade?) {
            /* rmfwk색 light grey로 지정 */
            view?.addSpan(object : ForegroundColorSpan(R.color.light_grey) {})
            /* 선택 불가능하게 함 */
            view?.setDaysDisabled(true)
        }
    }

    inner class TodayDecorator(private val today: CalendarDay) : DayViewDecorator {
        override fun shouldDecorate(day: CalendarDay?): Boolean =
            (day!!.month == today.month && day.year == today.year && day.day == today.day)

        override fun decorate(view: DayViewFacade?) {
            val drawable = requireContext().getDrawable(R.drawable.background_calendar_today)
            view?.setBackgroundDrawable(drawable!!)
            view?.addSpan(object : ForegroundColorSpan(R.color.calendar_main_color) {})
        }

    }
}