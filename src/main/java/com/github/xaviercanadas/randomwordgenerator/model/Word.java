/*
 * Word.java
 * RandomWordGenerator
 *
 * Created by Xavier Cañadas on 22/11/2025
 * Copyright (c) 2025. All rights reserved.
 */

package com.github.xaviercanadas.randomwordgenerator.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Word {
    private final String word;
    private final List<Category> categories;

    public Word(String word, Category... categories) {
        this.word = Objects.requireNonNull(word, "word cannot be null");
        this.categories = Collections.unmodifiableList(Arrays.asList(categories));

        if (this.categories.isEmpty()) {
            throw new IllegalArgumentException("word must contain at least one category");
        }
    }

    public String getWord() {
        return word;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public boolean hasAnyCategory(List<Category> targetCategories) {
        if (targetCategories == null || targetCategories.isEmpty()) {
            return true;
        }
        for (Category category : targetCategories) {
            if (this.categories.contains(category)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Word wordObj = (Word) obj;
        return this.word.equals(wordObj.word) &&  this.categories.equals(wordObj.categories);
    }
    @Override
    public int hashCode() {
        return Objects.hash(word, categories);
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", categories=" + categories +
                '}';
    }
}
