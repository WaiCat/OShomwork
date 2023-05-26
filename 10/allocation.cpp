#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>
#include <limits.h>
#include <stdio.h>

using namespace std;

struct Process {
    int start;
    int run;
    int size;
    bool last;

    Process(int start, int run, int size, bool last) {
        this->start = start;
        this->run = run;
        this->size = size;
        this->last = last;
    }
};

struct QProcess {
    int size;
    int end;
    bool last;

    QProcess(int end, int size, bool last) {
        this->end = end;
        this->size = size;
        this->last = last;
    }
};

int main() {
    ifstream inputFile("allocation.inp");
    ofstream outputFile("allocation.out");

    int n;
    inputFile >> n;

    vector<Process> listm;

    for (int i = 0; i < n; i++) {
        int start, run, size;
        inputFile >> start >> run >> size;
        bool last = (i == n - 1);
        listm.push_back(Process(start, run, size, last));
    }

    int totaltime = 0;
    vector<QProcess> memory;
    vector<QProcess> q;
    memory.push_back(QProcess(-1, 1000, false));
    vector<Process> list = listm;

    // First Fit
    while (true) {
        bool endb = false;
        for (int i = 0; i < list.size(); i++) {
            if (list[i].start <= totaltime) {
                q.push_back(QProcess(list[i].run, list[i].size, list[i].last));
                list.erase(list.begin() + i);
                i--;
            }
        }

        for (int i = 0; i < memory.size(); i++) {
            if (memory[i].end != -1 && memory[i].end <= totaltime) {
                memory[i].end = -1;
            }
        }

        for (int i = 0; i < memory.size() - 1; i++) {
            if (memory[i].end == -1 && memory[i + 1].end == -1) {
                memory[i].size += memory[i + 1].size;
                memory.erase(memory.begin() + i + 1);
                i--;
            }
        }

        for (int i = 0; i < q.size(); i++) {
            int size = q[i].size;
            for (int j = 0; j < memory.size(); j++) {
                if (memory[j].end == -1 && memory[j].size >= size) {
                    memory[j].size -= size;
                    memory.insert(memory.begin() + j, QProcess(totaltime + q[i].end, q[i].size, q[i].last));
                    if (memory[j].last) {
                        int sum = 0;
                        for (int k = 0; k < j; k++) {
                            sum += memory[k].size;
                        }
                        cout << sum << endl;
                        outputFile << sum << endl;
                        endb = true;
                    }
                    q.erase(q.begin() + i);
                    i--;
                    break;
                }
            }
        }

        if (endb) {
            break;
        }
        totaltime++;
    }

    totaltime = 0;
    memory.clear();
    q.clear();
    memory.push_back(QProcess(-1, 1000, false));
    list = listm;

    // Best Fit
    while (true) {
        bool endb = false;
        for (int i = 0; i < list.size(); i++) {
            if (list[i].start <= totaltime) {
                q.push_back(QProcess(list[i].run, list[i].size, list[i].last));
                list.erase(list.begin() + i);
                i--;
            }
        }

        for (int i = 0; i < memory.size(); i++) {
            if (memory[i].end != -1 && memory[i].end <= totaltime) {
                memory[i].end = -1;
            }
        }

        for (int i = 0; i < memory.size() - 1; i++) {
            if (memory[i].end == -1 && memory[i + 1].end == -1) {
                memory[i].size += memory[i + 1].size;
                memory.erase(memory.begin() + i + 1);
                i--;
            }
        }

        for (int i = 0; i < q.size(); i++) {
            int minSize = INT_MAX;
            int index = -1;
            for (int j = 0; j < memory.size(); j++) {
                if (memory[j].end == -1 && memory[j].size >= q[i].size && memory[j].size < minSize) {
                    minSize = memory[j].size;
                    index = j;
                }
            }

            if (index != -1) {
                memory[index].size -= q[i].size;
                memory.insert(memory.begin() + index, QProcess(totaltime + q[i].end, q[i].size, q[i].last));
                if (memory[index].last) {
                    int sum = 0;
                    for (int k = 0; k < index; k++) {
                        sum += memory[k].size;
                    }
                    cout << sum << endl;
                    outputFile << sum << endl;
                    endb = true;
                } else {
                    q.erase(q.begin() + i);
                    i--;
                }
            }
        }

        if (endb) {
            break;
        }
        totaltime++;
    }

    totaltime = 0;
    memory.clear();
    q.clear();
    memory.push_back(QProcess(-1, 1000, false));
    list = listm;

    // Worst Fit
    while (true) {
        bool endb = false;
        for (int i = 0; i < list.size(); i++) {
            if (list[i].start <= totaltime) {
                q.push_back(QProcess(list[i].run, list[i].size, list[i].last));
                list.erase(list.begin() + i);
                i--;
            }
        }

        for (int i = 0; i < memory.size(); i++) {
            if (memory[i].end != -1 && memory[i].end <= totaltime) {
                memory[i].end = -1;
            }
        }

        for (int i = 0; i < memory.size() - 1; i++) {
            if (memory[i].end == -1 && memory[i + 1].end == -1) {
                memory[i].size += memory[i + 1].size;
                memory.erase(memory.begin() + i + 1);
                i--;
            }
        }

        for (int i = 0; i < q.size(); i++) {
            int maxSize = INT_MIN;
            int index = -1;
            for (int j = 0; j < memory.size(); j++) {
                if (memory[j].end == -1 && memory[j].size >= q[i].size && memory[j].size > maxSize) {
                    maxSize = memory[j].size;
                    index = j;
                }
            }

            if (index != -1) {
                memory[index].size -= q[i].size;
                memory.insert(memory.begin() + index, QProcess(totaltime + q[i].end, q[i].size, q[i].last));
                if (memory[index].last) {
                    int sum = 0;
                    for (int k = 0; k < index; k++) {
                        sum += memory[k].size;
                    }
                    cout << sum << endl;
                    outputFile << sum << endl;
                    endb = true;
                } else {
                    q.erase(q.begin() + i);
                    i--;
                }
            }
        }

        if (endb) {
            break;
        }
        totaltime++;
    }

    outputFile.close();
    return 0;
}
