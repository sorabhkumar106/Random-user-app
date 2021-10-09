package com.example.randomuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(){
lateinit var imageButton: ImageButton
lateinit var progressBar: ProgressBar
lateinit var imageView: ImageView

//personal details
lateinit var username: TextView
lateinit var name: TextView
lateinit var email: TextView

// address
lateinit var city: TextView
lateinit var state: TextView
lateinit var country: TextView

//others
lateinit var dob: TextView
lateinit var phone: TextView
lateinit var age: TextView
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    imageButton = findViewById(R.id.refresh)
    progressBar = findViewById(R.id.progressBar)
    imageView = findViewById(R.id.imageView)

    username = findViewById(R.id.username)
    name = findViewById(R.id.name)
    email = findViewById(R.id.email)

    city = findViewById(R.id.city)
    state = findViewById(R.id.state)
    country = findViewById(R.id.country)

    dob = findViewById(R.id.dob)
    phone = findViewById(R.id.phone)
    age = findViewById(R.id.age)
    thread {
        getUser()
        imageButton.setOnClickListener(View.OnClickListener {
            getUser()
            Toast.makeText(applicationContext, "Refreshing....", Toast.LENGTH_SHORT).show()
        })
    }
}

fun getUser() {
  progressBar.visibility = View.VISIBLE
    var jsonQ =  Volley.newRequestQueue(this)
    lateinit var url: String
    val jsonObj =
        JsonObjectRequest(
            Request.Method.GET, "https://randomuser.me/api/", null,
            { response ->
                Log.d("sorabh", "success")
                 url = response.getJSONArray("results").getJSONObject(0).getJSONObject("picture").getString("thumbnail")
                Log.d("sorabh", url)
                Glide.with(this).load(url).into(imageView)

                var first_name = response.getJSONArray("results").getJSONObject(0).getJSONObject("name").getString("first")
                var last_name = response.getJSONArray("results").getJSONObject(0).getJSONObject("name").getString("last")
                name.text ="Name : "+ first_name+" "+last_name

                username.text = "uasername@ " +response.getJSONArray("results").getJSONObject(0).getJSONObject("login").getString("username")

                email.text ="Email : "+ response.getJSONArray("results").getJSONObject(0).getString("email")


                city.text ="City : "+ response.getJSONArray("results").getJSONObject(0).getJSONObject("location").getString("city")
                state.text ="State : "+ response.getJSONArray("results").getJSONObject(0).getJSONObject("location").getString("state")
                country.text ="Country : "+ response.getJSONArray("results").getJSONObject(0).getJSONObject("location").getString("country")

                dob.text ="DOB : "+ response.getJSONArray("results").getJSONObject(0).getJSONObject("dob").getString("date")
                age.text ="Age : " +response.getJSONArray("results").getJSONObject(0).getJSONObject("dob").getString("age")
                phone.text ="Phone : "+ response.getJSONArray("results").getJSONObject(0).getString("phone")

               progressBar.visibility = View.GONE

            },
            { error ->
                Log.d("sorabh", error.toString())

            }
        );
    jsonQ.add(jsonObj)
}
}


