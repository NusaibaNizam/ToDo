package com.saranusaibanizam.todo

import android.content.Context
import android.graphics.Paint
import java.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.saranusaibanizam.todo.databinding.TodoRowBinding

class ToDoAdapter(val context: Context,val listener:(ToDoModel,String)->Unit):ListAdapter<ToDoModel,ToDoAdapter.ToDoViewHolder>(ToDoDiffCallBack()) {
    private lateinit var binding:TodoRowBinding
    val swipeToDeleteCallback=SwipeToDeleteCallback()
    class ToDoViewHolder(val binding: TodoRowBinding,val listener:(ToDoModel,String)->Unit):RecyclerView.ViewHolder(binding.root){
        fun setBackground(toDoModel:ToDoModel, binding: TodoRowBinding){
            var colorID:Int?=null
            if(toDoModel.isDone){
                colorID=when(toDoModel.priority){
                    HIGH->R.color.highLight
                    LOW->R.color.lowLight
                    else->R.color.normalLight
                }
                binding.todoTV.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG)
            }else {
                colorID=when (toDoModel.priority) {
                    HIGH -> R.color.high
                    LOW -> R.color.low
                    else -> R.color.normal
                }
                binding.todoTV.setPaintFlags(Paint.ANTI_ALIAS_FLAG)
            }
            binding.containerL.setBackgroundColor(ContextCompat.getColor(binding.root.context,colorID))
        }
        fun bind(toDoModel: ToDoModel){
            binding.todoTV.text=toDoModel.name
            binding.dateRowTV.text = SimpleDateFormat("dd/MM/yyyy").format(toDoModel.date)
            binding.timeRowTV.text = SimpleDateFormat("hh:mm a").format(toDoModel.time)
            binding.isDoneCB.isChecked=toDoModel.isDone
            setBackground(toDoModel,binding)
            binding.isDoneCB.setOnClickListener{
                toDoModel.isDone=!toDoModel.isDone
                listener(toDoModel, TODO_EDIT)
                setBackground(toDoModel,binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        binding= TodoRowBinding.inflate(LayoutInflater.from(context),parent,false)
        return ToDoViewHolder(binding,listener)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    inner class SwipeToDeleteCallback:ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            listener(getItem(viewHolder.adapterPosition), TODO_DELETE)
        }

    }
}
class ToDoDiffCallBack:DiffUtil.ItemCallback<ToDoModel>(){
    override fun areItemsTheSame(oldItem: ToDoModel, newItem: ToDoModel): Boolean {
        return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(oldItem: ToDoModel, newItem: ToDoModel): Boolean {
        return oldItem==newItem
    }


}