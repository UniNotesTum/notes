# Primer C++

## I/O

Use iostream library with its fundamental types istream and ostream. This
library defines four standard I/O objects: cin, cout, cerr, clog.

Namespaces allow us to avoid inadvertent collisions between the names we define
and uses if thise same names inside a library. All the names defined by the
standard library are in std.

```c++
// read until the end of file
while (std::cin >> value)
```

An `istream` that is in an invalid state will cause the condition to yield
false.

Headers, that are not from the standard library are enclosed in quotes("").

## Primitive types

Arithmetic types are divided into two categories: integral types and
floating=point types.

bool, char, wchar_t, char16_t, char32_t, short, int, long, long
long, float, double, long double

Among operations that many types support is the ability to convert objects of
the given type to other, related types.

If we use both unsigned and int values in an arithmetic expression, the int
value ordinarily is converted to unsigned.

### Literal

A value, such as 42, is known as a literal because its value self-evident. Every
literal has a type. The for and value of a literal determine its type.

We can write an integer literal using decimal, octal or hexadecimal notation.

If we write what appears to be a negative decimal literal, for example, -42, the
minus sign is not part of the literal. The minus sign is an operator that
negates the value of its operand.

The type of a string literal is array of constant char.

```c++
// multiline string literal
std::cout << "a really, really long string literal "
	          "that spans two line" << std::endl;
```

Some special characters cannot be entered directly and for them we use special
escape sequences. We can also use some more geneneralized escape sequences,
which is \x followed by one or more hexadecimal digits or a \ followed by one,
two, ore three octal digits.
The word nullptr is a pointer literal.

Uninitialized objects of built-on type defined inside a dunction body have
undefined value. Objects of class type  that we do not explicitly initialize
have a value that is defined by the class.

{ } can be used to initialize objects, they prohibit the loss of information
during this process. For instance int i = { 3.14 } is illegal.

### Variable declaration and definition

A _declaration_ makes a name known to the program. A file that wants to use a
name defined elsewhere includes a declaration for that name. A definiton creates
the associated entity.

In addition to specifying the name and type, a definition also allocates storage
and may provide the variable with an initial value.

To obtain a declartaion we add the __extern__ keyword.

_extern int i; // declares but does not define i_

__Variables must be defined exactly once, but can be declared many times.__

C++ is a statically typed language, which means that types are checked at
compile time. The process by which types are checked is referred to as type
checking.

### Identifiers and scopes

```c++
#include <iostream>

int reused = 42;

int main() {
	int unique = 0;
	std::cout << reused << " " << unique << srd::endl;
	int reused = 0; // new local object named hides global reused
	// explicitly request the global reused
	std::cout << ::reused << " " << unique << std::endl;
	return 0;
}
```

## Compound types

A compund type is a type that is defined in terms of another type. (references
and pointers etc)

The new standard introduced new kind of reference an "rvalue reference".

A reference type "refers to" another type. (&d, where d is the name being
declared)

http://stackoverflow.com/questions/57483/what-are-the-differences-between-a-pointer-variable-and-a-reference-variable-in

Both pointers and references occupy the same amound of storage. References must
be initalized immediately after declaration and cannot be redefined later
on. They are often used for function parameters and return types.

A pointer "points to" another type. Unlike a reference, a pointer is an object
in its own right. Pointers can be assigned and copied; a single pointer can
point to several different objects over its lifetime.

```c++
int ival - 42;
int *p = &ival; // p hold the address of ival
```

The second statement defines p as a pointer to int and initalizes p to point to
the int object named ival. Because references are not objects, they don't have
addresses. Hence, we may not define a pointer to a reference.

The types must match because the type of the pointer is used to infer the type
of the object to which the pointer points.

When we assign to *p, we are assigning to the object to which p points.

Preprocessor is a program that runs before the compiler. Preprocessor variables
are managed by the preprocessor, and are not part of the std namespace.

__void* Pointer__ is a special pointer type that can hold the address of any
object. There are only a limited number of things we can di with it: compare to
other pointers, pass or return from a function, assign to another void* pointer.

```c++
int i = 42;
int *p;  // p is a pointer to int
int *&r = p; // r is a reference to the pointer p
r = &i; // r refers to a pointer; assigning &i to r makes p point to i
*r = 0; .. dereferencing r yields i, the object to which p points, changes i to 0
```

The easiest way to understand the type of r is to read the definition right to
left.

### const Qualifier

It makes a variable unchangeable in code, but the variable must be directly
initialized either at runtime or compiletime.

const int buffSize = 512;

The compiler will usually replace uses of the variable with its corresponding
value during compilation.

