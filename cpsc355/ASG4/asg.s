point_x = 0	//offset is 0 and size is 4
point_y = 4	//offset is 0 and size is 4
structPoint_size = 8 

dimension_width = 0	//offset is 0 and size is 4
dimension_height = 4	//offset is 4 and size is 4
structDimension_size = 8

box_origin = 8	//offset is 0 size is 8
box_Dsize = 16	//offset is 8 and size is 8
box_area = 0 //offset is 0 size is 4
structBox_size = 24

og_o = 16 //just to move the fp down to start saving

alloc = -[16 + structBox_size] & -16	//allocating enough data for the structures
dealloc = -alloc

FALSE = 0	//when true
TRUE = 1	//when false

fp	.req x29
lr	.req x30

fmt_1:	.string "initial box values: \n"
	.balign 4
fmt_2:	.string "box %s origin = (%d, %d) width = %d  height = %d  area = %d\n"
	.balign 4
fmt_3:	.string "Changed box va%dues: \n"
	.balign 4
fmt_first:	.string "first"
		.balign 4
fmt_second:	.string "second"
		.balign 4

///////////////////////////////////////////////////////////////////////////////newbox
////////////////////////////////////////////////////////////////////////////
newbox:
	stp fp, lr, [sp, alloc]!
	mov fp, sp
 //setting up the initialization of point x and y
	mov x11, 1
	mov x12, 0

// setting point x and y to 0 and width and height to 1
	str x12, [fp, og_o + box_origin + point_x]
	str x12, [fp, og_o + box_origin + point_y]
	str x11, [fp, og_o + box_Dsize + dimension_width]
	str x11, [fp, og_o + box_Dsize + dimension_height]

//getting area 
	ldr x9, [fp, og_o + box_Dsize + dimension_width]
	ldr x10, [fp, og_o + box_Dsize + dimension_height]
	mul x9, x9, x10
	str x9, [fp, og_o + box_area]

//now going to save to return struct
	ldr x9, [fp, og_o + box_origin + point_x]
	str x9, [x8, box_origin + point_x]
	
	ldr x9, [fp, og_o + box_origin + point_y]
	str x9, [x8, box_origin + point_y]

	ldr x9, [fp, og_o + box_Dsize + dimension_width]
	str x9, [x8, box_Dsize + dimension_width]

	ldr x9, [fp, og_o + box_Dsize + dimension_height]
	str x9, [x8, box_Dsize + dimension_height]

	ldr x9, [fp, og_o + box_area]
	str x9, [x8, box_area]

	ldp fp, lr, [sp], dealloc
	ret

/////////////////////////////////////////////////////////////////////////////////////////
//move
////////////////////////////////////////////////////////////////////////////////////////
move:
	stp fp, lr, [sp, -16]!
	mov fp, sp

//loading point x and y
	mov x9, x0
	ldr x10, [x9, box_origin + point_x]
	ldr x11, [x9, box_origin + point_y]

//adding x and y with the appropriate arguments 
	add x10, x10, x1
	add x11, x11, x2

//storing them back into the object / stack 
	str x10, [x9, box_origin + point_x]
	str x11, [x9, box_origin + point_y]
	
	ldp fp, lr, [sp], 16
	ret

/////////////////////////////////////////////////////////////////////////////////////////
//expand
////////////////////////////////////////////////////////////////////////////////////////
expand:
	stp fp, lr, [sp, -16]!
	mov fp, sp

	mov x9, x0

//loading dimensions for this function to alter 
	ldr x10, [x9, box_Dsize + dimension_width]
 	ldr x11, [x9, box_Dsize + dimension_height]
	ldr x12, [x9, box_area]

//multiplying each dimension by its specific argument and then finding the area 
	mul x10, x10, x1
	mul x11, x11, x1
	mul x12, x10, x11

