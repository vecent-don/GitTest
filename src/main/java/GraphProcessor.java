import com.opencsv.CSVReader;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GraphProcessor {
    static int index = 99;
    static BufferedWriter issueWriter;
    public static void main(String[] args) throws Exception {
        // extract
        InputStream f = new FileInputStream("src/main/resources/maven/issue.csv");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(f));
        String[] head = new String[]{
                "MNG-4120","MNG-5608","MNG-4735","MNG-3897","MNG-5516","MNG-5590","MNG-5612","MNG-2809","MNG-3524","MNG-5360","MNG-4174","MNG-4471","MNG-3773","MNG-5055","MNG-2363","MNG-1775","MNG-2627","MNG-3017","MPH-79","MNG-3140","MNG-3228","MNG-5127","MNG-4620"};
        Integer[] header = Arrays.stream(head).map(x->Integer.valueOf(x.split("-")[1])).collect(Collectors.toList()).toArray(new Integer[0]);

        int[][] m = new int[][]{{0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0},
                {0,0,0,0,1,1,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0},
                {0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0},
                {0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0},
                {1,1,1,0,0,0,0,0,1,1,1,0,0,0,0,1,0,1,0,1,0,0,1},
                {0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0},
                {0,0,0,1,0,0,0,0,0,0,1,0,1,1,0,0,0,0,0,0,0,1,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0}};

        //issueMap, get issue from CSV
        Map<Integer,Issue> issueMap = new HashMap<>();
//        String s = bufferedReader.readLine();
//        while ((s=bufferedReader.readLine())!=null){
//            String[] tmp = s.split(",");
//            String[] t = tmp[0].split("-");
//            if(t.length!=2){
//                continue;
//            }
//            int number = Integer.valueOf(t[1]);
//            String title = tmp[8];
//            String description = tmp[9];
//            issueMap.put(number,new Issue(0,title,description));
//        }
        List<List<String>> csv = MyCSVReader.read();
        for (int i=0;i<csv.size();i++){
            String[] s_issueNumber = csv.get(i).get(0).split("-");
            if(s_issueNumber.length!=2){
                continue;
            }
            int issueNumber = Integer.valueOf(s_issueNumber[1]);
            String title = csv.get(i).get(8);
            String description = csv.get(i).get(9);
            issueMap.put(issueNumber,new Issue(0,title,description,issueNumber));
        }
        String outputPath =  "E:\\graduation\\GitTest\\src\\main\\resources\\maven\\issue.txt";
        FileWriter fileWriter = new FileWriter(outputPath);
        issueWriter = new BufferedWriter(fileWriter);

        String outputPath2 =  "E:\\graduation\\GitTest\\src\\main\\resources\\maven\\relation.txt";
        FileWriter fileWriter2 = new FileWriter(outputPath2);
        BufferedWriter relationWriter = new BufferedWriter(fileWriter2);


        for (int i=0;i< m.length;i++){
            int issueNumber = header[i];
            Issue left = get(issueMap,issueNumber);
            for(int j=0;j<m[0].length;j++){
                if(m[i][j]!=1){
                    continue;
                }
                Issue right = get(issueMap,header[j]);
                // relation
                // (id),issueId1,issueId2
                String relation = "("+left.getId()+","+right.getId() +")";
                relationWriter.write(relation+",\n");
            }
        }
        issueWriter.close();
        relationWriter.close();
    }
    public static Issue get(Map<Integer,Issue> issueMap,int num) throws Exception{
        if(!issueMap.containsKey(num)){
            issueMap.put(num,new Issue(0,"",""));
        }
        check(issueMap.get(num));
        return issueMap.get(num);
    }
    public static void check(Issue issue) throws Exception{
        if(issue.getId()==0){
            issue.setId(index++);
            // issue
            // issueId,projectId,issueNumber,title,description
            String iss = "("+issue.getId()+","+ 6 +","+issue.getIssueNumber()+",\'"+issue.getTitle().replace('\'',' ')+"\',\'"+issue.getDescription().replace('\'',' ')+"\')";
            issueWriter.write(iss+",\n");
        }
    }
}
