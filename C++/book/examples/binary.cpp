#include<iostream>
#include<vector>

using namespace std;

// binary search on a sorted vector v
// key is the element
int binarysearch(const vector<int>& v, int key) {
	auto b = v.begin(), e = v.end();
	while (b != e) {
		auto mid = b + (b - e)/2;
		if (*mid == key) return 1;
		if (*mid < key) e = mid;
			else b = mid + 1;
	}
	return 0;
}

int main() {
	vector<int> vect = {1, 2, 3, 4, 5, 6, 7, 8, 9};
	cout << binarysearch(vect, 3) << endl;
}
