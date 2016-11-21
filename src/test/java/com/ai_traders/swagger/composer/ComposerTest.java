package com.ai_traders.swagger.composer;

import io.swagger.models.Swagger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.ai_traders.swagger.composer.TestUtils.loadString;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;


public class ComposerTest {
    Composer composer;

    @Before
    public void setUp() throws Exception {
        composer = new Composer();
    }

    @After
    public void tearDown() throws Exception {

    }

    InputSwaggers loadSimpleCase() throws IOException {
        return new InputSwaggers(
                loadString("simple/master.yaml"),
                loadString("simple/part1.yaml"));
    }

    @Test
    public void shouldMergeSimpleCase() throws Exception {
        InputSwaggers input = loadSimpleCase();
        Swagger output = composer.merge(input);
        assertNotNull(output.getPath("/v1/products"));
        assertNotNull(output.getPath("/v2/products"));
        assertNotNull(output.getDefinitions().get("Product"));
        assertNotNull(output.getDefinitions().get("Error"));
    }

}