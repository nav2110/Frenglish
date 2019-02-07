package com.viracademy.frenglish;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


import java.util.ArrayList;

public class WordImageArrayAdapter extends ArrayAdapter<WordImage> {

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context   The current context. Used to inflate the layout file.
     * @param wordImage A List of WordImage objects to display in a list
     */
    //set variable for colors
    private int color_of_category;
    public WordImageArrayAdapter(Activity context, ArrayList<WordImage> wordImage, int color) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, wordImage);
        this.color_of_category=color;
    }


        /**
         * Provides a view for an AdapterView (ListView, GridView, etc.)
         *
         * @param position The position in the list of data that should be displayed in the
         *                 list item view.
         * @param convertView The recycled view to populate.
         * @param parent The parent ViewGroup that is used for inflation.
         * @return The View for the position in the AdapterView.
         */
        @Override
        public View getView ( int position, View convertView, ViewGroup parent){
            // Check if the existing view is being reused, otherwise inflate the view
            View listItemView = convertView;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.list_item, parent, false);
            }

            // Get the {@link WordImage} object located at this position in the list
            WordImage currentWordImage = getItem(position);

            // Find the TextView in the list_item.xml layout with the ID french_word
            TextView frenchWord = (TextView) listItemView.findViewById(R.id.french_word);
            // Get the French word from the current WordImage object and
            // set this text on the French word TextView
            frenchWord.setText(currentWordImage.getFrenchWord());

            // Find the TextView in the list_item.xml layout with the ID english_word
            TextView englishWord = (TextView) listItemView.findViewById(R.id.english_word);
            // Get the English word from the current WordImage object and
            // set this text on the English word TextView
            englishWord.setText(currentWordImage.getEnglishWord());

//            // Find the ImageView in the list_item.xml layout with the ID image
            ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);


//

            //check if word has image

            if (currentWordImage.hasImage()) {

                // Get the image resource ID from the currentWordImage object and
//            // set the image to iconView

                imageView.setImageResource(currentWordImage.getImageResourceId());

                //make sure that the iconView is visible

                imageView.setVisibility(View.VISIBLE);
            } else /*make sure that the image is not visible and doesn't
                    occupy any space*/
                imageView.setVisibility(View.GONE);

            //set the theme color for the list item

            View textContainer=listItemView.findViewById(R.id.text_container);

            //Find the color that the resource ID maps to
            int color= ContextCompat.getColor(getContext(), color_of_category);

            //Set the background color of the text_container view
            textContainer.setBackgroundColor(color);

            // Return the whole list item layout (containing 2 TextViews and an ImageView)
            // so that it can be shown in the ListView
            return listItemView;
        }

}