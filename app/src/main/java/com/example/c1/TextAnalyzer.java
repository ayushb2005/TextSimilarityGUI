package com.example.c1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class TextAnalyzer {
    private FrequencyTable frequencyTable;
    private StringBuilder resultBuilder;

    public TextAnalyzer() {
        frequencyTable = new FrequencyTable();
        resultBuilder = new StringBuilder();
    }

    public String analyzeText(double similarity, ArrayList<Passage> passages) {
        resultBuilder.append("Reading texts...\n\n");
        for(Passage p: passages){
            frequencyTable.addPassage(p);
        }

        Collections.sort(passages, Comparator.comparing(Passage::getTitle));

        displaySimilarities(passages);
        findSuspectedAuthors(passages, similarity);

        return resultBuilder.toString();
    }

    private void displaySimilarities(ArrayList<Passage> passages) {
        resultBuilder.append(String.format("%-40s | %s\n", "Text (title)", "Similarities (%)"))
                .append("--------------------------------------------------------------------------------\n");

        int count = 0;
        for (Passage p1 : passages) {
            resultBuilder.append(String.format("%-100s", p1.getTitle()));
            ArrayList<String> simArr = new ArrayList<>();

            for (Passage p2 : passages) {
                if (!p1.equals(p2)) {
                    double similarity = Passage.cosineSimilarity(p1, p2);
                    simArr.add(p2.getTitle() + "(" + Math.round(similarity * 100) + "%)");
                }
            }

            for (int i = 0; i < simArr.size(); i++) {
                if(i>0){
                    resultBuilder.append(", ");
                }
                resultBuilder.append(simArr.get(i));
            }

            count++;
            resultBuilder.append("\n");
            if (count < passages.size()) {
                resultBuilder.append("--------------------------------------------------------------------------------\n");
            }
        }
    }

    private void findSuspectedAuthors(ArrayList<Passage> passages, double similarity) {
        resultBuilder.append("\n\nSuspected Texts With Same Authors\n")
                .append("--------------------------------------------------------------------------------\n");

        for (int i = 0; i < passages.size(); i++) {
            for (int j = i + 1; j < passages.size(); j++) {
                Passage p1 = passages.get(i);
                Passage p2 = passages.get(j);
                double sim = Passage.cosineSimilarity(p1, p2);

                if (sim >= similarity) {
                    resultBuilder.append("'")
                            .append(p1.getTitle())
                            .append("' and '")
                            .append(p2.getTitle())
                            .append("' may have the same author (")
                            .append(Math.round(sim * 100))
                            .append("% similar).\n");
                }
            }
        }

        //resultBuilder.append("\nProgram terminating...\n");
    }
}