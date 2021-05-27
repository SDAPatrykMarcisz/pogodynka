package pl.sdacademy.pogodynka.utils;

import java.io.InputStream;
import java.util.Scanner;

public class ReadFileToStringUtils {

    // get a file from the resources folder
    // works everywhere, IDEA, unit test and JAR file.
    public static InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ReadFileToStringUtils readFileToStringUtils = new ReadFileToStringUtils();
        ClassLoader classLoader = readFileToStringUtils.getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }

    public static String getFileFromResourceAsString(String filename){
        InputStream fileFromResourceAsStream = ReadFileToStringUtils.getFileFromResourceAsStream(filename);
        Scanner s = new Scanner(fileFromResourceAsStream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

}
