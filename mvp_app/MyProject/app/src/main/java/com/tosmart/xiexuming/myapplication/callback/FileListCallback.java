package com.tosmart.xiexuming.myapplication.callback;

import java.util.ArrayList;

/**
 * @author xiexuming
 */
public interface FileListCallback {

    /**
     * 获取文件列表成功
     * @param fileList 文件列表
     */
    void displayFileList(ArrayList<String> fileList);
}
