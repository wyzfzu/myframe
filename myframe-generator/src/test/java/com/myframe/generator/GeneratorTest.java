package com.myframe.generator;

import org.junit.Test;

public class GeneratorTest {

    @Test
    public void testGenerate() throws Exception {
        Generator generator = new Generator("generatorConfig.xml");
        generator.generate();
    }
}