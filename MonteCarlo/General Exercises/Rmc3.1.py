
from math import *
import numpy as np
import random

def expSamp1(lam):
    return ( (-1/lam) * np.log(1-random.random()))


def expSamp(n , lam):
    return [( (-1/lam) * np.log(1-random.random())) for i in range(n)]


E  = [1,2,3,4,5] 
    
def mean(l):
    return (sum(l)/len(l))
def var(l):
    ll = len(l)
    sq = [ (l[i])**2 for i in range (ll)]
    return ( (1/(ll-1))*( sum(sq) - ll*(mean(l)**2)))

import matplotlib.pyplot as plt
# Take the sample
e1s = expSamp(1000,2)
# Plot the histogram in the left of two panels
plt.subplot(121)
n, bins, patches = plt.hist(e1s, 20, normed=True)
# Plot the empirical cdf in the right of two panels
plt.subplot(122)
e1s.sort()
plt.plot(e1s, [(i+1)/float(1000) for i in range(1000)]) 
# Show the plot
plt.show()
    
print (mean(e1s))
print (var(e1s))