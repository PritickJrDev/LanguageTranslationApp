package com.e.bodobhasha;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FamilyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FamilyFragment extends Fragment {

    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                //pause playback
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                //resume playback
                mediaPlayer.start();
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                //stop playback
                releaseMediaPlayer();
            }
        }
    };

    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    public FamilyFragment(){

    }


    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer(){
        if(mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;

            audioManager.abandonAudioFocus(mOnAudioFocusListener);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words =  new ArrayList<>();
        words.add(new Word("Male","Hou’a",R.drawable.family_son,R.raw.f1));
        words.add(new Word("Female","Hinjao",R.drawable.family_daughter,R.raw.f2));
        words.add(new Word("Child","Gotho",R.drawable.family_son,R.raw.f3));
        words.add(new Word("Elder","Geder",R.drawable.family_grandfather,R.raw.f4));
        words.add(new Word("Younger","Wndwi",R.drawable.family_father,R.raw.f5));
        words.add(new Word("Mother","Bima",R.drawable.family_mother,R.raw.f6));
        words.add(new Word("Father","Bifa",R.drawable.family_father,R.raw.f7));
        words.add(new Word("Grandmother","abwi",R.drawable.family_grandmother,R.raw.f8));
        words.add(new Word("Grandfather","abou",R.drawable.family_grandfather,R.raw.f9));
        words.add(new Word("Elder Brother","Bida",R.drawable.family_older_brother,R.raw.f10));
        words.add(new Word("Elder Sister","Bibo",R.drawable.family_older_sister,R.raw.f11));
        words.add(new Word("Younger Brother","Phongbai",R.drawable.family_younger_brother,R.raw.f12));
        words.add(new Word("Younger Sister","Binanao",R.drawable.family_younger_sister,R.raw.f13));
        words.add(new Word("Uncle","Adwi",R.drawable.family_father,R.raw.f14));
        words.add(new Word("Aunt","Madwi",R.drawable.family_mother,R.raw.f15));
        words.add(new Word("Wife","Bisi",R.drawable.family_father,R.raw.f16));
        words.add(new Word("Husband","Pisai",R.drawable.family_mother,R.raw.f17));
        words.add(new Word("Bride","Hinjao Gwdan",R.drawable.family_older_sister,R.raw.f18));
        words.add(new Word("Groom","Hou’a Gwdan",R.drawable.family_older_brother,R.raw.f19));

        WordAdapter wordAdapter = new WordAdapter(getContext(),words,R.drawable.family_shape);
        ListView listView = (ListView) rootView.findViewById(R.id.word_list_view);
        listView.setAdapter(wordAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Word word = words.get(position);
                int result = audioManager.requestAudioFocus(mOnAudioFocusListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // Start playback
                    releaseMediaPlayer();
                    mediaPlayer = MediaPlayer.create(getActivity(), word.getAudioResourceID());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mOnCompletionListener);
                }
            }
        });
        return rootView;
    }
}