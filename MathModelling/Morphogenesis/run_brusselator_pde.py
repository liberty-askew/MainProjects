

from brusselator_pde import BrusselatorPDE

# set paremeter values
a = 2
b = 1
Du = 2.8
Dv = 22.4
L = 10
N = 100
tmax = 100
fig_dt = tmax/200
epsilon = 0.01
print ('b=',b)
print('tmax=',tmax)

pars = "a="+str(a)+ ", b="+str(b)+ ", Du="+str(Du)+", Dv="+str(Dv)+"L="+str(L)
fname_par="a="+str(a)+"_b="+str(b)+"_Du="+str(Du)+"_Dv"+str(Dv)+"_L"+str(L)+".pdf"

# Prepare the BrusselatorPDE for integration
br = BrusselatorPDE(L, N, 0, Du, Dv, a, b)
br.initial_condition(epsilon)

# Integrate until tmax with data saved every fig_dt
br.iterate(tmax,fig_dt)

# Plot Profile u(x) and v(x) at t=tmax
br.plot_v_u("Plot Profile u(x) and v(x) at t=tmax: " + pars, "fig_tmax_v_u_t_"+fname_par)

# Plot Profile u(3*L/8,t) and v(3*L/8,t)
br.plot_u_v_Lo2(3*L/8.0, "Plot Profile u(3*L/8,t) and v(3*L/8,t): " + pars, "fig_v_u_Lo2_"+fname_par)
