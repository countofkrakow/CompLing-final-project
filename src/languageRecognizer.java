import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Chris on 5/27/2015.
 */
public class languageRecognizer {
    public static final boolean WORD_MODE = true;

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.print("Input number of languages to parse: ");
        int langCount = input.nextInt();
        input.nextLine();

        String[] languages = new String[langCount];
        String[] dictionaryFile = new String[langCount];
        double[] percentMatch = new double[langCount];
        for (int i = 0; i < langCount; i++) {
            System.out.print("enter the language name: ");
            languages[i] = input.nextLine();
            System.out.print("enter this language's index name: ");
            dictionaryFile[i] = input.nextLine();
        }
        System.out.println(); // newline

        while(true) {
            System.out.print("Input file to recognize <q to quit>: ");
            String fileName = input.nextLine();
            if (fileName.equals("q"))
                break;
            for (int i = 0; i < langCount; i++) {
                percentMatch[i] = percentMatch(dictionaryFile[i], fileName);
            }

            System.out.println("Language percent match");
            for (int i = 0; i < langCount; i++) {
                System.out.println("    " + languages[i] + ": " + String.format("%.1f", percentMatch[i]) + "%");
            }
        }

    }

    public static double percentMatch(String indexName, String testFileName) {
        Map<String, Integer> counts = new HashMap<String, Integer>();

        int totalWords = 0;
        int recognizedWords = 0;
        try {
            // create the index in memory
            Scanner indexReader = new Scanner(new File(indexName));
            while (indexReader.hasNextLine()) {
                String[] line = indexReader.nextLine().split("\\s+");
                if (line.length == 2) {
                    // System.out.println(Arrays.toString(line));
                    String word = line[0];
                    int count = Integer.parseInt(line[1]);
                    counts.put(word, count);
                }
            }

            totalWords = 0;
            recognizedWords = 0;
            Scanner freader = new Scanner(new File(testFileName));
            if (WORD_MODE) {
                while (freader.hasNextLine()) {
                    String[] words = freader.nextLine().replaceAll("[^a-zA-Z ]", "").toLowerCase().split(" ");
                    totalWords += words.length;

                    for (String word : words) {
                        if (counts.containsKey(word)) {
                            recognizedWords++;
                        }
                    }
                }
            } else {
                freader.useDelimiter("");
                while (freader.hasNext()) {
                    String word = freader.next();
                    totalWords++;

                    if (counts.containsKey(word)) {
                        recognizedWords++;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("file " + testFileName + " not found.");
            System.exit(1);
        }
        return ((double) recognizedWords) / totalWords * 100;
    }
}
