package com.tosmart.xiexuming.myapplication.model;

import androidx.annotation.NonNull;

/**
 * @author xiexuming
 */
public class Program {
    private Integer id;
    private String programName;
    private Integer programNumber;
    private String channelStart;
    private String channelDescriptor;
    private String fileName;
    private boolean favorite;

    @NonNull
    @Override
    public String toString() {
        return "Program{" +
                "id=" + id +
                ", programName='" + programName + '\'' +
                ", programNumber=" + programNumber +
                ", channelStart='" + channelStart + '\'' +
                ", channelDescriptor='" + channelDescriptor + '\'' +
                ", fileName='" + fileName + '\'' +
                ", favorite=" + favorite + "\n" +
                '}';
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public Integer getProgramNumber() {
        return programNumber;
    }

    public void setProgramNumber(Integer programNumber) {
        this.programNumber = programNumber;
    }

    public String getChannelStart() {
        return channelStart;
    }

    public void setChannelStart(String channelStart) {
        this.channelStart = channelStart;
    }

    public String getChannelDescriptor() {
        return channelDescriptor;
    }

    public void setChannelDescriptor(String channelDescriptor) {
        this.channelDescriptor = channelDescriptor;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
