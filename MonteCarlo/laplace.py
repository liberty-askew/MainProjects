#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Mon Feb 12 12:30:40 2018

@author: libertyaskew
"""
import random 
import numpy as np


def f(x):
    if x < 0:
        return (0.5*np.exp(x))
    if x >= 0:
        return (1 - 0.5*np.exp(-x))

def laplaceSamp(n):
    l = []
    for x in  range (n):
        u = (2*random.random() - 1)
        l.append(f(u))
    return l

            
import matplotlib.pyplot as plt
# Take the sample
e1s = laplaceSamp(10000)
# Plot the histogram in the left of two panels
plt.subplot(121)
n, bins, patches = plt.hist(e1s, 20, normed=True)
# Plot the empirical cdf in the right of two panels

# Show the plot
plt.show()
    
        
