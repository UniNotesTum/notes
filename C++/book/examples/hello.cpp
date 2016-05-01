#include<iostream>

int *arr;

auto get_array() -> int*& {
	int a[] = {1, 2, 3};
	arr = a;
	return arr;
}

	int main() {
		//should auto make that variable a reference without me writing it down explicitly?
		auto &a = get_array();
	  a = new int[1];
		a[0] = 5;
		std::cout<< *arr <<std::endl;
		return 0;
	}
	
