#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri Apr 20 14:39:40 2018

@author: libertyaskew
"""
'''
import random
s=0

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
'''
'''
import random
import numpy as np


def var(l):
    ll = len(l)
    sq = [ (l[i])**2 for i in range (ll)]
    return ( (1/(ll-1))*( sum(sq) - ll*(mean(l)**2)))

def h(n):
    UV = ([ [random.random() , random.random()] for i in range (n)])
    h=[]
    for i in range (n):
        l = UV[i]
        h.append( (1 - l[0] * l[1] ) ** (-1) )
    return ( np.sqrt(sum(h) * 6/ n))
'''

import random
import numpy as np
import matplotlib.pyplot as plt
 
def Hint(n):
    t = 0
    for x in range (n):
        U = random.random()
        t += np.sqrt(1 - U**2)
    return (t * 4 / n)

piValuesM1 =[]
CMM1 = []
CM = 0
for i in range (100):
    piValuesM1.append(Hint(100))
    if i>0:
        CM += sum(piValuesM1) / len(piValuesM1)
        CMM1.append(CM)
        
        
plt.plot(CMM1)


def beta(n):
    T = [ random.random() for i in range (n)]
    I = [ ((T[i]**(-0.5)) * ((1-T[i])**(-0.5))) for i in range (n)]
    return( sum(I) / n)


piValuesM5 = []
for i in range(100):
    piValuesM5.append(beta(100))
    
    
    
def cPerc(vec,q):
    return [np.percentile(vec[:i],q) for i in range(1,len(vec))]
    
print (cPerc(CMM1, 0.975))

plt.plot(CMM1)
plt.show()

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    