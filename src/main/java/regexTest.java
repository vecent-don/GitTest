import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regexTest {
    public static void main(String[] args) {
        String issue = "adff. dfasf";
        String[] tmp  = issue.split("\\.");
        for (String i :issue.split(".")){
            System.out.println(i);
        }
        String s = "add.a";
        String pattern= "(.*)\\.(.*)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(s);
        if(m.find()){
            System.out.println("Found value: " + m.group(0) );
            System.out.println("Found value: " + m.group(1) );
            System.out.println("Found value: " + m.group(2) );
        }
    }
}
