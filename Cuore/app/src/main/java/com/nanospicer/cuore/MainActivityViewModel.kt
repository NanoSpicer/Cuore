package com.nanospicer.cuore

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

class MainActivityViewModel : ViewModel() {


    val reasons: LiveData<List<Reason>>
        get() = _reasons

    private val _reasons by lazy {
        liveData {
            val reasons = getReasons()
            emit(reasons)
        } as MutableLiveData
    }


    suspend fun refresh()  {
        _reasons.value = getReasons()
    }


    private suspend fun getReasons(): List<Reason> = withContext(Dispatchers.IO) {
        val url = URL("https://nanospicer.000webhost.com/maria/happy-birthday/reasons.php")
        val urlConnection = url.openConnection() as HttpURLConnection
        val sb = StringBuilder()
        try {
            val reader = urlConnection.inputStream.bufferedReader()
            do {
                val line = reader.readLine()
                if (line != null)
                    sb.append(line)
            } while (line != null)
        }catch (ex: Exception){
            Log.e("VM", "An error occurred", ex)
            sb.append("[]")
            sb.clear()
        } finally {
            urlConnection.disconnect()
        }

        val jsonList = sb.toString()

        Log.d("VM", jsonList)

        val array = JSONArray(jsonList)



        mutableListOf<Reason>().apply {
            for (i in 0 until array.length()) {
                val item = array.getJSONObject(i)
                add(
                    reason {
                        title = item.getString("title")
                        description = item.getString("description")
                        mipmapId = i.toMipmap()
                    }
                )
            }
        }
    }

}