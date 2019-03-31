// This program sets up GPIO pin 24 as an input pin, and sets it to generate
// an interrupt whenever a rising edge is detected. The pin is assumed to
// be connected to a push button switch on a breadboard. When the button is
// pushed, a 3.3V level will be applied to the pin. The pin should otherwise
// be pulled low with a pull-down resistor of 10K Ohms.

// Include files
#include "uart.h"
#include "sysreg.h"
#include "gpio.h"
#include "irq.h"
#include "systimer.h"


// Function prototypes
void init_GPIO24_to_risingEdgeInterrupt();
void init_GPIO23_to_fallingEdgeInterrupt();

void init_GPIO17_to_output();
void set_GPIO17();
void clear_GPIO17();

void init_GPIO27_to_output();
void set_GPIO27();
void clear_GPIO27();

void init_GPIO22_to_output();
void set_GPIO22();
void clear_GPIO22();

// Declare a global shared variable
unsigned int sharedValue;



////////////////////////////////////////////////////////////////////////////////
//
//  Function:       main
//
//  Arguments:      none
//
//  Returns:        void
//
//  Description:    This function first prints out the values of some system
//                  registers for diagnostic purposes. It then initializes
//                  GPIO pin 24 and 23 to be an input pin that generates an interrupt
//                  (IRQ exception) whenever a rising edge occurs on pin 24 and when a falling edge occurs on pin 23.
//                  The function then goes into an infinite loop, the program starts in state 1 where LED 1, LED2, then LED3 are lit in that order. The
//                  shared global variable is continually checked. If the
//                  interrupt service routine changes the shared variable,
//                  then this is detected in the loop, and the current value
//                  is printed out and the program transitions from state 1 to state 2, vice versa, or stays the in the same state. State 2 of the program is where LED3, LED2, LED1 are lit in that order.
//
////////////////////////////////////////////////////////////////////////////////

