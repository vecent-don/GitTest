
import org.apache.commons.lang3.tuple.Pair;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TxtProcess {
    public static void main(String[] args) throws Exception {
        InputStream f = new FileInputStream("src/main/resources/eclipse/all_output.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(f));
        InputStream f2 = new FileInputStream("src/main/resources/eclipse/EclipseBugRepository.txt");
        BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(f2));

        //getIssueMap
        Map<Integer, Pair<String,String>> issueMap = new HashMap();
        String line = null;
        int index =0;
        while ((line = bufferedReader2.readLine())!=null){
            String[] tmp = line.split(";");
            int id = Integer.valueOf(tmp[0]);
            String desc = tmp[1];
            String title = tmp[2];
            issueMap.put(id,Pair.of(title,desc));
        }

        String s = null;
        String outputPath =  "E:\\graduation\\GitTest\\src\\main\\resources\\eclipse\\issue.txt";
        FileWriter fileWriter = new FileWriter(outputPath);
        BufferedWriter bw = new BufferedWriter(fileWriter);

        String outputPath2 =  "E:\\graduation\\GitTest\\src\\main\\resources\\eclipse\\recommendation.txt";
        FileWriter fileWriter2 = new FileWriter(outputPath2);
        BufferedWriter bw2 = new BufferedWriter(fileWriter2);
        int i=0,j=0;
        // issueNumber, fileList


        do {
            s = bufferedReader.readLine();
            if(s!=null&&s.length()>1){
                i++;

                String[] element = s.split(",");
                String issueNumber = element[0];
                String fileName = element[1];
                String rank = element[2];
                String score = element[3];
                String title =issueMap.get(Integer.valueOf(issueNumber)).getLeft();
                String description =issueMap.get(Integer.valueOf(issueNumber)).getRight();

                // issue
                // id, issue_number,project_id,title,description
                // i , issueNumber, 4, title, descr
                String issue = "("+String.join(",", Integer.toString(i),issueNumber, Integer.toString(4) ,title ,description)+")";
                bw.write(issue+"\n");
                // bug_location
                // issueId, issue_number,file_name, rank, score
                // i, issueNumber, ....
                String bugReport = "("+String.join(",", Integer.toString(i), issueNumber ,fileName ,rank,score)+")";
                bw.write(bugReport+"\n");

            }
        }while (s!=null);
        bw.close();
        bw2.close();
    }
}
