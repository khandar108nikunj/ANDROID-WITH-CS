/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private ArrayList<String> wordList = new ArrayList<>();
    private HashSet<String> wordSet;
    private HashMap<String,ArrayList<String>> lettersToWord;
    private int wordLength=DEFAULT_WORD_LENGTH;

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        wordSet=new HashSet<String>();
        wordList=new ArrayList<String>();
        lettersToWord=new HashMap<String,ArrayList<String>>();
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            wordSet.add(word);
            String sortedWord=sortLetters(word);

            if(lettersToWord.containsKey(sortedWord)){
                ArrayList<String> anagram=lettersToWord.get(sortedWord);
                anagram.add(word);
                lettersToWord.put(sortedWord,anagram);
            }

            else{
                ArrayList<String> anagram=new ArrayList<String>();
                anagram.add(word);
                lettersToWord.put(sortedWord,anagram);

            }
        }
    }

    public boolean isGoodWord(String word, String base) {
        if(word.contains(base)) {
            return false;
        }
        if(wordSet.contains(word)) {
            return true;
        }

        return true;
    }

    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();

        String sortT=sortLetters(targetWord);
        String word;
        String sortWord;
        int limit=wordList.size();
        for(int i=0;i<limit;i++){
            word=wordList.get(i);

            if(word.length()==targetWord.length()){
                if(!word.equals(targetWord)){
                    sortWord=sortLetters(word);
                    if(sortWord.equals(sortT)){
                        result.add(word);
                    }
                }
            }
        }

        return result;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();

        for (char a='a';a<='z';a++){
            String newString=word+a;
            String newSorted=sortLetters(newString);
            boolean b=false;

            if(lettersToWord.containsKey(newSorted)) {
                ArrayList<String> ana = lettersToWord.get(newSorted);
                result.addAll(ana);
            }


        }

        for(int i = 0; i < result.size(); i++){
            if(!isGoodWord(result.get(i), word)){
                result.remove(i);
            }
        }

        return result;
    }

    public String pickGoodStarterWord() {

        String randomWord="";
        ArrayList<String> anagram=new ArrayList<>();
        while(anagram.size()<MIN_NUM_ANAGRAMS){
            anagram=new ArrayList<String>();
            while(randomWord.length()!=wordLength){
                randomWord=wordList.get(random.nextInt(wordList.size()));
            }
            anagram=getAnagramsWithOneMoreLetter(randomWord);
        }


        return randomWord;
    }

    public String sortLetters(String temp) {
        char[] str = temp.toCharArray();
        Arrays.sort(str);
        return String.valueOf(str);
    }
}
