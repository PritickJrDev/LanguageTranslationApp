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
 * Use the {@link PhrasesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhrasesFragment extends Fragment {

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

    public PhrasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.word_list, container, false);
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<>();
        words.add(new Word("Greetings", "Khulumbai",R.raw.phr1));
        words.add(new Word("Teach me Bodo","Angkou Bodo fwrwng",R.raw.p2));
        words.add(new Word ("Can you speak Bodo?", "Bodo railainw rwngou nama?",R.raw.p3));
        words.add(new Word ("What is your name?", "Nwngna ma mung?",R.raw.p4));
        words.add(new Word ("I can’t speak Bodo fluently", "Ang Bodo railainw mwjangwi rwnga.",R.raw.p5));
        words.add(new Word("Come back someday","Fwipindw malaba",R.raw.p6));
        words.add(new Word("Where are you going","Obehai thangnw",R.raw.p7));
        words.add(new Word("What are you doing?","Ma kalam dwng?",R.raw.p8));
        words.add(new Word(" I don’t have time","Angna som gwiya",R.raw.p9));
        words.add(new Word("Have you taken your meal","Wngkam jakangbai nama?",R.raw.p10));
        words.add(new Word("Yes, I have taken my food.","Aou, jakangbai",R.raw.p11));
        words.add(new Word("I want to buy this","Ang bekou bainw lubwidwng.",R.raw.p12));
        words.add(new Word("How are you?","nwng ma brwi dong?",R.raw.p13));
        words.add(new Word("I don’t have appetite. ","Ang janw gwsw gwiya",R.raw.p14));
        words.add(new Word("You look very good.","Nwng nainw mwjang thar.",R.raw.p15));
        words.add(new Word(" I am fine."," Ang mwjangwi nw dong.",R.raw.p16));
        words.add(new Word("I always think about you / I miss you.","Ang nwngkou gwswkangbai thayw.",R.raw.p17));
        words.add(new Word("I will come again."," Ang fwipin gwn.",R.raw.p19));
        words.add(new Word(" I am sleeping.","Ang undugasinw.",R.raw.p20));
        words.add(new Word(" I am waiting for you.","Ang nwngkou negasinw.",R.raw.p21));
        words.add(new Word(" Can I meet you?","Nwngkou lwgw homnw hagwn nama?",R.raw.p22));
        words.add(new Word(" I love you.","Ang nwngkou gwsw thwyw",R.raw.p23));
        words.add(new Word("I wont be able to forget you","Ang nwngkou baonw hanai nonga.",R.raw.p24));
        words.add(new Word("Thank You","Sabaikor",R.raw.p18));

        WordAdapter wordAdapter = new WordAdapter(getContext(),words,R.drawable.phrases_shape);
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

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        if(mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;

            audioManager.abandonAudioFocus(mOnAudioFocusListener);
        }
    }
}