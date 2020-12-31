#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Wed Apr 18 20:32:57 2018

@author: libertyaskew
"""
import random
random.random()

r10=[]
for i in range (10):
    r10.append(random.random())
    

def rbern1(p):
    u = random.random()
    if u<=p:
        return 1
    else:
        return 0 
    
def rbern(N,p):
    l = []
    for i in range (N):
        l.append(rbern1(p))
    return (l)

'''
R = rbern(1000 , 0.5)
Clist = []
sum = 0
for i in range (1000):
    sum += R[i]
    Clist.append(sum)
print (Clist)

import matplotlib.pyplot as plt
xlist = [i for i in range (1000)]
plt.plot(xlist , Clist)
plt.show
'''

def bern_11n(N,p):
    R = rbern(N,p)
    Clist = []
    sum = 0
    for i in range (N):
        sum += R[i]
        Clist.append(sum)
    xlist = [i for i in range (N)]
    plt.plot(xlist , Clist)
    plt.show()
    
print (bern_11n(1000,(1-(1/1000))))
    

def rbinom1(n,p):
    return sum(rbern(n,p))

def rbinom(N,n,p):
    return [ rbinom1(n,p) for i in range (N) ]



