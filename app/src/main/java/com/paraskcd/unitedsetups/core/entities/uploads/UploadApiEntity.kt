package com.paraskcd.unitedsetups.core.entities.uploads

import com.paraskcd.unitedsetups.domain.model.Upload

data class UploadApiEntity(
    val paths: List<String>,
    val thumbnails: List<String>
)

fun UploadApiEntity.toUploadList(): List<Upload>{
    return paths.mapIndexed { index, path ->
        Upload(
            paths = path,
            thumbnails = thumbnails[index]
        )
    }
}