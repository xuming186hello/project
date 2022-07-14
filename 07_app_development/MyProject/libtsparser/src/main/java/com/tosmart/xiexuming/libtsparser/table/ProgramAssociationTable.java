package com.tosmart.xiexuming.libtsparser.table;

import androidx.annotation.NonNull;

import com.tosmart.xiexuming.libtsparser.program.Program;

import java.util.ArrayList;

/**
 * @author xiexuming
 */
public class ProgramAssociationTable {
    private ArrayList<Program> programs;
    private int version;

    @NonNull
    @Override
    public String toString() {
        return "ProgramAssociationTable{" +
                " version=" + version +
                ", programs=" + programs +
                '}';
    }

    public ArrayList<Program> getPrograms() {
        return programs;
    }

    public void setPrograms(ArrayList<Program> programs) {
        this.programs = programs;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
