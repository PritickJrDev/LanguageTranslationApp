package com.e.bodobhasha;

import android.media.MediaPlayer;

public class Word {
    private String mDefaultTranslation;
    private String mBodoTranslation;
    private int mImageResourceId = NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED = -1;
    private int audioResourceID;

    public Word(String mDefaultTranslation, String mBodoTranslation, int audioResourceID) {
        this.mDefaultTranslation = mDefaultTranslation;
        this.mBodoTranslation = mBodoTranslation;
        this.audioResourceID = audioResourceID;
    }

    public Word(String mDefaultTranslation, String mBodoTranslation, int mImageResourceId, int audioResourceID) {
        this.mDefaultTranslation = mDefaultTranslation;
        this.mBodoTranslation = mBodoTranslation;
        this.mImageResourceId = mImageResourceId;
        this.audioResourceID = audioResourceID;
    }

    public String getMDefaultTranslation(){ return mDefaultTranslation; }

    public String getMBodoTranslation(){ return mBodoTranslation; }

    public int getMImageResourceId(){ return mImageResourceId; }

    public boolean hasImage(){
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    public int getAudioResourceID() { return audioResourceID; }

    @Override
    public String toString() {
        return "Word{" +
                "mDefaultTranslation='" + mDefaultTranslation + '\'' +
                ", mBodoTranslation='" + mBodoTranslation + '\'' +
                ", mImageResourceId=" + mImageResourceId +
                ", audioResourceID=" + audioResourceID +
                '}';
    }
}
