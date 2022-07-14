package com.tosmart.xiexuming.myapplication.start;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.tosmart.xiexuming.myapplication.mainmenu.MainMenuActivity;
import com.tosmart.xiexuming.myapplication.R;
import com.tosmart.xiexuming.myapplication.file.FileUtil;
import com.tosmart.xiexuming.myapplication.reposity.DataRepository;

import java.io.File;

/**
 * @author xiexuming
 */
public class StartActivity extends AppCompatActivity {
    private ListView mFileListView;
    private PopupWindow mPopupWindow;

    private Typeface mTypeface;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        initView();
        initData();
        initEven();
    }

    private void initView() {
        mFileListView = findViewById(R.id.listView_select_file);
        View startTopBarRl = findViewById(R.id.top_bar_start);
        TextView titleTextview = startTopBarRl.findViewById(R.id.textview_top_bar_title);
        ImageView backImageView = startTopBarRl.findViewById(R.id.image_top_bar_back);
        mTypeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");
        titleTextview.setTypeface(mTypeface);

        backImageView.setVisibility(View.INVISIBLE);
        titleTextview.setText(R.string.start_title);
    }

    private void initData() {
        TsFileListAdapter tsFileListAdapter = new TsFileListAdapter(this);
        mFileListView.setAdapter(tsFileListAdapter);
        File file = getExternalCacheDir();
        FileUtil.getInstance().requestFileList(file, tsFileListAdapter::updateFileList);
    }

    private void initEven() {
        mFileListView.setOnItemClickListener((adapterView, view, i, l) -> {
            showLoading();
            TextView fileNameView = view.findViewById(R.id.textView_file_name);

            String filePath = FileUtil.getInstance().getFilePath(fileNameView.getText().toString());
            DataRepository
                    .getInstance(this).
                    requestProgramList(filePath, programDescriptors ->
                    {
                        runOnUiThread(()->{
                            mPopupWindow.dismiss();
                            Intent intent =
                                    new Intent(StartActivity.this, MainMenuActivity.class);
                            startActivity(intent);
                        });
                    });
        });
    }

    private void showLoading() {
        mPopupWindow = new PopupWindow();
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setFocusable(true);
        @SuppressLint("InflateParams")
        View view = LayoutInflater
                .from(this).inflate(R.layout.start_load_popuwindow, null);
        TextView tip = view.findViewById(R.id.textview_start_popu_window_tip);
        tip.setTypeface(mTypeface);
        mPopupWindow.setContentView(view);
        mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }
}