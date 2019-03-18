	.data
converter:	.double 0r0.0174532925
compare:	.double 0r1.0e-10
zero:		.double 0r0.0
	
	.text
//defining macros for heavily used registers 
	
	
	

// assembler equates 
	buf_size = 8
      	alloc = -(16 + buf_size) & -16
	dealloc = -alloc 
	buf_o = 16
	AT_FDCWD =  -100

fp	.req x29
lr	.req x30

// strings 
pn: 	.string "input.bin"
fmt1:	.string	"Error opening file: %s\nAborting.\n"
fmt2:	.string "x = %13.10f\n"
	.balign 8
	.global main

main:	
	stp fp, lr, [sp, alloc]!
	mov fp, sp

//opening existing binary file
	mov x0, AT_FDCWD
	adrp x1, pn
	add x1, x1, :lo12:pn
	mov w2, 0
	mov w3, 0
	mov x8, 56
	svc 0

	mov w19, w0
	
//error checking for openat()
	cmp w19, 0
	b.ge bufbase
	
	adrp x0, fmt1
	add x0, x0, :lo12:fmt1
	adrp x1, pn
	add x1, x1, :lo12:pn
	bl printf
	mov w0, -1
	b return

bufbase:
//calculating buf base
	add x21, fp, buf_o

looptop:
	mov w0, w19
	mov x1, x21
	mov w2, buf_size
	mov x8, 63
	svc 0

	mov x20, x0

//error checking for read()
	cmp x20, buf_size
	b.ne close
	
	ldr d8, [x21]
		

	adrp x0, fmt2
	add x0, x0, :lo12:fmt2
	fmov x1, d8
	bl printf
	
	add x21, x21, buf_size
	b looptop

close:
	mov w0, w19
	mov x8, 57
	svc 0

	mov w0, 0

return:
	ldp fp, lr, [sp], dealloc
	ret
/*
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	cos
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
cos:
        stp fp, lr, [sp, -16]!
        mov fp, sp

        b cos_test

cos_test:
	fmov d0, d0
	fmov d1, 3.0
	b exponent

	b cos_return
	

cos_return:
	ldp fp, lr, [sp], 16
	ret

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	sin
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
sin:
	stp fp, lr, [sp, -16]!
	mov fp, sp

	b sin_return

sin_return:
	ldp fp, lr, [sp], 16
	ret

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 	exponent
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
exponent:
	stp fp, lr, [sp, -16]!
	mov fp, lr

	fmov d16, d0	//this is x
	fmov d19, d0	//second t for multiplication
	fmov d17, d1	//this is the exponent 
	adrp x9, converter
        add x9, x9, :lo12:converter
        ldr d18, [x9]
	fmov d19, 1.0

exponent_test:
	fcmp d18, d17	//checking if less than expnonent
       	b.lt exponent_return
	
	b exponent_work
exponent_work:
	fmul d19, d19, d16	//multipying

	fadd d18, d18, d20	//incrementing count

	b exponent_test

exponent_return:
	fmov d0, d19
	ldp fp, lr, [sp], 16
	ret

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	factorial
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
factorial:
	stp fp, lr, [sp, -16]!
	mov fp, sp

factorial_return:
	ldp fp, lr, [sp], 16
	ret

convert:
	stp fp, lr, [sp, -16]!
        mov fp, sp

        adrp x19, converter
        add x19, x19, :lo12:converter
        ldr d19, [x19]

        fmul d19, d19, d0

        fmov d0, d19

	ldp fp, lr, [sp], 16
        ret

*/		























