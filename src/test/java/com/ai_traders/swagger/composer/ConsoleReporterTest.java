package com.ai_traders.swagger.composer;

import io.swagger.models.Path;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class ConsoleReporterTest {
    private ConsoleReporter reporter;
    private PrintStream ps;
    private ByteArrayOutputStream baos;

    @Before
    public void SetUp() {
        baos = new ByteArrayOutputStream();
        ps = new PrintStream(baos);
        reporter = new ConsoleReporter(ps);
    }

    @Test
    public void reportShouldPrintSuccessWhenNoConflicts() throws Exception {
        MergedSwagger swagger = new MergedSwagger(null,null,null,new ArrayList<ConflictItem>());
        reporter.report(swagger);
        assertThat(getString(), containsString("SUCCESS"));
    }

    @Test
    public void reportShouldPrintFailureAndConflictsWhenConflicts() throws Exception {
        ArrayList<ConflictItem> conflicts = new ArrayList<>();
        conflicts.add(new ConflictItem<Path>(
                new SwaggerItem<Path>("left","/mypath",new Path()),
                new SwaggerItem<Path>("right","/mypath",new Path())));
        MergedSwagger swagger = new MergedSwagger(null,null,null, conflicts);
        reporter.report(swagger);
        assertThat(getString(), containsString("FAILED"));
        assertThat(getString(), containsString("CONFLICTS"));
        assertThat(getString(), containsString("\tleft | right"));
        assertThat(getString(), containsString("\t\tPath /mypath"));
    }

    private String getString() {
        return new String(baos.toByteArray(), StandardCharsets.UTF_8);
    }

}