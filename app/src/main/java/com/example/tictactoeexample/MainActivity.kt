package com.example.tictactoeexample

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private val recyclerView : RecyclerView by lazy { findViewById(R.id.recyclerView) }
    private val newGameButton : Button by lazy { findViewById(R.id.newGameButton) }
    private var dataList  : MutableList<String>? = null
    private var mainAdapter : MainViewAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadData()
        newGameButton.setOnClickListener {
            mainAdapter = null
            loadData()
        }
    }

    private fun loadData(){
        dataList = mutableListOf()
        dataList?.apply {
            add("X")
            add("X")
            add("X")
            add("X")
            add("X")
            add("X")
            add("X")
            add("X")
            add("X")
        }
        recyclerView.layoutManager = GridLayoutManager(this , 3 )
        mainAdapter = MainViewAdapter(this , dataList , ::onClick)
        recyclerView.adapter = mainAdapter
    }

    private fun onClick(position: Int){
      if(position > -1){
          dataList?.set(position , "A")
          mainAdapter?.notifyItemChanged(position)
          val remainingList = mutableListOf<Int>()
          for(i in 0 until dataList?.size!!){
              if(!dataList?.get(i).equals("A") && !dataList?.get(i).equals("B")){
                  remainingList.add(i)
              }
          }
          if(remainingList.isNotEmpty()){
              val randomIndex = Random.nextInt(remainingList.size)
              dataList?.set(remainingList[randomIndex], "B")
              mainAdapter?.notifyItemChanged(remainingList[randomIndex])
          }else{
              Toast.makeText(this , "Game Over!!!" , Toast.LENGTH_SHORT).show()
          }
      }
    }
}

class MainViewAdapter(private var context: Context? = null ,
                      private var dataList : MutableList<String>? = null ,
                      private var onClick : (Int) -> Unit) : RecyclerView.Adapter<MainViewAdapter.MainViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.child_view , parent , false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.text.text = dataList?.get(position)
    }

    override fun getItemCount(): Int {
        return dataList?.size ?: 0
    }

    inner class MainViewHolder(view : View) : ViewHolder(view){
        val text : TextView = view.findViewById(R.id.playEditBox)
        init {
            text.setOnClickListener { onClick(adapterPosition) }
        }
    }
}