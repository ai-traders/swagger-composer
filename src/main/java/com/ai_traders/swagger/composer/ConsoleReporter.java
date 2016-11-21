package com.ai_traders.swagger.composer;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class ConsoleReporter {
    private final PrintStream writer;

    public ConsoleReporter(){
        writer = System.out;
    }
    public ConsoleReporter(PrintStream s){
        writer = s;
    }

    public void report(MergedSwagger swagger) {
        if(swagger.success())
            writer.println("MERGE SUCCESSFUL");
        else {
            writer.println("MERGE FAILED");
            writer.println("");
            writer.println("CONFLICTS:");
            Map<String, List<ConflictItem>> conflicts = swagger.getConflictsByPairs();
            for(Map.Entry<String, List<ConflictItem>> set : conflicts.entrySet()){
                writer.println("\t" + set.getKey());
                for(ConflictItem conflict : set.getValue()) {
                    String type = conflict.getType();
                    writer.println("\t\t" + type + " " + conflict.getLeft().getKey());
                }
            }
        }
    }
}
