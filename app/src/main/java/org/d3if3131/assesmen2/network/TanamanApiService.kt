package org.d3if3131.assesmen2.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.d3if3131.assesmen2.model.OpStatus
import org.d3if3131.assesmen2.model.Tanaman
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query


private const val BASE_URL = "https://achmaddani.excione.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface TanamanApiService {
    @GET("tanaman.php")
    suspend fun getTanaman(
        @Query("auth") userId: String
    ): List<Tanaman>

    @Multipart
    @POST("tanaman.php")
    suspend fun postTanaman(
        @Part("auth") userId: String,
        @Part("nama") nama: RequestBody,
        @Part("deskripsi") deskripsi: RequestBody,
        @Part image: MultipartBody.Part,
//        @Part("tanggal") tanggal: RequestBody,
    ): OpStatus

    @DELETE("tanaman.php")
    suspend fun deleteTanaman(
        @Query("auth") userId: String,
        @Query("id") id: String
    ): OpStatus
}

object TanamanApi {
    val service: TanamanApiService by lazy {
        retrofit.create(TanamanApiService::class.java)
    }
    fun getTanamanUrl(gambar: String): String {
        return "$BASE_URL$gambar"
    }
    enum class ApiStatus { LOADING, SUCCESS, FAILED }
}