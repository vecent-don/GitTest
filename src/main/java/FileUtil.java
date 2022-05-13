import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class FileUtil {


    public static void main(String[] args) {
        Path startPath = Paths.get("src/main/resources/eclipse_src");
        try {
            Set<String> set = Files.walk(startPath)
                    .parallel().map(x->x.toString()).collect(Collectors.toSet());
            Map<Integer, Long> stats = Files.walk(startPath)
                    .parallel()
                    .collect(groupingBy(n -> Files.isDirectory(n, LinkOption.NOFOLLOW_LINKS) ? 1 : 2, counting()));
            System.out.format("Files: %d, dirs: %d. ", stats.get(2), stats.get(1));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static List<String> getFiles() {
        Path startPath = Paths.get("src/main/resources/eclipse_src");
        List<String> list = null;
        try {
            list = Files.walk(startPath)
                    .parallel().map(x->x.toString()).map(x->{
                        return x.replace("src\\main\\resources\\","");
                    }).collect(Collectors.toList());
            Map<Integer, Long> stats = Files.walk(startPath)
                    .parallel()
                    .collect(groupingBy(n -> Files.isDirectory(n, LinkOption.NOFOLLOW_LINKS) ? 1 : 2, counting()));
            System.out.format("Files: %d, dirs: %d. ", stats.get(2), stats.get(1));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public static List<String> getFiles(String location,String prefix) {
        Path startPath = Paths.get(location);
        List<String> list = null;
        try {
            list = Files.walk(startPath)
                    .parallel().map(x->x.toString()).map(x->{
                        return x.replace(prefix,"");
                    }).collect(Collectors.toList());
            Map<Integer, Long> stats = Files.walk(startPath)
                    .parallel()
                    .collect(groupingBy(n -> Files.isDirectory(n, LinkOption.NOFOLLOW_LINKS) ? 1 : 2, counting()));
            System.out.format("Files: %d, dirs: %d. ", stats.get(2), stats.get(1));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return list;
    }
}
