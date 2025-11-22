# Random Word Slug Generator - Java

A Java library for generating random, human-readable word combinations with customizable formatting and category
filtering. Perfect for creating unique identifiers, usernames, project names, or any scenario requiring memorable random
text.

## Description

Random Word Slugs generates combinations like `"happy-little-cat"`, `"braveBlueElephant"`, or `"Clever Fast Robot"` by
intelligently combining adjectives and nouns from a curated database of over 700 words across 30+ categories.
With the default configuration it can produce almost 30 million unique combinations.

### Features

- 🎲 **Random Generation** - Generate memorable word combinations
- 📝 **Multiple Formats** - kebab-case, camelCase, Title Case, lower case, Sentence case
- 🏷️ **Category Filtering** - Filter by animals, colors, professions, technology, and more
- 🎯 **Flexible Patterns** - Customize word order and parts of speech
- 📊 **Combinatorics** - Calculate total possible unique combinations

## Installation

### Using JitPack

Add the JitPack repository and dependency to your `build.gradle.kts`:

```kotlin
repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("com.github.XavierCanadas:random-word-slugs-java:v1.0.0")
}
```

Or if using Gradle Groovy (`build.gradle`):

```groovy
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.XavierCanadas:random-word-slugs-java:v1.0.0'
}
```

For Maven (`pom.xml`):

```xml

<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
<dependency>
    <groupId>com.github.XavierCanadas</groupId>
    <artifactId>random-word-slugs-java</artifactId>
    <version>v1.0.0</version>
</dependency>
</dependencies>
```

## Usage

### Basic Usage

```java
import com.github.xaviercanadas.randomwordslugs.generator.SlugGenerator;

public class Main {
    public static void main(String[] args) {
        SlugGenerator generator = new SlugGenerator();

        // Generate with defaults (3 words, adjective-adjective-noun, kebab-case)
        String slug = generator.generate();
        System.out.println(slug);
        // Output: "happy-little-cat"
    }
}
```

### Specify Number of Words

```java
SlugGenerator generator = new SlugGenerator();

String slug2 = generator.generate(2);  // "happy-cat"
String slug4 = generator.generate(4);  // "brave-big-blue-elephant"
String slug5 = generator.generate(5);  // "clever-fast-red-funny-robot"
```

### Custom Formatting

```java
import com.github.xaviercanadas.randomwordslugs.model.Case;
import com.github.xaviercanadas.randomwordslugs.generator.SlugOptions;

SlugGenerator generator = new SlugGenerator();

// camelCase
SlugOptions camelOptions = SlugOptions.builder()
        .format(Case.CAMEL)
        .build();
String camelSlug = generator.generate(3, camelOptions);
// Output: "happyLittleCat"

// Title Case
SlugOptions titleOptions = SlugOptions.builder()
        .format(Case.TITLE)
        .build();
String titleSlug = generator.generate(3, titleOptions);
// Output: "Happy Little Cat"

// Sentence case
SlugOptions sentenceOptions = SlugOptions.builder()
        .format(Case.SENTENCE)
        .build();
String sentenceSlug = generator.generate(3, sentenceOptions);
// Output: "Happy little cat"

// lower case
SlugOptions lowerOptions = SlugOptions.builder()
        .format(Case.LOWER)
        .build();
String lowerSlug = generator.generate(3, lowerOptions);
// Output: "happy little cat"
```

### Category Filtering

```java
import com.github.xaviercanadas.randomwordslugs.model.Category;

SlugGenerator generator = new SlugGenerator();

// Generate animal-themed slugs
SlugOptions animalOptions = SlugOptions.builder()
        .withNounCategories(Category.ANIMALS)
        .withAdjectiveCategories(Category.COLOR, Category.SIZE)
        .build();
String animalSlug = generator.generate(3, animalOptions);
// Output: "blue-big-elephant"

// Technology-themed slugs
SlugOptions techOptions = SlugOptions.builder()
        .withNounCategories(Category.TECHNOLOGY)
        .withAdjectiveCategories(Category.CONDITION)
        .format(Case.CAMEL)
        .build();
String techSlug = generator.generate(3, techOptions);
// Output: "fastPowerfulProcessor"
```

**Available Categories:**

