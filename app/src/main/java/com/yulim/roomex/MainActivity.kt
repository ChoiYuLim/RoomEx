package com.yulim.roomex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yulim.roomex.database.UserDatabase
import com.yulim.roomex.database.UserDto
import com.yulim.roomex.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = UserDatabase.getInstance(applicationContext)!!
        refreshUserList()

        binding.btnSave.setOnClickListener {
            addUser()
            refreshUserList()
        }
    }

    private fun addUser() {
        var name = binding.etName.text.toString()
        var age = binding.etAge.text.toString()
        var phone = binding.etPhone.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            db.userDao().insert(UserDto(name, age, phone))
        }
    }

    private fun refreshUserList() {
        var userList = "유저 리스트\n"

        CoroutineScope(Dispatchers.Main).launch {
            val users = CoroutineScope(Dispatchers.IO).async {
                db.userDao().getAll()
            }.await()

            for (user in users) {
                userList += "이름: ${user.name}, 나이: ${user.age}, 번호: ${user.phone}\n"
            }
            binding.tvPerson.text = userList
        }
    }
}