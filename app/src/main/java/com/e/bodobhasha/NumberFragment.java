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
 * Use the {@link NumberFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NumberFragment extends Fragment {

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

    public NumberFragment() {
        // Required empty public constructor
    }

    private void releaseMediaPlayer() {
        if(mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;

            audioManager.abandonAudioFocus(mOnAudioFocusListener);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> words = new ArrayList<>();

        words.add(new Word("one", "se",R.drawable.number_one,R.raw.n1));
        words.add(new Word("two","nwi",R.drawable.number_two,R.raw.n2));
        words.add(new Word ("three", "tham",R.drawable.number_three,R.raw.n3));
        words.add(new Word ("four", "brwi",R.drawable.number_four,R.raw.n4));
        words.add(new Word ("five", "ba",R.drawable.number_five,R.raw.n5));
        words.add(new Word("six","do",R.drawable.number_six,R.raw.n6));
        words.add(new Word("seven","sni",R.drawable.number_seven,R.raw.n7));
        words.add(new Word("eight","dain",R.drawable.number_eight,R.raw.n8));
        words.add(new Word("nine","gu",R.drawable.number_nine,R.raw.n9));
        words.add(new Word("ten","ji",R.drawable.number_ten,R.raw.n10));

        WordAdapter wordAdapter = new WordAdapter(getActivity(), words,R.drawable.number_shape);

        ListView listView = (ListView) rootView.findViewById(R.id.word_list_view);

        listView.setAdapter(wordAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Word word = words.get(position);

                // Request audio focus for playback
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