- **Nouns**: ANIMALS, FOOD, PLACE, PEOPLE, FAMILY, PROFESSION, TECHNOLOGY, TRANSPORTATION, SPORTS, MEDIA, EDUCATION,
  BUSINESS, HEALTH, RELIGION, SCIENCE, TIME, THING
- **Adjectives**: APPEARANCE, PERSONALITY, CONDITION, SIZE, COLOR, SHAPES, QUANTITY, TASTE, TOUCH, SOUNDS, TIME

### Custom Word Patterns

```java
import com.github.xaviercanadas.randomwordslugs.model.PartsOfSpeech;

SlugGenerator generator = new SlugGenerator();

// Custom pattern: noun-adjective-noun
SlugOptions customPattern = SlugOptions.builder()
        .partsOfSpeech(PartsOfSpeech.NOUN, PartsOfSpeech.ADJECTIVE, PartsOfSpeech.NOUN)
        .build();
String customSlug = generator.generate(customPattern);
// Output: "cat-happy-dog"

// Another pattern: adjective-adjective-adjective-noun
SlugOptions manyAdjectives = SlugOptions.builder()
        .partsOfSpeech(
                PartsOfSpeech.ADJECTIVE,
                PartsOfSpeech.ADJECTIVE,
                PartsOfSpeech.ADJECTIVE,
                PartsOfSpeech.NOUN
        )
        .build();
String descriptiveSlug = generator.generate(manyAdjectives);
// Output: "brave-clever-fast-robot"
```

### Calculate Possible Combinations

```java
SlugGenerator generator = new SlugGenerator();

// Total possible slugs with default settings
long total = generator.totalUniqueSlugs(3, null);
System.out.println("Total possible 3-word slugs: "+total );

// With category filtering
SlugOptions filtered = SlugOptions.builder()
        .withNounCategories(Category.ANIMALS)
        .withAdjectiveCategories(Category.COLOR)
        .build();
long filteredTotal = generator.totalUniqueSlugs(3, filtered);
System.out.println("Filtered combinations: "+filteredTotal);
```

## Project Structure

```
random-word-slugs-java/
├── src/
│   ├── main/
│   │   └── java/com/randomwords/
│   │       ├── Main.java                    # Example usage and demos
│   │       ├── data/
│   │       │   └── WordDatabase.java        # Word storage (700+ words)
│   │       ├── generator/
│   │       │   ├── SlugGenerator.java       # Main generator logic
│   │       │   └── SlugOptions.java         # Configuration builder
│   │       ├── model/
│   │       │   ├── Case.java                # Format types enum
│   │       │   ├── Category.java            # Word categories enum
│   │       │   ├── PartsOfSpeech.java       # Grammar types enum
│   │       │   └── Word.java                # Word data model
│   │       └── util/
│   │           └── Formatter.java           # Text formatting utilities
│   └── test/
│       └── java/com/randomwords/
│           ├── data/
│           │   └── WordDatabaseTest.java
│           ├── generator/
│           │   ├── SlugGeneratorTest.java
│           │   └── SlugOptionsTest.java
│           ├── model/
│           │   └── WordTest.java
│           └── util/
│               └── FormatterTest.java
├── build.gradle.kts                         # Gradle build configuration
├── jitpack.yml                              # JitPack configuration
├── LICENSE                                  # MIT License
└── README.md
```

### Key Components

- **`SlugGenerator`** - Main entry point for generating slugs
- **`SlugOptions`** - Builder pattern for configuring generation options
- **`WordDatabase`** - Contains 400+ nouns and 300+ adjectives across 30+ categories
- **`Formatter`** - Handles text case transformations
- **`Word`** - Immutable data model for words with categories
- **Model Classes** - Type-safe enums for categories, formats, and parts of speech

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

### Original Work Attribution

This library is a Java port inspired by the TypeScript [random-word-slugs](https://github.com/nas5w/random-word-slugs)
library by Nick Scialli, also licensed under MIT. The original word database and core concept are derived from that
project.

## Acknowledgments

- **Original Concept**: [random-word-slugs](https://github.com/nas5w/random-word-slugs) by Nick Scialli - The
  TypeScript library that inspired this Java implementation
- **Word Database**: Curated word lists adapted from the original project
- **Project Context**: Developed as part of a Computer Science final degree project (TFG) at Universitat Pompeu Fabra

### Requirements

- **Java**: 21 or higher.
