import time,pdb
a=[	[1,1,1,0,0],
	[0,0,1,0,0],
	[1,1,1,1,0],
	[0,0,0,1,1],
	[0,0,0,0,1]] #matrix
n=len(a)
prev=(0,0)
curr=(0,0)
b=a
def backtracker(c,p):
	while 1:
		
#pdb.set_trace()
while 1: 	
	b[curr[0]][curr[1]]=10
	print curr
	flag=1	
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
			print "Woah!"
			break
		else:
			curr=backtracker(prev,curr)
	time.sleep(1)
		
