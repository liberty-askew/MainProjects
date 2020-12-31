#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri Apr 20 12:28:56 2018

@author: libertyaskew
"""
'''
import numpy as np

def discUnifSamp(n,N):
    probN = [float(i)/(N) for i in range(N)]
    sampU = [random.random() for i in range(n)]
    isGreater = [sum([bb<U for bb in probN]) for U in sampU]
    return isGreater
print (discUnifSamp(7,100))

import matplotlib.pyplot as plt
# Take the sample
b2s = beta22Samp(1000)
# Plot the histogram in the left of two panels
plt.subplot(121)
n, bins, patches = plt.hist(e1s, 20, normed=True)
# Plot the empirical cdf in the right of two panels
plt.subplot(122)
e1s.sort()
plt.plot(e1s, [(i+1)/float(1000) for i in range(1000)]) 
# Show the plot
plt.show()

'''
def discSamp1(x,p):
    probN = [sum(p[:i]) for i in range(1,len(x)+1)]
    sampU = random.random()
    isGreater = sum([bb<sampU for bb in probN])
    return x[isGreater]
print (discSamp1([1,2,3,4,5],[0.2,0.2,0.2,0.2,0.2]) )


from math import exp, factorial
def poissonSamp_exp1(lam):
    L = exp(-lam)
    k=0
    p=1
    while p>L:
        k = k+1
        p = p*random.random()
    return k-1

print (poissonSamp_exp1(0.8))

def poissonSamp_exp(n , lam):
    return [ poissonSamp_exp1(lam) for i in range (n)]

print (poissonSamp_exp(6  , 2))