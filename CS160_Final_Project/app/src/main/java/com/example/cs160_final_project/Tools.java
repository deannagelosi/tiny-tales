package com.example.cs160_final_project;

import android.graphics.Bitmap;

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
    static int FindNextPicture(int index, ArrayList<Integer> duplicates, ArrayList<Bitmap> pictures, ArrayList<Set> labels) {
        Set a = labels.get(index);
        int max_ind = 0;
        int max_inter = 0;
        for (int i = 0; i < pictures.size(); i++) {
            if (duplicates.contains(i)) {
                continue;
            }
            Set b = labels.get(i);
            Set intersection = intersect(a, b);
            if (intersection.size() >= max_inter) {
                max_inter = intersection.size();
                max_ind = i;
            }
        }
        duplicates.add(max_ind);
        return max_ind;
    }
    static void Generatestories(ArrayList<Integer> duplicates, ArrayList<Bitmap> pictures, ArrayList<Set> labels) {
        Random rand = new Random();
        int index = rand.nextInt(pictures.size());
        duplicates.add(index);
        for (int i = 0; i < 3; i++) {
            FindNextPicture(index, duplicates, pictures, labels);
            index = duplicates.get(duplicates.size() - 1);
        }
    }

}
