package com.example.libtosmartparse.table

import com.example.libtosmartparse.table.Service
import java.util.ArrayList

/**
 * @author xxm
 * 2022/7/17
 **/
class ServiceDescriptionTable {
    private var service: ArrayList<Service>? = null
    private var versionNumber = 0
    override fun toString(): String {
        return "ServiceDescriptionTable{" +
                "versionNumber=" + versionNumber +
                " ,service=" + service +
                '}'
    }

    fun getVersionNumber(): Int {
        return versionNumber
    }

    fun setVersionNumber(versionNumber: Int) {
        this.versionNumber = versionNumber
    }

    fun getService(): ArrayList<Service>? {
        return service
    }

    fun setService(service: ArrayList<Service>?) {
        this.service = service
    }
}
