package com.saranusaibanizam.todo

import android.content.Context
import java.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.saranusaibanizam.todo.databinding.TodoRowBinding

class ToDoAdapter(val context: Context):ListAdapter<ToDoModel,ToDoAdapter.ToDoViewHolder>(ToDoDiffCallBack()) {
    private lateinit var binding:TodoRowBinding
    class ToDoViewHolder(val binding: TodoRowBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(toDoModel: ToDoModel){
            binding.todoTV.text=toDoModel.name
            binding.dateRowTV.text = SimpleDateFormat("dd/MM/yyyy").format(toDoModel.date)
            binding.timeRowTV.text = SimpleDateFormat("hh:mm a").format(toDoModel.time)
            binding.isDoneCB.isChecked=toDoModel.isDone
            val colorID:Int=when(toDoModel.priority){
                HIGH->R.color.primary
                LOW->R.color.low
                else->R.color.normal
            }
            binding.containerL.setBackgroundColor(ContextCompat.getColor(binding.root.context,colorID))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        binding= TodoRowBinding.inflate(LayoutInflater.from(context),parent,false)
        return ToDoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.bind(getItem(position))
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