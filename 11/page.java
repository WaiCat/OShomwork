import java.io.*;
import java.util.*;

public class page {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("page.inp"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("page.out"));

        //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        Vector<Integer> v = new Vector<>();

        Deque<Integer> d = new ArrayDeque<>();
        int fifo = 0;
        int memory = Integer.parseInt(br.readLine());
        while(true){
            int p = Integer.parseInt(br.readLine());
            v.add(p);
            if(p==-1){
                System.out.println("FIFO: " + fifo);
                bw.write("FIFO: " + fifo);
                bw.write("\n");
                break;
            }
            if(!d.contains(p)){
                if(d.size()< memory){
                    d.addFirst(p);
                    fifo++;
                }else{
                    d.addFirst(p);
                    d.removeLast();
                    fifo++;
                }
            }
        }

        int LRU = 0;
        int i = 0;

        Vector<Integer> lv = new Vector<>();
        while(true){
            int p = v.get(i);
            i++;
            if(p==-1){
                System.out.println("LRU: " + LRU);
                bw.write("LRU: " + LRU);
                bw.write("\n");
                break;
            }
            if(!lv.contains(p)){
                if(lv.size()< memory){
                    lv.add(p);
                     LRU++;
                }else{
                    lv.add(p);
                    lv.remove(0);
                    LRU++;
                }
            }else {
                int index = lv.indexOf(p);
                lv.remove(index);
                lv.add(p);
            }
        }

        int OPT = 0;
        lv = new Vector<>();
        while(true){
            int p = v.get(0);
            v.remove(0);
            if(p==-1){
                System.out.println("OPT: " + OPT);
                bw.write("OPT: " + OPT);
                bw.write("\n");
                break;
            }
            if(!lv.contains(p)){
                boolean pass = false;
                if(lv.size()< memory){
                    lv.add(p);
                    OPT++;
                }else{
                    int maxi = Integer.MIN_VALUE;
                    for (i = 0; i < memory; i++) {
                        if(v.contains(lv.get(i))){
                            maxi = maxi > v.indexOf(lv.get(i)) ? maxi : v.indexOf(lv.get(i));
                        }else{
                            lv.remove(lv.indexOf(lv.get(i)));
                            lv.add(p);
                            OPT++;
                            pass = true;
                            break;
                        }
                    }
                    if(!pass) {
                        maxi = lv.indexOf(v.get(maxi));
                        lv.remove(lv.get(maxi));
                        lv.add(p);
                        OPT++;
                    }

                }
            }
        }
        bw.close();
    }
}
