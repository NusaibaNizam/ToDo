package com.saranusaibanizam.todo

import android.graphics.Color
import java.text.SimpleDateFormat
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RadioButton
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.saranusaibanizam.todo.databinding.FragmentAddTodoBinding
import java.util.*

class AddTodoFragment : Fragment() {
    lateinit var binding: FragmentAddTodoBinding
    private var priority= NORMAL
    private var date:Long=System.currentTimeMillis()
    private var time:Long=System.currentTimeMillis()
    private val toDoViewModel:ToDoViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        binding= FragmentAddTodoBinding.inflate(inflater)

        binding.dateTV.text = SimpleDateFormat("dd/MM/yyyy").format(Date(date))
        binding.timeTV.text = SimpleDateFormat("hh:mm a").format(Date(time))

        binding.priorityRG.setOnCheckedChangeListener { group, checkedId ->
            val rb=container?.findViewById<RadioButton>(checkedId)
            priority=rb?.text.toString()
        }

        setDateListener(binding.dateBT)
        setDateListener(binding.dateTV)
        setTimeListener(binding.timeBT)
        setTimeListener(binding.timeTV)

        binding.saveBT.setOnClickListener {
            if(!TextUtils.isEmpty(binding.todoET.text)){
                val name=binding.todoET.text.toString()
                if(date>time){
                    time=toDoViewModel.getCorretctTime(time,date)
                }
                val todo=ToDoModel(name = name,priority = priority,date = date,time = time)
                toDoViewModel.addToDo(todo)
                findNavController().navigate(R.id.action_addTodoFragment_to_todoListFragment)

            }else{
                binding.todoET.setError("Please enter the task!")
                binding.todoET.requestFocus()
            }
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }
    override fun onResume() {
        super.onResume()
        activity?.window?.statusBarColor = Color.TRANSPARENT
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.window?.statusBarColor = Color.WHITE
    }


    private fun setDateListener(view: View){
        view.setOnClickListener {
            DatePickerFragment{
                date=it
                binding.dateTV.text = SimpleDateFormat("dd/MM/yyyy").format(Date(date))
            }.show(childFragmentManager,"Date Picker")
        }
    }
    private fun setTimeListener(view:View){
        view.setOnClickListener {
            TimePickerFragment(date) {
                time = it
                binding.timeTV.text = SimpleDateFormat("hh:mm a").format(Date(time))
            }.show(childFragmentManager,"Time Picker")
        }
    }
}