__By default const Objects are local to a file__

To substitute the value, the compiler has to see the variable's
initalizer. Every file that uses the cont must have access to its initializer.

To define a single instance of a const variable, we use the keyword extern on
both its definiton and declaration.

```c++
// file_1.cc defines and initialized a const that is accessible to other files
extern const int buffSize = fcn();
// file_1.h
extern const int bufSize; // same bufSize as defined in file_1.cc
```

References to const types don't allow to change the referenced objects, so they
must be const as well.

We can bind a reference to const to a non const object, a literal, or more
general expression:

```c++
int i = 42;
const int &r1 = i;
const int &r2 = 42;
const int &r3 = r1 * 2;
int &r4 = r * 2; // error: r4 is a plain, non const reference
```
When we bind an int to a double the compiler creates a temporary variables that
is constand that is the integer value of the double, and to this temporary value
wird gebounded. That's why only const binding for such things is possible.

```c++
double dval = 3.14;
const int &ri = dval;
```

__A reference to const may refere to an object that is not constant.__

### Pointers to const

We may store the address of a const object only in a pointer to const.

We can use a pointer to a const to point to a non const object.

By putting _const_ after the *, we indicate that the pointer itself and not the
pointed object is constant.

```c++
int errNumb = 0;
int *const curErr = &errNumb;
const double pi = 3.14159;
const double *const pip = &pi;
```

__Top-level const__ indicates that a pointer itself is a const, __low-level__
const means that te object it points to is a const.

Top-level const is ignored by copying and low-level not.

### Constant expressions

These are expressions whose value cannot change and that can be evaluated at
compile time. A literal is a constant expression.

Under new standard we use __constexpr__ to determine if an expression is
constant.

```c++
constexpr int mf = 20; // 20 is a constant expression
constexpr int limit  = mf + 1; // mf + 1 is a constant expression
constexpr int sz = size(); // ok only if size is a constexpr function
```

For constant expressions we can use only simple types like the arithmetic,
reference and pointer types, because they are simple enough to have literal
values.
Salce_item class and the library IO and string types are not literal types.

A constexpr expression for pointers applies to the pointer and not the type it
points to.

Consexpr imposes top-level const.

```c++
constexpr int *np = nullptr; // np is a constant pointer to int that is null
int j = 0;
constexpr int i = 42; // type of i is const int
// i and j must be defined outside any function
constexpr const int *p = &i; // p is a constant pointer to const int i
constexpr int *p1 = &j; // p1 is a constant pointer to the int j
```
### Dealing with types

Type aliases are names for other types, that simplify the use of complicated
type names.

```c++
typedef double wages;
typedef wages base, *p; // base is a synonym for double, p for double*
```
The new standard give us another way to define new types:

```c++
using SI = Sales_item;
```

### Using the auto type specifier

The type is deduced from the type of the result of the expression.

```c++
auto i = 0, *p = &i; // ok: i is an int and p is a pointer on an int
auto sz = 0, pi = 3.14; // not ok, different types
```

Use __decltype__ to deduce the type of an expression.

### Defining our own data structures

```c++
struct Sales_data {
	std::string bookNo;
	unsigned units_sold = 0;
	double revenue = 0.0;
};
```

Preprocessor works with # expressions like #include. It copies before the
compiler all the code that's held in these header into the file. C++ also offers
header guards:
e
```c++
#ifndef SALES_DATA_H
#define SALES_DATA_H
struct Sales_data {
	std::string bookNo;
	unsigned units_sold = 0;
	double revenue = 0.0;
}
#endif
```

## Chapter 3. Strings, Vectors, and Arrays

A string is a variable-length sequence of characters. A vactor holds a
variable-length sequence of objects of a given type.

std::getline

size is a member of string </br>
Instead of an int it returns a string::size_type

Some classes define several companion independent types, like size_type.

```c++
string s("Hello World!!!");
for (auto &c : s)
	c = toupper(c);
cout << s << endl;

// process characters in the first word and make them big
for (decltype(s.size()) index = 0;
	 index != s.size() && !isspace(s[index]); ++index)
		 s[index] = toupper(s[index]);


// ways to initialize a vector

vector<T> v1; //default init, empty
vector<T> v2(v1); // copy
vector<T> v3(n, val);
vector<T> v4{a, b, c, ...};

// adding elements
for (int i =0; i != 100; ++i)
	v2.push_back(i);
```

It is usually better not to define a  specific size for a vector and let it grow
dynamically, it boosts its efficiency.

### Iterators

