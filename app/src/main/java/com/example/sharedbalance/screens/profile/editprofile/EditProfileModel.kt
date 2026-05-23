package com.example.sharedbalance.screens.profile.editprofile

import android.content.Context
import android.net.Uri
import com.example.sharedbalance.data.api.ApiClient
import com.example.sharedbalance.screens.account.UserProfileResponse
import com.example.sharedbalance.screens.profile.ProfileRequest
import com.example.sharedbalance.screens.profile.ProfileResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class EditProfileModel(
    private val context: Context
) : EditProfileContract.Model {

    private val api = ApiClient.create(context)

    companion object {
        private const val SUPABASE_URL =
            "https://wwycccbmazhwodlutjyp.supabase.co/storage/v1/object/public/profile-images/"
        private const val SUPABASE_UPLOAD_URL =
            "https://wwycccbmazhwodlutjyp.supabase.co/storage/v1/object/profile-images/"
        // ⚠️ move this to BuildConfig or local.properties in production
        private const val SUPABASE_KEY =
            "your-supabase-anon-key-here"
    }

    override fun getProfile(callback: (EditProfile?) -> Unit) {
        api.getUserProfile()
            .enqueue(object : Callback<UserProfileResponse> {
                override fun onResponse(
                    call: Call<UserProfileResponse>,
                    response: Response<UserProfileResponse>
                ) {
                    val user = response.body()?.payload
                    if (response.isSuccessful && user != null) {
                        callback(
                            EditProfile(
                                firstName = user.firstName,
                                lastName = user.lastName,
                                email = user.email,
                                profileImage = user.profileImage
                            )
                        )
                    } else callback(null)
                }

                override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
                    callback(null)
                }
            })
    }

    override fun updateProfile(
        request: ProfileRequest,
        callback: (Boolean, String) -> Unit
    ) {
        api.updateProfile(request)
            .enqueue(object : Callback<ProfileResponse> {
                override fun onResponse(
                    call: Call<ProfileResponse>,
                    response: Response<ProfileResponse>
                ) {
                    if (response.isSuccessful) {
                        // ✅ persist updated name + photo to cache
                        val prefs = context.getSharedPreferences("sharedbalance", Context.MODE_PRIVATE)
                        prefs.edit()
                            .putString("firstName", request.firstName)
                            .putString("lastName", request.lastName)
                            .putString("name", "${request.firstName} ${request.lastName}")
                            .putString("profileImage", request.profileImage)
                            .apply()

                        callback(true, response.body()?.message ?: "Profile updated")
                    } else {
                        callback(false, "Failed to update profile")
                    }
                }

                override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                    callback(false, t.message ?: "Network error")
                }
            })
    }

    override fun uploadImage(imageUri: Uri, callback: (String?) -> Unit) {
        Thread {
            try {
                val inputStream = context.contentResolver.openInputStream(imageUri)
                val bytes = inputStream?.readBytes()
                inputStream?.close()

                if (bytes == null) {
                    callback(null)
                    return@Thread
                }

                val fileName = "${System.currentTimeMillis()}_profile.jpg"
                val uploadUrl = SUPABASE_UPLOAD_URL + fileName

                val client = OkHttpClient()
                val body = bytes.toRequestBody("image/jpeg".toMediaType())

                val request = Request.Builder()
                    .url(uploadUrl)
                    .post(body)
                    .addHeader("apikey", SUPABASE_KEY)
                    .addHeader("Authorization", "Bearer $SUPABASE_KEY")
                    .addHeader("Content-Type", "image/jpeg")
                    .build()

                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val publicUrl = SUPABASE_URL + fileName
                    callback(publicUrl)
                } else {
                    callback(null)
                }

            } catch (e: IOException) {
                callback(null)
            }
        }.start()
    }
}