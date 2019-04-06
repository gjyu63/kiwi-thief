package client;

import java.io.*;
import java.util.List;

public class Writer {
    public static void writeFile(List<String> lst) throws IOException{
        File fout = new File("out.txt");
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        for (String x : lst) {
            if (x != null)
                bw.write(x);
            else
                bw.write("xx");
            bw.newLine();
        }
        bw.close();
    }
}

