package com.tosmart.xiexuming.libtsparser.table;

import androidx.annotation.NonNull;

import com.tosmart.xiexuming.libtsparser.descriptor.Descriptor;

import java.util.ArrayList;

/**
 * @author xiexuming
 */
public class ProgramMapTable {
    private ArrayList<Component> components;
    private int pid;
    private ArrayList<Descriptor> programInfo;

    @NonNull
    @Override
    public String toString() {
        return "ProgramMapTable{" +
                "pid=0x" + Integer.toHexString(pid) +
                ", programInfo=" + programInfo +
                "， components=" + components +
                '}' + "\n";
    }

    public ArrayList<Component> getComponents() {
        return components;
    }

    public void setComponents(ArrayList<Component> components) {
        this.components = components;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public ArrayList<Descriptor> getProgramInfo() {
        return programInfo;
    }

    public void setProgramInfo(ArrayList<Descriptor> programInfo) {
        this.programInfo = programInfo;
    }
}
