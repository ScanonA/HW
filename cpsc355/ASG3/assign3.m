//defining variables in order to use stack efficiently 
	
	array_count = 50 //defining the size of the array
	array_size = array_count * 4
	i_size = 4
	j_size = 4
	min_size = 4 
	temp_size = 4

//defining offset variables

	i_so = 16
	j_so = min_so + min_size
	min_so = array_so + array_size
	temp_so = j_so + j_size
	array_so = i_so + i_size 
	
	var_size = array_size + i_size + j_size + min_size + temp_size // adding all sizes 

	alloc = -(16 + var_size) & -16
	dealloc = -alloc

fp 	.req x29
lr	.req x30



fmt_presort: .string "v[%d]: %d\n"
	.balign 4
fmt_statement:	.string "\nSorted Array:\n"
	.balign 4
fmt_sort:	.string "v[%d]: %d\n"
	.balign 4

	.global main

main: 
	stp fp, lr, [sp, alloc]!
	mov fp, sp

//initializing i = 0
	mov w19, 0
	str w19, [fp, i_so]
	
	b test

test:
//i < size
	ldr w19, [fp, i_so]
	cmp w19, 50
	
	b.ge reset

	b loop

loop:
	bl rand		//obtaining int from rand function
	and w21, w0, 0xFF	//doing AND operation on rand in with OxFF
	
	ldr x19, [fp, i_so]
	add x22, fp, array_so	//loading first address of array
       	str w21, [x22, w19, sxtw 2]	//loading adress of i on array by shifting x19(i) << 2


//1st print staement 
        adrp x0, fmt_presort
        add x0, x0, :lo12:fmt_presort

        mov w1, w19
	ldr w20, [x22, w19, sxtw 2]
        mov w2, w20

        bl printf

	
	add w19, w19, 1
	str w19, [fp, i_so]

	b test

reset:
//i = 0
	mov w19, 0
	str w19, [fp, i_so]
	
	b test_sort

test_sort:
//i < SIZE - 1
	ldr w19, [fp, i_so]
        cmp w19, 49
	
	b.ge reset_3

	b loop_sort

loop_sort:
//min = i
	ldr w19, [fp, i_so]
	str w19, [fp, min_so]

	b reset_2

test_sortception:
//j < SIZE
	ldr w19, [fp, j_so]
	cmp w19, 50

	b.ge swap

	b loop_sortception

reset_2:
//j - i + 1
	ldr w19, [fp, i_so]
	add w19, w19, 1
	str w19, [fp, j_so]

	b test_sortception

plus:
//j++
	ldr w19, [fp, j_so]
        add w19, w19, 1
        str w19, [fp, j_so]
	
	b test_sortception

loop_sortception:
//loading j and min 
	ldr w19, [fp, j_so]
	ldr w20, [fp, min_so]

	add x21, fp, array_so

//loading v[j] and v[min]
	ldr w19, [x21, w19, sxtw 2]
	ldr w20, [x21, w20, sxtw 2]

//v[j] < v[min]
	cmp w19, w20
	
	b.gt plus

//min = j
	ldr w19, [fp, j_so]
	str w19, [fp, min_so]

	b plus

swap:
//temp = v[min]
	ldr w19, [fp, min_so]
	add x20, fp, array_so
	ldr w19, [x20, w19, sxtw 2]
	str w19, [fp, temp_so]

//v[min] = v[i]
	ldr w19, [fp, i_so]
        add x20, fp, array_so
        ldr w19, [x20, w19, sxtw 2]
	ldr w21, [fp, min_so]
        str w19, [x20, w21, sxtw 2]

//v[i] = temp
	ldr w19, [fp, temp_so]
        add x20, fp, array_so
	ldr w21, [fp, i_so]
        str w19, [x20, w21, sxtw 2]

//i++
	ldr w19, [fp, i_so]
        add w19, w19, 1
        str w19, [fp, i_so]

	b test_sort

reset_3:
//i = 0
	mov w19, 0
	str w19, [fp, i_so]

//2nd print statemnt	
	adrp x0, fmt_statement
	add x0, x0, :lo12:fmt_statement

	bl printf

	b print_test

print_test:
//i < SIZE
	ldr w19, [fp, i_so]
	cmp w19, 50

	b.ge end

	b print_loop

print_loop:
//3rd print statement 
	adrp x0, fmt_sort
        add x0, x0, :lo12:fmt_sort

	ldr w19, [fp, i_so]
        mov w1, w19
	add x22, fp, array_so
        ldr w20, [x22, w19, sxtw 2]
        mov w2, w20

        bl printf

	ldr w19, [fp, i_so]
	add w19, w19, 1
	str w19, [fp, i_so]

	b print_test

end:

	ldp fp, lr, [sp], dealloc
	ret



