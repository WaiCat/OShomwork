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
	int index, num, time;
	Num(int index, int num, int time) : index(index), num(num), time(time) {}
};

struct MyComparator
{
	bool operator()(const Num& o1, const Num& o2)
	{
		if (o1.time > o2.time)
			return true;
		else if (o1.time < o2.time)
			return false;
		else {
			if (o1.num > o2.num)
				return true;
			else if (o1.num < o2.num)
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
	}
};

struct MComparator
{
	bool operator()(const Num& o1, const Num& o2)
	{
		
			if (o1.num > o2.num)
				return true;
			else if (o1.num < o2.num)
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

	ifstream fin("sjf.inp");
	ofstream fout("sjf.out");

	int n;
	fin >> n;

	vector<deque<int>> v;
	vector<bool> pass(n);
	vector<int> endtime(n);
	int total = 0, rest = 0, restf = 0;

	priority_queue<Num, vector<Num>, MyComparator> q;

	//int endtime[n];

	priority_queue<Num, vector<Num>, MComparator> start;
	//bool pass[n];
	int passn = 0;

	//memset(pass, false, sizeof(pass));

	for (int i = 0; i < n; i++)
	{

		int num;
		deque<int> d;
		fin >> num;
		

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
        Num k(i, num, d.front());
		start.push(k);
		v.push_back(d);
	}

	int cpu = 0;

	while (passn < n)
	{
		for (int i = 0; i < start.size(); i++)
		{
			Num k = start.top();
			if (total >= k.num)
			{
				q.push(k);
				start.pop();
			}
			else
			{
				break;
			}
		}
		int b = 0;
		if (!q.empty())
		{
			restf = 0;
			Num k = q.top();
			cpu = k.index;
			q.pop();

			b = v[cpu].front();
			v[cpu].pop_front();

			total += b;
			if (v[cpu].front() == -1)
			{
				pass[cpu] = true;
				endtime[cpu] = total;
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
			/*int min = INT_MAX;

			for (int i = 0; i < v.size(); i++)
			{
				if (v[i].back() == 1 && !pass[i])
				{
					int k = v[i].front();
					min = v[i].front() < min ? v[i].front() : min;
				}
			}
			if (start.size() > 0)
			{
				Num k = start.top();
				if (k.num < total + min)
				{
					q.push(k);
					start.pop();
					min = k.num - total;
				}
			}
			total += min;
			rest += min;
			restf += min;
			b = min;*/
			total++;
			rest++;
			restf++;
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
						endtime[i] = total + a;
						passn++;
					}
					else
					{
						Num k(i, total + a, v[i].front());
						q.push(k);
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