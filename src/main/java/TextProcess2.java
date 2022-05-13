import org.apache.commons.lang3.tuple.Pair;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TextProcess2 {

        public static void main(String[] args) throws Exception {
            InputStream f = new FileInputStream("src/main/resources/eclipse/all_output.txt");
            BufferedReader recommendReader = new BufferedReader(new InputStreamReader(f));
            InputStream f2 = new FileInputStream("src/main/resources/eclipse/EclipseBugRepository.txt");
            BufferedReader issueReader = new BufferedReader(new InputStreamReader(f2));

            //getIssueMap
            Map<Integer, Issue> issueMap = new HashMap();
            String line = null;
            int index =0;
            while ((line = issueReader.readLine())!=null){
                String[] tmp = line.split(";");
                int issueNumber = Integer.valueOf(tmp[0]);
                String desc = tmp[1];
                String title = tmp[2];
                issueMap.put(issueNumber,new Issue(index,title,desc));
            }

            String s = null;
            String outputPath =  "E:\\graduation\\GitTest\\src\\main\\resources\\eclipse\\issue.txt";
            FileWriter fileWriter = new FileWriter(outputPath);
            BufferedWriter issueWriter = new BufferedWriter(fileWriter);

            String outputPath2 =  "E:\\graduation\\GitTest\\src\\main\\resources\\eclipse\\recommendation.txt";
            FileWriter fileWriter2 = new FileWriter(outputPath2);
            BufferedWriter recommendationWriter = new BufferedWriter(fileWriter2);
            int i=1;
            index=1;
            List<String> files = FileUtil.getFiles();
            // issueNumber, fileList
            while ((s=recommendReader.readLine())!=null){
                if(s!=null&&s.length()>1){
                    i++;
                    String[] element = s.split(",");
                    String issueNumber = element[0];
                    String fileName = element[1].replace('\'',' ');
                    int type = fileName.lastIndexOf(".");
                    fileName = fileName.substring(0,type).replace(".","\\")+fileName.substring(type);
                    String finalFileName = fileName;
                    List<String> tmp = files.stream().parallel().filter(x->x.endsWith(finalFileName)).collect(Collectors.toList());
                    fileName = tmp.get(0).replace("\\","/");
                    String rank = element[2];
                    String score = element[3];
                    String title =issueMap.get(Integer.valueOf(issueNumber)).getTitle().replace('\'',' ');
                    String description =issueMap.get(Integer.valueOf(issueNumber)).getDescription().replace('\'',' ');

                    // issue
                    // IssueId, issue_number,project_id,title,description,status
                    // i , issueNumber, 5, title, descr,0
                    if(issueMap.get(Integer.valueOf(issueNumber)).getId()==0){
                        issueMap.get(Integer.valueOf(issueNumber)).setId(index);
                        String issue = "("+String.join(",", Integer.toString(index++),issueNumber, Integer.toString(5) ,"'"+title+"'" ,"'"+description+"'","0")+")";
                        issueWriter.write(issue+",\n");
                    }
                    int id  = issueMap.get(Integer.valueOf(issueNumber)).getId();
                    // bug_location
                    // id, issue_id, project_id, issue_number,file_name, rank, score
                    // i, issueNumber, ....
                    String bugReport = "("+String.join(",", Integer.toString(id), issueNumber, "5" ,"'"+fileName+"'" ,rank,score)+")";
                    recommendationWriter.write(bugReport+",\n");

                }
            }

            issueWriter.close();
            recommendationWriter.close();
        }



}
