//
//  DynamicField.cpp
//  Structures
//
//  Created by Yuriy Arabskyy on 26/04/16.
//
//
#include <stdexcept>
#include <stdio.h>
#include <iostream>

template<typename T>
class DynamicField {
    
private:
	unsigned capacity;
	unsigned n;
	constexpr static unsigned factor = 2;
    
	T *b = new T[capacity];
    
public:
    
	DynamicField():capacity(1), n(0) {

	}
    
	~DynamicField() {
		delete[] b;
	}
    
	T& get(unsigned i) const {
		if (i > n) {
			throw std::out_of_range("Index out of range");
		}
		return b[i];
	}
    
	void set(unsigned i, T value) {
		if (i > n) {
			throw std::out_of_range("Index out of range");
		}
		b[i] = value;
	}
    
	void push_back(T value) {
		if (n == capacity) reallocate(factor*capacity);
		b[n++] = value;
	}
    
	T pop_back() {
		if (n == 0) throw std::exception();
		T element = b[n--];
		if (n < capacity/4) reallocate(capacity/factor);
	}
    
	unsigned getCount() { return n; }
	unsigned getCapacity() { return capacity; }
    
private:
	void reallocate(unsigned size) {
		T *arr = new T[size];
		for (int i = 0; i < n; i++) {
			arr[i] = b[i];
		}
		delete[] b;
		capacity = size;
		b = arr;
	}
};

int main() {
	DynamicField<int> field;
	for (int i = 0; i < 10; i++)
		field.push_back(i);
    
	for (int i = 0; i < 10; i++)
		std::cout << field.get(i) << " ";
    
	std::cout << std::endl << field.getCapacity() << std::endl;
}
