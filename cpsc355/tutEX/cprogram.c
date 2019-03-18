include <stdio.h>

int add(int n, int m){
	return n + m; 
}

void main() {
	int n = rand();
	int m = rand();
	int v = add(n,m);
	printf ("n = %d, m = %d, n+m = %d", n, m, v);
}
