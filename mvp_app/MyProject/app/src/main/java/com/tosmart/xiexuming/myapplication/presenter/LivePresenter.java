package com.tosmart.xiexuming.myapplication.presenter;

import android.content.Context;

import com.tosmart.xiexuming.myapplication.live.LiveFragment;
import com.tosmart.xiexuming.myapplication.model.Program;
import com.tosmart.xiexuming.myapplication.reposity.DataRepository;

import java.util.ArrayList;

/**
 * @author xxm
 * 2022.7.14
 **/
public class LivePresenter {
    private Context mContext;
    private LiveFragment.Updatable mCallback;

    public LivePresenter(Context mContext, LiveFragment.Updatable callback) {
        this.mContext = mContext;
        mCallback = callback;
    }

    public void updateProgram(String searchCondition) {
        if (searchCondition != null && !searchCondition.isEmpty()) {
            mCallback.updateSearchText(searchCondition);

            DataRepository.getInstance(mContext)
                    .getFuzzyQueryProgram(searchCondition,
                            programDescriptors
                                    -> mCallback.updateProgramList(programDescriptors));

        } else {
            ArrayList<Program> programList =
                    DataRepository.getInstance(mContext).getProgramList();
            if (programList != null) {
                mCallback.updateProgramList(programList);
            }
        }
    }
}
