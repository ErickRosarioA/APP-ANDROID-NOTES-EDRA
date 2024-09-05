package com.devedra.androidnotesedra.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

//Esta class con expression de Parcelize está deprecate por lo que fue coloda la annotation Deprecated para quitar el warning del project.
@Suppress("DEPRECATED_ANNOTATION")

@Entity(tableName = "note_table")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    var noteId: Long,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "content")
    var content: String,

    @ColumnInfo(name = "updatedAt")
    var updatedAt: Long,
) : Parcelable {
    constructor() : this(0L, "", "", -1L)
}