void main()
{
    unsigned int r;
    sharedValue = 1;


    // Set up the UART serial port
    uart_init();

    // Query the current exception level
    r = getCurrentEL();

    // Print out the exception level
    uart_puts("Current exception level is:  0x");
    uart_puthex(r);
    uart_puts("\n");

    // Get the SPSel value
    r = getSPSel();

    // Print out the SPSel value
    uart_puts("SPSel is:  0x");
    uart_puthex(r);
    uart_puts("\n");

    // Query the current DAIF flag values
    r = getDAIF();

    // Print out the DAIF flag values
    uart_puts("Initial DAIF flags are:  0x");
    uart_puthex(r);
    uart_puts("\n");

    // Print out initial values of the Interrupt Enable Register 2
    r = *IRQ_ENABLE_IRQS_2;
    uart_puts("Initial IRQ_ENABLE_IRQS_2 is:  0x");
    uart_puthex(r);
    uart_puts("\n");

    // Print out initial values the GPREN0 register (rising edge interrupt
    // enable register)
    r = *GPREN0;
    uart_puts("Initial GPREN0 is:  0x");
    uart_puthex(r);
    uart_puts("\n");

    // Set up GPIO pin #24 to input and so that it triggers
    // an interrupt when a rising edge is detected
    init_GPIO24_to_risingEdgeInterrupt();

    // Set up GPIO pin #23 to input and so that it triggers
    // an interrupt when a falling edge is detected
    init_GPIO23_to_fallingEdgeInterrupt();

    // Enable IRQ Exceptions
    enableIRQ();

    // Query the DAIF flag values
    r = getDAIF();

    // Print out the new DAIF flag values
    uart_puts("\nNew DAIF flags are:  0x");
    uart_puthex(r);
    uart_puts("\n");

    // Print out new value of the Interrupt Enable Register 2
    r = *IRQ_ENABLE_IRQS_2;
    uart_puts("New IRQ_ENABLE_IRQS_2 is:  0x");
    uart_puthex(r);
    uart_puts("\n");

    // Print out new value of the GPREN0 register
    r = *GPREN0;
    uart_puts("New GPREN0 is:  0x");
    uart_puthex(r);
    uart_puts("\n");


    // Print out a message to the console
    uart_puts("\nRising Edge IRQ program starting.\n");

    // Loop forever, waiting for interrupts to change the shared value
    while (1) {
        // Check to see if the shared value was changed by an interrupt
	if (sharedValue == 1) {

	    // Print out the shared value
	    uart_puts("\nsharedValue is:  ");
	    uart_puthex(sharedValue);
	    uart_puts("\n");
      // Set up GPIO pin #17 for output
      init_GPIO17_to_output();

      // Set up GPIO pin #27 for output
      init_GPIO27_to_output();

      // Set up GPIO Pin #22 for output
      init_GPIO22_to_output();

      // Print out a message to the console
      uart_puts("State 1.\n");

      // Loop forever, blinking the LED on and off
          // LED1 on, LED2 and LED3 off
          set_GPIO17();
          clear_GPIO27();
          clear_GPIO22();

          // Print a message to the console
          uart_puts("LED1\n");

          // Delay 0.5 second using the system timer
          microsecond_delay(500000);


        // LED2 on, LED1 and LED3 off
          clear_GPIO17();
          set_GPIO27();
          clear_GPIO22();

          // Print a message to the console
          uart_puts("LED2\n");

          // Delay 0.5 second using the system timer
          microsecond_delay(500000);

    // LED3 on, LED1 and LED2 off
          clear_GPIO17();
          clear_GPIO27();
          set_GPIO22();

          // Print a message to the console
          uart_puts("LED3\n");

          // Delay 0.5 second using the system timer
          microsecond_delay(500000);
      }
  if (sharedValue == 2) {

    // Print out the shared value
    uart_puts("\nsharedValue is:  ");
    uart_puthex(sharedValue);
    uart_puts("\n");
    // Set up GPIO pin #17 for output
    init_GPIO17_to_output();

    // Set up GPIO pin #27 for output
    init_GPIO27_to_output();

    // Set up GPIO Pin #22 for output
    init_GPIO22_to_output();

    // Print out a message to the console
    uart_puts("State 2.\n");

    // Loop forever, blinking the LED on and off
        // LED3 on, LED2 and LED1 off
        clear_GPIO17();
        clear_GPIO27();
	      set_GPIO22();

        // Print a message to the console
        uart_puts("LED3\n");

        // Delay 0.25 second using the system timer
        microsecond_delay(250000);

    	// LED2 on, LED1 and LED3 off
        clear_GPIO17();
	      set_GPIO27();
	      clear_GPIO22();

        // Print a message to the console
        uart_puts("LED2\n");

        // Delay 0.25 second using the system timer
        microsecond_delay(250000);

	// LED1 on, LED3 and LED2 off
        set_GPIO17();
        clear_GPIO27();
	      clear_GPIO22();

        // Print a message to the console
        uart_puts("LED1\n");

        // Delay 0.25 second using the system timer
        microsecond_delay(250000);
    }
        }
    }



////////////////////////////////////////////////////////////////////////////////
//
//  Function:       init_GPIO24_to_risingEdgeInterrupt
//
//  Arguments:      none
//
//  Returns:        void
//
//  Description:    This function sets GPIO pin 24 to an input pin without
//                  any internal pull-up or pull-down resistors. Note that
//                  a pull-down (or pull-up) resistor must be used externally
//                  on the bread board circuit connected to the pin. Be sure
//                  that the pin high level is 3.3V (definitely NOT 5V).
//                  GPIO pin 24 is also set to trigger an interrupt on a
//                  rising edge, and GPIO interrupts are enabled on the
//                  interrupt controller.
//
////////////////////////////////////////////////////////////////////////////////

void init_GPIO24_to_risingEdgeInterrupt()
{
    register unsigned int r;


    // Get the current contents of the GPIO Function Select Register 2
    r = *GPFSEL2;

    // Clear bits 12 - 14. This is the field FSEL17, which maps to GPIO pin 24.
    // We clear the bits by ANDing with a 000 bit pattern in the field. This
    // sets the pin to be an input pin
    r &= ~(0x7 << 12);

    // Write the modified bit pattern back to the
    // GPIO Function Select Register 2
    *GPFSEL2 = r;

    // Disable the pull-up/pull-down control line for GPIO pin 24. We follow the
    // procedure outlined on page 101 of the BCM2837 ARM Peripherals manual. We
    // will pull down the pin using an external resistor connected to ground.

    // Disable internal pull-up/pull-down by setting bits 0:1
    // to 00 in the GPIO Pull-Up/Down Register
    *GPPUD = 0x0;

    // Wait 150 cycles to provide the required set-up time
    // for the control signal
    r = 150;
    while (r--) {
        asm volatile("nop");
    }

    // Write to the GPIO Pull-Up/Down Clock Register 0, using a 1 on bit 24 to
    // clock in the control signal for GPIO pin 24. Note that all other pins
    // will retain their previous state.
    *GPPUDCLK0 = (0x1 << 24);

    // Wait 150 cycles to provide the required hold time
    // for the control signal
    r = 150;
    while (r--) {
        asm volatile("nop");
    }

    // Clear all bits in the GPIO Pull-Up/Down Clock Register 0
    // in order to remove the clock
    *GPPUDCLK0 = 0;

    // Set pin 24 to so that it generates an interrupt on a rising edge.
    // We do so by setting bit 24 in the GPIO Rising Edge Detect Enable
    // Register 0 to a 1 value (p. 97 in the Broadcom manual).
    *GPREN0 = (0x1 << 24);

    // Enable the GPIO IRQS for ALL the GPIO pins by setting IRQ 52
    // GPIO_int[3] in the Interrupt Enable Register 2 to a 1 value.
    // See p. 117 in the Broadcom Peripherals Manual.
    *IRQ_ENABLE_IRQS_2 = (0x1 << 20);
}




