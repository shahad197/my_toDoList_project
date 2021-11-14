package com.example.todolist

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.database.ToDoList
import java.text.Format
import java.text.SimpleDateFormat
import java.util.*

const val KEY_ID = "new list"
 val currentDate = Date()
class FragmentToDoList : Fragment() {

    private lateinit var toDoRecycleView: RecyclerView


    private val toDoViewModel by lazy { ViewModelProvider(this).get(ToDoViewModel::class.java) }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_todo_menu, menu)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_list -> {
                val toDo = ToDoList()
                toDoViewModel.addList(toDo)

                val args = Bundle()
                args.putSerializable(KEY_ID, toDo.id)
                val fragment = ItemDetails()
                fragment.arguments = args
                activity?.let {
                    it.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .addToBackStack(null)
                        .commit()
                }

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_to_do_list, container, false)
        toDoRecycleView = view.findViewById(R.id.todo_list_recycle_view)

        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true
        toDoRecycleView.layoutManager = linearLayoutManager
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toDoViewModel.lifeDataToDo?.observe(

            viewLifecycleOwner, Observer {
                updateUI(it)

            }
        )


    }

    private fun updateUI(toDoList: List<ToDoList>) {
        val Adapter = AdapterToDoList(toDoList)
        toDoRecycleView.adapter = Adapter

    }


    private inner class Holder(View: View) : RecyclerView.ViewHolder(View), View.OnClickListener {
        private lateinit var item: ToDoList
        val listTitle: TextView = itemView.findViewById(R.id.list_title)
        val listDate: TextView = itemView.findViewById(R.id.list_date)
        val list_desc: TextView = itemView.findViewById(R.id.list_desc)
        val txt_date_due: TextView = itemView.findViewById(R.id.txt_date_due)
        val linCheck: LinearLayout = itemView.findViewById(R.id.isDone)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(toDoList: ToDoList) {
            this.item = toDoList
            listTitle.text = toDoList.title
             val sdf = SimpleDateFormat("dd/M/yyyy hh:mm")
             val currentDateNow = sdf.format(toDoList.date)
              Log.e("ShahedDevNew : " , currentDateNow)
            listDate.text = currentDateNow.toString()

            list_desc.text = toDoList.description
            if (toDoList.checkBox) {
                linCheck.visibility = View.VISIBLE
            } else {
                linCheck.visibility = View.GONE
            }




            val currentDate = Date()
            Log.e("CurentDateShahed : " , currentDate.toString())
            Log.e("CurentDateFromDB : " , toDoList.date.toString())

            if(toDoList.date != null ){
                if(currentDate.after(toDoList.date)){
                    Log.e("DateAfter : " , "After")

                    txt_date_due.visibility = View.VISIBLE
                    txt_date_due.text = "Your Time Is Over"
                }
                else {
                    Log.e("DateBefore : " , "Before")
                    txt_date_due.visibility = View.GONE

                }

            }



        }

        override fun onClick(p0: View) {
            if (p0 == itemView) {
                val args =
                    Bundle()
                args.putSerializable(KEY_ID, item.id)


                val fragment = ItemDetails()
                fragment.arguments = args
                activity?.let {
                    it.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .addToBackStack(null)
                        .commit()
                }
            }

        }


    }

    private inner class AdapterToDoList(var Item: List<ToDoList>) :
        RecyclerView.Adapter<FragmentToDoList.Holder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): FragmentToDoList.Holder {
            val view = layoutInflater.inflate(
                R.layout.recycleviewitem,
                parent,
                false
            )
            return Holder(view)

        }

        override fun onBindViewHolder(holder: FragmentToDoList.Holder, position: Int) {

            val nameData = Item[position]
            holder.bind(nameData)

        }

        override fun getItemCount(): Int = Item.size


    }
}

