import com.opencsv.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;


public class MyCSVReader {
    public static void main(String[] args) {
        read();
    }
    public static List<List<String>> read() {
        String srcPath = "src/main/resources/maven/issue.csv";
        String charset = "utf-8";
        List<List<String>> ans = new ArrayList<>();
        try {
            CSVReader csvReader = new CSVReaderBuilder(new BufferedReader(new InputStreamReader(new FileInputStream(new File(srcPath)), charset))).build();
            Iterator<String[]> iterator = (csvReader).iterator();
            while (iterator.hasNext()) {
                String[] a = iterator.next();
                List<String> tmp = Arrays.stream(a).collect(Collectors.toList());
                if(tmp.size()!=17){
                    //System.out.println(a[9]);
                    continue;
                }
                ans.add(tmp);
            }
            csvReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ans;
    }
}
