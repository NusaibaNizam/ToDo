package com.saranusaibanizam.todo

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import com.saranusaibanizam.todo.databinding.FragmentTodoListBinding

class TodoListFragment : Fragment() {

    private lateinit var binding: FragmentTodoListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        binding= FragmentTodoListBinding.inflate(inflater)
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