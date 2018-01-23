package com.java4qa.sandbox;

import java.util.Arrays;
import java.util.List;

public class Collections {

    public static void main(String[] args) {
        //noinspection unused
        String[] langs = {"Java", "C#", "Python", "PHP"};

//        List<String> languages = new ArrayList<String>();
//        languages.add("Java");
//        languages.add("C#");
//        languages.add("Python");
//        languages.add("PHP");

        List<String> languages = Arrays.asList("Java", "C#", "Python", "PHP");
        for (String l : languages){
            System.out.println("I want to learn " + l);
        }

//        for (int i =0; i < languages.size(); i++){
//            System.out.println("I want to learn " + languages.get(i));
//        }

//        List languages = Arrays.asList("Java", "C#", "Python", "PHP");
//        for (Object l : languages) {
//            System.out.println("I want to learn " + l);
//        }
    }
}

