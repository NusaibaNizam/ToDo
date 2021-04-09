package com.saranusaibanizam.todo

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Adapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.saranusaibanizam.todo.databinding.FragmentTodoListBinding

class TodoListFragment : Fragment() {

    private lateinit var binding: FragmentTodoListBinding
    private val toDoViewModel:ToDoViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        binding= FragmentTodoListBinding.inflate(inflater)
        val adapter = ToDoAdapter(requireActivity()){todo,changeType->
            when(changeType){
                TODO_EDIT->toDoViewModel.updateToDo(todo)
                TODO_DELETE->toDoViewModel.removeToDo(todo)
            }
        }
        binding.todoRV.layoutManager=LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false)
        binding.todoRV.adapter=adapter
        val ItemTouchHelper=ItemTouchHelper(adapter.swipeToDeleteCallback)
        ItemTouchHelper.attachToRecyclerView(binding.todoRV)

        toDoViewModel.getToDos().observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                binding.todoRV.isVisible = false
                binding.emptyTV.isVisible = true
            } else {
                adapter.submitList(it)
                binding.emptyTV.isVisible = false
                binding.todoRV.isVisible = true
            }
        })
        binding.addBT.setOnClickListener {
            findNavController().navigate(R.id.action_todoListFragment_to_addTodoFragment)
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