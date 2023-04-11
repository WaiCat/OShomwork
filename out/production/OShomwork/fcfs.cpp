#include <iostream>
#include <fstream>
#include <vector>
#include <deque>
#include <sstream>
#include <string>
#include <algorithm>
#include <cstring>

using namespace std;

int main()
{
    ifstream fin("4.inp");
    ofstream fout("44.txt");

    int n;
    fin >> n;

    vector<deque<int>> v;
    int total = 0;
    int rest = 0;
    int restf = 0;

    deque<int> q;

    int endtime[n];
    int start[n];

    bool pass[n];
    int passn = 0;

    memset(pass, false, sizeof(pass));

    for (int i = 0; i < n; i++)
    {
        deque<int> d;
        fin >> start[i];

        while (true)
        {
            int end;
            fin >> end;
            if (end == -1)
            {
                d.push_back(end);
                d.push_back(0);
                break;
            }
            d.push_back(end);
        }
        v.push_back(d);
    }

    int cpu = 0;

    while (passn < n)
    {
        for (int i = 0; i < n; i++)
        {
            if (start[i] <= total && start[i] >= 0)
            {
                q.push_back(i);
                start[i] = -1;
            }
        }
        // if (passn == n - 2)
        // {
        //     if (passn == n - 2)
        //     {
        //         fout << rest;
        //     }
        // }

        if (total == 22918)
        {
            if (total == 22918)
            {
                cout << " ";
            }
        }

        if (passn == n - 1)
        {
            int num = v[cpu].front();
            v[cpu].pop_front();
            if (num == -1)
            {
                endtime[cpu] = total;
                fout << rest << " " << total << "\n";
                break;
            }
            total += num;
            if (v[cpu].back() == 1)
            {
                rest += num;
                v[cpu].pop_back();
                v[cpu].push_back(0);
                if (v[cpu].size() <= 2)
                {
                    rest -= num;
                }
            }
            else
            {
                restf = 0;
                v[cpu].pop_back();
                v[cpu].push_back(1);
            }
            continue;
        }
        else
        {
            int b = 0;
            if (!q.empty())
            {
                restf = 0;
                cpu = q.front();
                q.pop_front();
                b = v[cpu].front();
                v[cpu].pop_front();
                total++;
                b--;
                if (b == 0)
                {
                    if (v[cpu].front() == -1)
                    {
                        pass[cpu] = true;
                        endtime[cpu] = total;
                        fout << rest << " " << total << "\n";
                        passn++;
                    }
                    else
                    {
                        v[cpu].pop_back();
                        v[cpu].push_back(2);
                    }
                }
                else
                {
                    q.push_front(cpu);
                    v[cpu].push_front(b);
                }
            }
            else
            {
                restf++;

                total++;
                rest++;
            }
        }

        for (int i = 0; i < v.size(); i++)
        {
            if (v[i].back() == 1 && !pass[i])
            {
                int a = v[i].front();
                v[i].pop_front();
                a--;
                if (a == 0)
                {
                    v[i].pop_back();
                    v[i].push_back(0);
                    if (v[i].front() == -1)
                    {
                        pass[i] = true;
                        endtime[i] = total;
                        fout << rest << " " << total << "\n";
                        passn++;
                    }
                    else
                    {
                        q.push_back(i);
                    }
                }
                else
                {
                    v[i].push_front(a);
                }
            }
            if (v[i].back() == 2)
            {
                v[i].pop_back();
                v[i].push_back(1);
            }
        }
        if (passn == n - 1)
        {
            for (int i = 0; i < n; i++)
            {
                if (!pass[i])
                {
                    cpu = i;
                    break;
                }
            }
        }
    }
    if (restf != 0)
    {
        rest -= restf;
    }
    fout << rest << "\n";
    for (int i = 0; i < n; i++)
    {
        fout << endtime[i] << "\n";
    }
    fout.close();

    return 0;
}