////////////////////////////////////////////////////////////////////////////////
//
//  Function:       init_GPIO23_to_risingEdgeInterrupt
//
//  Arguments:      none
//
//  Returns:        void
//
//  Description:    This function sets GPIO pin 23 to an input pin without
//                  any internal pull-up or pull-down resistors. Note that
//                  a pull-down (or pull-up) resistor must be used externally
//                  on the bread board circuit connected to the pin. Be sure
//                  that the pin high level is 3.3V (definitely NOT 5V).
//                  GPIO pin 23 is also set to trigger an interrupt on a
//                  rising edge, and GPIO interrupts are enabled on the
//                  interrupt controller.
//
////////////////////////////////////////////////////////////////////////////////

void init_GPIO23_to_fallingEdgeInterrupt()
{
    register unsigned int r;


    // Get the current contents of the GPIO Function Select Register 2
    r = *GPFSEL2;

    // Clear bits 9 - 11. This is the field FSEL17, which maps to GPIO pin 23.
    // We clear the bits by ANDing with a 000 bit pattern in the field. This
    // sets the pin to be an input pin
    r &= ~(0x7 << 9);

    // Write the modified bit pattern back to the
    // GPIO Function Select Register 2
    *GPFSEL2 = r;

    // Disable the pull-up/pull-down control line for GPIO pin 23. We follow the
    // procedure outlined on page 101 of the BCM2837 ARM Peripherals manual. We
    // will pull down the pin using an external resistor connected to ground.

    // Disable internal pull-up/pull-down by setting bits 0:1
    // to 00 in the GPIO Pull-Up/Down Register
    *GPPUD = 0x0;

    // Wait 150 cycles to provide the required set-up time
    // for the control signal
    r = 150;
    while (r--) {
        asm volatile("nop");
    }

    // Write to the GPIO Pull-Up/Down Clock Register 0, using a 1 on bit 23 to
    // clock in the control signal for GPIO pin 23. Note that all other pins
    // will retain their previous state.
    *GPPUDCLK0 = (0x1 << 23);

    // Wait 150 cycles to provide the required hold time
    // for the control signal
    r = 150;
    while (r--) {
        asm volatile("nop");
    }

    // Clear all bits in the GPIO Pull-Up/Down Clock Register 0
    // in order to remove the clock
    *GPPUDCLK0 = 0;

    // Set pin 23 to so that it generates an interrupt on a rising edge.
    // We do so by setting bit 23 in the GPIO Rising Edge Detect Enable
    // Register 0 to a 1 value (p. 97 in the Broadcom manual).
    *GPFEN0 = (0x1 << 23);

    // Enable the GPIO IRQS for ALL the GPIO pins by setting IRQ 52
    // GPIO_int[3] in the Interrupt Enable Register 2 to a 1 value.
    // See p. 117 in the Broadcom Peripherals Manual.
    *IRQ_ENABLE_IRQS_2 = (0x1 << 20);
}





////////////////////////////////////////////////////////////////////////////////
//
//  Function:       init_GPIO17_to_output
//
//  Arguments:      none
//
//  Returns:        void
//
//  Description:    This function sets GPIO pin 17 to an output pin without
//                  any pull-up or pull-down resistors.
//
////////////////////////////////////////////////////////////////////////////////

