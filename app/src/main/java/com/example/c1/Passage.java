package com.example.c1;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Set;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

/**
 * This class has the title, word count, and similar titles of each passage.
 *
 * @author Ayush Bhanushali
 *         email: ayush.bhanushali@stonybrook.edu
 *         Stony Brook Id: 116120180
 *         Recitation: 02
 */
public class Passage {
    private String title;
    private int wordCount;
    private Hashtable<String, Double> similarTitles;
    private HashSet<String> stopWord;
    private Context context;

    /**
     * Constructor for Passage class
     *
     * @param context Android context for accessing resources
     * @param title   to have for passage
     * @param uri     to parse from passage
     */
    public Passage(Context context, String title, Uri uri) {
        this.context = context;
        this.title = title;
        similarTitles = new Hashtable<>();
        stopWordsSet();
        parseFile(uri);
    }

    /**
     * Parses the stop word file and makes a stop word set out of the words in it.
     */
//    private void stopWordsSet() {
//        stopWord = new HashSet<>();
//        try {
//            InputStream inputStream = context.getAssets().open("app/src/main/assets/StopWords.txt");
//    //app/src/main/assets/StopWords.txt
//            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line.trim().toLowerCase());
//                System.out.println("ballzzzz");
//                stopWord.add(line.trim().toLowerCase());
//            }
//            reader.close();
//        } catch (IOException e) {
//            Log.e("Passage", "Error reading stop words", e);
//        }
//    }
    private void stopWordsSet() {
        stopWord = new HashSet<>();
        stopWord.add("a");
        stopWord.add("about");
        stopWord.add("above");
        stopWord.add("after");
        stopWord.add("again");
        stopWord.add("against");
        stopWord.add("all");
        stopWord.add("am");
        stopWord.add("an");
        stopWord.add("and");
        stopWord.add("any");
        stopWord.add("are");
        stopWord.add("as");
        stopWord.add("at");
        stopWord.add("be");
        stopWord.add("because");
        stopWord.add("been");
        stopWord.add("before");
        stopWord.add("being");
        stopWord.add("below");
        stopWord.add("between");
        stopWord.add("both");
        stopWord.add("but");
        stopWord.add("by");
        stopWord.add("could");
        stopWord.add("did");
        stopWord.add("do");
        stopWord.add("does");
        stopWord.add("doing");
        stopWord.add("down");
        stopWord.add("during");
        stopWord.add("each");
        stopWord.add("few");
        stopWord.add("for");
        stopWord.add("from");
        stopWord.add("further");
        stopWord.add("had");
        stopWord.add("has");
        stopWord.add("have");
        stopWord.add("having");
        stopWord.add("he");
        stopWord.add("hed");
        stopWord.add("hell");
        stopWord.add("hes");
        stopWord.add("her");
        stopWord.add("here");
        stopWord.add("heres");
        stopWord.add("hers");
        stopWord.add("herself");
        stopWord.add("him");
        stopWord.add("himself");
        stopWord.add("his");
        stopWord.add("how");
        stopWord.add("hows");
        stopWord.add("i");
        stopWord.add("id");
        stopWord.add("ill");
        stopWord.add("im");
        stopWord.add("ive");
        stopWord.add("if");
        stopWord.add("in");
        stopWord.add("into");
        stopWord.add("is");
        stopWord.add("it");
        stopWord.add("its");
        stopWord.add("itself");
        stopWord.add("lets");
        stopWord.add("me");
        stopWord.add("more");
        stopWord.add("most");
        stopWord.add("my");
        stopWord.add("myself");
        stopWord.add("nor");
        stopWord.add("of");
        stopWord.add("on");
        stopWord.add("once");
        stopWord.add("only");
        stopWord.add("or");
        stopWord.add("other");
        stopWord.add("ought");
        stopWord.add("our");
        stopWord.add("ours");
        stopWord.add("ourselves");
        stopWord.add("out");
        stopWord.add("over");
        stopWord.add("own");
        stopWord.add("same");
        stopWord.add("she");
        stopWord.add("shed");
        stopWord.add("shell");
        stopWord.add("shes");
        stopWord.add("should");
        stopWord.add("so");
        stopWord.add("some");
        stopWord.add("such");
        stopWord.add("than");
        stopWord.add("that");
        stopWord.add("thats");
        stopWord.add("the");
        stopWord.add("their");
        stopWord.add("theirs");
        stopWord.add("them");
        stopWord.add("themselves");
        stopWord.add("then");
        stopWord.add("there");
        stopWord.add("theres");
        stopWord.add("these");
        stopWord.add("they");
        stopWord.add("theyd");
        stopWord.add("theyll");
        stopWord.add("theyre");
        stopWord.add("theyve");
        stopWord.add("this");
        stopWord.add("those");
        stopWord.add("through");
        stopWord.add("to");
        stopWord.add("too");
        stopWord.add("under");
        stopWord.add("until");
        stopWord.add("up");
        stopWord.add("very");
        stopWord.add("was");
        stopWord.add("we");
        stopWord.add("wed");
        stopWord.add("well");
        stopWord.add("were");
        stopWord.add("weve");
        stopWord.add("what");
        stopWord.add("whats");
        stopWord.add("when");
        stopWord.add("whens");
        stopWord.add("where");
        stopWord.add("wheres");
        stopWord.add("which");
        stopWord.add("while");
        stopWord.add("who");
        stopWord.add("whos");
        stopWord.add("whom");
        stopWord.add("why");
        stopWord.add("whys");
        stopWord.add("with");
        stopWord.add("would");
        stopWord.add("you");
        stopWord.add("youd");
        stopWord.add("youll");
        stopWord.add("youre");
        stopWord.add("youve");
        stopWord.add("your");
        stopWord.add("yours");
        stopWord.add("yourself");
        stopWord.add("yourselves");
    }


