package com.tosmart.xiexuming.myapplication.callback;

import com.tosmart.xiexuming.myapplication.model.Program;

import java.util.ArrayList;

/**
 * @author xiexuming
 */
public interface ProgramListCallback {

    /**
     * 准备跳转时所需要数据
     * @param programs 节目
     */
    void displayProgram(ArrayList<Program> programs);
}
