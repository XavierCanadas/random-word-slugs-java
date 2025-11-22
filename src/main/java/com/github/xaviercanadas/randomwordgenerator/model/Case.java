/*
 * Case.java
 * RandomWordGenerator
 *
 * Created by Xavier Cañadas on 22/11/2025
 * Copyright (c) 2025. All rights reserved.
 */

package com.github.xaviercanadas.randomwordgenerator.model;

public enum Case {
    /**
     * kebab-case: words separated by hyphens, all lowercase
     * Example: "happy-little-cat"
     */
    KEBAB,

    /**
     * camelCase: first word lowercase, subsequent words capitalized, no separators
     * Example: "happyLittleCat"
     */
    CAMEL,

    /**
     * Title Case: Each Word Capitalized, separated by spaces
     * Example: "Happy Little Cat"
     */
    TITLE,

    /**
     * lower case: all lowercase, separated by spaces
     * Example: "happy little cat"
     */
    LOWER,

    /**
     * Sentence case: First word capitalized, rest lowercase, separated by spaces
     * Example: "Happy little cat"
     */
    SENTENCE
}
