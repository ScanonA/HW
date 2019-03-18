//defining constants
	MAXOP = 20
	NUMBER = '0'
	TOOBIG = '9'

	MAXVAL = 100
	BUFFSIZE = 100

//global variables
	.data
	.global sp_m	// making sp visible in main 
sp_m:	.word	0	//initializing sp as 0
	.global bufp_m
bufp_m:	.word	0
	.global array_max
array_val:	.skip MAXVAL*4	//initialing an array
	.global array_buff
array_buf:	.skip BUFFSIZE*1

//starting with code 
	.text
fp	.req x29
lr	.req x30

p_full: 	.string	"error stack full \n"
p_empty: 	.string	"error stack empty \n"
p_toomany:	.string	"ungetch: too many characters \n"
		.balign 4
//////////////////////////////////////////////////////////////////////////////////////////////////////
//	push
//////////////////////////////////////////////////////////////////////////////////////////////////////

	.global push
push:
	stp fp, lr, [sp, -16]!
	mov fp, sp
	
	mov w11, w0

//laoding sp and max array
	adrp x0, sp_m
	add x0, x0, :lo12:sp_m
	ldr w9,[x0]
	adrp x1, array_val
	add x1, x1, :lo12:array_val
	mov x10, MAXVAL

//comparing sp to maxval
	cmp w9, w10
	b.ge push_else
	
	add w9, w9, 1	//incrementing by one
	str w11, [x1, w9, sxtw 2]	//stroing value in to the array
	
	b push_return

push_else:
//print stack full
	adrp x0, p_full
	add x0, x0, :lo12:p_full
	bl printf

	b clear

	b push_return

push_return:
	ldp fp, lr, [sp], 16
	ret



//////////////////////////////////////////////////////////////////////////////////////////////////
//	pop
//////////////////////////////////////////////////////////////////////////////////////////////////

	.global pop
pop:
	stp fp, lr, [sp, -16]!
	mov fp, sp

//loading sp
	adrp x0, sp_m
	add x0, x0, :lo12:sp_m
	ldr w9, [x0]
		
//loading val array
	adrp x1, array_val
	add x1, x1, :lo12:array_val

//if sp > 0
	cmp w9, 0
	b.le pop_else

//--sp, and returning val[sp]
	sub w9, w9, 1
	ldr w10, [x1, w9, sxtw 2]
	mov w0, w10

	b pop_return

pop_else:
//print stack empty
	adrp x0, p_empty
	add x0, x0, :lo12:p_empty
	bl printf

	b clear

	b pop_return

pop_return:
	ldp fp, lr, [sp], 16
	ret


//////////////////////////////////////////////////////////////////////////////////////////////////
//	clear
///////////////////////////////////////////////////////////////////////////////////////////////////

	.global clear 

clear:
//sp = 0
	stp fp, lr, [sp, -16]!
	mov fp, sp
	
	adrp x0, sp_m
	add x0, x0, :lo12:sp_m
	mov w9, 0
	str w9, [x0]

	ldp fp, lr, [sp], 16
        ret

//////////////////////////////////////////////////////////////////////////////////////////////////////
//	getop
//////////////////////////////////////////////////////////////////////////////////////////////////////

char1 = ' '
char2 = '\t'
char3 = '\n'

//variable offset
i_o = 16
c_o = 16 + 4

alloc = -(16+4+4) & -16
dealloc = -alloc

	.global getop
getop:
	stp fp, lr, [sp, alloc]!
	mov fp, sp
	
	mov w2, w1
	mov x5, x0

//getting ASCII number for " "
	mov w0, char1
	bl atoi
	mov w9, w0
	
//getting ASCII number for '\t'
	mov w0, char2
	bl atoi
	mov w10, w0

//getting ASCII number for '\n'
	mov w0, char3
	bl atoi
	mov w11,w0

// ASCII for '0'
	mov w0, NUMBER
	bl atoi
	mov w12, w0

//ASCII for '9'
	mov w0, TOOBIG
	bl atoi
	mov w13, w0

	b getop_set