void init_GPIO17_to_output()
{
    register unsigned int r;


    // Get the current contents of the GPIO Function Select Register 2
    r = *GPFSEL1;

    // Clear bits 21-23. This is the field FSEL23, which maps to GPIO pin 17.
    // We clear the bits by ANDing with a 000 bit pattern in the field.
    r &= ~(0x7 << 21);

    // Set the field FSEL23 to 001, which sets pin 17 to an output pin.
    // We do so by ORing the bit pattern 001 into the field.
    r |= (0x1 << 21);

    // Write the modified bit pattern back to the
    // GPIO Function Select Register 2
    *GPFSEL1 = r;

    // Disable the pull-up/pull-down control line for GPIO pin 17. We follow the
    // procedure outlined on page 101 of the BCM2837 ARM Peripherals manual. The
    // internal pull-up and pull-down resistor isn't needed for an output pin.

    // Disable pull-up/pull-down by setting bits 0:1
    // to 00 in the GPIO Pull-Up/Down Register
    *GPPUD = 0x0;

    // Wait 150 cycles to provide the required set-up time
    // for the control signal
    r = 150;
    while (r--) {
      asm volatile("nop");
    }

    // Write to the GPIO Pull-Up/Down Clock Register 0, using a 1 on bit 17 to
    // clock in the control signal for GPIO pin 17. Note that all other pins
    // will retain their previous state.
    *GPPUDCLK0 = (0x1 << 17);

    // Wait 150 cycles to provide the required hold time
    // for the control signal
    r = 150;
    while (r--) {
      asm volatile("nop");
    }

    // Clear all bits in the GPIO Pull-Up/Down Clock Register 0
    // in order to remove the clock
    *GPPUDCLK0 = 0;
}





////////////////////////////////////////////////////////////////////////////////
//
//  Function:       init_GPIO27_to_output
//
//  Arguments:      none
//
//  Returns:        void
//
//  Description:    This function sets GPIO pin 27 to an output pin without
//                  any pull-up or pull-down resistors.
//
////////////////////////////////////////////////////////////////////////////////

void init_GPIO27_to_output()
{
    register unsigned int r;


    // Get the current contents of the GPIO Function Select Register 2
    r = *GPFSEL2;

    // Clear bits 21-23. This is the field FSEL23, which maps to GPIO pin 27.
    // We clear the bits by ANDing with a 000 bit pattern in the field.
    r &= ~(0x7 << 21);

    // Set the field FSEL23 to 001, which sets pin 27 to an output pin.
    // We do so by ORing the bit pattern 001 into the field.
    r |= (0x1 << 21);

    // Write the modified bit pattern back to the
    // GPIO Function Select Register 2
    *GPFSEL2 = r;

    // Disable the pull-up/pull-down control line for GPIO pin 27. We follow the
    // procedure outlined on page 101 of the BCM2837 ARM Peripherals manual. The
    // internal pull-up and pull-down resistor isn't needed for an output pin.

    // Disable pull-up/pull-down by setting bits 0:1
    // to 00 in the GPIO Pull-Up/Down Register
    *GPPUD = 0x0;

    // Wait 150 cycles to provide the required set-up time
    // for the control signal
    r = 150;
    while (r--) {
      asm volatile("nop");
    }

    // Write to the GPIO Pull-Up/Down Clock Register 0, using a 1 on bit 27 to
    // clock in the control signal for GPIO pin 27. Note that all other pins
    // will retain their previous state.
    *GPPUDCLK0 = (0x1 << 27);

    // Wait 150 cycles to provide the required hold time
    // for the control signal
    r = 150;
    while (r--) {
      asm volatile("nop");
    }

    // Clear all bits in the GPIO Pull-Up/Down Clock Register 0
    // in order to remove the clock
    *GPPUDCLK0 = 0;
}




////////////////////////////////////////////////////////////////////////////////
//
//  Function:       init_GPIO22_to_output
//
//  Arguments:      none
//
//  Returns:        void
//
//  Description:    This function sets GPIO pin 22 to an output pin without
//                  any pull-up or pull-down resistors.
//
////////////////////////////////////////////////////////////////////////////////

