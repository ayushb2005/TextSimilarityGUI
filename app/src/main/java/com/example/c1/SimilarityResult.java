package com.example.c1;
import java.util.* ;
public class SimilarityResult {
    private String title;
    private List<String> similarTitles;

    public SimilarityResult(String title, List<String> similarTitles) {
        this.title = title;
        this.similarTitles = similarTitles;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getSimilarTitles() {
        return similarTitles;
    }
}
