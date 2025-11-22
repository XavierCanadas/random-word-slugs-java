/*
 * Formatter.java
 * RandomWordGenerator
 *
 * Created by Xavier Cañadas on 22/11/2025
 * Copyright (c) 2025. All rights reserved.
 */

package com.github.xaviercanadas.randomwordslugs.util;

import com.github.xaviercanadas.randomwordslugs.model.Case;

import java.util.List;

/**
 * Utility class for formatting word lists into different case styles.
 */
public class Formatter {

    /**
     * Formats a list of words according to the specified case style.
     *
     * @param words the list of words to format
     * @param caseStyle the desired case format
     * @return formatted string
     * @throws IllegalArgumentException if words is null or empty
     */
    public static String format(List<String> words, Case caseStyle) {
        if (words == null || words.isEmpty()) {
            throw new IllegalArgumentException("Words list cannot be null or empty");
        }

        return switch (caseStyle) {
            case KEBAB -> formatKebab(words);
            case CAMEL -> formatCamel(words);
            case TITLE -> formatTitle(words);
            case LOWER -> formatLower(words);
            case SENTENCE -> formatSentence(words);
        };
    }

    /**
     * kebab-case: words separated by hyphens, all lowercase
     * Example: ["Happy", "Little", "Cat"] -> "happy-little-cat"
     */
    private static String formatKebab(List<String> words) {
        return String.join("-", words).toLowerCase();
    }

    /**
     * camelCase: first word lowercase, subsequent words capitalized, no separators
     * Example: ["Happy", "Little", "Cat"] -> "happyLittleCat"
     */
    private static String formatCamel(List<String> words) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.size(); i++) {
            String word = words.get(i);
            if (i == 0) {
                result.append(word.toLowerCase());
            } else {
                result.append(capitalize(word));
            }
        }

        return result.toString();
    }

    /**
     * Title Case: Each Word Capitalized, separated by spaces
     * Example: ["happy", "little", "cat"] -> "Happy Little Cat"
     */
    private static String formatTitle(List<String> words) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.size(); i++) {
            if (i > 0) {
                result.append(" ");
            }
            result.append(capitalize(words.get(i)));
        }

        return result.toString();
    }

    /**
     * lower case: all lowercase, separated by spaces
     * Example: ["Happy", "Little", "Cat"] -> "happy little cat"
     */
    private static String formatLower(List<String> words) {
        return String.join(" ", words).toLowerCase();
    }

    /**
     * Sentence case: First word capitalized, rest lowercase, separated by spaces
     * Example: ["happy", "little", "cat"] -> "Happy little cat"
     */
    private static String formatSentence(List<String> words) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.size(); i++) {
            if (i > 0) {
                result.append(" ");
            }

            String word = words.get(i);
            if (i == 0) {
                result.append(capitalize(word));
            } else {
                result.append(word.toLowerCase());
            }
        }

        return result.toString();
    }

    /**
     * Capitalizes the first letter of a word and lowercases the rest.
     *
     * @param word the word to capitalize
     * @return capitalized word
     */
    private static String capitalize(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }

        if (word.length() == 1) {
            return word.toUpperCase();
        }

        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }
}
