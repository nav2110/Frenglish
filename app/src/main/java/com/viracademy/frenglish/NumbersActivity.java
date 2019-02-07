package com.viracademy.frenglish;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;



import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    //Handles playback of all sound files
    private MediaPlayer mediaPlayer;

    //handles audio focus for the sound files
    private AudioManager audioManager;

    //creating a global variable that contains the onAudioFocusChangeListener object
    AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener=
            new AudioManager.OnAudioFocusChangeListener(){
                public void onAudioFocusChange(int focusChange){
                    if (focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT||
                            focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                        //Pause playback
                        mediaPlayer.pause();

                        //if the mediaPlayer is paused we want the sound to be repeated from the
                        //beginning
                        mediaPlayer.seekTo(0);

                    } else if (focusChange==AudioManager.AUDIOFOCUS_GAIN){
                        //Resume playback. We can simply use the start method since we don't have a
                        //resume method
                        mediaPlayer.start();

                    }else if (focusChange==AudioManager.AUDIOFOCUS_LOSS){
                        //this means that we lost AudioFocus and we stopped playback. We need to clear
                        //the resources. We have defined our own method releaseMediaPlayer
                        releaseMediaPlayer();
                    }
                }

            };

    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);



        setContentView(R.layout.word_list);

        //Instantiate the audioManager object
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<WordImage> wordImage = new ArrayList<WordImage>();
        wordImage.add(new WordImage("zéro", "zero", R.drawable.zero, R.raw.zero));
        wordImage.add(new WordImage("un", "one", R.drawable.one, R.raw.un));
        wordImage.add(new WordImage("deux", "two", R.drawable.two, R.raw.deux));
        wordImage.add(new WordImage("trois", "three", R.drawable.three, R.raw.trois));
        wordImage.add(new WordImage("quatre", "four", R.drawable.four, R.raw.quatre));
        wordImage.add(new WordImage("cinq", "five", R.drawable.five, R.raw.cinq));
        wordImage.add(new WordImage("six", "six", R.drawable.six, R.raw.six));
        wordImage.add(new WordImage("sept", "seven", R.drawable.seven, R.raw.sept));
        wordImage.add(new WordImage("huit", "eight", R.drawable.eight, R.raw.huit));
        wordImage.add(new WordImage("neuf", "nine", R.drawable.nine, R.raw.neuf));
        wordImage.add(new WordImage("dix", "ten", R.drawable.ten, R.raw.dix));


        WordImageArrayAdapter adapter =
                new WordImageArrayAdapter(this, wordImage, R.color.category_numbers);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);

        // configure onClickListener on ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {

                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();

                //get the position of the exact word
                WordImage word = wordImage.get(position);

                // Request audio focus so in order to play the audio file. The app needs to play a
                // short audio file, so we will request audio focus with a short amount of time
                // with AUDIOFOCUS_GAIN_TRANSIENT.
                int result = audioManager.requestAudioFocus(onAudioFocusChangeListener,
                        //use the music stream
                        AudioManager.STREAM_MUSIC,
                        //Request permanent focus for short sound files
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    //We have audiofocus and now we can create our MediaPlayer object
                    //to play our sound parameter in each WordImage object.



                    //associate a sound to mediaPlayer object and start
                    mediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getSoundResourceId());
                    mediaPlayer.start();


                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mediaPlayer.setOnCompletionListener(completionListener);

                    Log.v("NumbersActivity", "Current word: " + word.toString());
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        //The mediaPlayer will release the space once the user leaves presses
        // the home button for instance
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;

            //Abandon AudioFocus when playback complete
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);
        }
    }


}
