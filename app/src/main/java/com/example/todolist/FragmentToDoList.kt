package com.example.todolist

import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.database.ToDoList
import java.util.*

const val  KEY_ID = "new list"
class FragmentToDoList :Fragment() {

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
                                args.putSerializable(KEY_ID,toDo.id)
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


        private inner class Holder(View: View) : RecyclerView.ViewHolder(View),View.OnClickListener {
                private lateinit var item: ToDoList
                val listTitle: TextView = itemView.findViewById(R.id.list_title)
                val listDate: TextView = itemView.findViewById(R.id.list_date)

                init {
                    itemView.setOnClickListener(this)
                }

                fun bind(toDoList: ToDoList) {
                        this.item = toDoList
                        listTitle.text = toDoList.title
                        listDate.text = toDoList.date.toString()

                        listDate.setOnClickListener {
                                val currentDate=Date()
                                if (toDoList.dueDate!=null){
                               if (currentDate.after(toDoList.dueDate))  {
                                       Toast.makeText(context,"the time over",Toast.LENGTH_SHORT).show()
                               }

                        }
                        }



                }

              override  fun onClick(p0: View) {
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

