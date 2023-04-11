import java.io.*;
import java.util.*;

public class fcfs {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Administrator\\Downloads\\OShomwork\\5\\4.inp"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("44.txt"));

        int n = Integer.parseInt(br.readLine());

        Vector<Deque<Integer>> v = new Vector<>();

        int total = 0;
        int rest = 0;
        int restf = 0;

        Deque<Integer> q = new LinkedList<>();

        int endtime[] = new int[n];
        int start[] = new int[n];

        Boolean[] pass = new Boolean[n];
        int passn = 0;

        for (int i = 0; i < n; i++) {
            pass[i] = false;
        }

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            Deque<Integer> d = new LinkedList<Integer>();
            start[i] = Integer.parseInt(st.nextToken());
            while (true) {
                int end = Integer.parseInt(st.nextToken());
                if (end == -1) {
                    d.add(end);
                    d.add(0);
                    break;
                }
                d.add(end);
            }
            v.add(d);
        }

        int cpu = 0;

        while (passn < n) {


            for (int i = 0; i < n; i++) {
                if (start[i] <= total && start[i] >= 0) {
                    q.add(i);
                    start[i] = -1;
                }
            }
            if (total == 212819) {
                if (total == 212819) {

                }
            }
//            if (passn == n - 2){
//                if (passn == n - 2){
//
//                }
//            }
//            if (passn == n - 1) {
//                int num = v.get(cpu).poll();
//                if (num == -1) {
//                    endtime[cpu] = total;
//                    break;
//                }
//                total += num;
//                if (v.get(cpu).getLast() == 1) {
//                    rest += num;
//                    v.get(cpu).pollLast();
//                    v.get(cpu).addLast(0);
//                    if (v.get(cpu).size() <= 2) {
//                        rest -= num;
//                    }
//                } else {
//                    restf=0;
//                    v.get(cpu).pollLast();
//                    v.get(cpu).addLast(1);
//                }
//                continue;
//            } else {
            int b = 0;
            if (q.size() > 0) {
                restf = 0;
                cpu = q.poll();
                b = v.get(cpu).poll();
                total++;
                b--;
                if (b == 0) {
                    if (v.get(cpu).getFirst() == -1) {
                        pass[cpu] = true;
                        endtime[cpu] = total;
                        passn++;
                    } else {
                        v.get(cpu).pollLast();
                        v.get(cpu).addLast(2);
                    }
                } else {
                    q.addFirst(cpu);
                    v.get(cpu).addFirst(b);
                }
            } else {
                total++;
                rest++;
                restf++;
            }


            for (int i = 0; i < v.size(); i++) {
                if (v.get(i).getLast() == 1 && !pass[i]) {
                    int a = v.get(i).poll();
                    a--;
                    if (a == 0) {
                        v.get(i).pollLast();
                        v.get(i).addLast(0);
                        if (v.get(i).getFirst() == -1) {
                            pass[i] = true;
                            endtime[i] = total;
                            passn++;
                        } else {
                            q.add(i);
                        }
                    } else {
                        v.get(i).addFirst(a);
                    }
                }
                if (v.get(i).getLast() == 2) {
                    v.get(i).pollLast();
                    v.get(i).addLast(1);
                }
            }
//            if (passn == n - 1) {
//                for (int i = 0; i < n; i++) {
//                    if (!pass[i]) {
//                        cpu = i;
//                        break;
//                    }
//                }
//            }
            //}

        }

        if(restf!=0){
            rest -= restf;
        }
        bw.write(rest + "\n");
        for (int i = 0; i < n; i++) {
            bw.write(endtime[i] + "\n");
        }
        bw.close();
    }
}