# m also supports conditionals
fn f(x): {
    x * x * 3 + 7 * x + 8
}

>> f(7)
>> f(8)

# If f(7) is less than f(8)
if (f(7) < f(8)) {
    >> 7
} else {
    >> 8
}