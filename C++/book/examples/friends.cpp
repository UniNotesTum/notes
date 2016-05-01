#include<iostream>

class Person {
public:
	Person() = default;
	Person(int number, std::string name): _number(number), _name(name) {}
	Person(std::istream&);
private:
	int _number = 0;
	std::string _name;
	friend std::istream &read(std::istream&, Person&);
	friend std::ostream &print(std::ostream&, const Person&);
};

std::istream &read(std::istream &input, Person& person) {
	input >> person._number >> person._name;
	return input;
}

std::ostream &print(std::ostream &output, const Person& person) {
	output << person._name << " " << person._number << std::endl;
	return output;
}

Person::Person(std::istream &input) {
	read(input, *this);
}

int main() {
	Person p(std::cin);
	print(std::cout, p);
}





