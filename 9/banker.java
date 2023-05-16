import java.io.*;
import java.util.*;

public class banker {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Administrator\\Downloads\\OShomwork\\9\\2.inp"));

        BufferedWriter bw = new BufferedWriter(new FileWriter("banker.out"));


        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[] availavle = new int[m];
        int[][] max = new int[n][m];
        int[][] allocation = new int[n][m];
        int[][] need = new int[n][m];
        Vector<Vector<Integer>> q = new Vector<Vector<Integer>>();
        Boolean[] end = new Boolean[n];
        Arrays.fill(end, false);

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            availavle[i] = Integer.parseInt(st.nextToken());
        }

        br.readLine();
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                max[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        br.readLine();
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                int k = Integer.parseInt(st.nextToken());
                allocation[i][j] = k;
                availavle[j] -= k;
                need[i][j] = max[i][j] - k;
            }
        }

        br.readLine();
        while(true){
            st = new StringTokenizer(br.readLine());
            String s = st.nextToken();

            Boolean state = true;
            Boolean safe = false;
            if(s.equals("request")){
                Vector<Integer> v = new Vector<Integer>();
                int index = Integer.parseInt(st.nextToken());
                int[] c = need[index].clone();
                v.add(index);
                int[] b = availavle.clone();

                for (int i = 0; i < m; i++) {
                    int k = Integer.parseInt(st.nextToken());
                    c[i]-=k;
                    if(k>need[index][i]){
                        state=false;
                        break;
                    }
                    v.add(k);
                    b[i] = b[i] - k;
                    if(availavle[i] < k){
                        state = false;
                    }
                }

                if(state){

                    for (int i = 0; i < n; i++) {
                        if(end[i]){
                            continue;
                        }
                        for (int k = 0; k < m; k++) {
                            if(k==index){
                                if(c[k] > b[k]){
                                    break;
                                }
                                if(k==(m-1)){
                                    safe = true;
                                }
                            }else {
                                if (need[i][k] > b[k]) {
                                    break;
                                }
                                if (k == (m - 1)) {
                                    safe = true;
                                }
                            }
                        }
                        if(safe){
                            break;
                        }
                    }
                    if(safe){
                        int sum=0;
                        for (int i = 0; i < m; i++) {
                            need[index][i] -= v.get(i+1);
                            availavle[i] -= v.get(i+1);
                            sum += need[index][i];
                        }
                        if(sum == 0){
                            end[index] = true;
                        }
                    }else{
                        q.add(v);
                    }
                }else{
                    q.add(v);
                }

            }else if(s.equals("release")){
                int index = Integer.parseInt(st.nextToken());
                for (int i = 0; i < m; i++) {
                    int k = Integer.parseInt(st.nextToken());
                    availavle[i] += k;
                    need[index][i] += k;
                }

                Boolean can=false;
                for (int i = 0; i < q.size(); i++) {
                    Vector<Integer> v = q.get(i);
                    index = v.get(0);
                    for (int j = 0; j < m; j++) {
                        if(v.get(j+1) > availavle[j]){
                            break;
                        }
                        if(j==m-1){
                            can =true;
                        }
                    }
                    if(can){
                        int[] b = availavle.clone();
                        int[] c = need[index].clone();
                        for (int j = 0; j < m; j++) {
                            c[j]-= v.get(j+1);
                        }


                        for (int j = 0; j < n; j++) {

                            if(end[j]){
                                continue;
                            }
                            for (int k = 0; k < m; k++) {
                                if(k==index){
                                    if(c[k] > b[k]){
                                        break;
                                    }
                                    if(k==(m-1)){
                                        safe = true;
                                    }
                                }else {
                                    if (need[j][k] > b[k]) {
                                        break;
                                    }
                                    if (k == (m - 1)) {
                                        safe = true;
                                    }
                                }
                            }
                        }
                        if(safe){
                            int sum=0;
                            for (int j = 0; j < m; j++) {
                                need[index][j] -= v.get(j+1);
                                availavle[j] -= v.get(j+1);
                                sum += need[index][j];
                            }
                            if(sum == 0){
                                end[index] = true;
                            }
                            q.remove(i);
                            break;
                        }
                    }
                }
            }else if (s.equals("quit")){
                break;
            }
            for (int i = 0; i < m; i++) {
                bw.write(availavle[i] + " ");
                System.out.println(availavle[i] + " ");
            }
            System.out.println("\n");
            bw.write("\n");
        }
        bw.close();
    }
}
