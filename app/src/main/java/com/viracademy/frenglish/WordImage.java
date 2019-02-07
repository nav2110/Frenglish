package com.viracademy.frenglish;

/**
 * {@link WordImage} represents an Image associated with a French word and its English translation.
 * Each object has 3 properties: image resource ID, French word, and English corresponding word.
 */
public class WordImage {



    private String frenchWord;

    private String englishWord;

    private int imageResourceId=NO_IMAGE_WORD;

    private int soundResourceId;

    //set the int value to -1 because no resource can have this value
    private static final int NO_IMAGE_WORD=-1;

    public WordImage(String frenchWord, String englishWord, int soundResourceId) {

        this.frenchWord = frenchWord;
        this.englishWord = englishWord;
        this.soundResourceId=soundResourceId;
    }

    public WordImage(String frenchWord, String englishWord, int imageResourceId, int soundResourceId) {

        this.frenchWord = frenchWord;
        this.englishWord = englishWord;
        this.imageResourceId = imageResourceId;
        this.soundResourceId=soundResourceId;

    }

    //getters of names




    public String getFrenchWord() {
        return frenchWord;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public int getSoundResourceId() {
        return soundResourceId;
    }

    //returns whether the word has an image
    public boolean hasImage(){

            return imageResourceId!=NO_IMAGE_WORD;

    }

    @Override
    public String toString() {
        return "WordImage{" +
                "frenchWord='" + frenchWord + '\'' +
                ", englishWord='" + englishWord + '\'' +
                ", imageResourceId=" + imageResourceId +
                ", soundResourceId=" + soundResourceId +
                '}';
    }
}