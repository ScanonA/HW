fmt: .string"1 + 2 + ...+ 98 + 99 = %d\n"
	.balign 4 

	.global main

	define(i, x19)
	define(sums, x20)

main:
	stp x29, x30 [sp, -(16+8) & -16]!
	mov x29, sp

	mov sums, #0
	mov i, #1

	b test
	
test:	
	cmp i, 100
        b.gt end

        b main
	
	add sums, sums, i
	
	b loop

loop:
	add sums, sums, i

        b main

end:
	
	
