package cityLst;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Loader {
    private final static int expLimit = 10;
    private final static String csvSplitBy = ",";
    private final static String fileName = "cities.csv";
    public List<String> load2memory (boolean experiment) {
        List<String> ret = new ArrayList<String>();
        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(classLoader.getResourceAsStream(fileName)));
            String line = bufferedReader.readLine();
            int counter = 0;
            while (line != null) {
                counter++;
                if (experiment && counter > expLimit) break;
                // scan each line to make sure it only contains ascii character
                if (!line.matches("\\A\\p{ASCII}*\\z")) {
                    line = bufferedReader.readLine();
                    continue;
                }
                String[] splitted = line.split(csvSplitBy);
                StringBuilder sb = new StringBuilder();
                sb.append(splitted[0]);
                sb.append(", ");
                sb.append(splitted[2]);
                sb.append(", ");
                sb.append(splitted[1]);
                ret.add(sb.toString());
                // read next line
                line = bufferedReader.readLine();
            }
            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