```c++
// b denotes the first element and e denotes one past the last element in v
auto b = v.begin(), e = v.end();
```
An iterator is a pointer on an element in a collection. All the operators for
iterators are overriden, so it's pretty clear what to do.

```c++
// turns the first character to an uppercase

string s("some string");
if (s.begin() != s.end()) {
	auto it = s.begin();
	*it = toupper(*it);
}

// change the first word to uppercase

for (auto it = s.begin(); it != s.end() && !isspace(*it); ++it)
	*it = toupper(*it);
```

We use != and iterators, because all of the shit in different libraries has
implemented such behaviour and thus we don't to have to worry about it not
being there.

There are two types of iterators cont_iterator and normal iterator. And you
always get the first one if the vector or something else is defined const and
you also get a constant iterator when using cbegin or cend functions.

### Arithmetic operations on iterators

You can use any additions and  subtractions that come into your mind. Everything
is overloaded.

After a subtraction of two iterators you get a difference_type.

```c++
// binary search on a sorted vector v
// key is the element
int binarysearch(vector<int> v, int key) {
	auto b = v.begin(), e = v.end();
	while (b != e) {
		auto mid = b + (b - e)/2;
		if (*mid == key) return 1;
		if (*mid < key) e = mid;
			else b = mid + 1;
	}
	return 0;
}
```

### Arrays

Arrays must be initialized with a concrete dimension, which has to be a constant
expression.

Char array have a special feature, they can be instantiated from a string, but
do not forget the special character at the end, so reserve some space for it.

Cannot initialize one array with another or assign one to another.

__Reading complicated declarations__ If there are no parethesize just read from
right to left, otherwise from inside out and then from right to left.

size_t is a machine-specific unsigned type that is guaranteed to be large enough
to hold the size of any object in memory. It's defined in cstddef.

In most expressions, when we use an object of array type, we are really using a
pointer to the first element in that array.

```c++
int ia[] = {1, 2, 3};
auto ia2(ia);
ia = 42; // error, because auto gives you int*
```

$$*ia[0] = ia$$

But!!! This conversion doesn't happen, when we use decltype(ia).

We can use pointers on arrays as iterators, to get the pointer on the first
element we just assign the array var, and the last by taking a reference to the
first nonexisting element.

The new library includes two new functions, named __begin and end__, that are global
and take an array as an argument. They did this, because otherwise it would be
error-prone.


```c++
int ia[] = {1, 2, 3};
int *beg = begin(ia);
int *last = end(ia);
```

All the operations on pointers are the same as on iterators. But the result of
two subtracting two pointers is a library type named __ptrdiff_t__.

We can use relational operators on pointers, that point to elements of the same
array, but not to unrelated objects.

```c++
int *p = &ia[2];    // p points to the element indexed by 2
int j = p[1];       // p[1] is equivalent to *(p+1)
int k = p[-2];      // p[-2] is the same as ia[0]
```

Unlike subscripts for *vector* and *string*, the index of the built-in subscript
operator is not an unsigned type.

A thing to note about multidimensional arrays. When using cool nested for (:),
all the outer loop elements are actually pointers on other arrays. If we leave
it like that, the compiler will just copy the pointers and in the end we will
get an error. So we should do the following:

```c++
for (const auto &row : ia)
	for (auto col : row)
		cout << col << endl;
```

Oh btw, let me show you some cool shit.

```c++
for (auto p = ia; p != ia + 3; ++p) {
	for (auto q = *p; q != *p + 4; ++q)
		cout << *q << ' ';
	cout << endl;
}
```


## Chapter 4

### lvalue and rvalue

lvalue means that this is is addressible in memory and rvalue is everything
else.

But it's not that simple. const objects may not be left0hand operand of an
assignment. We can always use an lvalue, when an rvalue is required.

### Order of evaluation, precedence, associativity

Assignment is right-associative.

Precedence of ++ is higher than of *.

### The sizeof operator

sizeof(type), sizeof expr.

It returns the size in bytes, the result of it is a constant expression of type
size_t.


### Named casts

static_cast, dynamic_cast, const_cast, reinterpret_cast.

dynamic_cast supports runtime type identification.

```c++
double slope = static_cast<double>(j) / i;

//convert something that the compiler cant
void* p = &d;
double *dp = static_cast<double*>(p);
```

const_cast casts away the const.

## Statements

Null statements are just a semicolon, it's helpful where the language requires a
statement, but do you don't give a shit.

### try and catch blocks

```c++
if (item1.isbn() != item2.isbn())
	throw runtime_error("Data must refer to the same ISBN");
cout << item1 + item2 << endl;
```

runtime_error type is one of the standard library exception types and is defined
in the stdexcept header. It has a member what().

