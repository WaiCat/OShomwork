#include <iostream>
#include <vector>
#include <deque>
#include <istream>
#include <algorithm>
#include <fstream>
#include <string>
#include <map>
#include <limits>

using namespace std;

ifstream fin;
ofstream fout;
int n;

class Process
{
public:
    int ready;               // 준비된 시간
    deque<pair<int, int>> p; //
    int p_size;
    bool isFinish = false;
    int finish_time = 0;
};

int main()
{
    fin.open("4.inp");
    fout.open("44444.txt");

    fin >> n;
    vector<Process> v(n);

    int total = 0; // 현재 시간
    int idle = 0;  // 유휴시간

    int check = 0;

    int time = 0;
    // 입력
    for (int i = 0; i < n; i++)
    {
        Process process;
        int start_time = 0;
        fin >> start_time;
        process.ready = start_time; // 시작 시간
        v[i] = process;
        deque<pair<int, int>> temp_deque;
        int index = 1;
        while (true)
        {
            if (index % 2 == 1)
                check = 1; // 사용이면 1
            else
                check = 0; // 입출력 0
            int temp;
            fin >> temp;
            if (temp == -1)
            {
                break;
            }
            temp_deque.push_back({temp, check});
            index++;
        }
        v[i].p.swap(temp_deque); // 클래스에 값 넣음
        v[i].p_size = v[i].p.size();
    }

    while (true)
    {
        int finish = 0;
        int use_index = 0;
        int min_ready = numeric_limits<int>::max();
        for (int i = 0; i < v.size(); i++)
        { // 제일 오래 기다린 프로세스 인덱스 찾기
            if (v[i].isFinish == true)
            {
                finish++;
                continue; // 끝난건 찾지 않음
            }
            if (v[i].ready < min_ready)
            {
                min_ready = v[i].ready;
                use_index = i;
            }
        }

        // if (finish == v.size() - 1)
        // {
        //     if (finish == v.size() - 1)
        //     {
        //         fout << idle;
        //     }
        // }

        int a = 0;

        if (finish == v.size())
            break;

        if (min_ready > total)
        {
            idle += min_ready - total; // 유휴시간
            total += min_ready - total;
        }

        if (finish == v.size()-2)
        {
                cout << 0;
        }

        total += v[use_index].p.front().first; // cpu 사용
        v[use_index].p.pop_front();
        v[use_index].p_size--;
        if (v[use_index].p_size == 0)
        {
            if(use_index== 498){
                cout << 0;
            }
            v[use_index].isFinish = true;
            v[use_index].finish_time = total;
            fout << idle << " " << total << "\n";
            time = total;
        }

        if (v[use_index].isFinish == true)
            continue;
        v[use_index].ready = total + v[use_index].p.front().first; // 다음 준비 완료 시간은 IO 끝난 후 , IO 진행
        v[use_index].p.pop_front();
        v[use_index].p_size--;
        if (v[use_index].p_size == 0)
        {
            if(use_index== 498){
                cout << 0;
            }
            v[use_index].isFinish = true;
            v[use_index].finish_time = v[use_index].ready;
            fout << idle << " " << v[use_index].ready << "\n";
            time = v[use_index].ready;
        }
    }
    fout << idle << endl;
    for (int i = 0; i < v.size(); i++)
    {
        fout << v[i].finish_time << endl;
    }
}
