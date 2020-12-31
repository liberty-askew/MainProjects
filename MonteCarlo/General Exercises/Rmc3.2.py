#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri Apr 20 11:07:54 2018

@author: libertyaskew
"""
'''
import random
from math import *

def beta22SampAR1():
    while True:
        U = random.random()
        H = random.random()
        if U <= 4*H*(1-H):
            return H
    

def beta22Samp(n):
    return[beta22SampAR1() for i in range (n)]
    

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

def laplaceSamp(n):
    l=[]
    for i in range (n):
        U = random.random()
        if U > 0.5:
            l.append(log(2*U))
        else:
            l.append(-log(2-2*U))
        
    return l

def mean(l):
    return (sum(l)/len(l))
def var(l):
    ll = len(l)
    sq = [ (l[i])**2 for i in range (ll)]
    return ( (1/(ll-1))*( sum(sq) - ll*(mean(l)**2)))
'''
from math import exp
def normpdf(x):
    return exp(-x**2/2)/((2*pi)**(0.5))
def lappdf(x):
    return 0.5*exp(-abs(x))

def normalSamp1():
    while True:
        tildeX = laplaceSamp(1)
        U = random.random()
        C = 1.5 #try changing this value
        acc = normpdf(tildeX[0])/(C*lappdf(tildeX[0]))
        if U<=acc:
            return tildeX[0]
print (normalSamp1())

def normalSamp(n):
    return([normalSamp1() for i in range (n)])
    
print (normalSamp(10))

import matplotlib.pyplot as plt
# Take the sample
n2s = normalSamp(1000)
# Plot the histogram in the left of two panels
plt.subplot(121)
n, bins, patches = plt.hist(e1s, 20, normed=True)
# Plot the empirical cdf in the right of two panels
plt.subplot(122)
e1s.sort()
plt.plot(e1s, [(i+1)/float(1000) for i in range(1000)]) 
# Show the plot
plt.show()
    
