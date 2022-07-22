package com.example.newpaserapp.db.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import org.w3c.dom.Text

/**
 * @author xxm
 * 2022/7/21
 **/
@Entity(tableName = "programDescriptor")
data class TransportStreamProgramDescriptor(
    var programNumber: Int,
    var programName: String,
    var fileName: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

}
