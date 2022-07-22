package com.example.newpaserapp.db.dao

import androidx.room.*
import com.example.newpaserapp.db.entity.TransportStreamProgramDescriptor

/**
 * @author xxm
 * 2022/7/21
 **/
@Dao
interface ProgramDescriptorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDescriptor(transportStreamProgramDescriptor: TransportStreamProgramDescriptor)

    @Delete
    fun deleteDescriptor(transportStreamProgramDescriptor: TransportStreamProgramDescriptor)

    @Query("select * from programDescriptor where fileName = :fileName")
    fun queryAll(fileName: String): List<TransportStreamProgramDescriptor>

    @Query(
        "select * from programDescriptor where fileName=:fileName and programNumber != 0"
    )
    fun queryALLProgram(fileName: String): List<TransportStreamProgramDescriptor>
}