getop_set:
	ldr w1, [fp, c_o]

	b getch

	mov w1, w0
	str w1, [fp, c_o] 	//c = getch()

	b getop_while

getop_while:
	

	cmp w1, w9		//c == ' '
	b.ne getop_if

	b getop_set

	cmp w1, w10		// c == '\t'
	b.ne getop_if

	b getop_set

	cmp w1, w11 		// c == '\n'
	b.ne getop_if

	b getop_set

getop_if:
	cmp w1, w12		// c < '0'
	b.ge getop_if_part2
	
	mov w0, w1

	b getop_return

getop_if_part2:
	cmp w1, w13		//c > '9'
	b.le getop_set2

	mov w0, w1
	
	b getop_return  

getop_set2:
	str w1, [x5,0]	//s[0] = c

	ldr w15, [fp, i_o]	//i = 1
	mov w15, 1

	b getop_for

getop_for:
	bl getchar	
        mov w1, w0		//c = getch()

	cmp w1, w12		//c >= '0'
      	b.le getop_if2
	
	cmp w1, w13		//c <= '9'
	b.ge getop_if2
	
	b getop_iffor

getop_iffor:
	cmp w15, w2		//i < lim
	b.ge getop_addi
	str w1, [x5, w15, sxtw] //s[i] = c


	b getop_addi

getop_addi:
	add w15, w15, 1		//i++

	b getop_for

getop_if2:
	cmp w15, w2	// 1< lim
	b.ge getop_else

	mov w0, w1

	bl ungetch

//s[i] = '\0'
	mov w0, '\0'
	i = w15
	str w0, [x5, w15, sxtw]

	mov w0, NUMBER	//return value

	b getop_return 

getop_else:
	cmp w1, w11	//c != '\n'
	b.eq getop_else_part2

	cmp w1, -1 	//c != EOF
	b.eq getop_else_part2

//c = getchar()
	bl getchar
	mov w1, w0
	str w1, [fp, c_o]

	b getop_else	//while loop 

getop_else_part2:
//s[lim-1] = '\0\'
	sub w4, w2, 1
	mov w0, '\0'
	str w0, [x5, w4, sxtw]

	mov w0, TOOBIG	//return TOOBIG

	b getop_return

getop_return:
	ldp fp, lr, [sp], dealloc
	ret

///////////////////////////////////////////////////////////////////////////////////////////////		getch
///////////////////////////////////////////////////////////////////////////////////////////

	.global getch
getch:
	stp fp, lr, [sp, -16]!
	mov fp, sp

//loading bufp
	adrp x0, bufp_m
	add x0, x0, :lo12:bufp_m
	ldr w9, [x0]

//loading buf array
	adrp x1, array_buf
	add x1, x1, :lo12:array_buf

	cmp w9, 0	//bufp > 0
	b.le getch_else

// returning buf[--bufp]
	sub w10, w9, 1
	ldr w11, [x1, w10, sxtw 2]
	mov w0, w11

	b getch_return

getch_else:
	bl getchar
	mov x0,x0	//return value (redundant)
	
	b getch_return

getch_return:
	ldp fp, lr, [sp], 16
	ret

/////////////////////////////////////////////////////////////////////////////////////////////////////////	ungetch
//////////////////////////////////////////////////////////////////////////////////////////////////////

	.global ungetch

ungetch:
	stp fp, lr, [sp, -16]!
	mov fp, sp

//moving argument into temp register
	mov w9, w0

//loading bufp
	adrp x0, bufp_m
	add x0, x0, :lo12: bufp_m
	ldr w10, [x0]

	cmp w10, BUFFSIZE	//bufp > BUFFSIZE
	b.le ungetch_else

	adrp x1, p_toomany
	add x1, x1, :lo12:p_toomany
	bl printf

	b ungetch_return 

ungetch_else:
	adrp x1, array_buf	//loading the buf array 
	add x1, x1, array_buf
	
	add w12, w10, 1		//bifp++

	ldr w9, [x0, w12, sxtw 2]	//buf[bufp++] = c

	b ungetch_return 

ungetch_return:
	ldp fp, lr, [sp], 16
	ret


		








