from bxl_ode import Bxl_ODE
import numpy as np
import matplotlib.pyplot as plt
import math

# Set the model parameters
a = 2
b = 5

ode = Bxl_ODE(a=a, b=b) 

print("a =",a," b =",b)

# We compute the value of the homogeneous static solution ...
U = ode.static_solution() 
print("u0 =", U[0], " v0 =", U[1])

# We integrate the brusselator equations for the static solutions
t0=0.0
tmax=1000
dt=1e-4
ode = Bxl_ODE(U, dt, t0, a=a, b=b)

ode.iterate(tmax, fig_dt=0.1)

# We display display  u(t) v(t) v(u)
# pars : parameter values for the title
pars = "a="+str(a)+ ", b="+str(b)+ ", u(0)="+str(U[0])+", v(0)="+str(U[1])

# fname_par : parameter values for the filename
fname_par="a="+str(a)+"_b="+str(b)+"_u0="+str(U[0])+"_v0="+str(U[1])+".pdf"

# ode.plot(title,file_name)
ode.plot_u("u(t): " + pars, "fig_bru_ode_u_t_"+fname_par)
ode.plot_v("v(t): " + pars, "fig_bru_ode_v_t_"+fname_par)
ode.plot_v_u("v(u): " + pars, "fig_bru_ode_v_u_"+fname_par)


# We now perturb the homogeneous static solution a little
U += np.array([0.001, 0.001]) 

# Compute the stability eigen values
sigmap, sigmam, trA, disc = ode.sigma()
print("sigma_+ =", sigmap," sigma_- =", sigmam, "trA=", trA, "disc=", disc)

# We integrate the brusselator equations ...
t0=0.0
tmax=100
dt=1e-4
ode = Bxl_ODE(U, dt, t0, a=a, b=b)

ode.iterate(tmax, fig_dt=0.1)

# and display  u(t) v(t) v(u)
pars = "a="+str(a)+ ", b="+str(b)+ ", u(0)="+str(U[0])+", v(0)="+str(U[1])
fname_par="a="+str(a)+"_b="+str(b)+"_u0="+str(U[0])+"_v0="+str(U[1])+".pdf"

ode.plot_u("u(t): " + pars, "fig_bru_ode_u_t_"+fname_par)
ode.plot_v("v(t): " + pars, "fig_bru_ode_v_t_"+fname_par)
ode.plot_v_u("v(u): " + pars, "fig_bru_ode_v_u_"+fname_par)
