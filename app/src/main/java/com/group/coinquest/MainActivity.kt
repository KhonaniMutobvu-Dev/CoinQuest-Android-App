package com.group.coinquest

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {


    private lateinit var database: AppDatabase

    private var registerMode = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)



        val usernameInput =
            findViewById<EditText>(R.id.etEmailUsername)


        val passwordInput =
            findViewById<EditText>(R.id.etPassword)


        val loginButton =
            findViewById<Button>(R.id.btnLogin)


        val errorText =
            findViewById<TextView>(R.id.tvErrorMessage)


        val registerToggle =
            findViewById<TextView>(R.id.tvRegisterToggle)



        database =
            Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "coinquest_db"
            )
                .build()




        loginButton.setOnClickListener {


            val username =
                usernameInput.text.toString().trim()


            val password =
                passwordInput.text.toString().trim()



            if(username.isEmpty() || password.isEmpty()){

                errorText.text =
                    "Fill in all fields"

                errorText.visibility =
                    View.VISIBLE

                return@setOnClickListener
            }





            lifecycleScope.launch {



                if(registerMode){



                    val existingUser =
                        database.userDao()
                            .checkUser(username)



                    if(existingUser != null){


                        runOnUiThread {

                            errorText.text =
                                "Username already exists"

                            errorText.visibility =
                                View.VISIBLE
                        }

                        return@launch
                    }



                    val newUser =
                        User(
                            username = username,
                            email = "",
                            password = password
                        )


                    database.userDao()
                        .registerUser(newUser)



                    runOnUiThread {


                        registerMode = false


                        loginButton.text =
                            "Log In"


                        registerToggle.text =
                            "Don't have an account? Register"


                        errorText.text =
                            "Account created. Login now"


                        errorText.visibility =
                            View.VISIBLE

                    }



                }
                else{



                    val user =
                        database.userDao()
                            .login(
                                username,
                                password
                            )



                    runOnUiThread {


                        if(user != null){


                            errorText.visibility =
                                View.GONE



                            // SAVE USER SESSION
                            getSharedPreferences(
                                "session",
                                MODE_PRIVATE
                            )
                                .edit()
                                .putInt(
                                    "USER_ID",
                                    user.id
                                )
                                .apply()



                            startActivity(
                                Intent(
                                    this@MainActivity,
                                    DashboardActivity::class.java
                                )
                            )


                            finish()

                        }
                        else{


                            errorText.text =
                                "Wrong username or password"


                            errorText.visibility =
                                View.VISIBLE

                        }

                    }


                }


            }



        }




        registerToggle.setOnClickListener {



            registerMode =
                !registerMode



            if(registerMode){


                loginButton.text =
                    "Register"


                registerToggle.text =
                    "Already have an account? Login"


                errorText.visibility =
                    View.GONE


            }
            else{


                loginButton.text =
                    "Log In"


                registerToggle.text =
                    "Don't have an account? Register"


                errorText.visibility =
                    View.GONE

            }


        }



    }


}