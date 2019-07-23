package com.example.currencyconverter

import android.os.AsyncTask
import org.json.JSONObject
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class Download(val currencyString : String)
                : AsyncTask<String,Void,String>(){

      /*  companion object Rate{
            var rate : Double ?= null

            fun getRate(): Double{
                return rate!!
            }
        }*/

    override fun doInBackground(vararg params: String?): String {
        var url : URL
        var result = ""
        var httpURLConnection : HttpURLConnection

        try{
            url = URL(params[0])
            httpURLConnection = url.openConnection() as HttpURLConnection

            val inputStream = httpURLConnection.inputStream

            val inputStreamReader = InputStreamReader(inputStream)

            var data = inputStreamReader.read()

            while(data > 0){
                var character = data.toChar()
                result += character
                data = inputStreamReader.read()
            }

            return result

        }catch (e: Exception){
            return result
        }
    }

    override fun onPostExecute(result: String?) {

        try {

            val jsonObject = JSONObject(result)
            rate = jsonObject.getString(currencyString).toDouble()


        }catch (e : Exception){

        }


        super.onPostExecute(result)
    }
}