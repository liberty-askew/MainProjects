#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Thu Feb  8 10:39:19 2018

@author: libertyaskew
"""

def factorial(n):
    result = 1
    while n>1:
        result = result*n
        n = n-1
    return result
    
def binomial(n,j):
    return factorial(n)/(factorial(j)*factorial(n-j))

def dbinom(j,n,p):
    return binomial(n,j) * (p**j) * ((1-p)**(n-j))



def pbinom(j,n,p):
    result = 0
    for x in range(n+1):
        result = result + dbinom(j, x ,p)
    return result


def cbinom(j,n,p):
    r = 0 
    for x in range (n):
        r = r + dbinom(j, x, p)
    return r

print(dbinom(1,0,0.5))
print(cbinom(1,1,0.5))

