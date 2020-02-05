package com.example.enterpriseclient.requestServer

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.StringRequest
import com.example.enterpriseclient.myDataBase.database.ReservationDatabase
import com.example.enterpriseclient.myDataBase.model.User
import com.example.enterpriseclient.myDataBase.viewModel.UsersViewModel
import org.json.JSONObject

class RequestUser {
    companion object {

        private var db: ReservationDatabase? = null
        const val URL = "http://192.168.103.210:8000"


        //------------------- User, login and register -----------------------


        @JvmStatic fun login(context: Context,email_text:EditText,password_text:EditText,usersViewModel: UsersViewModel) {

            val loginJsonobj = JSONObject()

            loginJsonobj.put("email", email_text.text)
            loginJsonobj.put("password", password_text.text)

            // new Volley newRequestQueue
            val queue = Volley.newRequestQueue(context)
            val url = URL +"/api/auth/login"
            val req = object : JsonObjectRequest(Request.Method.POST, url, loginJsonobj,
                Response.Listener {
                    updateToken(context,email_text,usersViewModel,it.getString("token"))
                    Toast.makeText(context, "Identificacion correcta ", Toast.LENGTH_SHORT).show()
                },
                Response.ErrorListener {
                    Toast.makeText(context, "Identificacion erronea ", Toast.LENGTH_SHORT).show()
                }
            ){}
            queue.add(req)
        }


        @JvmStatic fun updateToken(context: Context,email_text:EditText,usersViewModel: UsersViewModel,token:String) {

            val jsonObject = JSONObject()

            jsonObject.put("email",email_text.text.toString())

            // new Volley newRequestQueue
            val queue = Volley.newRequestQueue(context)
            val url = URL +"/api/auth/user"
            val req = object : JsonObjectRequest(Request.Method.POST, url, jsonObject,
                Response.Listener {
                    usersViewModel.updateUser(User(1, it.getString("id"), token))

//                    val intent = Intent(context,SearchActivity::class.java)
//                    context.startActivity(intent)

                },
                Response.ErrorListener {
                    Log.println(Log.INFO,null,"ERROR "+it.message)
                }
            ){}
            queue.add(req)
        }


        @JvmStatic fun registerUser(context: Context,name_editText:EditText,lastname_editText:EditText,dni_editText:EditText,telephone: EditText,email_editText:EditText,password_editText:EditText) {

            val loginJsonobj = JSONObject()

            loginJsonobj.put("name", name_editText.text)
            loginJsonobj.put("lastname", lastname_editText.text)
            loginJsonobj.put("dni", dni_editText.text)
            loginJsonobj.put("phone", telephone.text)
            loginJsonobj.put("email", email_editText.text)
            loginJsonobj.put("password", password_editText.text)

            val queue = Volley.newRequestQueue(context)
            val url = URL +"/api/auth/register"
            val req = object : JsonObjectRequest(
                Request.Method.POST, url, loginJsonobj,
                Response.Listener {

                    Toast.makeText(context, "Registro realizado ! ", Toast.LENGTH_LONG).show()

//                    val intent = Intent(context, LoginActivity::class.java)
//                    context.startActivity(intent)
                },
                Response.ErrorListener {
                    Log.println(Log.INFO,null,"ERROR "+it.networkResponse)
                })
            {}

            queue.add(req)
        }

        @JvmStatic fun selectUser(context: Context, usersViewModel: UsersViewModel, name_editText: TextView, lastname_editText:TextView, dni_editText:TextView, telephone:TextView, email_editText:TextView) {


            val queue = Volley.newRequestQueue(context)
            val url = URL +"/api/user/"+usersViewModel.getUserId(1)
            val req = object : JsonObjectRequest(
                Request.Method.GET, url, null,
                Response.Listener {

                    name_editText.setText(it.getString("name"))
                    lastname_editText.setText(it.getString("lastname"))
                    dni_editText.setText(it.getString("dni"))
                    telephone.setText(it.getString("phone"))
                    email_editText.setText(it.getString("email"))

                },
                Response.ErrorListener {
                    Log.println(Log.INFO,null,"ERROR "+it.message)
                })
            {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers: MutableMap<String, String> =
                        HashMap()
                    // Basic Authentication
                    var token = usersViewModel.getToken(1)
                    headers["Authorization"] = "Bearer "+token
                    return headers
                }
            }

            queue.add(req)
        }

        @JvmStatic fun updateUser(context: Context,usersViewModel: UsersViewModel,name_editText:TextView,lastname_editText:TextView,dni_editText:TextView,telephone:TextView,email_editText:TextView) {

            val updateJsonobj = JSONObject()

            updateJsonobj.put("name", name_editText.text)
            updateJsonobj.put("lastname", lastname_editText.text)
            updateJsonobj.put("dni", dni_editText.text)
            updateJsonobj.put("phone", telephone.text)
            updateJsonobj.put("email", email_editText.text)

            val queue = Volley.newRequestQueue(context)
            val url = URL +"/api/user/"+usersViewModel.getUserId(1)
            val req = object : JsonObjectRequest(
                Request.Method.PUT, url, updateJsonobj,
                Response.Listener {
                    Toast.makeText(context, "actualización realizada con exito", Toast.LENGTH_LONG).show()

//                    val intent=Intent(context,LoggedInActivity::class.java)
//                    context.startActivity(intent)

                },
                Response.ErrorListener {
                    Log.println(Log.INFO,null,"ERROR "+it.message)
                })
            {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers: MutableMap<String, String> =
                        HashMap()
                    // Basic Authentication
                    var token = usersViewModel.getToken(1)
                    headers["Authorization"] = "Bearer "+token
                    return headers
                }
            }

            queue.add(req)
        }

        @JvmStatic fun deleteUser(context: Context,usersViewModel: UsersViewModel) {

            val queue = Volley.newRequestQueue(context)
            val url = URL +"/api/user/"+usersViewModel.getUserId(1)
            val req = object : StringRequest(
                Request.Method.DELETE, url,
                Response.Listener {
                    Toast.makeText(context, it, Toast.LENGTH_LONG).show()

//                    val intent=Intent(context,LoginActivity::class.java)
//                    context.startActivity(intent)

                },
                Response.ErrorListener {
                    Log.println(Log.INFO,null,"ERROR "+it.toString())
                })
            {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers: MutableMap<String, String> =
                        HashMap()
                    // Basic Authentication
                    var token = usersViewModel.getToken(1)
                    Log.println(Log.INFO,null,"ERROR "+token)
                    headers["Authorization"] = "Bearer "+token
                    return headers
                }
            }

            queue.add(req)
        }

        @JvmStatic fun logout(context: Context,usersViewModel: UsersViewModel){
            usersViewModel.updateToken(User(1,"",""))
//            val intent = Intent(context,LoginActivity::class.java)
//            context.startActivity(intent)
        }

    }
}