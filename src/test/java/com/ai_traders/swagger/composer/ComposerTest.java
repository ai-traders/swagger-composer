package com.ai_traders.swagger.composer;

import io.swagger.models.Swagger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.ai_traders.swagger.composer.TestUtils.loadString;
import static com.ai_traders.swagger.composer.TestUtils.loadSwaggerSource;
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
                loadSwaggerSource("simple/master.yaml"),
                loadSwaggerSource("simple/part1.yaml"));
    }

    @Test
    public void shouldMergeSimpleCase() throws Exception {
        InputSwaggers input = loadSimpleCase();
        MergedSwagger outputMerged = composer.merge(input);
        Swagger output = outputMerged.getMerged();
        assertNotNull(output.getPath("/v1/products"));
        assertNotNull(output.getPath("/v2/products"));
        assertNotNull(output.getDefinitions().get("Product"));
        assertNotNull(output.getDefinitions().get("Error"));
        assertThat(outputMerged.getConflicts().size(),is(0));
    }

    InputSwaggers loadBasePathCase() throws IOException {
        return new InputSwaggers(
                loadSwaggerSource("basePath/master.yaml"),
                loadSwaggerSource("basePath/part1.yaml"));
    }

    @Test
    public void shouldMergeWhenPartHasBasePath() throws Exception {
        InputSwaggers input = loadBasePathCase();
        MergedSwagger outputMerged = composer.merge(input);
        Swagger output = outputMerged.getMerged();
        assertNotNull(output.getPath("/v1/products"));
        assertNotNull(output.getPath("/v2/products"));
        assertNotNull(output.getDefinitions().get("Product"));
        assertNotNull(output.getDefinitions().get("Error"));
        assertThat(outputMerged.getConflicts().size(),is(0));
    }

    InputSwaggers loadCompletePartCase() throws IOException {
        return new InputSwaggers(
                loadSwaggerSource("completePart/master.yaml"),
                loadSwaggerSource("completePart/part1.yaml"));
    }

    @Test
    public void shouldMergeWhenPartHasResponsesParametersAndDefinitions() throws Exception {
        InputSwaggers input = loadCompletePartCase();
        MergedSwagger outputMerged = composer.merge(input);
        Swagger output = outputMerged.getMerged();
        assertNotNull(output.getPath("/persons"));
        assertTrue(output.getResponses().containsKey("PersonsOK"));
        assertNotNull(output.getParameter("Size"));
        assertNotNull(output.getDefinitions().get("ArrayOfPersons"));
        assertThat(outputMerged.getConflicts().size(),is(0));
    }

    InputSwaggers loadNoConflictEqualCase() throws IOException {
        return new InputSwaggers(
                loadSwaggerSource("noConflictEqual/master.yaml"),
                loadSwaggerSource("noConflictEqual/part1.yaml"),
                loadSwaggerSource("noConflictEqual/part2.yaml"));
    }

    @Test
    public void shouldMergeWhenPartialsHaveEqualElements() throws Exception {
        InputSwaggers input = loadNoConflictEqualCase();
        MergedSwagger outputMerged = composer.merge(input);
        Swagger output = outputMerged.getMerged();
        assertNotNull(output.getPath("/persons"));
        assertTrue(output.getResponses().containsKey("PersonsOK"));
        assertNotNull(output.getParameter("Size"));
        assertNotNull(output.getDefinitions().get("ArrayOfPersons"));
        assertThat(outputMerged.getConflicts().size(),is(0));
    }

    InputSwaggers loadConflictCase() throws IOException {
        return new InputSwaggers(
                loadSwaggerSource("conflict/master.yaml"),
                loadSwaggerSource("conflict/part1.yaml"),
                loadSwaggerSource("conflict/part2.yaml"));
    }

    @Test
    public void shouldDetectConflictInPartials() throws Exception {
        InputSwaggers input = loadConflictCase();
        MergedSwagger outputMerged = composer.merge(input);
        assertThat(outputMerged.getConflicts().size(),is(2));
    }
}