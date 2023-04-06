#include <iostream>
#include <fstream>
#include <vector>
#include <deque>
#include <cstring>

using namespace std;

int main()
{
    ifstream fin("multi.inp");
    ofstream fout("multi.out");

    int n;
    fin >> n;

    vector<deque<int>> v;

    int total = 0;
    int rest = 0;

    bool pass[n];
    int passn = 0;

    memset(pass, false, sizeof(pass));

    for (int i = 0; i < n; i++)
    {
        deque<int> d;
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
        if (passn == n - 1)
        {
            int num = v[cpu].front();
            v[cpu].pop_front();
            if (num == -1)
            {
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
                v[cpu].pop_back();
                v[cpu].push_back(1);
            }
        }
        else
        {
            int b = 0;
            for (int i = 0; i <= n; i++)
            {
                if (i == n)
                {
                    total++;
                    rest++;
                    break;
                }

                if (v[cpu].back() == 0 && !pass[cpu])
                {
                    b = v[cpu].front();
                    v[cpu].pop_front();
                    total += b;
                    v[cpu].pop_back();
                    v[cpu].push_back(2);

                    if (v[cpu].front() == -1)
                    {
                        pass[cpu] = true;
                        passn++;
                    }
                    cpu = 0;
                    break;
                }
                else
                {
                    cpu++;
                    cpu = cpu % n;
                }
            }

            for (int i = 0; i < v.size(); i++)
            {
                if (v[i].back() == 1 && !pass[i])
                {
                    int a = v[i].front();
                    v[i].pop_front();
                    if (b == 0)
                    {
                        a--;
                    }
                    else
                    {
                        a = a - b;
                    }
                    if (a < 1)
                    {
                        v[i].pop_back();
                        v[i].push_back(0);
                        if (v[i].front() == -1)
                        {
                            pass[i] = true;
                            passn++;
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
    }

    fout << rest << " " << total;
    fout.close();

    return 0;
}