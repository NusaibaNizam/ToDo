package com.saranusaibanizam.todo

import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.os.Binder
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RadioButton
import androidx.annotation.RequiresApi
import com.saranusaibanizam.todo.databinding.FragmentAddTodoBinding
import java.util.*

class AddTodoFragment : Fragment() {
    lateinit var binding: FragmentAddTodoBinding
    private var priority= NORMAL
    private var date:Long=System.currentTimeMillis()
    private var time:Long=System.currentTimeMillis()
    @RequiresApi(Build.VERSION_CODES.N)
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
        binding.dateBT.setOnClickListener {
            DatePickerFragment{
             date=it
             binding.dateTV.text = SimpleDateFormat("dd/MM/yyyy").format(Date(date))
            }.show(childFragmentManager,"Date Picker")
        }
        binding.timeBT.setOnClickListener {
            TimePickerFragment(date) {
                time = it
                binding.timeTV.text = SimpleDateFormat("hh:mm a").format(Date(time))
            }.show(childFragmentManager,"Time Picker")
        }
        binding.saveBT.setOnClickListener {
            if(!TextUtils.isEmpty(binding.todoET.text)){
                val name=binding.todoET.text.toString()
                if(date>time){
                    time=date+time
                }
                val todo=ToDoModel(name = name,priority = priority,date = date,time = time)
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
}