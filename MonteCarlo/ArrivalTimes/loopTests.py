def loopTestV1():   #Define function loopTestV1 without arguments.
    t = 0           #Set starting value t = 0.
    a = []          #Create an empty list a.
    while t<10:     #Initiate the loop and set condition t<10. That is, when we
                    # reach line 9, we return to line 4 and check the statement
                    # t<10. If t<10, then execute lines 5 t0 9. If t>=10, exit
                    # the loop and move to line 10.
        t = t+1     #Add one to the current value of t.
        a.append(t) #Append the current value of t to the list a.
    return a        #Return list a.
        

def loopTestV2():      #Define function loopTestV2 without arguments.
    t = 0              #Set starting value t = 0.
    a = []             #Create an empty list a.
    while True:        #Initiate the loop and set condition True. Note that
                       # this loop continues infinitely unless another
                       # statement within points to an "exit". Otherwise, it
                       # will loop through lines 17 to 25 indefinitely.
        if t<10:       #Check whether t<10. If it holds, execute lines 21-22.
            t = t+1    #Add one to the current value of t.
            a.append(t)#Append the current value of t to the list a.
        else:          #This is a continuation of line 20. If t>=10, then
                       # move here and execute lines 24 and 25.
            return a   #Return the current value of list a. The return command
                       # causes the while loop in line 16 to break and we exit
                       # returning list a.

def loopTestV3():       #Define function loopTestV2 without arguments.
    t = 0               #Set starting value t = 0.
    a = []              #Create an empty list a.
    while True:         #Initiate the loop and set condition True. Note that
                        # this loop continues infinitely unless another
                        # statement within points to an "exit" (line 38-39). Otherwise, it
                        # will loop through lines 36 to 37 indefinitely.
        t = t+1         #Add one to the current value of t.
        a.append(t)     #Append the current value of t to the list a.
        if t<10:        #Check whether t<10. If it holds, execute line 36
            continue    #continue loop lines 33-34 if line 35 holds if not exit loop
        return a        #return list a when loop breaks.
print (loopTestV1())
print (loopTestV2())
print (loopTestV3())
