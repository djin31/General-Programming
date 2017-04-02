import time,pdb
p=[]
a=[	[1,1,1,0,0],
	[0,0,1,0,0],
	[1,1,1,1,0],
	[1,0,0,0,1],
	[1,1,1,1,1]] #matrix
n=len(a)
prev=(0,0)
curr=(0,0)
b=a
def backtracker():
	global p
        while 1:
		curr=p[-2]
		p=p[:-2]
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



		
#pdb.set_trace()
while 1: 	
	p.append(curr)
	b[curr[0]][curr[1]]=10
	print curr
	flag=1	
	if curr==(n-1,n-1):
			print "Woah!"
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
			print "Woah!"
			break
		else:
			curr,prev=backtracker()
	time.sleep(1)
		
