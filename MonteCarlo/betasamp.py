# -*- coding: utf-8 -*-
"""
Created on Tue Jan 30 09:51:40 2018

@author: rfnb51
"""

import random

def beta22SampAR1(n):
    l=[]
    i=0
    while i < n:
        r1 = random.random()
        r2 = random.random()
        if r1 <= (3*r2*(1-r2)):
            i +=1
            l.append(r2)       
    return l
    
print (beta22SampAR1(15))

import matplotlib.pyplot as plt
# Take the sample
e1s = beta22SampAR1(10000)
# Plot the histogram in the left of two panels
plt.subplot(121)
n, bins, patches = plt.hist(e1s, 20, normed=True)
# Plot the empirical cdf in the right of two panels

# Show the plot
plt.show()
    
    