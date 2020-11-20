package com.example.cs160_final_project;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Tools {
    static Set intersect(Set a, Set b) {
        Set c = new HashSet(a);
        c.retainAll(b);
        return c;
    }
    static int FindNextPicture(int index, ArrayList<Integer> duplicates, ArrayList<Set> combinedLabels) {
        Set a = combinedLabels.get(index);
        int max_ind = 0;
        int max_inter = 0;
        for (int i = 0; i < combinedLabels.size(); i++) {
            if (duplicates.contains(i)) {
                continue;
            }
            Set b = combinedLabels.get(i);
            Set intersection = intersect(a, b);
            if (intersection.size() >= max_inter) {
                max_inter = intersection.size();
                max_ind = i;
            }
        }
        duplicates.add(max_ind);
        return max_ind;
    }
    static ArrayList<Drawable> Generatestories(ArrayList<Drawable> userPictures, ArrayList<Drawable> stockPictures, ArrayList<Set> userLabels, ArrayList<Set> stockLabels) {
        Random rand = new Random();
        ArrayList<Drawable> combinedPictures = new ArrayList<>(userPictures);
        combinedPictures.addAll(stockPictures);
        ArrayList<Set> combinedLabels = new ArrayList<>(userLabels);
        combinedLabels.addAll(stockLabels);

        ArrayList<Integer> duplicates = new ArrayList<>();
        int index;
        if (userPictures.size() > 0) {
            index = rand.nextInt(userPictures.size());
        } else {
            index = rand.nextInt(stockPictures.size());
        }
        duplicates.add(index);
        for (int i = 0; i < 3; i++) {
            FindNextPicture(index, duplicates, combinedLabels);
            index = duplicates.get(duplicates.size() - 1);
        }

        ArrayList<Drawable> story = new ArrayList<>();
        for (int i = 0; i < duplicates.size(); i++) {
            story.add(combinedPictures.get(duplicates.get(i)));
        }
        return story;
    }

}
