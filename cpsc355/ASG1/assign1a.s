fmt: .string "the value of x is %d, the value of y is %d and the current max is %d\n"
	.balign 4 

	.global main

main:
	stp x29, x30, [sp,-16]!
        mov x29, sp
	
	// initializing registers to be used in loop
	mov x19, #-4
	mov x28, #-10000
	mov x23, #20
        mov x24, #-10
        mov x25, #12
        mov x26, #5
	mov x27, #-1

loop:
	cmp x19, #3	// checking if x is smaller or equall to 3

        b.gt exit	// if x is larger than 3 the code will move to exit where it will end


	mul x20, x19, x23	//calculating 20x C
	mul x21, x19, x19
	mul x21, x21, x25	//calculating 12x^2 B
	mul x22, x19, x19
	mul x22, x22, x19
	mul x22, x22, x26	//calculating 5x^3 A
	
	mul x22, x22, x27	//reversing sign of A to use add upcode

	//calculating y by adding B to A and then C to the result and fianlly adding 10
	add x22, x22, x21
	add x20, x22, x20
	add x20, x20, x24

	//restoring sign of y
	mul x20, x27, x20

	cmp x28, x20 //finding out if y will be the new maximun 
	
	b.gt print

	mov x28, x20

	b print

print:
	
	adrp x0, fmt
        add x0, x0, :lo12:fmt

	//loading all the appropiate info on the right registers to be printed 
        mov x1, x19
        mov x2, x20
        mov x3, x28

        bl printf

        add x19, x19, #1	//add 1 to x

	b loop


exit:
	ldp x29, x30, [sp], 16
	ret



	
