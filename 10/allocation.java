import java.io.*;
import java.util.*;

class process{
    int start;
    int run;
    int size;
    boolean last;

    process(int start, int run, int size, boolean last){
        this.start = start;
        this.run = run;
        this.size = size;
        this.last = last;
    }
}

class qprocess{
    int size;
    int end;
    boolean last;
    qprocess(int end, int size, boolean last){
        this.end = end;
        this.size = size;
        this.last = last;
    }
}
public class allocation {
    public static void main(String[] args) throws IOException {
        //BufferedReader br = new BufferedReader(new FileReader("banker.inp"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("banker.out"));

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());


        Vector<process> list = new Vector<process>();
        Vector<qprocess> q = new Vector<qprocess>();

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            Boolean last = false;
            if(i==n-1){
                last = true;
            }

            list.add(new process(Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()),
                    last));
        }

        int totaltime = 0;
        Vector<qprocess> memory = new Vector<qprocess>();
        memory.add(new qprocess(-1, 1000, false));
        //first fit
        while(true){
            Boolean endb = false;
            //시작시간 도달한 프로세서 큐에 올림
            for (int i = 0; i < list.size(); i++) {
                if(list.get(i).start<=totaltime){
                    q.add(new qprocess(list.get(i).run, list.get(i).size, list.get(i).last));
                    list.remove(i);
                }
            }

            //시간 다된 프로세서 자원 반납
            for (int i = 0; i < memory.size(); i++) {
                if(memory.get(i).end!=-1 && memory.get(i).end <= totaltime){
                    memory.get(i).end=-1;
                }
            }

            //반납된 자원 합치기
            for (int i = 0; i < memory.size()-1; i++) {
                if(memory.get(i).end==-1){
                    if(memory.get(i+1).end==-1){
                        memory.get(i).size+=memory.get(i+1).end;
                        memory.remove(i+1);
                        i--;
                    }
                }
            }

            //등록할 수 있는 프로세서 등록
            for (int i = 0; i < q.size(); i++) {
                int size = q.get(i).size;
                for (int j = 0; j < memory.size(); j++) {
                    if(memory.get(j).end==-1){
                        if(memory.get(j).size>size){
                            memory.get(j).size-=size;
                            memory.add(j, new qprocess(totaltime+q.get(i).end,q.get(i).size, q.get(i).last));
                            if(memory.get(j).last){
                                int sum = 0;
                                for (int k = 0; k < j; k++) {
                                    sum+=memory.get(k).size;
                                }
                                System.out.println(sum);
                                endb=true;
                            }
                            q.remove(i);
                            i--;
                            break;
                        }
                    }

                }
            }
            if(endb){
                break;
            }
            totaltime++;
        }

    }
}
