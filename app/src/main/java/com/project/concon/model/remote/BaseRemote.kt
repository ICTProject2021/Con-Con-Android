package com.project.concon.model.remote

import android.util.Log
import com.project.concon.model.remote.dto.Res
import retrofit2.Response
import io.reactivex.rxjava3.functions.Function
import org.json.JSONObject

abstract class BaseRemote<SV> {
    abstract val service: SV

    protected fun <T> getResponse(): Function<Response<Res<T>>, T> {
        return Function {
            checkError(it)
            it.body()!!.data
        }
    }

    protected fun <T> getMessage(): Function<Response<Res<T>>, String> {
        return Function {
            checkError(it)
            it.body()?.message?:""
        }
    }

    private fun <T> checkError(response: Response<T>) {
        Log.d("server", response.raw().toString())

        if (!response.isSuccessful) {
            val errorBody = JSONObject(response.errorBody()!!.string())
            throw Throwable(errorBody.getString("message"))
        }
    }
}