If no appropriate catch is found, execution is transferred to a libraru function
named terminate. Guaranteed to stop further execution of the program.

Standard exceptions are defined in four headers. The __exception__ defines the
most general kind of exception class named exception. __Stdexcept__ defines
several general-purpose exception classes. The __new__ header defines
__bad_alloc__. __type_info__ defines __bad_cast__.

<stdexcept>: exception, runtime_error(can be detected only at run time),
range_error, overflow_error, underflow_error, logic_error, domain_error,
invalid_argument, length_error, out_of_range.

```c++
size_t count_calls() {
	static size_t ctr = 0; // value will persist across calls
	return ++ctr;
}

int main() {
	for (size_t i = 0; i != 10; ++i)
		cout << count_calls() << endl;
	return 0;
}

```

A function may be defined only once but may be declared multiple times.

Reference parameters that are not changed inside a function should be references
to const.

```c++
bool isShorter(const string &s1, const string &s2) {
	return s1.size() < s2.size();
}

//returning quasi more than one value, using references
string::size_type find_char(const string &s, char c, string::size_type &occurs)
{
	auto ret = s.size();
	occurs = 0;
	for (decltype(ret) i = 0; i != s.size(); ++i) {
		if (s[i] == c) {
			if (ret == s.size())
				ret = i;
			++occurs;
		}
	}
}
```

If you use a plain reference and not a const, you won't be able to pass on const
objects, literal, or an object that requires conversion to a plain reference
paramater.

### Arrays as paramters

Arrays cannot be copied, that's why we can only pass them as a pointer to the
first element.

Because only a pointer to the first paramter is passed, we usually don't know
the size of the array. There are three common techniques used to manage pointer
paramaters. 

1. Using a marker to specify the extent of an array

```c++
void print(const char *cp) {
	if (cp)
		while (*cp)
			cout << *cp++;
}
```

2. Using the standard library conventions

```c++
void print(const it *beg, const int *end) {
	while (beg != end) 
		cout << *beg++ << endl;
}
```

3. Explicitly passing a size parameters

```c++
void print(const int ia[], size_t size) {
	for (size_t i = 0; i != size; ++i)
		cout << ia[i] << endl;
}

int j[] = {0, 1};
print(j, end(j) - begin(j));
```

### Main

int main(int argc, char **argv) or  
int main(int argc, char *argv[])

The first element of the argv is the name of the program.

### Functions with varying paramaters

The new standard provides two ways to write these kind of functions.

You should use an initializer list in situations, when all the parameters have
the same type. And you give them in a list initializer {}. It's almost the same
as a vector, but all the elements are consts so you can't change them.

### Return

You should only return references and pointers to objects that aren't local,
otherwise these local objects get deleted after the function finishes and you
get an undefined behaviour trying to use the reference/pointer.

Reference returns are Lvalues.

```c++
char &get_val(string &str, string::size_type ix)
{
	return str[ix];
}
int main()
{
	string s("a value");
	cout << s << endl;
	get_val(s, 0) = 'A';
	cout << s << endl;
	return 0;
}
```

It's okay because the return type is a reference, thus an lvalue.

Under the new standard functions can return a braced list of values.

Return from main is an exception, if a compiler comes to the end of this
function and finds no return, it implicitly inserts return 0.

cstdlib header defines two preprocessor variables, that we can use to indicate
success or failure.

```c++
int main()
{
	if (some_failure) 
		return EXIT_FAILURE;
	else
		return EXIT_SUCCESS;
}
```

### Returning a pointer to an array

We cannot copy arrays, so we return references or pointers to them.

It's good to use aliase, because things can get intimidating.

```c++
typedef int arrT[10];
using arrT = int[10];
arrT* func(int i);
```

If we don't use them, then the syntaxix will look as follows:  
```c++
int (*func(int i))[10]; //a function that returns a pointer to an array of ten
ints
```

__Using a Trailing return type__ 

We use auto to tell the compiler that the return type will be defined
afterwards.

```c++
auto func(int i) -> int(*)[10];

//Or we can even use decltype.

int odd[] = {1, 3, 5, 7, 9};
int even[] = {0, 2, 4, 6, 8};

decltype(odd) *arrPtr(int i)
{
	return (i%2) ? &odd : &even;
}
```

### Overloaded functions

It is an error for two functions to differ only on terms of their return types.

A paramater that has a top-level const is idistiguishable from one without a
top-level const.

```c++
Record lookup(Phone);
Record lookup(const Phone); //redeclares

Record lookup(Phone*);
Record lookup(Phone* const); //redeclares
```

