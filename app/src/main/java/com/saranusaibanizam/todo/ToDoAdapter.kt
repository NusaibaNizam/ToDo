package com.saranusaibanizam.todo

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
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
                binding.todoTV.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                binding.todoTV.setTypeface(binding.todoTV.typeface, Typeface.ITALIC)
            }else {
                colorID=when (toDoModel.priority) {
                    HIGH -> R.color.high
                    LOW -> R.color.low
                    else -> R.color.normal
                }
                binding.todoTV.paintFlags = Paint.ANTI_ALIAS_FLAG
                binding.todoTV.setTypeface(binding.todoTV.typeface, Typeface.BOLD)
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

        private val deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_delete_accent)
        private val intrinsicWidth = deleteIcon?.intrinsicWidth
        private val intrinsicHeight = deleteIcon?.intrinsicHeight
        private val background = ColorDrawable()
        private val backgroundColor = ContextCompat.getColor(context,R.color.delete)
        private val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

            val itemView = viewHolder.itemView
            val itemHeight = itemView.bottom - itemView.top
            val isCanceled = dX == 0f && !isCurrentlyActive

            if (isCanceled) {
                clearCanvas(c, itemView.right + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                return
            }

            // Draw the red delete background
            background.color = backgroundColor
            background.setBounds(
                    itemView.right + dX.toInt(),
                    itemView.top,
                    itemView.right,
                    itemView.bottom
            )
            background.draw(c)

            // Calculate position of delete icon
            val iconTop = itemView.top + (itemHeight - intrinsicHeight!!) / 2
            val iconMargin = (itemHeight - intrinsicHeight) / 2
            val iconLeft = itemView.right - iconMargin - intrinsicWidth!!
            val iconRight = itemView.right - iconMargin
            val iconBottom = iconTop + intrinsicHeight

            // Draw the delete icon
            deleteIcon?.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            deleteIcon?.draw(c)

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            listener(getItem(viewHolder.adapterPosition), TODO_DELETE)
        }

        private fun clearCanvas(c: Canvas?, left: Float, top: Float, right: Float, bottom: Float) {
            c?.drawRect(left, top, right, bottom, clearPaint)
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