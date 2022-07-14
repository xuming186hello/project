package com.tosmart.xiexuming.myapplication.file;

import com.tosmart.xiexuming.myapplication.callback.FileListCallback;
import com.tosmart.xiexuming.myapplication.thread.ApplicationThreadPool;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author xiexuming
 */
public class FileUtil {
    private static final String TS_FILE_SUFFIX = "ts";

    private static volatile FileUtil instance;
    private HashMap<String, String> mFileMap;
    private ArrayList<String> mFileList;

    private FileUtil() {
    }

    public static FileUtil getInstance() {
        if (instance == null) {
            synchronized (FileUtil.class) {
                instance = new FileUtil();
            }
        }
        return instance;
    }

    public String getFilePath(String fileName) {
        if (mFileMap.containsKey(fileName)) {
            return mFileMap.get(fileName);
        }
        return null;
    }

    public void requestFileList(File file, FileListCallback callback) {
        Runnable runnable = () -> {
            mFileMap = new HashMap<>(0);
            mFileList = new ArrayList<>();
            updateFileList(file);
            callback.displayFileList(mFileList);
        };
        ThreadPoolExecutor threadPool = ApplicationThreadPool.getInstance().getThreadPool();
        threadPool.execute(runnable);
    }

    private void updateFileList(File rootDir) {
        if (!rootDir.isDirectory()) {
            String path = rootDir.getPath();
            String substring = path.substring(path.lastIndexOf(".") + 1);
            if (substring.equals(TS_FILE_SUFFIX)) {
                String fileName = path.substring(path.lastIndexOf("/") + 1);
                mFileList.add(fileName);
                mFileMap.put(fileName, path);
            }
            return;
        }
        File[] files = rootDir.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                updateFileList(file);
            } else {
                String path = file.getPath();
                String substring = path.substring(path.lastIndexOf(".") + 1);
                if (substring.equals(TS_FILE_SUFFIX)) {
                    String fileName = path.substring(path.lastIndexOf("/") + 1);
                    mFileMap.put(fileName, path);
                    mFileList.add(fileName);
                }
            }
        }
    }

}
