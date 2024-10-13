package com.paraskcd.unitedsetups.core.interfaces.repository

import android.net.Uri
import com.paraskcd.unitedsetups.core.common.DataOrException
import com.paraskcd.unitedsetups.domain.model.Upload
import okhttp3.MultipartBody

interface IUploadApiRepository {
    suspend fun uploadPostMedia(files: List<Uri>): DataOrException<List<Upload>, Exception>
}