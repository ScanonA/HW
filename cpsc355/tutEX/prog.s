fmt: .string "a= 0x%x, b= 0x%x\na|b = 0x%x\n"
	.balign 4
	.global main
	
main:
	stp x29, x30, [sp,-16]!
	mov x29, sp
	ldr x0, =fmt
	mov x1, 0b1001
	mov x2, 0b0111
	orr x3, x2, x1
	bl printf

	ldp x29, x30, [sp], 16
	ret
