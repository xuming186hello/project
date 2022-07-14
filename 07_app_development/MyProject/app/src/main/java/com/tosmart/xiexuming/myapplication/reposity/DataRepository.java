package com.tosmart.xiexuming.myapplication.reposity;

import android.content.Context;

import com.tosmart.xiexuming.libtsparser.TransportStreamManager;
import com.tosmart.xiexuming.myapplication.callback.FavoriteCallback;
import com.tosmart.xiexuming.myapplication.callback.FuzzyQueryCallback;
import com.tosmart.xiexuming.myapplication.callback.ProgramListCallback;
import com.tosmart.xiexuming.myapplication.callback.SearchCallback;
import com.tosmart.xiexuming.myapplication.db.DataBaseManager;
import com.tosmart.xiexuming.myapplication.model.Program;
import com.tosmart.xiexuming.myapplication.thread.ApplicationThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author xiexuming
 */
public class DataRepository {
    private static volatile DataRepository instance;

    private ArrayList<String> mSearchHistory;
    private ArrayList<Program> mProgramList;
    private ArrayList<Integer> mFavoriteList;

    private final DataBaseManager mDatabaseManager;
    private final TransportStreamManager mTsManager;
    private final ThreadPoolExecutor mThreadPool;

    private String mFileName;

    public static DataRepository getInstance(Context context) {
        if (instance == null) {
            synchronized (DataRepository.class) {
                instance = new DataRepository(context);
            }
        }
        return instance;
    }

    private DataRepository(Context context) {
        mDatabaseManager = new DataBaseManager(context);
        mTsManager = new TransportStreamManager();
        mThreadPool = ApplicationThreadPool.getInstance().getThreadPool();
    }

    public void getSearchHistory(SearchCallback searchCallBack) {
        Runnable runnable = () -> {
            if (mSearchHistory != null && mSearchHistory.size() > 0) {
                searchCallBack.displayHistory(mSearchHistory);
                return;
            }
            mSearchHistory = mDatabaseManager.getHistoryData();
            searchCallBack.displayHistory(mSearchHistory);
        };
        mThreadPool.execute(runnable);
    }

    public void setSearchHistory(String history) {
        Runnable runnable = () -> {
            if (history == null || history.isEmpty() || mSearchHistory.contains(history)) {
                return;
            }
            mSearchHistory.add(history);
            if (mDatabaseManager != null) {
                mDatabaseManager.setHistoryData(history);
            }
        };
        mThreadPool.execute(runnable);
    }

    public void deleteHistory(String history, SearchCallback callback) {
        Runnable runnable = () -> {
            if (mSearchHistory.contains(history)) {
                mSearchHistory.remove(history);
                callback.displayHistory(mSearchHistory);
                mDatabaseManager.deleteHistory(history);
            }
        };
        mThreadPool.execute(runnable);
    }

    public void deleteHistory(SearchCallback callback) {
        Runnable runnable = () -> {
            mSearchHistory.clear();
            callback.displayHistory(mSearchHistory);
            mDatabaseManager.deleteHistory();
        };
        mThreadPool.execute(runnable);
    }

    public void getFuzzyQueryData(FuzzyQueryCallback callback) {
        Runnable runnable = () -> {
            if (mProgramList != null && mProgramList.size() > 0) {
                ArrayList<String> fuzzyQueryList = new ArrayList<>();
                for (Program program : mProgramList) {
                    if (program.getProgramNumber() != 0) {
                        String fuzzyData = program.getProgramNumber()
                                + " " + program.getProgramName();
                        fuzzyQueryList.add(fuzzyData);
                    }
                }
                callback.getFuzzyQueryData(fuzzyQueryList);
            }
        };
        mThreadPool.execute(runnable);
    }

    public ArrayList<Program> getProgramList() {
        return mProgramList;
    }

