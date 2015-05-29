import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Created by Chris on 5/28/2015.
 */
public class ByteCounter {
    public static void main(String[] args) {
        Map<String, Integer> wordsToCounts = new TreeMap<String, Integer>();
        Scanner input = new Scanner(System.in);
        System.out.print("Input word count filename: ");
        String fileName = input.nextLine();
        System.out.print("Input data directory name: ");
        String dataDirectoryName = input.nextLine();

        File directory = new File(dataDirectoryName);

        addWordsToSet(directory, wordsToCounts);

        // print it to the file
        try {
            // for output
            PrintWriter outputFile = new PrintWriter(fileName);
            for (String word : wordsToCounts.keySet()) {
                // only do counts larger than 1
                if (wordsToCounts.get(word) > 1) {
                    outputFile.println(word + " " + wordsToCounts.get(word));
                    // System.out.println(word + ": " + wordsToCounts.get(word));
                }
            }
            outputFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("a file reading error occurred");
            System.exit(1);
        }
    }

    public static void addWordsToSet(File folder, Map<String, Integer> counts) {
        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isFile()) {
                processFile(fileEntry, counts);
            }
        }
    }

    public static void processFile(File book, Map<String, Integer> counts) {

        try{
            Scanner freader = new Scanner(book);
            freader.useDelimiter("");
            while (freader.hasNext()) {
                String word = freader.next();
                if (counts.containsKey(word)) {
                    counts.put(word, counts.get(word) + 1);
                } else if (!word.equals("")) {
                    counts.put(word, 1);
                }
            }
        } catch (FileNotFoundException e) {
            // don't do anything lol
        }

    }
}
