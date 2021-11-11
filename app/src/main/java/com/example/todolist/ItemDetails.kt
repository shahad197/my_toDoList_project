package com.example.todolist

import DatePicerDailogFragment
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.database.ToDoList
import java.util.*

const val LIST_DATE_KEY = "listDate"

class ItemDetails : Fragment(),DatePicerDailogFragment.DatePickerCallBack {
    private lateinit var editText: EditText
    private lateinit var editText2: EditText
    private lateinit var button: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var completeCheckBox: CheckBox

    private lateinit var todolist : ToDoList


    private val fragmentViewModel by lazy { ViewModelProvider(this).get(ItemDetailsViewModel::class.java) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_item_details, container, false)

        editText = view.findViewById(R.id.todo_list)
        editText2 = view.findViewById(R.id.description)
        button = view.findViewById(R.id.list_date)
        button2 = view.findViewById(R.id.add)
        button3 = view.findViewById(R.id.delete_but)
        completeCheckBox = view.findViewById(R.id.list_complet)

        button .apply {
            text= todolist.date.toString()

        }
        return view
    }

    override fun onStart() {
        super.onStart()

            button2.setOnClickListener{
                fragmentViewModel.saveUpdate(todolist)
                val fragment=FragmentToDoList()
                activity?.let {
                    it.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainer,fragment)
                        .addToBackStack(null)
                        .commit()
                }

            }
        button3.setOnClickListener{
            fragmentViewModel.delete(todolist)
            val fragment=FragmentToDoList()
            activity?.let {
                it.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentContainer,fragment)
                    .addToBackStack(null)
                    .commit()
            }

        }


        button.setOnClickListener {

            val args = Bundle()
            args.putSerializable(LIST_DATE_KEY, todolist.date)
            val datePicker = DatePicerDailogFragment()
            datePicker.setTargetFragment(this, 0)
            datePicker.show(this.parentFragmentManager, "DatePicker")
        }

            val textWatcher = object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    todolist.title = s.toString()
                }

                override fun afterTextChanged(s: Editable?) {
                }
            }
            val textWatcher2 = object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    todolist.description = s.toString()
                }

                override fun afterTextChanged(s: Editable?) {

                }
            }

            editText.addTextChangedListener(textWatcher)
            editText2.addTextChangedListener(textWatcher2)
            completeCheckBox.setOnCheckedChangeListener { _, isChecked ->
                todolist.checkBox = isChecked
            }

    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        todolist = ToDoList()
        val todolistID = arguments?.getSerializable(KEY_ID)as UUID //
        fragmentViewModel.loadTooList(todolistID)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentViewModel.todoLiveData.observe(
            viewLifecycleOwner, androidx.lifecycle.Observer {
                it?.let {
                    todolist = it
                    editText .setText(it.title)
                    editText2 .setText(it.description)
                    button.text = it.dueDate.toString()
                    completeCheckBox.isChecked = it .checkBox

                }
            }

        )
    }

    override fun onStop() {
        super.onStop()
        fragmentViewModel.saveUpdate(todolist)
    }

      override  fun onDateSelected (date:Date){

            todolist.date = date
            button.text = date.toString()
        }

}