But the following are four overloaded functions, because const is low-level:

```c++
Record lookup(Account&);
Record lookup(const Account&); // new function takes a const ref

Record lookup(Account*);
Record lookup(const Account*); //takes a pointer to a const
```

using const_cast

```c++
string &shorterString(string &s1, string &s2)
{
	auto &r = shorterString(const_cast<const string&>(s1),
		                     const_cast<const string&>(s2));
	return const_cast<string&>(r);
}
```

Returning pointers to a funcntion

## Classes

Data abstraction relies on the separation of interface and implementation.

### Defining abstract data types

The _this_ parameter is defined for us implicitly.

By default this is a const pointer to a nonconst object. We cannot call an
ordinary member function of a const object.

If const follows a parameter list, it means that _this_ is a pointer to a const
object, and such functions are called const member functions.

Objects that are const, and references or pointers to const object, may call
only const member functions.

The compiler processes classes in two steps - the member declarations are
compiled first, after which the member function bodies, if any, are processed.

Member functions declared outside a class must match their defintions. Once the
compiler sees the function name, the rest of the code is interpreted as being
inside the scope of the class.

```c++
double Sales_data::avg_price() const {
	if (units_sold)
		return revenue/units_sold;
	else
		return 0;
}

Sales_data& Sales_data::combine(const Sales_data &rhs) {
	units_sold += rhs.units_sold;
	revenue += rhs.revenue;
	return *this;
}
```

To return an lvalue, our combine functino must return a reference.

### Defining nonmember class_related functions

Ordinarily, nonmember functions that are part of the interface of a class should
be declared in the same header as the class itself.

```c++
istream &read(istream &is, Sales_data &item) {
	double price = 0;
	is >> item.bookNo >> item.units_sold >> price;
	item.revenue = price * item.units_sold;
	return is;
}
ostream &print(ostream &os, const Sales_data &item) {
	os << item.isbn() << " " << item.units_sold << " "
		<< item.revenue << " " << item.avg.price();
	return os;
}
```

Reading or writing to a stream changes that stream, so both functions take
ordinary reference, not references to const.

By default __copying a class object copies that object's members.

### Constructors

Constructors can write to const objects during their construction.

```c++
struct Sales_data {
	Sales_data() = default;
	Sales_data(const std::string &s): bookNo(s) {}
	Sales_data(const std::string &s, unsigned n, double p): bookNo(s),
	units_sold(n), revenue(p*n) {}
	Sales_data(std::istream &);
	std::string isbn() const { return bookNo; }
	Sales_data& combine(const Sales_data&);
	double avg_price() const;
	std::string bookNo;
	unsigned units_sold = 0;
	double revenue = 0.0;
};

Sales_data::Sales_data(str::istream &is) {
	read(is, *this);
}
```
Even though the constructor initializer list is empty, the members of this
object are still initialized before the constructor body is executed.

### Copy, assignment, and destruction

Object are copied when we initialize a variable or when we pass or return an
object by value. Objects are assigned when we use the assignment
operator. Object are destroyed when they cease to exist, such as when a local
object is destroyed on exit from the block in which it was created.

These things are synthesized for us by the compiler. It just applies the give
operation to all the members of a class.

Classes that manage dynamic memory, generally cannot rely on the synthesized
versions of these operations.

If a class has a vector or a string, it still works correclty, because all the
hard work is done inside of those classes.

### Access control and encapsulation

private and public yey

__Using the class or struct keyword__

The obly difference between struct and class is the default access level. In a
class all the members before the first access specifier are private and in a
struct it's the other way around.

### Friends

Now that the data members of Sales_data are private, our read, print, and add
functions will no longer compile. A class can allow another class or function to
access its nonpublic members by making that class or function a friend.

A friend declaration only specifies access, we must declare a function
separately. We usually declare each friend in the same header as the class
itself.

### Class members revisited

A class can define its own local names for types. Unlike ordinary members,
members that define types must appear before they are used.

```c++
class Screen {
public:
	using pos = std::string::size_type;
private:
	pos cursor = 0;
	pos height = 0, width = 0;
	std::string contents;
}
```

We can specify inline on the definition of the function.

Even if we want to change a member inside a const function, we declare them
_mutable_.

We can call only const functions on cons objects, and both for nonconst, but
nonconst functions are a better match in this case.

```c++
class Screen {
public:
	Screen &display(std::ostream &os) { do_display(os); return *this; }
	const Screen &display(std::ostream &os) const {do_display(os);return *this;}
private:
	void do_display(std::ostream &os) const {os << contents;}
};
```

