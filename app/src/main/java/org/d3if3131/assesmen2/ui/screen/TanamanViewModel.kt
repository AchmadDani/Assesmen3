package org.d3if3131.assesmen2.ui.screen

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.d3if3131.assesmen2.model.Tanaman
import org.d3if3131.assesmen2.network.TanamanApi
import java.io.ByteArrayOutputStream


class TanamanViewModel : ViewModel() {

    var data = mutableStateOf(emptyList<Tanaman>())
        private set

    var status = MutableStateFlow(TanamanApi.ApiStatus.LOADING)
        private set

    var errorMessage = mutableStateOf<String?>(null)
        private set

    fun retrieveData(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            status.value = TanamanApi.ApiStatus.LOADING
            try {
                data.value = TanamanApi.service.getTanaman(userId)
                status.value = TanamanApi.ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("TanamanViewModel", "Failure: ${e.message}")
                status.value = TanamanApi.ApiStatus.FAILED
            }
        }
    }

    fun saveData(userId: String, nama: String, deskripsi: String, bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = TanamanApi.service.postTanaman(
                    userId,
                    nama.toRequestBody("text/plain".toMediaTypeOrNull()),
                    deskripsi.toRequestBody("text/plain".toMediaTypeOrNull()),
                    bitmap.toMultipartBody(),
//                    tanggal.toRequestBody("text/plain".toMediaTypeOrNull())
                )

                if (result.status == "success")
                    retrieveData(userId)
                else
                    throw Exception(result.message)
            } catch (e: Exception) {
                Log.d("TanamanViewModel", "Failure: ${e.message}")
                errorMessage.value = "Error: ${e.message}"
            }
        }
    }

    fun deleteData(userId: String, minumanId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("TanamanViewModel", "Attempting to delete voting with ID: $minumanId using user ID: $userId")
                val result = TanamanApi.service.deleteTanaman(userId, minumanId)
                Log.d("TanamanViewModel", "API Response: status=${result.status}, message=${result.message}")
                if (result.status == "success") {
                    retrieveData(userId)
                } else {
                    throw Exception(result.message)
                }
            } catch (e: Exception) {
                Log.d("TanamanViewModel", "Failure: ${e.message}")
                errorMessage.value = "Error: ${e.message}"
            }
        }
    }

    private fun Bitmap.toMultipartBody(): MultipartBody.Part {
        val stream = ByteArrayOutputStream()
        compress(Bitmap.CompressFormat.JPEG, 80, stream)
        val byteArray = stream.toByteArray()
        val requestBody = byteArray.toRequestBody(
            "image/jpg".toMediaTypeOrNull(), 0, byteArray.size)
        return MultipartBody.Part.createFormData(
            "image", "image.jpg", requestBody)
    }

    fun clearMessage() { errorMessage.value = null }
}
