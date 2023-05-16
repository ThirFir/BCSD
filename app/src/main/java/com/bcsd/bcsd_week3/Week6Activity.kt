package com.bcsd.bcsd_week3

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bcsd.bcsd_week3.databinding.ActivityWeek6Binding
import com.bcsd.bcsd_week3.databinding.ItemNameBinding

class Week6Activity : AppCompatActivity() {
    private lateinit var binding: ActivityWeek6Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeek6Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val nameList = mutableListOf<String>()
        binding.recyclerView.adapter = NameAdapter(nameList)
        binding.floatingButton.setOnClickListener {
            (binding.recyclerView.adapter as NameAdapter).addItem(binding.editTextName.text.toString())
        }
    }
}



class NameAdapter(private val nameList: MutableList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return NameViewHolder(ItemNameBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = nameList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NameViewHolder).binding.textName.text = nameList[position]
        holder.binding.root.setOnClickListener {
            AlertDialog.Builder(context).setTitle("이름 목록 삭제").setMessage("이름 삭제하기").setPositiveButton("확인") { _, _ ->
                removeItem(position)
            }.setNegativeButton("취소") { _, _ ->  }.show()
        }

        val editDialog = AlertDialog.Builder(context).setView(R.layout.dialog_edit_item).create()

        holder.binding.root.setOnLongClickListener {
            editDialog.show()
            editDialog.findViewById<Button>(R.id.button_cancel)?.setOnClickListener {
                Log.d("dddd", "ddddddddd")
                editDialog.dismiss()
            }
            editDialog.findViewById<Button>(R.id.button_ok)?.setOnClickListener {
                modifyItem(position, editDialog.findViewById<EditText>(R.id.editText_new_name)?.text.toString())
                editDialog.dismiss()
            }
            false
        }
    }

    fun addItem(name: String) {
        nameList.add(name)
        notifyItemInserted(itemCount)
    }

    fun removeItem(position: Int) {
        nameList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }

    fun modifyItem(position: Int, newName: String) {
        nameList[position] = newName
        notifyItemChanged(position)
    }

    inner class NameViewHolder(val binding: ItemNameBinding): RecyclerView.ViewHolder(binding.root)
}