A class is considered defined as soon as its name in the declaration is
seen. Members of the class cannot be of its own type, because the class type is
incomplete at that time, but we can have pointers or references of itself.

### Friendship between classes

```c++
class Screen {
	// Window_mgr members can access the private parts of class Screen
	friend class Window_mgr;
}
```

Friendship is not transitive.

Rather than making the entire class a friend, Screen can specify which specific
function does it want to make a friend.

Each overloaded function must be a friend if you want to use it.

```c++
// overloaded storeOn functions
extern std::ostream& storeOn(std::ostream &, Screen &);
extern BitMap& storeOn(BitMap &, Screen &);
class Screen {
	friend std::ostream& storeOn(std::ostream &, Screen &);
};
```

A friend declaration affects access but is not a declaration in an ordinary
sense.

Even if we define the function inside the class, we must still provide a
declaration outside of the class itself to make that function visible. A
declaration must exist even if we only call the friend from members of the
friendship granting class.

### Class Scope

Yeah nothing new here.

In a class:

+ First, the member declarations are compiled.
+ Function bodies are compiled only after the entire class has been seen.

This applies only to names used in the body of a member function. Names used in
declarations, including names used for the return type and types in the
parameter list, must be seen before they are used.

An inner scope can redefine a name from an outer scope even if that name has
already been used in the inner scope. Howeverm in a class, if a member uses a
name from an outer scope and that name is a typem then the class may not be
subsequently redefine that name.

__Using constructor initializers__

Some members need to be initialized directly and not just given a value
assigned. (consts, references)

It is a good idea to write constructor initializers in the same order as the
members are declared. Moreover, when possible, avoid using members to initialize
other members.

The new std lets us delegate constructors.

```c++
class Sales_data {
public:
	Sales_data(std::string s, unsigned cnt, double price):
		bookNo(s), units_sold(cnt), revenue(cnt*price) {}
	Sales_data(): Sales_data("", 0, 0) {}
	Sales_data(std::string s): Sales_data(s, 0, 0) {}
	Sales_data(std::istream &is): Sales_data() {
	   read(is, *this);
	}
};
```

When a constructor delegates to another constructor, the constructor initializer
list and function body of the delegated-to constructor are both executed.

### Implicit class-type conversions

A constructor that can be called with a single argument defines an implicit
conversion from the contructor's parameter type to the class type.

```c++
string null_book = "9-999-9999-9";
// constructs a temporary Sales_data object
// with units_sold and revenue equal to 0 and bookNo equal to null_book
item.combine(null_book);

//but this is not allowed, because it requires two conversions
// converts "..." to string and then to Sales_data
item.combine("9-999-9999-9"); // error!!!
```

To prevent such automatic conversion, we use the keyword _explicit_.

It is meaningful only on constructors that can be called with a single
argument. And it is only allowed on the definition inside a class header.

### Aggregate classes

All of its members are public, doesn't contain any constructors, has no in-class
initializers, has no base classes or virtual functions.

```c++
struct Data {
	int ival;
	string s;
};
```

### Literal classes

In addition to the arithmetic types, references, and pointers, certain classes
are also literal types.

+ The data members all must have literal type
+ The class must have at least one constexpr constructor
+ If a data member has an in-class initializer, the initlializer for a member of
  built-in type must be a constant expression, or if the member has class type,
  the initializer must use the member's own constexpr contructor.
+ The class must use default definition for its destructor.

A literal type is a type that can qualify as constexpr.

### Static

Static member functions are not bound to any object; they do not have a this
pointer. 

We can access a static member direclty through the scope operator. But we can
also use an object, reference, or a pointer of the class type to access a static
member.

The keyword appears only with the declaration inside the body.

In general we may not initialize a static member inside the class. Instead, we
must define and initialize each static data member outside the class body. Like
any other object, a static data member may be defined only once.

```c++
// define and initialize a static class member
double Account::interestRate = initRate();
```

The best way to ensure the objects is defined exaclty once is to put the
definition of static data members in the same file that contains the definitions
of the class noninline member functions.

We can provide in-class initializers for static members that have const integral
type and must do so for static members that are constexprs of literal type.

If an initializer is provided inside the class, the member's defintion must not
specify an initial value. Even if a const static data member is initialized in
the class body, that member should be defined outside the class definition.

A static member can even be of the same type as the class in which it is
declared.

Another difference between static and ordinary members if that we can use a
static member as a default argument.

```c++
class Screen {
public:
	Screen& clear(char = bkground);
private:
	static const char bkground;
};
```

# The C++ Library

## The IO Library

