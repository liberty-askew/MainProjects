# -*- coding: utf-8 -*-
"""
Created on Tue Jan 23 09:32:32 2018

@author: rfnb51
"""
'''
import matplotlib.pyplot as plt

def rbern1(p):
    u = random.random()
    if u<=p:
        return 1
    else:
        return 0 

def rbern(N,p):
    l=[]
    for i in range(N):
        b = rbern1(p)
        l.append(b)
    return l

print (rbern(1000,0.25))

def rbernsum(N,p):
    b =rbern(N,p)
    s = 0
    l = []
    for i in range(N):
        s+=b[i]
        l.append((s)/(i+1))
    return l

print (rbernsum(1000,0.5))


plt.plot(rbernsum(1000,0.5))
'''
def bern_lln(N,p):
    b =rbern(N,p)
    s = 0
    l = []
    for i in range(N):
        s+=b[i]
        l.append((s)/(i+1))
    plt.plot(l)
    
print(bern_lln(500,0.1))