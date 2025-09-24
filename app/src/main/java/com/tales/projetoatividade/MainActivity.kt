package com.tales.projetoatividade

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.tales.projetoatividade.database.AppDatabase
import com.tales.projetoatividade.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db = AppDatabase.getDatabase(applicationContext)

        val novoUsuario = User(name = "Rute", email ="rute@gmail.com")
        val userDao = db.userDao()

        CoroutineScope(Dispatchers.IO).launch {
            userDao.insert(novoUsuario)
            val users = userDao.getAllUsers()
            for(user in users){
                Log.d("User", "${user.id}: ${user.name} - ${user.email}")
            }
        }
    }
}