package com.saranusaibanizam.todo

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.saranusaibanizam.todo.databinding.FragmentTodoListBinding


class TodoListFragment : Fragment() {

    private lateinit var binding: FragmentTodoListBinding
    var todoLiveDataList:LiveData<List<ToDoModel>>?=null
    private val toDoViewModel:ToDoViewModel by activityViewModels()
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        binding= FragmentTodoListBinding.inflate(inflater)


        val adapter = ToDoAdapter(requireActivity()){ todo, changeType->
            when(changeType){
                TODO_EDIT -> toDoViewModel.updateToDo(todo)
                TODO_DELETE -> {
                    val newTodo=ToDoModel(todo.id,todo.name,todo.isDone,todo.priority,todo.date,todo.time)
                    toDoViewModel.removeToDo(todo)
                    val undoSnackbar = Snackbar.make(binding.todoListCV,
                            R.string.undo_delete, Snackbar.LENGTH_LONG)
                    undoSnackbar.setAction(R.string.undo, View.OnClickListener {
                        toDoViewModel.addToDo(newTodo)
                    })
                    undoSnackbar.anchorView = binding.snackBarAnchor
                    undoSnackbar.show()
                    undoSnackbar.addCallback(object :BaseTransientBottomBar.BaseCallback<Snackbar>(){
                        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                            super.onDismissed(transientBottomBar, event)
                        }
                    })
                }
            }
        }
        binding.todoRV.layoutManager=LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.todoRV.adapter=adapter
        val ItemTouchHelper=ItemTouchHelper(adapter.swipeToDeleteCallback)
        ItemTouchHelper.attachToRecyclerView(binding.todoRV)

        getTodoList(adapter, 0)

        binding.todoTV.setOnClickListener {
            getTodoList(adapter, 0)
        }

        binding.completeTV.setOnClickListener {
            getTodoList(adapter, 1)
        }

        binding.allTV.setOnClickListener {
            getTodoList(adapter, 2)
        }

        binding.addBT.setOnClickListener {
            findNavController().navigate(R.id.action_todoListFragment_to_addTodoFragment)
        }
        return binding.root
    }

    private fun getTodoList(adapter: ToDoAdapter, menu: Int) {

        todoLiveDataList?.removeObservers(viewLifecycleOwner)
        when(menu){
            0 -> {
                val string = SpannableString(getString(R.string.to_do))
                string.setSpan(UnderlineSpan(), 0, string.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.todoTV.text=string
                binding.todoTV.setTextColor(resources.getColor(R.color.white,null))
                binding.allTV.text=getString(R.string.all)
                binding.allTV.setTextColor(resources.getColor(R.color.grey,null))
                binding.completeTV.text=getString(R.string.complete)
                binding.completeTV.setTextColor(resources.getColor(R.color.grey,null))
                todoLiveDataList = toDoViewModel.getRemainingToDos()
            }
            1 -> {
                val string = SpannableString(getString(R.string.complete))
                string.setSpan(UnderlineSpan(), 0, string.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.todoTV.text=getString(R.string.to_do)
                binding.todoTV.setTextColor(resources.getColor(R.color.grey,null))
                binding.allTV.text=getString(R.string.all)
                binding.allTV.setTextColor(resources.getColor(R.color.grey,null))
                binding.completeTV.text=string
                binding.completeTV.setTextColor(resources.getColor(R.color.white,null))
                todoLiveDataList = toDoViewModel.getCompleteToDos()
            }
            else->{
                val string = SpannableString(getString(R.string.all))
                string.setSpan(UnderlineSpan(), 0, string.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.todoTV.text=getString(R.string.to_do)
                binding.todoTV.setTextColor(resources.getColor(R.color.grey,null))
                binding.allTV.text=string
                binding.allTV.setTextColor(resources.getColor(R.color.white,null))
                binding.completeTV.text=getString(R.string.complete)
                binding.completeTV.setTextColor(resources.getColor(R.color.grey,null))
                todoLiveDataList=toDoViewModel.getAllToDos()
            }
        }


        todoLiveDataList?.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                adapter.submitList(it)
                binding.todoRV.isVisible = false
                binding.emptyTV.isVisible = true
            } else {
                adapter.submitList(it)
                binding.emptyTV.isVisible = false
                binding.todoRV.isVisible = true
            }
        })

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

