# -*- coding: utf-8 -*-
"""
Spyder Editor

This is a temporary script file.
"""

import random 
from math import log

def expSamp(n , lam):
    l = []
    for p in range (n):
        u = random.random()
        v = (-1/lam)*log(u)
        l.append(v)
    return l

print (expSamp(1000,2))


def mean(x):
    n = len(x)
    return( sum(x) / n)
print (mean(expSamp(1000,2)))


def var(x):
    n = len(x)
    y = [(x[i] - mean(x))**2 for i in range(n)]
    return( sum(y) / (n-1))

print(var(expSamp(1000,2)))

import matplotlib.pyplot as plt
# Take the sample
e1s = expSamp(1000,2)
# Plot the histogram in the left of two panels
plt.subplot(121)
n, bins, patches = plt.hist(e1s, 20, normed=True)
# Plot the empirical cdf in the right of two panels
plt.subplot(122)
e1s.sort()
plt.plot(e1s, [(i+1)/float(1000) for i in range(1000)]) 
# Show the plot
plt.show()