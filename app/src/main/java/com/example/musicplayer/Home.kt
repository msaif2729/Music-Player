package com.example.musicplayer

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class Home : AppCompatActivity() {

    lateinit var recycle : RecyclerView
    lateinit var myAdapter: MyAdapter


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var t1 : String = "eminem"
        recycle = findViewById<RecyclerView>(R.id.recycle)


        val textInputLayout = findViewById<TextInputLayout>(R.id.filledTextField)
        val textInputEditText = findViewById<TextInputEditText>(R.id.ar)

        val retrofitbuilder = Retrofit.Builder()
            .baseUrl("https://deezerdevs-deezer.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        textInputLayout.setStartIconOnClickListener {
            val enteredText = textInputEditText.text.toString()

            if(enteredText.equals("")) {
                Toast.makeText(this, "Plz Enter Artist Name", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, enteredText, Toast.LENGTH_SHORT).show()
                t1 = enteredText

                val retrofit = retrofitbuilder.getData(t1)

                retrofit.enqueue(object : Callback<MyData?> {
                    override fun onResponse(p0: Call<MyData?>, p1: Response<MyData?>) {
                        Toast.makeText(this@Home,"Success",Toast.LENGTH_LONG).show()

                        val datalist = p1.body()?.data!!

                        myAdapter = MyAdapter(this@Home,datalist)
                        recycle.adapter = myAdapter
                        recycle.layoutManager = LinearLayoutManager(this@Home)


                    }

                    override fun onFailure(p0: Call<MyData?>, p1: Throwable) {
                        Toast.makeText(this@Home,"Success",Toast.LENGTH_LONG).show()

                    }
                })
            }
        }
    }



}