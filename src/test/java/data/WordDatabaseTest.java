/*
 * WordDatabaseTest.java
 * RandomWordGenerator
 *
 * Created by Xavier Cañadas on 22/11/2025
 * Copyright (c) 2025. All rights reserved.
 */

package data;

import com.github.xaviercanadas.randomwordslugs.data.WordDatabase;
import com.github.xaviercanadas.randomwordslugs.model.Category;
import com.github.xaviercanadas.randomwordslugs.model.PartsOfSpeech;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for WordDatabase class.
 */
class WordDatabaseTest {

    private WordDatabase database;

    @BeforeEach
    void setUp() {
        database = new WordDatabase();
    }

    @Test
    void testGetWordsReturnsNonEmptyLists() {
        assertFalse(database.getWords(PartsOfSpeech.NOUN).isEmpty());
        assertFalse(database.getWords(PartsOfSpeech.ADJECTIVE).isEmpty());
    }

    @Test
    void testGetWordsReturnsUnmodifiableList() {
        List<?> words = database.getWords(PartsOfSpeech.NOUN);

        assertThrows(UnsupportedOperationException.class, () -> {
            words.clear();
        });
    }

    @Test
    void testGetWordsByCategoryWithNoFilter() {
        List<String> nouns = database.getWordsByCategory(PartsOfSpeech.NOUN, null);
        List<String> nounsEmpty = database.getWordsByCategory(PartsOfSpeech.NOUN, Collections.emptyList());

        assertFalse(nouns.isEmpty());
        assertEquals(nouns.size(), nounsEmpty.size());
    }

    @Test
    void testGetWordsByCategoryWithSingleCategory() {
        List<String> animals = database.getWordsByCategory(
                PartsOfSpeech.NOUN,
                Collections.singletonList(Category.ANIMALS)
        );

        assertFalse(animals.isEmpty());
        assertTrue(animals.contains("cat"));
        assertTrue(animals.contains("dog"));
    }

    @Test
    void testGetWordsByCategoryWithMultipleCategories() {
        List<String> words = database.getWordsByCategory(
                PartsOfSpeech.NOUN,
                Arrays.asList(Category.ANIMALS, Category.FOOD)
        );

        assertFalse(words.isEmpty());
        // Should contain words from either category
        assertTrue(words.contains("cat") || words.contains("apple"));
    }

    @Test
    void testGetWordsByCategoryForAdjectives() {
        List<String> colors = database.getWordsByCategory(
                PartsOfSpeech.ADJECTIVE,
                Collections.singletonList(Category.COLOR)
        );

        assertFalse(colors.isEmpty());
        assertTrue(colors.contains("blue"));
    }

    @Test
    void testGetWordsByCategoryFiltersCorrectly() {
        List<String> technology = database.getWordsByCategory(
                PartsOfSpeech.NOUN,
                Collections.singletonList(Category.TECHNOLOGY)
        );

        assertTrue(technology.contains("computer"));
        assertFalse(technology.contains("cat")); // Cat is not technology
    }

    @Test
    void testGetWordCountWithoutFilter() {
        int nounCount = database.getWordCount(PartsOfSpeech.NOUN);
        int adjectiveCount = database.getWordCount(PartsOfSpeech.ADJECTIVE);

        assertTrue(nounCount > 0);
        assertTrue(adjectiveCount > 0);
    }

    @Test
    void testGetWordCountWithFilter() {
        int animalCount = database.getWordCount(
                PartsOfSpeech.NOUN,
                Collections.singletonList(Category.ANIMALS)
        );

        assertTrue(animalCount > 0);
        assertTrue(animalCount <= database.getWordCount(PartsOfSpeech.NOUN));
    }

    @Test
    void testWordCountMatchesListSize() {
        List<Category> categories = Collections.singletonList(Category.ANIMALS);

        int count = database.getWordCount(PartsOfSpeech.NOUN, categories);
        int listSize = database.getWordsByCategory(PartsOfSpeech.NOUN, categories).size();

        assertEquals(count, listSize);
    }

    @Test
    void testDatabaseContainsExpectedWords() {
        List<String> allNouns = database.getWordsByCategory(PartsOfSpeech.NOUN, null);

        // Test sample words from the example
        assertTrue(allNouns.contains("cat"));
        assertTrue(allNouns.contains("dog"));
        assertTrue(allNouns.contains("computer"));
        assertTrue(allNouns.contains("apple"));
    }

    @Test
    void testDatabaseContainsExpectedAdjectives() {
        List<String> allAdjectives = database.getWordsByCategory(PartsOfSpeech.ADJECTIVE, null);

        assertTrue(allAdjectives.contains("happy"));
        assertTrue(allAdjectives.contains("big"));
        assertTrue(allAdjectives.contains("blue"));
    }

    @Test
    void testMultipleCategoryWordIsIncludedInBothFilters() {
        // "airport" has both TRANSPORTATION and PLACE categories
        List<String> transportation = database.getWordsByCategory(
                PartsOfSpeech.NOUN,
                Collections.singletonList(Category.TRANSPORTATION)
        );

        List<String> places = database.getWordsByCategory(
                PartsOfSpeech.NOUN,
                Collections.singletonList(Category.PLACE)
        );

        assertTrue(transportation.contains("airport"));
        assertTrue(places.contains("airport"));
    }

    @Test
    void testWordListHasNoRepeats() {
        List<String> allNouns = database.getWordsByCategory(PartsOfSpeech.NOUN, null);
        List<String> allAdjectives = database.getWordsByCategory(PartsOfSpeech.ADJECTIVE, null);

        // Check for duplicates in nouns
        long uniqueNouns = allNouns.stream().distinct().count();
        assertEquals(allNouns.size(), uniqueNouns, "Nouns list contains duplicates");

        // Check for duplicates in adjectives
        long uniqueAdjectives = allAdjectives.stream().distinct().count();
        assertEquals(allAdjectives.size(), uniqueAdjectives, "Adjectives list contains duplicates");
    }
}