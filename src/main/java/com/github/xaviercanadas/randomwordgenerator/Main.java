/*
 * Main.java
 * RandomWordGenerator
 *
 * Created by Xavier Cañadas on 22/11/2025
 * Copyright (c) 2025. All rights reserved.
 */

package com.github.xaviercanadas.randomwordgenerator;

import com.github.xaviercanadas.randomwordgenerator.generator.SlugGenerator;
import com.github.xaviercanadas.randomwordgenerator.generator.SlugOptions;
import com.github.xaviercanadas.randomwordgenerator.model.Case;
import com.github.xaviercanadas.randomwordgenerator.model.Category;


public class Main {
    public static void main(String[] args) {
        SlugGenerator generator = new SlugGenerator();

        // Simple usage
        System.out.println(generator.generate());  // "happy-little-cat"

        // With categories
        SlugOptions animalOptions = SlugOptions.builder()
                .withNounCategories(Category.ANIMALS)
                .withAdjectiveCategories(Category.COLOR)
                .format(Case.TITLE)
                .build();

        System.out.println(generator.generate(3, animalOptions));  // "Blue Happy Cat"

        // Calculate possibilities
        long combinations = generator.totalUniqueSlugs(3, null);
        System.out.println("Total possible slugs: " + combinations);
    }
}