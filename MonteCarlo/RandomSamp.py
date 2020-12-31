#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri Apr 20 14:17:50 2018

@author: libertyaskew
"""

from random import randint
def coinToss(n):
    return [randint(0,1) for i in range(n)]
coinSamp = coinToss(100)

def b2d(b):
    i = 0
    d = []
    while i < (len(b) ):
        if b[i]==1:
            d.append(float(2**(-(i+1))))
        i+=1
    return (sum(d))

def sampUnif(N , n):
    return [ b2d(coinToss(n)) for i in range(N)]

'''
test2bit = sampUnif(1000,10)
import matplotlib.pyplot as plt
plt.subplot(121)
n, bins, patches = plt.hist(test2bit,20,normed=1)
plt.subplot(122)
plt.plot(test2bit)
plt.show()
'''

from random import random #Snippet 2.7.3
import matplotlib.pyplot as plt
def sampCircle(n):
    x=[random() for i in range(n)]
    y=[random() for i in range(n)]
    rejLabel = [x[i]**2+y[i]**2<1 for i in range(n)]
    estPi = 4*sum([1 for i in range(n) if rejLabel[i]==True])/float(n)
    plt.scatter(x,y,c=rejLabel,marker='.',lw = 0.0)
    xp = [i/1000. for i in range(1000)]
    plt.plot(xp,[(1-xi**2)**(0.5) for xi in xp],c='k',lw=2)
    plt.show()
    return [x,y,rejLabel,estPi]


def sampCircleReg(sn): #Snippet 2.7.4
    n = int(sn**(0.5))
    x=[i/float(n-1) for i in range(n)]*n
    y=[i/float(n-1) for i in range(n) for j in range(n)]
    rejLabel = [x[i]**2+y[i]**2<1 for i in range(len(x))]
    estPi = 4*sum([1 for i in range(len(x)) if
                   rejLabel[i]==True])/float(len(x))
    plt.scatter(x,y,c=rejLabel,marker='.',lw = 0.0)
    xp = [i/1000. for i in range(1000)]
    plt.plot(xp,[(1-xi**2)**(0.5) for xi in xp],c='k',lw=2)
    plt.show()
    return [x,y,rejLabel,estPi]

stx=sampCircle(10000)
stx2=sampCircleReg(10000)
print ( stx[3] , stx2[3] )