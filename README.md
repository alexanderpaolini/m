# m - A Functional, Math-Oriented Programming Language

The **m** programming language is a minimalistic, functional programming language designed to perform mathematical operations and simplify computations.

---

## Table of Contents

1. [Overview](#overview)
2. [Features](#features)
3. [Basic Syntax](#basic-syntax)
4. [BNF Grammar](#bnf-grammar)
5. [Examples](#examples)
6. [Usage](#usage)
7. [Contributing](#contributing)
8. [License](#license)

---

## Overview

**m** is a simple and intuitive programming language optimized for math-centric operations. It allows users to easily define functions, perform arithmetic, and loop over ranges in a concise, functional style. The language is designed with minimalism in mind—providing only the essential features required for mathematical computations. It will be a compiled language down to a bytecode, executed by a custom virtual machine.

---

## Features

- **Functional Style**: **m** encourages functional programming principles, where functions are first-class citizens.
- **Basic Arithmetic**: Supports addition, subtraction, multiplication, division, and exponentiation.
- **Variables**: Use variables to store and manipulate data.
- **Lists**: Define and manipulate lists of numbers.
- **Loops**: Both `while` and `for` loops are supported for iteration.
- **Built-in Standard Library**: Access to standard computational and mathematical methods and constants.
- **Alias System**: Easily alias functions or constants for cleaner code.
- **Simple Syntax**: Easy-to-read syntax inspired by modern functional programming languages.

---

## Basic Syntax

### 1. Comments

Comments are written using `#`. Everything after `#` on a line is ignored.

```txt
# This is a comment
```

### 2. Printing Output

The `>` symbol is used to print output to the terminal.

```txt
> 5 + 5  # Outputs: 10
```

### 3. Variable Definitions

Variables are declared using `=`. You can store the result of an expression in a variable.

```txt
x = 9 + 10
y = x + 1  # Valid
```

### 4. Lists

Lists are created using square brackets `[]`.

```txt
l1 = [1, 2, 3]
> l1  # Outputs: [1, 2, 3]
```

### 5. Block scope

Scopes can be created using the curly brackets `{}`.

```txt
x = 1
{
    y = 2
    x = 2
}
> x # Outputs: 2
> y # error: 'y' is not defined
```

### 6. Control flow

If-else statements are supported!

```txt
x = 1
if (x == 0): {
  > 1
} else: {
  > 2
}
```

### 7. Function Definitions

Functions are defined using the syntax `name(paramaters...): statement | { statement* }`.

```txt
f(x): x + 1  # Function that adds 1 to x

g(x): {
  a = x ** 2 + 3 * x + 5
  a + 1  # Returns the result of the expression
}
```

### 7. Loops

#### While Loop

```txt
while (x): x = f(x)  # Continuously modify x until the condition is false
```

#### For Loop

For loops, by design, only support the `for x in y` syntax. To iterate, the range function must be used like in python.

```txt
for x in range(0, 10): {
  > x  # Outputs: 0, 1, 2, 3, 4, 5, 6, 7, 8, 9
}
```

### 9. Namespaces

Namespaces allow functions and constants to be grouped together. They can be nested to allow for groups inside groups.

```txt
# Example Standard Library Namespace
namespace std {
    namespace trig {
        # ...
        sin(x): {} # Computes and returns sin(x)
        # ...
    }

    namespace constants {
        # ...
        PI = 3.1415 # ... 
        # ...
    }
}
```

### 10. Import Statements

Import statements begin with the `!include "path"` statement. They do this because I want them to. There is no particular reason to do this over `include("path")` or `include "path"`.

```txt
!include "std/trig.m"  # Import external libraries
!include "std/constants.m"
```

### 11. Aliases

You can create aliases for functions or constants using the `alias` keyword.

```txt
alias sin std::trig::sin
alias PI std::constants::PI

> sin(PI / 2)  # Equivalent to std::trig::sin(std::constants::PI / 2)
```

---

## BNF Grammar

As the language has not yet been constructed (and I am unfamiliar with the full process), this BNF specification is not completely accurate.

```bnf
<program> ::= <statement>*

<value_producing_statement> ::= <expression>
                            | <block_statement>
                            | <if_statement>

<statement> ::= <print_statement>
            | <block_statement>
            | <variable_declaration>
            | <function_declaration>
            | <loop_statement>
            | <import_statement>
            | <alias_statement>
            | <value_producing_statement>

<print_statement> ::= ">" <expression>

<block_statement> ::= "{" <statement>* "}"

<variable_declaration> ::= <identifier> "=" <expression>

<if_statement> ::= "if" "(" <expression> ")" ":" <value_producing_statement> ("else" ":" <value_producing_statement>)?

<function_declaration> ::= <identifier> "(" <identifier_list> ")" ":" <expression>
                        | <identifier> "(" <identifier_list> ")" ":" "{" <statement>* "}"

<identifier_list> ::= <identifier> ("," <identifier>)*

<while_loop_statement> ::= "while" "(" <expression> ")" ":" <statement>*

<for_loop_statement> ::= "for" <identifier> in <identifier> ":" <statement>*

<import_statement> ::= "!include" <string>

<alias_statement> ::= "alias" <identifier> <string>

<expression> ::= <term> (<additive_operator> <term>)* (<comparison_operator> <term>)*

<term> ::= <factor> (<multiplicative_operator> <term>)*

<factor> ::= <number>
         | <identifier>
         | "(" <expression> ")"
         | <function_call>
         | <list_literal>
         | <list_access>
         | <value_producing_statement>

<additive_operator> ::= "+" | "-"

<comparison_operator> ::= "<" | "<="  | ">" | ">=" | "==" | "!="

<multiplicative_operator> ::= "*" | "/"

<unary_operator> ::= "!" | "-"

<function_call> ::= <identifier> "(" <expression_list> ")"

<expression_list> ::= <expression> ("," <expression>)*
                  | ε

<list_literal> ::= "[" <expression_list> "]"

<list_access> ::= <identifier> "[" <expression> "]"

<identifier> ::= <letter> <letter_or_digit>*

<letter> ::= "a" | "b" | "c" | "d" | "e" | "f" | "g" | "h" | "i" | "j" | "k" | "l" | "m" | "n" | "o" | "p" | "q" | "r" | "s" | "t" | "u" | "v" | "w" | "x" | "y" | "z"
          | "A" | "B" | "C" | "D" | "E" | "F" | "G" | "H" | "I" | "J" | "K" | "L" | "M" | "N" | "O" | "P" | "Q" | "R" | "S" | "T" | "U" | "V" | "W" | "X" | "Y" | "Z"

<letter_or_digit> ::= <letter> | <digit>

<digit> ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"

<number> ::= <digit>+
          | <digit>* "." <digit>+

<string> ::= "\"" <character>* "\""

<character> ::= <letter> | <digit> | <symbol> | <escape_sequence>

<escape_sequence> ::= "\\" ("\"" | "\\" | "/" | "n" | "t" | "r")

<symbol> ::= "+" | "-" | "*" | "/" | "^" | ">" | "(" | ")" | "[" | "]" | "," | ":" | "=" | "{" | "}" | "!" | "#" | "<" | ">" | "|"
```

---

## Examples

Here are a few examples to demonstrate the syntax and capabilities of **m**:

### Example 1: Basic Arithmetic and Functions

```txt
x = 5
y = x * 2
> y  # Outputs: 10

f(x): x + 1
z = f(y)
> z  # Outputs: 11
```

### Example 2: Working with Lists

```txt
l1 = [1, 2, 3, 4]
l2 = [10, 20, 30]
> l1 + l2  # Outputs: [1, 2, 3, 4, 10, 20, 30]
```

### Example 3: Defining and Using Functions

```txt
f(x): x^2 + 2*x + 1
g(x): {
  a = x^3 + 3*x^2 + 5
  a + 1
}

result = g(f(2))  # Apply f first, then g
> result  # Outputs: 206
```

### Example 4: Loops

```txt
for x in range(1, 5): {
  > x  # Outputs: 1, 2, 3, 4
}

x = 5
while (x): {
  x = f(x)  # Compute f(x) until f(x) is 0
}
```

### Example 5: Simple Mathematics

```txt
# ./prog1.m

fib(n): {
  a = 0
  b = 1
  i = 0
  
  while (i < n): {
    temp = a
    a = b
    b = temp + b
    i = i + 1
  }
  
  a
}

for i in range(0, 100): {
  > fib(i)
}
```

### Example 6: Simple Programming

```txt
# ./prog2.m

quick_sort(l): {
  if (length(l) <= 1): 
    return l
  
  pivot = l[0]
  
  smaller = []
  greater = []
  
  for x in l[1:]:
    if (x < pivot): 
      smaller = smaller + [x]
    else:
      greater = greater + [x]
  
  quick_sort(smaller) + [pivot] + quick_sort(greater)
}

# Initialize a list with some unsorted values
l = [9, 2, 5, 7, 1, 4, 8]

> quick_sort(l)
```

---

## Usage

There is way to use m yet :(

---

## Contributing

This project was created for the Knight Hacks Project Launch 2025. Before submission date code cannot be contributed from anyone not in the team. As such, please do not attempt to contribute until then.

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
