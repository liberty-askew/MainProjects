#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Thu Feb 15 10:14:16 2018

@author: libertyaskew
"""

import random
import numpy as np

def Hint(n):
    t = 0
    for x in range (n):
        U = random.random()
        t += np.sqrt(1 - U**2)
    return (t * 4 / n)

piValuesM1 =[]
for i in range (10000):
    piValuesM1.append(Hint(1000))
mean = sum(piValuesM1) / len(piValuesM1)

import matplotlib.pyplot as plt
# Take the sample
e1s = piValuesM1
# Plot the histogram in the left of two panels
plt.subplot(121)
n, bins, patches = plt.hist(e1s, 20, normed=True)
# Plot the empirical cdf in the right of two panels

# Show the plot
plt.show()

n = len(piValuesM1)

SDerr = np.sqrt( sum( [(piValuesM1[i] - mean)**2 for i in range(n) ] )) / n


print(SDerr)


def estPi(n, disPlot):
    x = [random.random() for i in range(n)]
    y = [random.random() for i in range(n)]
    rejLabel = [x[i]**2+y[i]**2<1 for i in range(n)]
    estOut = 4*sum([1 for i in range(n) if rejLabel[i]])/float(n)
    if disPlot == True:
        plt.scatter(x,y,c=rejLabel,marker='.',lw=0.0)
        xp = [i/1000. for i in range(1000)]
        plt.plot(xp,[(1-xi**2)**0.5 for xi in xp],c='k',lw=2)
        plt.show()
    return(estOut)
(estPi(1000, False) )


piValuesM2 =[]
for i in range (10000):
    piValuesM2.append(estPi(1000,False))
print (piValuesM2)
mean = sum(piValuesM2) / len(piValuesM2)

print ('mean =', mean)
print ('var=' , np.var(piValuesM2))
# Take the sample
e1s = piValuesM2
# Plot the histogram in the left of two panels
plt.subplot(121)
n, bins, patches = plt.hist(e1s, 20, normed=True)
# Plot the empirical cdf in the right of two panels

# Show the plot
plt.show()

n = len(piValuesM2)

SDerr = np.sqrt( sum( [(piValuesM2[i] - mean)**2 for i in range(n) ] )) / n


print(SDerr)

def estPi(n):
    l = []
    for x in range(n):
        U = random.random()
        V = random.random()
        l.append( 1/(1 - U*V))
    return ( np.sqrt( sum(l) * 6 / len(l)))



piValuesM3 =[]
for i in range (10000):
    piValuesM3.append(estPi(1000))
print (piValuesM3)

n = len(piValuesM3)
mean = sum(piValuesM3) / n

print ('mean =', mean)
print ('var=' , np.var(piValuesM3))

SDerr = np.sqrt( sum( [(piValuesM3[i] - mean)**2 for i in range(n) ] )) / n

print(SDerr)

# Take the sample
e1s = piValuesM3
# Plot the histogram in the left of two panels
plt.subplot(121)
n, bins, patches = plt.hist(e1s, 10, normed=True)
# Plot the empirical cdf in the right of two panels

# Show the plot
plt.show()





def estPi(n):
    l = []
    for x in range(n):
        U = random.random()
        V = random.random()
        l.append( 1/(1 - U*V))
    return ( np.sqrt( sum(l) * 6 / len(l)))



piValuesM3 =[]
for i in range (10000):
    piValuesM3.append(estPi(1000))
print (piValuesM3)

n = len(piValuesM3)
mean = sum(piValuesM3) / n

print ('mean =', mean)
print ('var=' , np.var(piValuesM3))

SDerr = np.sqrt( sum( [(piValuesM3[i] - mean)**2 for i in range(n) ] )) / n

print(SDerr)

# Take the sample
e1s = piValuesM3
# Plot the histogram in the left of two panels
plt.subplot(121)
n, bins, patches = plt.hist(e1s, 10, normed=True)
# Plot the empirical cdf in the right of two panels

# Show the plot
plt.show()
