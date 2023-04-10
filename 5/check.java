import java.io.*;

public class check {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Administrator\\IdeaProjects\\OShomwork\\5\\5.txt"));
        BufferedReader br2 = new BufferedReader(new FileReader("C:\\Users\\Administrator\\IdeaProjects\\OShomwork\\5\\55.txt"));

        while(true) {
            int a = Integer.parseInt(br.readLine());
            int b = Integer.parseInt(br2.readLine());
            if (a!=b) {
                System.out.println(a);
                System.out.println(b);
            }

        }
    }
}