Three separate headers: _iostream_ defines the basic types used to read from and
write to a stream, _fstream_ defines the types used to read and write named
files, and _sstream_ defines the types used to read and write in-memory strings.

To support languages that use wide characters, the library defines a set of
types and objects that manipulate wchar_t data.

No copy or assign for IO Objects.

We can use the stream as long as it is in a valid state, which we can check
using a condition.

```c++
while (cin >> word)
```

The condition checks the state of the stream returned from the >> expression. 

If we want to get a detailed information on what happened: The mIO library
defines a machine-dependent integral type named iostate that it uses to convey
info about the state of a stream. The library also defines four constexpr values
of type iostate that represent particular patterns(goodbit, badbit, failbit, reching
end-of-file sets both eofbit and failbit). It also gives us a set of functions
to interrogate the state of these flags(good, bad, fail, eof).

### File modes

in, out, app, ate, trunc, binary

When an ostream is opened without any speicifiers for a mode it automatically
get out and trunc modes and the file gets overwritten.

```c++
ofstream("file1"); // out and trunc are implicit
ofstream("file1", ofstream::out);
ofstream("file1", ofstream::out | ofstream::trunc);
```

### String streams (sstream header)

Defined types: istringstream, ostringstream, stringstream.

### istringstream

An istringstream is often used when we have some work to do on an entire line,
and other work to do with individual words within a line.

```c++
// morgan 123123123 12312
// zambo 123342 23 1 1 23 12312

struct PersonInfo {
	string name;
	vector<string> numbers; 
};

string line, word;

while (getline(cin, line)) {
	PersonInfo info;
	istringstream record(line);
	istringstream >> info.name;
	while (record >> word)
		info.numbers.push_back(word);
	people.push_back(info);
}

```

### ostringstream

Is useful when we need to build up our output a little at a time, but do not
want to print the output until later. 

```c++
// checking for validity of some those numbers
for (const auto &entry : people) {
	ostringstream formatted, badnums;
	for (const auto &nums : entry.phones) {
		if (!valid(nums)) {
			badnums << " " << nums;
			} else
				formatted << " " << format(nums);
	}
	if (badnums.str().empty()) {
		os << entry.name << " " << formatted.str() << endl;
	else
		cerr < "input error: " << entry.name << " invalid number(s) " <<
		badNums.str() << endl;
}
}
```

## Sequential Containers

The order in these containers correspond to the position at which elements are
put into the container.

+ vector - flexible-size array, supports fast random access. Insertin or
  deleting elements other than at the back may be slow. Implementation: array.
  
+ deque - double-ended queue
+ list - doubly linked list. Fast insert/delete at any point in the list.
+ forward_list - singly linked list
+ array - fixed-size array
+ string - similar to vector, that contains characters

string and vector hold their elements in contiguous memory.

Unless you have a reason to use another container, use a vector.

Operations on all container types:

Type aliases:

+ iterator
+ const_iterator
+ size_type
+ difference_type
+ value_type
+ reference
+ const_reference

Construction:

+ C c;
+ C c1(c2); Construct c1 as a copy of c2
+ C c(b, e); Copy elements fromt the range denoted by iterators
+ C c{a,b,c...}; List initialize c

Assignment ans swap:

+ c1 = c2; //replacement
+ c1 = {a,b,c...};
+ a.swap(b);
+ swap(a, b); equivalent to the previous one

Size:

+ c.size();  // number of elements(not valid for forward_list)
+ c.max_size(); // max number of elements c can hold
+ c.empty();

Add/remove elements(not valid for array):  
Note: the interface varies

+ c.insert(args); // copy elements as specified by args into c
+ c.emplace(inits); // use inits to construct in c
+ c.erase(args);
+ c.clear(); // remove all elements from c

Obtain iterator:

c.begin(), c.end()  
c.cbegin(), c.cend()  

There are also additional members for reversible containers.

We can define a container for a type that does not support an operation-specific
requirement, but we can use an operation only if the element type meets that
operation's requirements.

### Iterators

Iterator range - pair of iterators, begin and end. End refers to the one past
one element.

When write access is not needed, use _cbegin_ and _cend_.

When we initialize a container as a copy of another container, the container
type and element type of both containers must be identical. But this doesn't
apply to iterator initialization.

```c++
list<string> authors = {"Milton", "Shakespeare", "Austen"};
vector<const char*> articles = {"a", "an", "the"};
list<string> list2(authors); //ok
deque<string> authList(authors); //error
//ok: converts const char* elements to string
forward_list<string> words(articles.begin(), articles.end());
```

Under the new standard we can list initialize a container.

Array has fixed size.

