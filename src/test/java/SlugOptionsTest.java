/*
 * SlugOptionsTest.java
 * RandomWordGenerator
 *
 * Created by Xavier Cañadas on 22/11/2025
 * Copyright (c) 2025. All rights reserved.
 */

import com.github.xaviercanadas.randomwordgenerator.generator.SlugOptions;
import com.github.xaviercanadas.randomwordgenerator.model.Case;
import com.github.xaviercanadas.randomwordgenerator.model.Category;
import com.github.xaviercanadas.randomwordgenerator.model.PartsOfSpeech;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for SlugOptions and its Builder.
 */
class SlugOptionsTest {

    @Test
    void testBuilderWithDefaults() {
        SlugOptions options = SlugOptions.builder().build();

        assertNotNull(options);
        assertEquals(Case.KEBAB, options.getFormat());
        assertTrue(options.getPartsOfSpeech().isEmpty());
        assertTrue(options.getCategories().isEmpty());
    }

    @Test
    void testBuilderWithFormat() {
        SlugOptions options = SlugOptions.builder()
                .format(Case.CAMEL)
                .build();

        assertEquals(Case.CAMEL, options.getFormat());
    }

    @Test
    void testBuilderWithPartsOfSpeechVarargs() {
        SlugOptions options = SlugOptions.builder()
                .partsOfSpeech(PartsOfSpeech.ADJECTIVE, PartsOfSpeech.NOUN)
                .build();

        assertEquals(2, options.getPartsOfSpeech().size());
        assertEquals(PartsOfSpeech.ADJECTIVE, options.getPartsOfSpeech().get(0));
        assertEquals(PartsOfSpeech.NOUN, options.getPartsOfSpeech().get(1));
    }

    @Test
    void testBuilderWithPartsOfSpeechList() {
        List<PartsOfSpeech> parts = Arrays.asList(
                PartsOfSpeech.ADJECTIVE,
                PartsOfSpeech.ADJECTIVE,
                PartsOfSpeech.NOUN
        );

        SlugOptions options = SlugOptions.builder()
                .partsOfSpeech(parts)
                .build();

        assertEquals(3, options.getPartsOfSpeech().size());
    }

    @Test
    void testBuilderWithNounCategories() {
        SlugOptions options = SlugOptions.builder()
                .withNounCategories(Category.ANIMALS, Category.FOOD)
                .build();

        assertTrue(options.getCategories().containsKey(PartsOfSpeech.NOUN));
        assertEquals(2, options.getCategories().get(PartsOfSpeech.NOUN).size());
        assertTrue(options.getCategories().get(PartsOfSpeech.NOUN).contains(Category.ANIMALS));
        assertTrue(options.getCategories().get(PartsOfSpeech.NOUN).contains(Category.FOOD));
    }

    @Test
    void testBuilderWithAdjectiveCategories() {
        SlugOptions options = SlugOptions.builder()
                .withAdjectiveCategories(Category.COLOR, Category.SIZE)
                .build();

        assertTrue(options.getCategories().containsKey(PartsOfSpeech.ADJECTIVE));
        assertEquals(2, options.getCategories().get(PartsOfSpeech.ADJECTIVE).size());
    }

    @Test
    void testBuilderWithBothNounAndAdjectiveCategories() {
        SlugOptions options = SlugOptions.builder()
                .withNounCategories(Category.ANIMALS)
                .withAdjectiveCategories(Category.COLOR, Category.SIZE)
                .build();

        assertEquals(2, options.getCategories().size());
        assertTrue(options.getCategories().containsKey(PartsOfSpeech.NOUN));
        assertTrue(options.getCategories().containsKey(PartsOfSpeech.ADJECTIVE));
    }

    @Test
    void testBuilderWithGenericCategories() {
        SlugOptions options = SlugOptions.builder()
                .withCategories(PartsOfSpeech.NOUN, Category.ANIMALS, Category.FOOD)
                .build();

        assertTrue(options.getCategories().containsKey(PartsOfSpeech.NOUN));
        assertEquals(2, options.getCategories().get(PartsOfSpeech.NOUN).size());
    }

    @Test
    void testBuilderFluentAPI() {
        SlugOptions options = SlugOptions.builder()
                .partsOfSpeech(PartsOfSpeech.ADJECTIVE, PartsOfSpeech.ADJECTIVE, PartsOfSpeech.NOUN)
                .withNounCategories(Category.ANIMALS)
                .withAdjectiveCategories(Category.COLOR)
                .format(Case.TITLE)
                .build();

        assertEquals(3, options.getPartsOfSpeech().size());
        assertEquals(Case.TITLE, options.getFormat());
        assertEquals(2, options.getCategories().size());
    }

    @Test
    void testOptionsAreImmutable() {
        SlugOptions options = SlugOptions.builder()
                .partsOfSpeech(PartsOfSpeech.ADJECTIVE, PartsOfSpeech.NOUN)
                .build();

        assertThrows(UnsupportedOperationException.class, () -> {
            options.getPartsOfSpeech().add(PartsOfSpeech.NOUN);
        });

        assertThrows(UnsupportedOperationException.class, () -> {
            options.getCategories().put(PartsOfSpeech.NOUN, Arrays.asList(Category.ANIMALS));
        });
    }

    @Test
    void testBuilderCanBeReused() {
        SlugOptions.Builder builder = SlugOptions.builder()
                .format(Case.KEBAB);

        SlugOptions options1 = builder
                .withNounCategories(Category.ANIMALS)
                .build();

        SlugOptions options2 = builder
                .withNounCategories(Category.FOOD)
                .build();

        // Both should be valid
        assertNotNull(options1);
        assertNotNull(options2);
    }

    @Test
    void testComplexConfiguration() {
        SlugOptions options = SlugOptions.builder()
                .partsOfSpeech(
                        PartsOfSpeech.ADJECTIVE,
                        PartsOfSpeech.ADJECTIVE,
                        PartsOfSpeech.NOUN,
                        PartsOfSpeech.ADJECTIVE,
                        PartsOfSpeech.NOUN
                )
                .withNounCategories(Category.ANIMALS, Category.FOOD, Category.PLACE)
                .withAdjectiveCategories(Category.COLOR, Category.SIZE, Category.PERSONALITY)
                .format(Case.CAMEL)
                .build();

        assertEquals(5, options.getPartsOfSpeech().size());
        assertEquals(3, options.getCategories().get(PartsOfSpeech.NOUN).size());
        assertEquals(3, options.getCategories().get(PartsOfSpeech.ADJECTIVE).size());
        assertEquals(Case.CAMEL, options.getFormat());
    }

    @Test
    void testEmptyConfiguration() {
        SlugOptions options = SlugOptions.builder().build();

        assertNotNull(options.getPartsOfSpeech());
        assertNotNull(options.getCategories());
        assertNotNull(options.getFormat());
    }
}
