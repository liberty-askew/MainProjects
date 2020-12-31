
#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Dec 26 17:35:42 2017

@author: libertyaskew
"""
from brusselator_pde import BrusselatorPDE 
import matplotlib.pyplot as plt
import numpy as np

a = 1.5
Dv = 22.4
L = 50
N = 100
tmax = 100
fig_dt=tmax/100
epsilon = 0.01

def amp(b,Du):
    """Calculates amplitude A_u and A_v and returns them as a tuple
    A_v = max(v(x)) - min(v(x))
    A_u = max(u(x)) - min(u(x))
    """
    br = BrusselatorPDE(L, N, 0, Du, Dv, a, b)
    br.initial_condition(epsilon)
    br.iterate(tmax,fig_dt)
    ux = [br.v[2*i] for i in range (tmax)]
    vx = [br.v[2*i + 1] for i in range (tmax)]
    Au = np.amax(ux) - np.amin(ux)
    Av = np.amax(vx) - np.amin(vx)
    return (tuple( [ Au , Av]))

for DU in range (1, 8):
    """loop scanning Du values from 1 - 7 producing a graph for each of
    A_v , A_u for a range of b values
    """
    bl = np.linspace(0.5 ,5,50)
    Sulist , Svlist , ulist , vlist , Sblist , blist = [],[],[],[],[],[] 
    for i in range (len(bl)):
        stable = (((bl[i]-1-a**2)<0) and ((Dv*(bl[i]-1) + DU*(-a**2)) < 2*(DU*Dv*a**2)**(0.5)))
        Amp = np.array(amp(bl[i] , DU))
        if stable==True:
            Sulist.append(Amp[0]-0.05)
            Svlist.append(Amp[1])
            Sblist.append(bl[i])
        else:
            ulist.append(Amp[0]-0.05)
            vlist.append(Amp[1])
            blist.append(bl[i])
        
    pars = "a="+str(a)+ ", $D_u$="+str(DU)+", $D_v$="+str(Dv) 
    fname_par="a="+str(a)+"_Du="+str(DU)+"_Dv="+str(Dv)+".pdf"
  
    plt.plot(Sblist , Sulist , 'k*' , label = "$A_u$(b) stable")
    plt.plot(Sblist , Svlist , 'b+' , label = "$A_v$(b) stable")
    plt.plot(blist , ulist , 'g*' , label = "$A_u$(b) unstable")
    plt.plot(blist , vlist , 'r+' , label = "$A_v$(b) unstable")
    plt.title("$A_u$(b),$A_v$(b)=:" +pars , fontsize=16)
    plt.xlabel("b" , fontsize=16)
    plt.ylabel("$A_u$, $A_v$" , fontsize=16)
    plt.savefig('fig_Duvar_'+fname_par)
    plt.legend()
    plt.show()







    
    


    