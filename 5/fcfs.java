import java.io.*;
import java.util.*;

public class fcfs {

    static class Num {
        public int index;
        public int num;
        public int time;

        Num(int index, int num, int time) {
            this.index = index;
            this.num = num;
            this.time = time;
        }
        void settime(int time){
            this.time = time;
        }
    }

    public static class MyComparator implements Comparator<Num> {
        @Override
        public int compare(Num o1, Num o2) {
            if (o1.time > o2.time)
                return 1;
            else if (o1.time < o2.time)
                return -1;
            else {
                if (o1.num > o2.num)
                    return 1;
                else if (o1.num < o2.num)
                    return -1;
                else {
                    if (o1.index > o2.index)
                        return 1;
                    else if (o1.index < o2.index)
                        return -1;
                    else
                        return 1;
                }
            }
        }
    }
    public static class MComparator implements Comparator<Num> {
        @Override
        public int compare(Num o1, Num o2) {

                if (o1.num > o2.num)
                    return 1;
                else if (o1.num < o2.num)
                    return -1;
                else {
                    if (o1.index > o2.index)
                        return 1;
                    else if (o1.index < o2.index)
                        return -1;
                    else
                        return 1;
                }
            }

    }

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Administrator\\Downloads\\OShomwork\\5\\1.txt"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("11.txt"));

        // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        Vector<Deque<Integer>> v = new Vector<>();

        int total = 0;
        int rest = 0;
        int restf = 0;

        PriorityQueue<Num> q = new PriorityQueue<>(new MyComparator());

        int endtime[] = new int[n];

        PriorityQueue<Num> start = new PriorityQueue<>(new MComparator());

        Boolean[] pass = new Boolean[n];
        int passn = 0;

        for (int i = 0; i < n; i++) {
            pass[i] = false;
        }

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            Deque<Integer> d = new LinkedList<Integer>();
            int s = Integer.parseInt(st.nextToken());

            while (true) {
                int end = Integer.parseInt(st.nextToken());
                if (end == -1) {
                    d.add(end);
                    d.add(0);
                    break;
                }
                d.add(end);
            }
            Num k = new Num(i, s, d.getFirst());
            start.add(k);
            v.add(d);
        }

        int cpu = 0;

        while (passn < n) {
            for (int i = 0; i < start.size(); i++) {
                Num k = start.peek();
                if (total >= k.num) {
                    q.add(k);
                    start.poll();
                }else{
                    break;
                }
            }
            int b = 0;
            if (q.size() > 0) {
                restf = 0;
                Num k = q.poll();
                cpu = k.index;

                b = v.get(cpu).poll();
                total += b;
                if (v.get(cpu).getFirst() == -1) {
                    pass[cpu] = true;
                    endtime[cpu] = total;
                    passn++;
                } else {
                    v.get(cpu).pollLast();
                    v.get(cpu).addLast(2);
                }
            } else {
                // int min = Integer.MAX_VALUE;

                // for (int i = 0; i < v.size(); i++) {
                //     if (v.get(i).getLast() == 1 && !pass[i]) {
                //         min = v.get(i).getFirst() < min ? v.get(i).getFirst() : min;
                //     }
                // }
                // if (start.size() > 0) {
                //     Num k = start.peek();
                //     if (k.num < total + min) {
                //         q.add(k);
                //         start.poll();
                //         min = k.num - total;
                //     }
                // }
                // total += min;
                // rest += min;
                // restf += min;
                // b = min;
                total++;
                rest++;
                restf++;
            }

            for (int i = 0; i < v.size(); i++) {
                if (v.get(i).getLast() == 1 && !pass[i]) {
                    int a = v.get(i).poll();
                    if (b == 0) {
                        a--;
                    } else {
                        a = a - b;
                    }
                    if (a < 1) {
                        v.get(i).pollLast();
                        v.get(i).addLast(0);
                        if (v.get(i).getFirst() == -1) {
                            pass[i] = true;
                            endtime[i] = total + a;
                            passn++;
                        } else {
                            Num k = new Num(i, total + a, v.get(i).getFirst());
                            q.add(k);
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
        }

        if (restf != 0) {
            rest -= restf;
        }
        bw.write(rest + "\n");
        for (int i = 0; i < n; i++) {
            bw.write(endtime[i] + "\n");
        }
        bw.close();
    }
}