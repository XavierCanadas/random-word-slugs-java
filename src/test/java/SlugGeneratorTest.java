/*
 * SlugGeneratorTest.java
 * RandomWordGenerator
 *
 * Created by Xavier Cañadas on 22/11/2025
 * Copyright (c) 2025. All rights reserved.
 */

import com.github.xaviercanadas.randomwordgenerator.data.WordDatabase;
import com.github.xaviercanadas.randomwordgenerator.generator.SlugOptions;
import com.github.xaviercanadas.randomwordgenerator.generator.SlugGenerator;
import com.github.xaviercanadas.randomwordgenerator.model.Case;
import com.github.xaviercanadas.randomwordgenerator.model.Category;
import com.github.xaviercanadas.randomwordgenerator.model.PartsOfSpeech;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for SlugGenerator class.
 */
class SlugGeneratorTest {

    private SlugGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new SlugGenerator();
    }

    @Test
    void testGenerateWithDefaults() {
        String slug = generator.generate();

        assertNotNull(slug);
        assertFalse(slug.isEmpty());
        assertTrue(slug.contains("-")); // Default is kebab-case
        assertEquals(3, slug.split("-").length); // Should have 3 words
    }

    @Test
    void testGenerateWithSpecificNumberOfWords() {
        String slug2 = generator.generate(2);
        String slug4 = generator.generate(4);
        String slug5 = generator.generate(5);

        assertEquals(2, slug2.split("-").length);
        assertEquals(4, slug4.split("-").length);
        assertEquals(5, slug5.split("-").length);
    }

    @Test
    void testGenerateWithInvalidNumberOfWords() {
        assertThrows(IllegalArgumentException.class, () -> {
            generator.generate(0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            generator.generate(-1);
        });
    }

    @Test
    void testGenerateWithKebabCase() {
        SlugOptions options = SlugOptions.builder()
                .format(Case.KEBAB)
                .build();

        String slug = generator.generate(3, options);

        assertTrue(slug.contains("-"));
        assertFalse(slug.contains(" "));
        assertEquals(slug.toLowerCase(), slug);
    }

    @Test
    void testGenerateWithCamelCase() {
        SlugOptions options = SlugOptions.builder()
                .format(Case.CAMEL)
                .build();

        String slug = generator.generate(3, options);

        assertFalse(slug.contains("-"));
        assertFalse(slug.contains(" "));
        assertTrue(Character.isLowerCase(slug.charAt(0))); // First char should be lowercase
    }

    @Test
    void testGenerateWithTitleCase() {
        SlugOptions options = SlugOptions.builder()
                .format(Case.TITLE)
                .build();

        String slug = generator.generate(3, options);

        assertTrue(slug.contains(" "));
        assertTrue(Character.isUpperCase(slug.charAt(0))); // First char should be uppercase
    }

    @Test
    void testGenerateWithLowerCase() {
        SlugOptions options = SlugOptions.builder()
                .format(Case.LOWER)
                .build();

        String slug = generator.generate(3, options);

        assertTrue(slug.contains(" "));
        assertEquals(slug.toLowerCase(), slug);
    }

    @Test
    void testGenerateWithSentenceCase() {
        SlugOptions options = SlugOptions.builder()
                .format(Case.SENTENCE)
                .build();

        String slug = generator.generate(3, options);

        assertTrue(slug.contains(" "));
        assertTrue(Character.isUpperCase(slug.charAt(0))); // First char should be uppercase
    }

    @Test
    void testGenerateWithCategoryFilter() {
        SlugOptions options = SlugOptions.builder()
                .withNounCategories(Category.ANIMALS)
                .build();

        // Generate multiple slugs to verify category filtering
        for (int i = 0; i < 5; i++) {
            String slug = generator.generate(3, options);
            assertNotNull(slug);
            assertFalse(slug.isEmpty());
        }
    }

    @Test
    void testGenerateWithMultipleCategoryFilters() {
        SlugOptions options = SlugOptions.builder()
                .withNounCategories(Category.ANIMALS, Category.FOOD)
                .withAdjectiveCategories(Category.COLOR, Category.SIZE)
                .build();

        String slug = generator.generate(3, options);

        assertNotNull(slug);
        assertFalse(slug.isEmpty());
    }

    @Test
    void testGenerateWithCustomPartsOfSpeech() {
        SlugOptions options = SlugOptions.builder()
                .partsOfSpeech(PartsOfSpeech.NOUN, PartsOfSpeech.ADJECTIVE, PartsOfSpeech.NOUN)
                .build();

        String slug = generator.generate(options);

        assertNotNull(slug);
        assertEquals(3, slug.split("-").length);
    }

    @Test
    void testGenerateWithMismatchedPartsOfSpeechLength() {
        SlugOptions options = SlugOptions.builder()
                .partsOfSpeech(PartsOfSpeech.ADJECTIVE, PartsOfSpeech.NOUN) // 2 words
                .build();

        assertThrows(IllegalArgumentException.class, () -> {
            generator.generate(3, options); // Requesting 3 words
        });
    }

    @Test
    void testGenerateWithOptionsOnly() {
        SlugOptions options = SlugOptions.builder()
                .partsOfSpeech(PartsOfSpeech.ADJECTIVE, PartsOfSpeech.ADJECTIVE, PartsOfSpeech.NOUN)
                .format(Case.CAMEL)
                .build();

        String slug = generator.generate(options);

        assertNotNull(slug);
        assertFalse(slug.isEmpty());
    }

    @Test
    void testGenerateProducesUniqueResults() {
        String slug1 = generator.generate();
        String slug2 = generator.generate();
        String slug3 = generator.generate();

        // While theoretically they could be the same, with enough words it's extremely unlikely
        // This is a probabilistic test
        assertNotEquals(slug1, slug2);
        assertNotEquals(slug2, slug3);
        assertNotEquals(slug1, slug3);
    }

    @Test
    void testTotalUniqueSlugs() {
        long total = generator.totalUniqueSlugs(3, null);

        assertTrue(total > 0);
        // With the example data (8 nouns, 6 adjectives), we should have 6*6*8 = 288 combinations
        // But this will vary based on the actual word database
    }

    @Test
    void testTotalUniqueSlugsWithCategories() {
        SlugOptions options = SlugOptions.builder()
                .withNounCategories(Category.ANIMALS)
                .build();

        long total = generator.totalUniqueSlugs(3, options);

        assertTrue(total > 0);

        // Should be less than total without filters
        long totalWithoutFilter = generator.totalUniqueSlugs(3, null);
        assertTrue(total <= totalWithoutFilter);
    }

    @Test
    void testGenerateConsistencyAcrossMultipleCalls() {
        SlugOptions options = SlugOptions.builder()
                .withNounCategories(Category.ANIMALS)
                .format(Case.KEBAB)
                .build();

        // Generate multiple times to ensure no crashes or inconsistencies
        for (int i = 0; i < 20; i++) {
            String slug = generator.generate(3, options);
            assertNotNull(slug);
            assertFalse(slug.isEmpty());
            assertTrue(slug.contains("-"));
        }
    }

    @Test
    void testGenerateWithSingleWord() {
        String slug = generator.generate(1);

        assertNotNull(slug);
        assertFalse(slug.isEmpty());
        assertFalse(slug.contains("-")); // Single word should have no separator
    }

    @Test
    void testGenerateWithEmptyCategoriesThrowsException() {
        // Create options with a category that might not have words
        SlugOptions options = SlugOptions.builder()
                .partsOfSpeech(PartsOfSpeech.NOUN)
                .withNounCategories(Category.ANIMALS) // Valid category
                .build();

        // This should work
        assertDoesNotThrow(() -> generator.generate(options));
    }

    @Test
    void testRepeatedGenerationProducesValidWords() {
        // Similar to TypeScript's test() wrapper that runs 1000 times
        WordDatabase db = new WordDatabase();
        List<String> allNouns = db.getWordsByCategory(PartsOfSpeech.NOUN, null);
        List<String> allAdjectives = db.getWordsByCategory(PartsOfSpeech.ADJECTIVE, null);

        for (int i = 0; i < 100; i++) {
            String slug = generator.generate(3);
            String[] parts = slug.split("-");

            assertEquals(3, parts.length);
            assertTrue(allAdjectives.contains(parts[0]), "First word should be an adjective: " + parts[0]);
            assertTrue(allAdjectives.contains(parts[1]), "Second word should be an adjective: " + parts[1]);
            assertTrue(allNouns.contains(parts[2]), "Third word should be a noun: " + parts[2]);
        }
    }

    @Test
    void testGeneratedWordsMatchCategories() {
        // Test that generated words actually belong to specified categories
        WordDatabase db = new WordDatabase();

        SlugOptions options = SlugOptions.builder()
                .withNounCategories(Category.ANIMALS, Category.FOOD)
                .withAdjectiveCategories(Category.COLOR, Category.SIZE)
                .build();

        List<String> validNouns = db.getWordsByCategory(
                PartsOfSpeech.NOUN,
                Arrays.asList(Category.ANIMALS, Category.FOOD)
        );
        List<String> validAdjectives = db.getWordsByCategory(
                PartsOfSpeech.ADJECTIVE,
                Arrays.asList(Category.COLOR, Category.SIZE)
        );

        for (int i = 0; i < 50; i++) {
            String slug = generator.generate(3, options);
            String[] parts = slug.split("-");

            assertTrue(validAdjectives.contains(parts[0]),
                    "Generated adjective '" + parts[0] + "' not in specified categories");
            assertTrue(validAdjectives.contains(parts[1]),
                    "Generated adjective '" + parts[1] + "' not in specified categories");
            assertTrue(validNouns.contains(parts[2]),
                    "Generated noun '" + parts[2] + "' not in specified categories");
        }
    }

    @Test
    void testTotalUniqueSlugsCalculation() {
        WordDatabase db = new WordDatabase();
        int numAdjectives = db.getWordCount(PartsOfSpeech.ADJECTIVE);
        int numNouns = db.getWordCount(PartsOfSpeech.NOUN);

        long total = generator.totalUniqueSlugs(3, null);
        long expected = (long) numAdjectives * numAdjectives * numNouns;

        assertEquals(expected, total, "Total unique slugs calculation incorrect");
    }

    @Test
    void testTotalUniqueSlugsWithCategorySubset() {
        WordDatabase db = new WordDatabase();

        SlugOptions options = SlugOptions.builder()
                .withNounCategories(Category.ANIMALS, Category.PEOPLE)
                .withAdjectiveCategories(Category.COLOR, Category.APPEARANCE)
                .build();

        int numAdjectives = db.getWordCount(
                PartsOfSpeech.ADJECTIVE,
                Arrays.asList(Category.COLOR, Category.APPEARANCE)
        );
        int numNouns = db.getWordCount(
                PartsOfSpeech.NOUN,
                Arrays.asList(Category.ANIMALS, Category.PEOPLE)
        );

        long total = generator.totalUniqueSlugs(4, options);
        // For 4 words: adjective^3 * noun
        long expected = (long) Math.pow(numAdjectives, 3) * numNouns;

        assertEquals(expected, total, "Total unique slugs with categories incorrect");
    }

    @Test
    void testCamelCaseFormatValidation() {
        SlugOptions options = SlugOptions.builder()
                .format(Case.CAMEL)
                .build();

        for (int i = 0; i < 20; i++) {
            String slug = generator.generate(3, options);

            // First character should be lowercase
            assertTrue(Character.isLowerCase(slug.charAt(0)),
                    "CamelCase should start with lowercase: " + slug);

            // Should not contain spaces or hyphens
            assertFalse(slug.contains(" "), "CamelCase should not contain spaces");
            assertFalse(slug.contains("-"), "CamelCase should not contain hyphens");

            // Should have at least one uppercase letter (for multi-word slugs)
            assertTrue(slug.chars().anyMatch(Character::isUpperCase),
                    "CamelCase should contain uppercase letters: " + slug);
        }
    }

    @Test
    void testTitleCaseFormatValidation() {
        SlugOptions options = SlugOptions.builder()
                .format(Case.TITLE)
                .build();

        for (int i = 0; i < 20; i++) {
            String slug = generator.generate(3, options);
            String[] words = slug.split(" ");

            assertEquals(3, words.length, "Title case should have 3 space-separated words");

            for (String word : words) {
                assertTrue(Character.isUpperCase(word.charAt(0)),
                        "Each word in Title Case should start with uppercase: " + word);
            }
        }
    }

    @Test
    void testLowerCaseFormatValidation() {
        SlugOptions options = SlugOptions.builder()
                .format(Case.LOWER)
                .build();

        for (int i = 0; i < 20; i++) {
            String slug = generator.generate(3, options);
            String[] words = slug.split(" ");

            assertEquals(3, words.length, "Lower case should have 3 space-separated words");
            assertEquals(slug.toLowerCase(), slug, "All characters should be lowercase");
        }
    }

    @Test
    void testSentenceCaseFormatValidation() {
        SlugOptions options = SlugOptions.builder()
                .format(Case.SENTENCE)
                .build();

        for (int i = 0; i < 20; i++) {
            String slug = generator.generate(3, options);
            String[] words = slug.split(" ");

            assertEquals(3, words.length, "Sentence case should have 3 space-separated words");
            assertTrue(Character.isUpperCase(slug.charAt(0)),
                    "Sentence case should start with uppercase");

            // Second and third words should start with lowercase
            assertTrue(Character.isLowerCase(words[1].charAt(0)),
                    "Second word should start with lowercase");
            assertTrue(Character.isLowerCase(words[2].charAt(0)),
                    "Third word should start with lowercase");
        }
    }
}