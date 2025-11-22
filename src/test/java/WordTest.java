
import com.github.xaviercanadas.randomwordgenerator.model.Category;
import com.github.xaviercanadas.randomwordgenerator.model.Word;


import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Word class.
 */
class WordTest {

    @Test
    void testCreateWordWithSingleCategory() {
        Word word = new Word("cat", Category.ANIMALS);

        assertEquals("cat", word.getWord());
        assertEquals(1, word.getCategories().size());
        assertTrue(word.getCategories().contains(Category.ANIMALS));
    }

    @Test
    void testCreateWordWithMultipleCategories() {
        Word word = new Word("airport", Category.TRANSPORTATION, Category.PLACE);

        assertEquals("airport", word.getWord());
        assertEquals(2, word.getCategories().size());
        assertTrue(word.getCategories().contains(Category.TRANSPORTATION));
        assertTrue(word.getCategories().contains(Category.PLACE));
    }

    @Test
    void testCreateWordWithNullWord() {
        assertThrows(NullPointerException.class, () -> {
            new Word(null, Category.ANIMALS);
        });
    }

    @Test
    void testCreateWordWithNoCategories() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Word("test");
        });
    }

    @Test
    void testHasAnyCategoryWithMatchingSingleCategory() {
        Word word = new Word("cat", Category.ANIMALS);
        List<Category> targets = Collections.singletonList(Category.ANIMALS);

        assertTrue(word.hasAnyCategory(targets));
    }

    @Test
    void testHasAnyCategoryWithMatchingMultipleCategories() {
        Word word = new Word("airport", Category.TRANSPORTATION, Category.PLACE);
        List<Category> targets = Collections.singletonList(Category.PLACE);

        assertTrue(word.hasAnyCategory(targets));
    }

    @Test
    void testHasAnyCategoryWithNoMatch() {
        Word word = new Word("cat", Category.ANIMALS);
        List<Category> targets = Collections.singletonList(Category.FOOD);

        assertFalse(word.hasAnyCategory(targets));
    }

    @Test
    void testHasAnyCategoryWithEmptyList() {
        Word word = new Word("cat", Category.ANIMALS);
        List<Category> targets = Collections.emptyList();

        assertTrue(word.hasAnyCategory(targets));
    }

    @Test
    void testHasAnyCategoryWithNull() {
        Word word = new Word("cat", Category.ANIMALS);

        assertTrue(word.hasAnyCategory(null));
    }

    @Test
    void testHasAnyCategoryWithMultipleTargets() {
        Word word = new Word("teacher", Category.PROFESSION, Category.EDUCATION);
        List<Category> targets = Arrays.asList(Category.EDUCATION, Category.PEOPLE);

        assertTrue(word.hasAnyCategory(targets));
    }

    @Test
    void testCategoriesListIsUnmodifiable() {
        Word word = new Word("cat", Category.ANIMALS);

        assertThrows(UnsupportedOperationException.class, () -> {
            word.getCategories().add(Category.FOOD);
        });
    }

    @Test
    void testEqualsAndHashCode() {
        Word word1 = new Word("cat", Category.ANIMALS);
        Word word2 = new Word("cat", Category.ANIMALS);
        Word word3 = new Word("dog", Category.ANIMALS);
        Word word4 = new Word("cat", Category.FOOD);

        assertEquals(word1, word2);
        assertEquals(word1.hashCode(), word2.hashCode());

        assertNotEquals(word1, word3);
        assertNotEquals(word1, word4);
    }

    @Test
    void testToString() {
        Word word = new Word("cat", Category.ANIMALS);
        String result = word.toString();

        assertTrue(result.contains("cat"));
        assertTrue(result.contains("ANIMALS"));
    }
}