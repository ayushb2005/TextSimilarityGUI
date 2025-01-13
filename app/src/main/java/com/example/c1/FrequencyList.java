package com.example.c1;

import java.util.ArrayList;
import java.util.Hashtable;
/**
 * This class creates lists of frequences of a word compared with the other passages
 *  * @author Ayush Bhanushali
 *         email: ayush.bhanushali@stonybrook.edu
 *         Stony Brook Id: 116120180
 *         Recitation: 02
 */
public class FrequencyList {
    
    private String word;
    private ArrayList<Integer> frequencies;
    private Hashtable<String, Integer> passageIndicies;
    /**
     * 
     * @param word to create a list and frequency of
     * @param passages of all the other passages
     */
    public FrequencyList(String word, ArrayList<Passage> passages){
        this.word = word;
        frequencies = new ArrayList<>();
        passageIndicies = new Hashtable<>();

        for(int i =0; i<passages.size(); i++){
            Passage p = passages.get(i);
            double frequency = p.getWordFrequency(word);
            if(frequency>0){
                int freqInt = (int) frequency*100;
                frequencies.add(freqInt);
                passageIndicies.put(p.getTitle(), i);
            }

        }
    }
    /**
     * 
     * @param p to add to the list with its frequency
     */
    public void addPassage(Passage p){
        double frequency = p.getWordFrequency(word);
        if(frequency>0){
            int freqInt = (int) frequency*100;
            frequencies.add(freqInt);
            passageIndicies.put(p.getTitle(), frequencies.size() - 1);
        }
    }
    /**
     * 
     * @param p to get frequency of a passage
     * @return how many times the passage shows up in the arraylist
     */
    public int getFrequency(Passage p) {
        Integer index = passageIndicies.get(p.getTitle());
        if (index != null) {
            return frequencies.get(index);
        }
        return 0;  
    }

}   
