import java.util.Scanner;

public class TestRunner {
    public static void main(String[] args) {

        SimpleMap map = new SimpleMap();
        try (Scanner s = new Scanner(System.in)) {
            while (s.hasNextLine()) {
                String[] line = s.nextLine().split(" ");
                if (line.length > 1) {
                    if (line[0].equals("+")) {
                        map.put(line[1], Integer.parseInt(line[2]));
                    } else if (line[0].equals("-")) {
                        if (map.containsKey(line[1])) {
                            System.out.println(map.get(line[1]));
                            map.remove(line[1]);
                        }
                    }
                }
            }
        }

        System.out.println(map.size());

    }
}