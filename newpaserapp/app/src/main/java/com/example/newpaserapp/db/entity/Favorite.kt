package com.example.newpaserapp.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.w3c.dom.Text

/**
 * @author xxm
 * 2022/7/21
 **/
@Entity(primaryKeys = ["programNumber","fileName"], tableName = "Favorite")
data class Favorite(
    var programNumber: Int,
    var fileName: String
)
