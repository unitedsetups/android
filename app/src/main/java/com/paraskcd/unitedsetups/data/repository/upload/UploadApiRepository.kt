package com.paraskcd.unitedsetups.data.repository.upload

import android.content.Context
import android.net.Uri
import android.util.Log
import com.paraskcd.unitedsetups.core.common.DataOrException
import com.paraskcd.unitedsetups.core.entities.uploads.toUploadList
import com.paraskcd.unitedsetups.core.interfaces.apis.IUploadApi
import com.paraskcd.unitedsetups.core.interfaces.repository.IUploadApiRepository
import com.paraskcd.unitedsetups.domain.model.Upload
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.apache.commons.io.FileUtils
import java.io.File
import java.lang.Exception
import javax.inject.Inject

class UploadApiRepository @Inject constructor(
    private val uploadApi: IUploadApi,
    @ApplicationContext private val context: Context
) : IUploadApiRepository {
    override suspend fun uploadPostMedia(files: List<Uri>): DataOrException<List<Upload>, Exception> {
        return try {
            DataOrException(uploadApi.uploadPostMedia(createMultipartBody(files)).toUploadList(), null)
        } catch (ex: Exception) {
            Log.e("uploadPostMedia", ex.message.toString())
            DataOrException(null, ex)
        }
    }

    private fun createMultipartBody(files: List<Uri>): List<MultipartBody.Part> {
        val fileList = files.map { createFileFromUri(it) }
        return fileList.map { file ->
            MultipartBody.Part.createFormData(
                "files",
                file!!.name,
                file.asRequestBody("image/png".toMediaTypeOrNull())
            )
        }
    }

    private fun createFileFromUri(uri: Uri): File? {
        return try {
            val stream = context.contentResolver.openInputStream(uri)
            val file =
                File.createTempFile(
                    "$${System.currentTimeMillis()}",
                    ".png",
                    context.cacheDir
                )
            FileUtils.copyInputStreamToFile(stream, file)
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}