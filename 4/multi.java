import java.io.*;
import java.util.*;

public class multi {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("7.inp"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("multi.out"));


        int n = Integer.parseInt(br.readLine());

        Vector<Deque<Integer>> v = new Vector<>();

        int total = 0;
        int rest = 0;
        
        Boolean[] pass = new Boolean[n];
        int passn = 0;

        for (int i = 0; i < n; i++) {
            pass[i] = false;
        }

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            Deque<Integer> d = new LinkedList<Integer>();
            while (true) {
                int end = Integer.parseInt(st.nextToken());
                if (end == -1) {
                    d.add(end);
                    d.add(0);
                    break;
                }
                d.add(end);

                end = Integer.parseInt(st.nextToken());
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
            if (passn == n - 1) {
                int num = v.get(cpu).poll();
                if (num == -1) {
                    break;
                }
                total += num;
                if (v.get(cpu).getLast() == 1) {
                    rest += num;
                    v.get(cpu).pollLast();
                    v.get(cpu).addLast(0);
                    if (v.get(cpu).size() <= 2) {
                        rest -= num;
                    }
                } else {
                    v.get(cpu).pollLast();
                    v.get(cpu).addLast(1);
                }

            } else {
                int b = 0;
                for (int i = 0; i <= n; i++) {
                    if (i == n) {
                        total++;
                        rest++;
                        break;
                    }

                    if (v.get(cpu).getLast() == 0 && !pass[cpu]) {
                        b = v.get(cpu).poll();
                        total += b;
                        v.get(cpu).pollLast();
                        v.get(cpu).addLast(2);

                        if (v.get(cpu).getFirst() == -1) {
                            pass[cpu] = true;
                            passn++;
                        }
                        cpu = 0;
                        break;
                    } else {
                        cpu++;
                        cpu = cpu % n;
                    }
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
                                passn++;
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
                if (passn == n - 1) {
                    for (int i = 0; i < n; i++) {
                        if (!pass[i]) {
                            cpu = i;
                            break;
                        }
                    }
                }
            }
        }

        System.out.println(rest + " " + total);
        bw.write(rest + " " + total);
        bw.close();
    }
}