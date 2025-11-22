/*
 * SlugOptions.java
 * RandomWordGenerator
 *
 * Created by Xavier Cañadas on 22/11/2025
 * Copyright (c) 2025. All rights reserved.
 */

package com.github.xaviercanadas.randomwordslugs.generator;

import com.github.xaviercanadas.randomwordslugs.model.Case;
import com.github.xaviercanadas.randomwordslugs.model.Category;
import com.github.xaviercanadas.randomwordslugs.model.PartsOfSpeech;

import java.util.*;

/**
 * Configuration options for slug generation.
 * Uses builder pattern for flexible configuration.
 */
public class SlugOptions {
    private final List<PartsOfSpeech> partsOfSpeech;
    private final Map<PartsOfSpeech, List<Category>> categories;
    private final Case format;

    private SlugOptions(Builder builder) {
        this.partsOfSpeech = builder.partsOfSpeech;
        this.categories = builder.categories;
        this.format = builder.format;
    }

    public List<PartsOfSpeech> getPartsOfSpeech() {
        return partsOfSpeech;
    }

    public Map<PartsOfSpeech, List<Category>> getCategories() {
        return categories;
    }

    public Case getFormat() {
        return format;
    }

    /**
     * Creates a new builder for SlugOptions.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for constructing SlugOptions with fluent API.
     */
    public static class Builder {
        private List<PartsOfSpeech> partsOfSpeech = new ArrayList<>();
        private Map<PartsOfSpeech, List<Category>> categories = new EnumMap<>(PartsOfSpeech.class);
        private Case format = Case.KEBAB;

        /**
         * Sets the parts of speech pattern for word selection.
         * Example: [ADJECTIVE, ADJECTIVE, NOUN] for "happy-little-cat"
         */
        public Builder partsOfSpeech(PartsOfSpeech... parts) {
            this.partsOfSpeech = new ArrayList<>(Arrays.asList(parts));
            return this;
        }

        /**
         * Sets the parts of speech pattern from a list.
         */
        public Builder partsOfSpeech(List<PartsOfSpeech> parts) {
            this.partsOfSpeech = new ArrayList<>(parts);
            return this;
        }

        /**
         * Adds category filters for a specific part of speech.
         * Only words matching at least one of these categories will be selected.
         */
        public Builder withCategories(PartsOfSpeech partOfSpeech, Category... categories) {
            this.categories.put(partOfSpeech, Arrays.asList(categories));
            return this;
        }

        /**
         * Adds category filters for nouns.
         */
        public Builder withNounCategories(Category... categories) {
            return withCategories(PartsOfSpeech.NOUN, categories);
        }

        /**
         * Adds category filters for adjectives.
         */
        public Builder withAdjectiveCategories(Category... categories) {
            return withCategories(PartsOfSpeech.ADJECTIVE, categories);
        }

        /**
         * Sets the output format (default: KEBAB).
         */
        public Builder format(Case format) {
            this.format = format;
            return this;
        }

        /**
         * Builds the SlugOptions instance.
         */
        public SlugOptions build() {
            // Create a new builder with immutable copies for the SlugOptions instance
            Builder immutableBuilder = new Builder();
            immutableBuilder.partsOfSpeech = List.copyOf(this.partsOfSpeech);
            immutableBuilder.categories = Collections.unmodifiableMap(new EnumMap<>(this.categories));
            immutableBuilder.format = this.format;

            return new SlugOptions(immutableBuilder);
        }
    }
}
