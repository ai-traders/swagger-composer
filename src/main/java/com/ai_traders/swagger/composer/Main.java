package com.ai_traders.swagger.composer;

import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import io.swagger.util.Json;
import org.apache.commons.cli.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CommandLineParser parser = new DefaultParser();

        Options options = new Options();
        options.addOption("m","master",true,"Location of master swagger JSON or YAML. Can be an url or file path");
        options.addOption("o","output",true,"Location of merged output file swagger.json");

        try {
            CommandLine line = parser.parse( options, args );
            if(!line.hasOption("master"))
            {
                System.out.println("--master [FILE] option is required");
                System.exit(1);
            }
            String masterLocation = line.getOptionValue("master");

            Composer composer = new Composer();
            ConsoleReporter reporter = new ConsoleReporter();
            List<SwaggerSource> partials = new ArrayList<>();
            List<String> arguments = line.getArgList();
            for(String location : arguments){
                if(!location.equals(masterLocation))
                    partials.add(new SwaggerSource(location));
            }
            InputSwaggers swaggers = new InputSwaggers(new SwaggerSource(masterLocation),partials);
            MergedSwagger output = composer.merge(swaggers);

            reporter.report(output);
            if(output.success()) {
                String outputPath = line.getOptionValue("output","swagger.json");
                String swaggerString = Json.pretty(output.getMerged());
                try {
                    try(  PrintWriter out = new PrintWriter(outputPath)  ){
                        out.println(swaggerString);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    System.exit(5);
                }
            }
            else {
                System.exit(10);
            }

        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(3);
        }

    }
}
