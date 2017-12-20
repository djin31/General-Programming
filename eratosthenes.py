#eratosthenes sieve
#improved version
#print prime numbers uptil a certain number 
#pass the input to the given function the number upto which you want to print prime numbers

def sieve(n):
	l=[False,False]
	for i in xrange(2,n+1):
		l.append(True) 		#init
	for i in xrange(2,(n+2)/2):
		if (l[i]):
			for j in xrange(i+1,n+1):
				if j%i==0:
					l[j]=False
	s=[i for i in range(n+1) if l[i]==True]
	print s
