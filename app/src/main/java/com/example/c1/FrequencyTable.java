package com.example.c1;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
/**
 * This class creates the frequency table and the passages name and amount of times it shows up.
 *  * @author Ayush Bhanushali
 *         email: ayush.bhanushali@stonybrook.edu
 *         Stony Brook Id: 116120180
 *         Recitation: 02
 */
public class FrequencyTable{
    private Hashtable<String, FrequencyList> frequencyLists;
    /**
     * instantiates frequency table 
     */
    public FrequencyTable() {
        frequencyLists = new Hashtable<>();
    }
    /**
     * 
     * @param passages to add to table
     * @return frequency table of passages
     */
    public static FrequencyTable buildTable(ArrayList<Passage> passages){
        FrequencyTable ft = new FrequencyTable();
        for(Passage p: passages){
            ft.addPassage(p);
        }
        return ft;
    }
    /**
     * 
     * @param p to add to frequency table
     * @throws IllegalArgumentException to be thrown if passage is empty
     */
    public void addPassage(Passage p) throws IllegalArgumentException{
        if(p==null && p.getWordCount() == 0){
            throw new IllegalArgumentException("Passage can't be empty");
        }
        for (String word : p.getWords()) {
            FrequencyList freqList = frequencyLists.get(word);
            if (freqList == null) {
                freqList = new FrequencyList(word, new ArrayList<>());
                freqList.addPassage(p);
                frequencyLists.put(word, freqList);
            } else {
                freqList.addPassage(p);
            }
        }
    }
    /**
     * 
     * @param word to find in frequency lists
     * @param p to get frequency of in frequency lists
     * @return amount of times p shows up in frequency list
     * @throws IllegalArgumentException if word or passage is empty
     */
    public int getFrequency(String word, Passage p ) throws IllegalArgumentException{
        if(word == null || p ==null){
            throw new IllegalArgumentException("Word or passage can't be empty");
        }
        FrequencyList freqList = frequencyLists.get(word);
        if(freqList!=null){
            return freqList.getFrequency(p);
        }
        return 0;
    }
     public Map<String, FrequencyList> getFrequencyLists() {
        return frequencyLists;
    }
    
}
