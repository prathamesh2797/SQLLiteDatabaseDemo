package com.example.sqllitedemo

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.items_row.view.*

class ItemAdapter(val context: Context, val items : ArrayList<EmpModelClass> )
    : RecyclerView.Adapter<ItemAdapter.ViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.items_row, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]
        holder.tvName.text = item.tvName
        holder.tvEmail.text = item.tvEmail


        if(position% 2==0){
            holder.llMain.setCardBackgroundColor(ContextCompat.getColor(context, R.color.grey))
        }
        else{
            holder.llMain.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))

        }

        holder.ivEdit.setOnClickListener { view->

            if (context is MainActivity){
                context.updateRecordDialog(item)
            }
        }

        holder.ivDelete.setOnClickListener { view->
            if (context is MainActivity){
                context.deleteRecordAlertDialog(item)
            }
        }
    }

    override fun getItemCount(): Int {

        return items.size
    }




    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val tvName = itemView.tvName
        val tvEmail = itemView.tvEmail
        val ivEdit = itemView.ivEdit
        val ivDelete = itemView.ivDelete
        val llMain = itemView.llMain

    }
}