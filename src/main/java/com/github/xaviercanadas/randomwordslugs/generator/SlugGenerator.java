/*
 * SlugGenerator.java
 * RandomWordGenerator
 *
 * Created by Xavier Cañadas on 22/11/2025
 * Copyright (c) 2025. All rights reserved.
 */

package com.github.xaviercanadas.randomwordslugs.generator;

import com.github.xaviercanadas.randomwordslugs.data.WordDatabase;
import com.github.xaviercanadas.randomwordslugs.model.Case;
import com.github.xaviercanadas.randomwordslugs.model.Category;
import com.github.xaviercanadas.randomwordslugs.model.PartsOfSpeech;
import com.github.xaviercanadas.randomwordslugs.util.Formatter;

import java.util.*;

/**
 * Main generator for creating random word slugs.
 * Thread-safe for concurrent slug generation.
 */
public class SlugGenerator {
    private static final int DEFAULT_NUMBER_OF_WORDS = 3;

    private final WordDatabase wordDatabase;
    private final Random random;

    public SlugGenerator() {
        this.wordDatabase = new WordDatabase();
        this.random = new Random();
    }
    public SlugGenerator(WordDatabase wordDatabase) {
        this.wordDatabase = wordDatabase;
        this.random = new Random();
    }

    /**
     * Generates a slug with default settings (3 words, kebab-case).
     * Pattern: adjective-adjective-noun
     *
     * @return generated slug (e.g., "happy-little-cat")
     */
    public String generate() {
        return generate(DEFAULT_NUMBER_OF_WORDS);
    }

    /**
     * Generates a slug with specified number of words.
     * Pattern: (n-1 adjectives) + 1 noun
     *
     * @param numberOfWords total number of words in the slug
     * @return generated slug
     */
    public String generate(int numberOfWords) {
        return generate(numberOfWords, null);
    }

    /**
     * Generates a slug with specified number of words and options.
     *
     * @param numberOfWords total number of words in the slug
     * @param options configuration options (can be null for defaults)
     * @return generated slug
     */
    public String generate(int numberOfWords, SlugOptions options) {
        if (numberOfWords <= 0) {
            throw new IllegalArgumentException("Number of words must be positive");
        }

        SlugOptions opts = options != null ? options : createDefaultOptions(numberOfWords);

        List<PartsOfSpeech> partsOfSpeech = opts.getPartsOfSpeech().isEmpty()
                ? getDefaultPartsOfSpeech(numberOfWords)
                : opts.getPartsOfSpeech();

        if (partsOfSpeech.size() != numberOfWords) {
            throw new IllegalArgumentException(
                    "Parts of speech pattern length (" + partsOfSpeech.size() +
                            ") must match number of words (" + numberOfWords + ")"
            );
        }

        List<String> words = new ArrayList<>(numberOfWords);
        Map<PartsOfSpeech, List<Category>> categoryMap = opts.getCategories();

        for (PartsOfSpeech partOfSpeech : partsOfSpeech) {
            List<Category> categories = categoryMap.get(partOfSpeech);
            List<String> candidates = wordDatabase.getWordsByCategory(partOfSpeech, categories);

            if (candidates.isEmpty()) {
                throw new IllegalStateException(
                        "No words available for " + partOfSpeech +
                                " with categories " + categories
                );
            }

            String selectedWord = candidates.get(random.nextInt(candidates.size()));
            words.add(selectedWord);
        }

        return Formatter.format(words, opts.getFormat());
    }

    /**
     * Generates a slug with custom options using builder pattern.
     *
     * @param options configuration options
     * @return generated slug
     */
    public String generate(SlugOptions options) {
        if (options == null || options.getPartsOfSpeech().isEmpty()) {
            return generate(DEFAULT_NUMBER_OF_WORDS, options);
        }
        return generate(options.getPartsOfSpeech().size(), options);
    }

    /**
     * Calculates total number of unique possible slugs for given configuration.
     *
     * @param numberOfWords number of words in slug
     * @param options configuration options (can be null)
     * @return total unique combinations possible
     */
    public long totalUniqueSlugs(int numberOfWords, SlugOptions options) {
        SlugOptions opts = options != null ? options : createDefaultOptions(numberOfWords);
        List<PartsOfSpeech> partsOfSpeech = opts.getPartsOfSpeech().isEmpty()
                ? getDefaultPartsOfSpeech(numberOfWords)
                : opts.getPartsOfSpeech();

        long combinations = 1;
        Map<PartsOfSpeech, List<Category>> categoryMap = opts.getCategories();

        for (PartsOfSpeech partOfSpeech : partsOfSpeech) {
            List<Category> categories = categoryMap.get(partOfSpeech);
            int wordCount = wordDatabase.getWordCount(partOfSpeech, categories);
            combinations *= wordCount;
        }

        return combinations;
    }

    /**
     * Creates default options with specified number of words.
     */
    private SlugOptions createDefaultOptions(int numberOfWords) {
        return SlugOptions.builder()
                .partsOfSpeech(getDefaultPartsOfSpeech(numberOfWords))
                .format(Case.KEBAB)
                .build();
    }

    /**
     * Creates default parts of speech pattern: (n-1) adjectives + 1 noun.
     */
    private List<PartsOfSpeech> getDefaultPartsOfSpeech(int numberOfWords) {
        List<PartsOfSpeech> pattern = new ArrayList<>(numberOfWords);

        for (int i = 0; i < numberOfWords - 1; i++) {
            pattern.add(PartsOfSpeech.ADJECTIVE);
        }
        pattern.add(PartsOfSpeech.NOUN);
        return pattern;
    }
}
