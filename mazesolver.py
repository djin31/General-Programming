#enter the maze in form of binary grid, 1 representing a path and 0 representing a wall
import time,pdb
p=[]
c=raw_input("Enter the length of maze(0 if u want a default maze")
if c=="0":
  a=[[1,1,1,1,1,1],
     [1,0,1,0,0,1],
     [1,0,1,0,0,1],
     [1,1,1,0,0,1],
     [0,0,0,0,0,1],
     [1,0,1,1,1,1]] #matrix
else:
  a=[]
  for i in xrange(int(c)):
    tmp=raw_input().split(' ')
    tmp2=[]
    for i in xrange(int(c)):
      tmp2.append(int(tmp[i]))
    a.append(tmp2)
  
for i in a:
  print i
n=len(a)
prev=(0,0)
curr=(0,0)
b=a
def backtracker():
  global p
  #print p
  while 1:
		s=len(p)
		curr=p[s-2]
		#print curr
		p=p[:-1]
		flag=1	
		if curr[0]<n-1:	
			if b[curr[0]+1][curr[1]]==1:
				prev=curr
				curr=(curr[0]+1,curr[1])
				return curr,prev
		if curr[0]>0 and flag==1:
			if b[curr[0]-1][curr[1]]==1:
				prev=curr
				curr=(curr[0]-1,curr[1])
				return curr,prev
		if curr[1]<n-1 and flag==1:
			if b[curr[0]][curr[1]+1]==1:
				prev=curr
				curr=(curr[0],curr[1]+1)
				return curr,prev
		if curr[1]> 0 and flag==1:
			if b[curr[0]][curr[1]-1]==1:
				prev=curr
				curr=(curr[0],curr[1]-1)
				return curr,prev
		#print p
		if len(p)==1:
		  print "Are you kidding me!"
		  break
		  



		
#pdb.set_trace()
while 1: 	
	p.append(curr)
	b[curr[0]][curr[1]]=10
	print curr
	flag=1	
	if curr==(n-1,n-1):
			print "Woah!\n"
			break


	if curr[0]<n-1:	
		if b[curr[0]+1][curr[1]]==1:
			prev=curr
			curr=(curr[0]+1,curr[1])
			flag=0
	if curr[0]>0 and flag==1:
		if b[curr[0]-1][curr[1]]==1:
			prev=curr
			curr=(curr[0]-1,curr[1])
			flag=0
	if curr[1]<n-1 and flag==1:
		if b[curr[0]][curr[1]+1]==1:
			prev=curr
			curr=(curr[0],curr[1]+1)
			flag=0
	if curr[1]> 0 and flag==1:
		if b[curr[0]][curr[1]-1]==1:
			prev=curr
			curr=(curr[0],curr[1]-1)
			flag=0
	if flag==1:
		if curr==(n-1,n-1):
			print "Woah!\n"
			break
		else:
		  z=backtracker()
		  if z==None :
			  print "No way home!"
			  break
		  curr,prev=z[0],z[1]
			
	time.sleep(0.5)
	
for i in range(n):
  for j in range(n):
    if(i,j) in p:
      print 1,
    else:
      print " ",
    
  print " "
