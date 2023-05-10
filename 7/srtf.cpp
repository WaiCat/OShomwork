#include <iostream>
#include <fstream>
#include <vector>
#include <deque>
#include <sstream>
#include <string>
#include <algorithm>
#include <cstring>
#include <queue>
#include <limits.h>

using namespace std;

struct Num
{
    int index, start, cpu, cor;
    Num(int index, int start, int cpu, int cor) : index(index), start(start), cpu(cpu), cor(cor) {}
};

struct MyComparator //cpu time op
{
    bool operator()(const Num &o1, const Num &o2)
    {
        if (o1.cor > o2.cor)
            return true;
        else if (o1.cor < o2.cor)
            return false;
        else
        {
            if (o1.index > o2.index)
                return true;
            else if (o1.index < o2.index)
                return false;
            else
                return true;
        }
    }
};

struct MComparator //start time op
{
    bool operator()(const Num &o1, const Num &o2)
    {
        if (o1.start > o2.start)
            return true;
        else if (o1.start < o2.start)
            return false;
        else
        {
            if (o1.index > o2.index)
                return true;
            else if (o1.index < o2.index)
                return false;
            else
                return true;
        }
    }
};

int main()
{

    ifstream fin("srtf.inp");
    ofstream fout("srtf.out");

    int n;
    fin >> n;

    int total = 0, rest = 0;

    vector<int> v(n);
    vector<bool> pass(n);

    priority_queue<Num, vector<Num>, MyComparator> runq;

    priority_queue<Num, vector<Num>, MComparator> startq;

    int passn = 0;

    for (int i = 0; i < n; i++)
    {

        int start;
        fin >> start;

        int cpu;
        fin >> cpu;

        Num k(i, start, cpu, cpu);

        startq.push(k);

        v[i] = cpu;
    }

    int cpu = 0;

    int time = 0;

    Num t(0,0,0,0);

    while (passn < n)
    {
        total++;

        for (int i = 0; i < startq.size(); i++)
        {
            Num k = startq.top();

            if (total > k.start){
                startq.pop();
                runq.push(k);
                t=runq.top();
                
            }else{
                break;
            }
        }

        if(!runq.empty()){

            Num k = runq.top();
            k.cor--;
            runq.pop();
            if(k.cor == 0){
                rest += total - k.start - k.cpu;
                passn++;
            }else{
                runq.push(k);
            }
        }
    }

    fout << rest << "\n";

    fout.close();

    return 0;
}