//storing the info back into the object 
 	str x10, [x9, box_Dsize + dimension_width]
	str x11, [x9, box_Dsize + dimension_height]
	str x12, [x9, box_area]

	ldp fp, lr, [sp], 16
	ret


////////////////////////////////////////////////////////////////////////////////////////
//print_box
////////////////////////////////////////////////////////////////////////////////////////
print_box:
	stp fp, lr, [sp, alloc]!
	mov fp, sp

//loading each info onto a register in order for it to be printed out 
	mov x9, x0
	ldr x0, =fmt_2
	ldr x2, [x9, box_origin + point_x]
	ldr x3, [x9, box_origin + point_y]
	ldr x4, [x9, box_Dsize + dimension_width]
 	ldr x5, [x9, box_Dsize + dimension_height]
	ldr x6, [x9, box_area]
	bl printf

	ldp fp, lr, [sp], dealloc
        ret

/////////////////////////////////////////////////////////////////////////////////////////
//equal
/////////////////////////////////////////////////////////////////////////////////////////
alloc = -[16 + structBox_size + 4] & -16
dealloc = -alloc
equal:
	stp fp, lr, [sp, -16] !
        mov fp, sp
	
	mov x9, x0 //moving b onto a temporary register

	ldr x1, [x9, box_origin + point_x]
	ldr x11, [x9, box_origin + point_y]
	ldr x12, [x9, box_Dsize + dimension_width]
	ldr x13, [x9, box_Dsize + dimension_height]

	ldr x14, [x9, structBox_size + box_origin + point_x]
	ldr x15, [x9, structBox_size + box_origin + point_y]
	ldr x0, [x9, structBox_size + box_Dsize + dimension_width]
 	ldr x10, [x9, structBox_size + box_Dsize + dimension_height]

	cmp x1, x14	//comparing the first and second x point
	b.ne else

	cmp x11, x15	//comparing the first and second y point
	b.ne else

	cmp x12, x0	//comparing the first and second width dimension
	b.ne else

//i honestly have no idea why but if you take this print statement out, then the following if statement will fail and the code will automatically go to else. NO IDEA WHY
	ldr x0, =fmt_3
        mov x1, x10
	bl printf 
	
	cmp x13, x10	//comparing the first and second height dimension 
	b.ne else

	mov x0, TRUE	//settign the return value


	ldp fp, lr, [sp], 16
        ret

	b main_2

.global main
/////////////////////////////////////////////////////////////////////////////////////////
//main
/////////////////////////////////////////////////////////////////////////////////////////
alloc = -[16 + structBox_size + structBox_size]&-16
dealloc = -alloc
main:
	stp fp, lr, [sp, alloc]!
	mov fp, sp
	
	ldr x0, =fmt_1
	bl printf

//making first box
	add x8, fp, og_o
	bl newbox

//making second box
	add x8, fp, og_o + structBox_size
	bl newbox
	
//printing first box
	add x0, fp, og_o
        ldr x1, =fmt_first
	bl print_box

//printing second box
	add x0, fp, og_o + structBox_size
        ldr x1, =fmt_second
        bl print_box

//comparing if first and second box are equal 
	add x0, fp, og_o
	bl equal
main_2:
	cmp x0, TRUE
	b.ne end

//modifying x add y points of first box
	add x0, fp, og_o
	ldr x1, =-5
	ldr x2, =7
	bl move

//modying width and length from second box 
	add x0, fp, og_o + structBox_size 
	ldr x1, =3
	bl expand

//printing out both first and second modified boxes 
	add x0, fp, og_o
	ldr x1, =fmt_first
	bl print_box

	add x0, fp, og_o + structBox_size
	ldr x1, =fmt_second
	bl print_box


	ldp fp, lr, [sp], dealloc
        ret
	
end:
	ldp fp, lr, [sp], dealloc
	ret

else:
// code gets directed here if the first and second boxes are not the same
	mov w0, FALSE

        ldp fp, lr, [sp], 16
        ret

        b main_2