    public void requestProgramList(String path, ProgramListCallback callBack) {
        Runnable runnable = () -> {
            {
                if (mProgramList != null) {
                    if (mFileName != null && mFileName.equals(path)) {
                        mFavoriteList = mDatabaseManager.getFavoriteData(path);
                        composeProgramFavorite();

                        callBack.displayProgram(mProgramList);
                        return;
                    }
                }

                mProgramList = mDatabaseManager.getProgramDescriptor(path);

                if (mProgramList != null) {
                    mFileName = path;
                    mFavoriteList = mDatabaseManager.getFavoriteData(path);
                    composeProgramFavorite();
                    callBack.displayProgram(mProgramList);
                    return;
                }
                List<com.tosmart.xiexuming.libtsparser.program.Program> programs
                        = mTsManager.parseTsStream(path);
                if (programs == null) {
                    return;
                }
                mProgramList = new ArrayList<>();
                Program networkProgram = new Program();
                for (com.tosmart.xiexuming.libtsparser.program.Program program : programs) {
                    Program programDescriptor = new Program();
                    programDescriptor.setProgramName(program.getProgramName());
                    programDescriptor.setProgramNumber(program.getProgramNumber());
                    programDescriptor.setChannelDescriptor("五");
                    programDescriptor.setChannelStart("");
                    programDescriptor.setFileName(path);
                    if (program.getProgramNumber() == 0) {
                        networkProgram = programDescriptor;
                    }
                    mProgramList.add(programDescriptor);
                }
                mDatabaseManager.setProgramDescriptor(mProgramList);
                mFileName = path;
                mProgramList.remove(networkProgram);
                mFavoriteList = mDatabaseManager.getFavoriteData(path);
                composeProgramFavorite();
                callBack.displayProgram(mProgramList);
            }
        };
        mThreadPool.execute(runnable);
    }

    private void composeProgramFavorite() {
        for (Program program : mProgramList) {
            if (mFavoriteList.contains(program.getProgramNumber())) {
                program.setFavorite(true);
            }
        }
    }

    public void setFavorite(Integer programNumber) {
        Runnable runnable = () -> {
            if (programNumber != null && !mFavoriteList.contains(programNumber)) {
                mFavoriteList.add(programNumber);
                if (mDatabaseManager != null) {
                    mDatabaseManager.setFavoriteData(programNumber, mFileName);
                }
            }
        };
        mThreadPool.execute(runnable);
    }

    public ArrayList<Program> modifyFavorite(Integer index) {
        Program program = mProgramList.get(index);
        Runnable runnable = () -> {
            if (program.isFavorite()) {
                mDatabaseManager.setFavoriteData(program.getProgramNumber(), mFileName);
            } else {
                mDatabaseManager.removeFavoriteData(program.getProgramNumber(), mFileName);
            }
        };
        mThreadPool.execute(runnable);
        program.setFavorite(!program.isFavorite());
        return mProgramList;
    }

    public void getFavoriteProgram(FavoriteCallback favoriteCallBack) {
        mThreadPool.execute(() -> {
            if (mProgramList != null && mProgramList.size() > 0) {
                ArrayList<Program> favoriteProgram = new ArrayList<>();
                for (Program program : mProgramList) {
                    if (!program.isFavorite()) {
                        continue;
                    }
                    favoriteProgram.add(program);
                }
                favoriteCallBack.displayFavoriteList(favoriteProgram);
            }
        });
    }

    public void getFuzzyQueryProgram(String searchCondition,
                                     ProgramListCallback callback) {
        Runnable runnable = () -> {
            if (mProgramList == null || mProgramList.size() <= 0
                    || searchCondition == null || searchCondition.isEmpty()) {
                return;
            }
            ArrayList<Program> programs = new ArrayList<>();
            for (Program program : mProgramList) {
                String programNumber = String.valueOf(program.getProgramNumber());
                if (programNumber.contains(searchCondition)
                        || program.getProgramName().contains(searchCondition)
                        || searchCondition.contains(programNumber)
                        || searchCondition.contains(program.getProgramName())
                ) {
                    programs.add(program);
                }
            }
            callback.displayProgram(programs);
        };
        mThreadPool.execute(runnable);
    }
}
