
	.text
jan_m:	.string "January"
feb_m:	.string "February"
mar_m:	.string "March"
apr_m:	.string "April"
may_m:	.string "May"
jun_m:	.string "June"
jul_m:	.string "July"
aug_m:	.string "August"
sept_m:	.string "September"
oct_m:	.string "October"
nov_m:	.string "November"
dec_m:	.string "December"
d1_m:	.string "1st"
d2_m:	.string "2nd"
d3_m:    .string "3rd"
d4_m:    .string "4th"
d5_m:    .string "5th"
d6_m:    .string "6th"
d7_m:    .string "7th"
d8_m:    .string "8th"
d9_m:    .string "9th"
d10_m:    .string "10th"
d11_m:    .string "11th"
d12_m:    .string "12th"
d13_m:    .string "13th"
d14_m:    .string "14th"
d15_m:    .string "15th"
d16_m:    .string "16th"
d17_m:    .string "17th"
d18_m:    .string "18th"
d19_m:    .string "19th"
d20_m:    .string "20th"
d21_m:    .string "21st"
d22_m:    .string "22nd"
d23_m:    .string "23rd"
d24_m:    .string "24th"
d25_m:    .string "25th"
d26_m:    .string "26th"
d27_m:    .string "27th"
d28_m:    .string "28th"
d29_m:    .string "29th"
d30_m:    .string "30th"
d31_m:    .string "31st"

	.balign 8

	.data
month_m: .dword jan_m, feb_m, mar_m, apr_m, may_m, jun_m, jul_m, aug_m, sept_m, oct_m, nov_m, dec_m

day_m: .dword d1_m, d2_m, d3_m, d4_m, d5_m, d6_m, d7_m, d8_m, d9_m, d10_m, d11_m, d12_m, d13_m, d14_m, d15_m, d16_m, d17_m, d18_m, d19_m, d20_m, d21_m, d22_m, d23_m, d24_m, d25_m, d26_m, d27_m, d28_m, d29_m, d30_m, d31_m	
	.balign 8

	.text

fp	.req x29
lr	.req x30
imonth_r	.req w19
basemonth_r	.req x20
iday_r		.req w21
baseday_r	.req x22
year_r		.req w23
fmt: .string "%s %s, %d\n"
fmt_usage: .string "usage: a5b mm dd yyyy\n"
fmt_range: .string "input is either\n not a month number\n not a day of the month\n"
	.balign 8
	.global main
main:
	stp fp, lr, [sp, -16]!
	mov fp, sp

	mov w9, 1	
	mov x28, x1	//loading arguments into x28 so print doesn't stomp on x1
	mov w27, w0	//loading number of arguments in x27

	cmp w27, 4	//seeing in the input has the correct amount of arguments
	b.eq date

	b print_usage

date:
	ldr x0, [x28, w9, sxtw 3]	//loading the first argument
	bl atoi				//converting argument into an int
	mov imonth_r, w0

//checking if month number is between 1 and 12
	
		cmp imonth_r, 0
		b.le range
		cmp imonth_r, 12
		b.gt range	
	
	sub imonth_r, imonth_r, 1	//subtracting 1 from int so that the month numbers match-up
	adrp basemonth_r, month_m	
	add basemonth_r, basemonth_r, :lo12: month_m	//loading up the values of month array
	ldr x24, [basemonth_r, imonth_r, sxtw 3]	//month string name loaded onto x25

	mov w9, 2

        ldr x0, [x28, w9, sxtw 3]	//loading 2nd argument
        bl atoi				//converting argument into int
        mov iday_r, w0

//checking if argument is a day number 
		cmp iday_r, 0
                b.le range
                cmp iday_r, 31
                b.gt range

	sub iday_r, iday_r, 1		//making sure number match-up
	adrp baseday_r, day_m
        add baseday_r, baseday_r, :lo12: day_m	//loading day array
        ldr x25, [baseday_r, iday_r, sxtw 3]	//storing aquired day in x25

	mov w9, 3

        ldr x0, [x28, w9, sxtw 3]	//loading third argument
        bl atoi				//convering to int
        mov year_r, w0			//storing int


//printing out the date 
	adrp x0, fmt
	add x0, x0, :lo12:fmt
	mov x1, x24
	mov x2, x25
	mov w3, year_r
	bl printf
	
	b return

print_usage:
//used if the the correct number of arguments arent used
	ldr x0, =fmt_usage
	bl printf
	b return		
range:
//used if either the day or month arguments are out of range and arent a real date or month 
	ldr x0, =fmt_range
	bl printf
	b return
return:	ldp fp, lr, [sp], 16
	ret