```c++
array<int, 42>
array<string, 10>
array<int, 10>::size_type i;
```

To use an array type, we must both specify the type and the size.

Using assign (sequential containers only)

### Strings

## Algorithms

All algorithms are container independent. They only use iterators on those
containers, whichhave their own overloaded operations.

These algs do not delete or insert elements, they can rearrange them or some
other stuff like that.

But there is a special iterator called the insert iterator, which may append the
underlying data structures. 

```c++
auto it = back_inserter(vec); // assigning through it adds elements to vec
*it = 42; // vec now has one element with 42

// read-only algs
// sum the elements in vec starting the summation with the value 0
int sum = accumulate(vec.cbegin(), vec.cend(), 0);

// operates on two sequences, assumes the second sequence has enough elements
equal(roster1.cbegin(), roster1.cend(), roster2.cbegin());

// algs that write to cont types
fill(vec.begin(), vec.end(), 0);
fill(vec.begin(), vec.begin() + vec.size()/2, 10);
// they do not check before they write, so be cautious

//copy
int a1[] = {0,1,2,3,4,5,6,7,8,9};
int a2[sizeof(a1)/sizeof(*a1)];
auto ret = copy(begin(a1), end(a1), a2);
// the value returned is the value of its destination iterator

// replace algs..

// use back_inserter to grow destination as needed
replace_copy(list.cbegin(), list.cend(), back_inserter(ivec), 0, 42);
// it lets the input container stay the same

// sorted
// unique
```

### Customizing operations

### Passing a function to an algorithm

Assuming we want to sort a vector by length, we can use the overloaded function,
which takes the third argument that is a __predicate__.

It's an expression that can be called and that returns a value that can be used
as a condition. _Unary or binary predicates_ are used.

```c++
bool isShorter(const string &s1, const string &s2) {
	return s1.size() < s2.size();
}

sort(words.begin(), words.end(), isShorter);
```

stable_sorts makes equal words remain on their old positions relatively to each
other.

find_if takes an unary predicate as the third argument. 

Callables: functions, function pointers, classes that overload the function-call
operator, and __lambda expressions__.

```
[captue list] (parameter list) -> return type { function body }
```

Capture list is an (often empty) list of local variables defined in the
enclosing function. WE may not omit the capture list and the return body.

```c++
auto f = [] { return 42; }
cout << f() << endl;
```

If we omit the return type, the lambda has an inferred return type that depends
on the code in the funciton body. If the function body is just a return
statemenet, the return type is inferred form the type of the expression that is
returned. Otherwise, the return type is void.

Unlike ordinary functions a lambdas may not have deafult arguments.

```c++
[] (const string &a, const string &b) {
	return a.size() < b.size();
}

auto wc = find_if(words.begin(), words.end(), [sz](const string &a) {
	return a.size() >= sz;
});
```

The call to find_if returns an iterator to the first element that is at least as
long as the given sz, or a copy of words.end() if no such elements exists.


make_plural, for_each

The capture list is used for local nonstatic variables only; lambdas can use
local statics and variables declared outside of the function directly.

When we pass a lambda to a function, we are defining both a new type and an
object of that type.

We can both capture by reference or by value.

Let the compiler do the implicit catch by just typing in & or =.

When we mix implicit and explicit captures, the explicit;y captured variables
must use the alternate form.

By default, a lambda may ot change the value of a variable that it copies by
value. If we want to be able to change the value of a captured variable, we must
follow the parameter list with the keyword _mutable_.

Trailing returns.

```c++
transform(vi.begin(), vi.end(), vi.begin(), [](int i) -> int {
	if (i < 0) return -i; else return i; }
});
```

The transform function takes three iterators and a callable. The first two
iterators denote an input sequence and the third a destination. The alg calls
the given callable on each element in the input sequence and wruites the result
to the destination.

### Binding functions

The functional header defines a function called bind, which takes our function
as the first parameter and another parameter arg_list, which can have _n, which
ae placeholders for our parameters. This bind return another callable, that
calls our function.

```c++
auto check6 = bind(check_size, _1, 6);
string s = "hello";
bool b1 = check6(s); // check6(s) calls check_size(s, 6)

// instead of a lambda expression
auto wc = find_if(words.begin(), words.end(), [sz](const string &sz)...)

auto wc = find_if(words.begin(), words.end(), bind(check_size, _1, sz));
```

The _n names are defined in a namespace named _placeholders_, which is itself
defined inside the std namespace. 

We should have used at the beginning:

```c++
using std::placeholders::_1;
```

### Revisiting iterators

The library defines several additional kinds of iterators in the iterator
header.