    /**
     * Parses the file, removes punctuation, and populates similarTitles.
     *
     * @param uri the file's Uri to read
     */
    public void parseFile(Uri uri) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(" ");
                for (String word : words) {
                    word = regexPunct(word).toLowerCase().trim();
                    if (word.length() > 0 && !stopWord.contains(word)) {
                        similarTitles.put(word, similarTitles.getOrDefault(word, 0.0) + 1.0);
                        wordCount++;
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            Log.e("Passage", "Error reading file", e);
        }
    }

    /**
     * Removes all non-letter characters from the word.
     *
     * @param word the word to process
     * @return the cleaned word with only letters
     */
    private String regexPunct(String word) {
        StringBuilder cleanWord = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            if (Character.isLetter(word.charAt(i))) {
                cleanWord.append(word.charAt(i));
            }
        }
        return cleanWord.toString();
    }


    public static double cosineSimilarity(Passage passage1, Passage passage2) {
        Map<String, Double> t1 = passage1.getSimilarTitles();
        Map<String, Double> t2 = passage2.getSimilarTitles();

        Set<String> combined = new HashSet<>();
        combined.addAll(t1.keySet());
        combined.addAll(t2.keySet());

        double dotProduct = 0.0;
        double m1 = 0.0;
        double m2 = 0.0;

        for (String word : combined) {
            double val1 = t1.getOrDefault(word, 0.0);
            double val2 = t2.getOrDefault(word, 0.0);

            dotProduct += val1 * val2;
            m1 += val1 * val1;
            m2 += val2 * val2;
        }

        return dotProduct / (Math.sqrt(m1) * Math.sqrt(m2));
    }

    public double getWordFrequency(String word) {
        if (wordCount == 0) {
            return 0.0;
        }
        Double freq = similarTitles.get(word.toLowerCase());
        return (freq != null) ? freq / wordCount : 0.0;
    }

    public Set<String> getWords() {
        return new HashSet<>(similarTitles.keySet());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public Hashtable<String, Double> getSimilarTitles() {
        return similarTitles;
    }

    public void setSimilarTitles(Hashtable<String, Double> similarTitles) {
        this.similarTitles = similarTitles;
    }

    @Override
    public String toString() {
        return "Title: " + title + " , similarities: " + similarTitles;
    }
}
