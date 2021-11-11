import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.todolist.KEY_ID
import java.util.*

class DatePicerDailogFragment: DialogFragment() {

    interface DatePickerCallBack{
        fun onDateSelected(date: Date)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date = arguments?.getSerializable(KEY_ID) as? Date
        val calender = Calendar.getInstance()
        if (date != null) {
            calender.time = date
        }
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)
        val datelistenr = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->

            val resultDate = GregorianCalendar(year,month,day).time
            targetFragment?.let {

                (it as DatePickerCallBack).onDateSelected(resultDate)
            }

        }
        return DatePickerDialog(
            requireContext(),
            datelistenr,
            year,
            month,
            day


        )

    }
}