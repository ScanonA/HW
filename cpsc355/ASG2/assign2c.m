fmt_1: 	.string "multiplier = 0x%08x (%d)  multiplicand = 0x%08x (%d) \n\n"
	.balign 4
fmt_2:	.string "product = 0x%08x  multiplier = 0x%08x\n"
	.balign 4
fmt_3:	.string "62-bit result = 0x%016lx (%ld)\n"
	.balign 4

	.global main

//adding names to register 

	define(multiplier, w19)
	define(multiplicand, w20)
	define(product, w21)
	define(i, w22)
	define(negative, w23)
	define(result, x24)
	define(temp1, x25)
	define(temp2,x26)
	define(FALSE, w27)
	define(TRUE, w28)

main:	
	stp x29, x30, [sp,-16]!
	mov x29, sp	

//initializing all the registers with their initial numbers 
	mov multiplier, #-256
	mov multiplicand, #-252645136
	mov product, #0
	mov FALSE, #1
	mov i, #0
	mov TRUE, #0
	mov negative, FALSE

//printing out the multiplier and multiplicand in hexidecimal and decimal to the consol
	adrp x0, fmt_1
	add x0, x0, :lo12:fmt_1

	mov w1, multiplier
	mov w2, multiplier
	mov w3, multiplicand
	mov w4, multiplicand

	bl printf

	b negative_check

negative_check:
//checking if the multiplier is a negative or not
	cmp multiplier, #0
	b.le loop_test	//if multiplier is not negative then the code will move to the top of the loop

	mov negative, TRUE

	b loop_test

loop_2:
	asr multiplier, multiplier, #1	//shifting the multiplier over to the right by 1 byte
	tst product, #0x1	//testing if lsd is 1 or 0
	b.eq else

	orr multiplier, multiplier, 0x80000000 

	b product_shift

else:
	and multiplier, multiplier, 0x7fffffff

	b product_shift
loop_1:
	tst multiplier, #0x1	//testing if the least significant digit is a 1 or 0
	b.eq loop_2	//if 0

	add product, product, multiplicand	//if lsd is 1 
	
	b loop_2

product_shift:
	asr product, product, #1	// shifting product over to the right by 1 byte

	add i, i, #1	//i++

	b loop_test

loop_test:
//checking initial condition to run the loop 
	cmp i, #31
	b.gt product_option	// once i not < 32 the code will exit the loop 

	b loop_1

product_option:
//this code is only executed if the multiplier is negative. checked in negative_check
	cmp negative, #1
	b.ne print_2

	sub product, product, multiplicand

	b print_2

print_2:
//printing out the new product and multiplier
	adrp x0, fmt_2
	add x0, x0, :lo12:fmt_2
	
	mov w1, product
	mov w2, multiplier
	bl printf

	b combine

combine:
	sxtw temp1, product
	and temp1, temp1, 0xffffffff
	lsl temp1, temp1, #32
	sxtw temp2, multiplier
	and temp2, temp2, 0xffffffff
	add result, temp2, temp1

	adrp x0, fmt_3
        add x0, x0, :lo12:fmt_3

        mov x1, result
	mov x2, result
        bl printf

	b end	

end:
	ldp x29, x30, [sp], 16
	ret
