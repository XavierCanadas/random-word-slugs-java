
import com.github.xaviercanadas.randomwordgenerator.util.Formatter;
import com.github.xaviercanadas.randomwordgenerator.model.Case;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Formatter class.
 */
public class FormatterTest {


        @Test
        void testFormatKebab() {
            List<String> words = Arrays.asList("happy", "little", "cat");
            String result = Formatter.format(words, Case.KEBAB);
            assertEquals("happy-little-cat", result);
        }

        @Test
        void testFormatKebabWithMixedCase() {
            List<String> words = Arrays.asList("Happy", "LITTLE", "CaT");
            String result = Formatter.format(words, Case.KEBAB);
            assertEquals("happy-little-cat", result);
        }

        @Test
        void testFormatCamel() {
            List<String> words = Arrays.asList("happy", "little", "cat");
            String result = Formatter.format(words, Case.CAMEL);
            assertEquals("happyLittleCat", result);
        }

        @Test
        void testFormatCamelWithMixedCase() {
            List<String> words = Arrays.asList("HAPPY", "LITTLE", "CAT");
            String result = Formatter.format(words, Case.CAMEL);
            assertEquals("happyLittleCat", result);
        }

        @Test
        void testFormatCamelSingleWord() {
            List<String> words = Collections.singletonList("cat");
            String result = Formatter.format(words, Case.CAMEL);
            assertEquals("cat", result);
        }

        @Test
        void testFormatTitle() {
            List<String> words = Arrays.asList("happy", "little", "cat");
            String result = Formatter.format(words, Case.TITLE);
            assertEquals("Happy Little Cat", result);
        }

        @Test
        void testFormatTitleWithMixedCase() {
            List<String> words = Arrays.asList("HAPPY", "little", "CaT");
            String result = Formatter.format(words, Case.TITLE);
            assertEquals("Happy Little Cat", result);
        }

        @Test
        void testFormatLower() {
            List<String> words = Arrays.asList("Happy", "Little", "Cat");
            String result = Formatter.format(words, Case.LOWER);
            assertEquals("happy little cat", result);
        }

        @Test
        void testFormatSentence() {
            List<String> words = Arrays.asList("happy", "little", "cat");
            String result = Formatter.format(words, Case.SENTENCE);
            assertEquals("Happy little cat", result);
        }

        @Test
        void testFormatSentenceWithMixedCase() {
            List<String> words = Arrays.asList("HAPPY", "LITTLE", "CAT");
            String result = Formatter.format(words, Case.SENTENCE);
            assertEquals("Happy little cat", result);
        }

        @Test
        void testFormatWithSingleWord() {
            List<String> words = Collections.singletonList("cat");

            assertEquals("cat", Formatter.format(words, Case.KEBAB));
            assertEquals("cat", Formatter.format(words, Case.CAMEL));
            assertEquals("Cat", Formatter.format(words, Case.TITLE));
            assertEquals("cat", Formatter.format(words, Case.LOWER));
            assertEquals("Cat", Formatter.format(words, Case.SENTENCE));
        }

        @Test
        void testFormatWithTwoWords() {
            List<String> words = Arrays.asList("big", "dog");

            assertEquals("big-dog", Formatter.format(words, Case.KEBAB));
            assertEquals("bigDog", Formatter.format(words, Case.CAMEL));
            assertEquals("Big Dog", Formatter.format(words, Case.TITLE));
            assertEquals("big dog", Formatter.format(words, Case.LOWER));
            assertEquals("Big dog", Formatter.format(words, Case.SENTENCE));
        }

        @Test
        void testFormatWithNullWords() {
            assertThrows(IllegalArgumentException.class, () -> {
                Formatter.format(null, Case.KEBAB);
            });
        }

        @Test
        void testFormatWithEmptyList() {
            assertThrows(IllegalArgumentException.class, () -> {
                Formatter.format(Collections.emptyList(), Case.KEBAB);
            });
        }

        @Test
        void testAllCasesWithRealExample() {
            List<String> words = Arrays.asList("awesome", "Java", "project");

            assertEquals("awesome-java-project", Formatter.format(words, Case.KEBAB));
            assertEquals("awesomeJavaProject", Formatter.format(words, Case.CAMEL));
            assertEquals("Awesome Java Project", Formatter.format(words, Case.TITLE));
            assertEquals("awesome java project", Formatter.format(words, Case.LOWER));
            assertEquals("Awesome java project", Formatter.format(words, Case.SENTENCE));
        }


}