void init_GPIO22_to_output()
{
    register unsigned int r;


    // Get the current contents of the GPIO Function Select Register 2
    r = *GPFSEL2;

    // Clear bits 6-8. This is the field FSEL23, which maps to GPIO pin 22.
    // We clear the bits by ANDing with a 000 bit pattern in the field.
    r &= ~(0x7 << 6);

    // Set the field FSEL23 to 001, which sets pin 22 to an output pin.
    // We do so by ORing the bit pattern 001 into the field.
    r |= (0x1 << 6);

    // Write the modified bit pattern back to the
    // GPIO Function Select Register 2
    *GPFSEL2 = r;

    // Disable the pull-up/pull-down control line for GPIO pin 22. We follow the
    // procedure outlined on page 101 of the BCM2837 ARM Peripherals manual. The
    // internal pull-up and pull-down resistor isn't needed for an output pin.

    // Disable pull-up/pull-down by setting bits 0:1
    // to 00 in the GPIO Pull-Up/Down Register
    *GPPUD = 0x0;

    // Wait 150 cycles to provide the required set-up time
    // for the control signal
    r = 150;
    while (r--) {
      asm volatile("nop");
    }

    // Write to the GPIO Pull-Up/Down Clock Register 0, using a 1 on bit 22 to
    // clock in the control signal for GPIO pin 22. Note that all other pins
    // will retain their previous state.
    *GPPUDCLK0 = (0x1 << 22);

    // Wait 150 cycles to provide the required hold time
    // for the control signal
    r = 150;
    while (r--) {
      asm volatile("nop");
    }

    // Clear all bits in the GPIO Pull-Up/Down Clock Register 0
    // in order to remove the clock
    *GPPUDCLK0 = 0;
}

////////////////////////////////////////////////////////////////////////////////
//
//  Function:       set_GPIO17
//
//  Arguments:      none
//
//  Returns:        void
//
//  Description:    This function sets the GPIO output pin 17
//                  to a 1 (high) level.
//
////////////////////////////////////////////////////////////////////////////////

void set_GPIO17()
{
	  register unsigned int r;

	  // Put a 1 into the SET17 field of the GPIO Pin Output Set Register 0
	  r = (0x1 << 17);
	  *GPSET0 = r;
}





////////////////////////////////////////////////////////////////////////////////
//
//  Function:       set_GPIO27
//
//  Arguments:      none
//
//  Returns:        void
//
//  Description:    This function sets the GPIO output pin 27
//                  to a 1 (high) level.
//
////////////////////////////////////////////////////////////////////////////////

void set_GPIO27()
{
          register unsigned int r;

          // Put a 1 into the SET27 field of the GPIO Pin Output Set Register 0
          r = (0x1 << 27);
          *GPSET0 = r;
}





////////////////////////////////////////////////////////////////////////////////
//
//  Function:       set_GPIO22
//
//  Arguments:      none
//
//  Returns:        void
//
//  Description:    This function sets the GPIO output pin 22
//                  to a 1 (high) level.
//
////////////////////////////////////////////////////////////////////////////////

void set_GPIO22()
{
          register unsigned int r;

          // Put a 1 into the SET22 field of the GPIO Pin Output Set Register 0
          r = (0x1 << 22);
          *GPSET0 = r;
}





////////////////////////////////////////////////////////////////////////////////
//
//  Function:       clear_GPIO17
//
//  Arguments:      none
//
//  Returns:        void
//
//  Description:    This function clears the GPIO output pin 17
//                  to a 0 (low) level.
//
////////////////////////////////////////////////////////////////////////////////

void clear_GPIO17()
{
	  register unsigned int r;

	  // Put a 1 into the CLR17 field of the GPIO Pin Output Clear Register 0
	  r = (0x1 << 17);
	  *GPCLR0 = r;
}





////////////////////////////////////////////////////////////////////////////////
//
//  Function:       clear_GPIO27
//
//  Arguments:      none
//
//  Returns:        void
//
//  Description:    This function clears the GPIO output pin 27
//                  to a 0 (low) level.
//
////////////////////////////////////////////////////////////////////////////////

void clear_GPIO27()
{
          register unsigned int r;

          // Put a 1 into the CLR27 field of the GPIO Pin Output Clear Register 0
          r = (0x1 << 27);
          *GPCLR0 = r;
}





////////////////////////////////////////////////////////////////////////////////
//
//  Function:       clear_GPIO22
//
//  Arguments:      none
//
//  Returns:        void
//
//  Description:    This function clears the GPIO output pin 22
//                  to a 0 (low) level.
//
////////////////////////////////////////////////////////////////////////////////

void clear_GPIO22()
{
          register unsigned int r;

          // Put a 1 into the CLR22 field of the GPIO Pin Output Clear Register 0
          r = (0x1 << 22);
          *GPCLR0 = r;
}
