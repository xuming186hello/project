package com.example.newpaserapp.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.w3c.dom.Text

/**
 * @author xxm
 * 2022/7/21
 **/
@Entity(tableName = "History")
data class SearchHistory(
    @PrimaryKey
    var history: String
)
