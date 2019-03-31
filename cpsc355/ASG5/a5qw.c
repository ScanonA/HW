#define MAXVAL 100

int sp = 0;

int val[MAXVAL];



int push(int f)

{

if (sp < MAXVAL)

return val[sp++] = f;

else {

printf("error: stack full\n");

clear();

return 0;

}

}



int pop()

{

if (sp > 0)

return val[--sp];

else {

printf("error: stack empty\n");

clear();

return 0;

}

}



void clear()

{

sp = 0;

}



int getop(char *s, int lim)

{

int i, c;

while ((c = getch()) == ' ' || c == '\t' || c == '\n')

;

if (c < '0' || c > '9')

return c;

s[0] = c;

for (i = 1; (c = getchar()) >= '0' && c <= '9'; i++)

if (i < lim)

s[i] = c;

if (i < lim) {

ungetch(c);

s[i] = '\0';

return NUMBER;

} else {

while (c != '\n' && c != EOF)

c = getchar();

s[lim-1] = '\0';

return TOOBIG;

}

}



#define BUFSIZE 100

char buf[BUFSIZE];

int bufp = 0;



int getch()

{

return bufp > 0 ? buf[--bufp] : getchar();

}



void ungetch(int c)

{

if (bufp > BUFSIZE)

printf("ungetch: too many characters\n");

else

buf[bufp++] = c;

}
