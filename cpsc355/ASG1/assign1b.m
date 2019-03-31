fmt: .string "the value of x is %d the value of y is %d and the current max is %d\n"
	.balign 4

	.global main
//adding names to registers 

	define(x_r, x19)
	define(max_r, x28)
	define(twenty_r, x23)
	define(ten_r, x24)
	define(twelve_r, x25)
	define(five_r, x26)
	define(open_r, x21)
	define(y_r, x20)
	define(free_r, x22)
	define(neg_r, x27)

main:
	stp x29, x30, [sp,-16]!
	mov x29, sp

//initializing registers to be used in calculations
	mov x_r, #-4
	mov max_r, #-10000
	mov twenty_r, #20
	mov ten_r, #10
	mov twelve_r, #12
	mov five_r, #5
	mov neg_r, #-1

	b loop_test //sending to check if x is good 

loop:
	mul y_r, x_r, twenty_r	//calculating 20x C
	mul free_r,x_r, x_r
	mul free_r,free_r,twelve_r	//calculating 12x^2 B
	mul open_r, x_r, x_r
	mul open_r, open_r, x_r
	mul open_r, open_r, five_r	//calculating 5x^3 A

//calculating y by reversing sign of A then adding B to it then adding C and finally adding 10
	madd open_r, open_r, neg_r, free_r
	add y_r, open_r, y_r
	madd y_r, y_r, neg_r, ten_r

//restoring sign of y
	cmp max_r, y_r

	b.gt print

	mov max_r, y_r

	b print

loop_test:
//checks to see if x is smaller or equall to 3 
        cmp x_r, #3

        b.gt exit

        b loop


print:
	adrp x0, fmt
	add x0, x0, :lo12:fmt

//printing out the data to the console
	mov x1, x_r
	mov x2, y_r
	mov x3, max_r

	bl printf

	add x_r, x_r, #1
	
	b loop_test

exit:
	ldp x29, x30, [sp], 16
	ret
	
