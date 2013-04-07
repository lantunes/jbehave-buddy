package org.bigtesting.jbehave.buddy.util;

import java.io.FileWriter;
import java.io.PrintWriter;

public class ExceptionFileWriter {

    public static void writeException(Throwable e) {
        
        try {
            
            FileWriter fw = new FileWriter("errors.log", true);
            PrintWriter pw = new PrintWriter(fw);
            e.printStackTrace(pw);
            fw.close();
            pw.flush();
            pw.close();
            
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
