// This program demonstrates how to initialize a frame buffer for a
// 1024 x 768 display, and how to draw on it using a simple checker board
// pattern.

// Included header files
#include "uart.h"
#include "framebuffer.h"
#include "gpio.h"
#include "systimer.h"



// Function prototypes
unsigned short get_SNES();
void init_GPIO9_to_output();
void set_GPIO9();
void clear_GPIO9();
void init_GPIO11_to_output();
void set_GPIO11();
void clear_GPIO11();
void init_GPIO10_to_input();
unsigned int get_GPIO10();

#define BUTTON_B (1<<0)
#define BUTTON_Y (1<<1)
#define BUTTON_SEL (1<<2)
#define BUTTON_START (1<<3)
#define BUTTON_UP (1<<4)
#define BUTTON_DOWN (1<<5)
#define BUTTON_LEFT (1<<6)
#define BUTTON_RIGHT (1<<7)
#define BUTTON_A (1<<8)
#define BUTTON_X (1<<9)
#define BUTTON_L (1<<10)
#define BUTTON_R (1<11)


#define BLACK5    0x060606
#define BLACK4    0x505050
#define BLACK3    0x828282
#define BLACK2    0xb4b4b4
#define BLACK1    0xe6e6e6
#define WHITE     0x00FFFFFF
#define RED       0x00FF0000
#define LIME      0x0000FF00
#define BLUE      0x000000FF
#define AQUA      0x0000FFFF
#define FUCHSIA   0x00FF00FF
#define YELLOW    0x00FFFF00
#define GRAY      0x00808080
#define MAROON    0x00800000
#define OLIVE     0x00808000
#define GREEN     0x00008000
#define TEAL      0x00008080
#define NAVY      0x00000080
#define PURPLE    0x00800080
#define SILVER    0x00C0C0C0

void drawSquare(int rowStart, int columnStart, int squareSize, unsigned int color);

int MAZE_WIDTH = 16, MAZE_HEIGHT = 12;

unsigned int pColour;
//playerrow = x
//playercol = y
int playerCol, playerRow, endY, endX, startY, startX;

int start = 0;

int maze[12][16] = {
  {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5},

  {8, 5, 5, 0, 5, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 5},

  {0, 5, 0, 0, 5, 0, 5, 0, 5, 0, 0, 0, 5, 5, 0, 5},

  {0, 5, 5, 5, 5, 0, 5, 0, 5, 5, 5, 5, 5, 5, 0, 5},

  {0, 5, 0, 5, 0, 0, 5, 0, 0, 0, 0, 0, 0, 5, 0, 5},

  {0, 0, 0, 0, 0, 5, 5, 5, 5, 5, 0, 5, 5, 5, 0, 5},

  {0, 5, 0, 5, 0, 5, 0, 0, 0, 5, 0, 5, 0, 0, 0, 5},

  {0, 5, 5, 5, 0, 5, 0, 5, 5, 5, 0, 5, 0, 5, 5, 5},

  {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 9},

  {0, 0, 0, 5, 0, 0, 0, 5, 0, 5, 0, 5, 5, 5, 0, 5},

  {0, 5, 0, 5, 0, 5, 0, 0, 0, 5, 0, 0, 0, 0, 0, 5},

  {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5}};

int mazeRecovery[12][16];

void saveMaze() {
  for(unsigned int x = 0; x < 16; ++x) {
    for(unsigned int y = 0; y < 12; ++y) {
        mazeRecovery[y][x] = maze[y][x];
      }
    }
}

void refreshSquare(int row, int col, int squareSize) {

  if(maze[row][col] == 0) {
    drawSquare(row*squareSize, col*squareSize, squareSize, WHITE);
  }

  else if(maze[row][col] == 1) {
    drawSquare(row*squareSize, col*squareSize, squareSize, BLACK1);
  }
  else if(maze[row][col] == 2) {
    drawSquare(row*squareSize, col*squareSize, squareSize, BLACK2);
  }
  else if(maze[row][col] == 3) {
    drawSquare(row*squareSize, col*squareSize, squareSize, BLACK3);
  }
  else if(maze[row][col] == 4) {
    drawSquare(row*squareSize, col*squareSize, squareSize, BLACK4);
  }
  else if(maze[row][col] == 5) {
    drawSquare(row*squareSize, col*squareSize, squareSize, BLACK5);
  }
  else if(maze[row][col] == 8) {
    drawSquare(row*squareSize, col*squareSize, squareSize, NAVY);
    playerCol = row;
    playerRow = col;
    startY = row;
    startX = col;
  }
  else if(maze[row][col] == 9) {
    drawSquare(row*squareSize, col*squareSize, squareSize, SILVER);
    endY = col;
    endX = row;

  }
}

int checkCollision (int x, int y) {
  if (maze[y][x] == 5 || maze[y][x] == 4 || maze[y][x] == 3 || maze[y][x] == 2 || maze[y][x] == 1 ) {
    return 1;
  }
  else {
    return 0;
  }
}

void reset() {
  playerCol = startY;
  playerRow = startX;
  for(unsigned int x = 0; x < 16; ++x) {
    for(unsigned int y = 0; y < 12; ++y) {
        maze[y][x] = mazeRecovery[y][x];
      }
    }
  }

void drawMaze(int squareSize) {
  for(unsigned int x = 0; x < 16; ++x) {

    for(unsigned int y = 0; y < 12; ++y) {
        refreshSquare(y, x, squareSize);
    }

  }

}

void drawPlayer(int squareSize, unsigned int colour) {
  drawSquare(playerCol*squareSize, playerRow*squareSize, squareSize, colour);
}

int wallTypeCheck(int y, int x) {
  return maze[y][x];
}

////////////////////////////////////////////////////////////////////////////////
//
//  Function:       main
//
//  Arguments:      none
//
//  Returns:        void
//
//  Description:    This function initializes the UART terminal and initializes
//                  a frame buffer for a 1024 x 768 display. Each pixel in the
//                  frame buffer is 32 bits in size, which encodes an RGB value
//                  (plus an 8-bit alpha channel that is not used). The program
//                  then draws and displays an 18 x 12 checker board pattern.
//
////////////////////////////////////////////////////////////////////////////////

void main() {
    unsigned short data, currentState = 0xFFFF;

    // Initialize the UART terminal
    uart_init();

    // Set up GPIO pin #9 for output (LATCH output)
    init_GPIO9_to_output();

    // Set up GPIO pin #11 for output (CLOCK output)
    init_GPIO11_to_output();

    // Set up GPIO pin #10 for input (DATA input)
    init_GPIO10_to_input();

    // Clear the LATCH line (GPIO 9) to low
    clear_GPIO9();

    // Set CLOCK line (GPIO 11) to high
    set_GPIO11();

    // Print out a message to the console
    uart_puts("SNES Controller Program starting.\n");

    // Initialize the frame buffer
    initFrameBuffer();

    // Draw the maze and display it
    pColour = NAVY;
    saveMaze();
    drawMaze(64);
    drawPlayer(64, pColour);

    // Loop forever, echoing characters received from the console
    // on a separate line with : : around the character
    while (1) {
      data = get_SNES();

  // Write out data if the state of the controller has changed
  if ((data != currentState)) {
      // Write the data out to the console in hexadecimal
      //Start Button
      if ( (data & BUTTON_START) == BUTTON_START ) {
        refreshSquare(playerCol, playerRow, 64);
        refreshSquare(endX, endY, 64);
        reset();
        drawMaze(64);
        pColour = RED;
        drawPlayer(64, pColour);
        start = 1;
      }

      if(start ==1) {

      //Move Left
      if ( (data & BUTTON_LEFT) == BUTTON_LEFT ) {
        if ((playerRow > 0) && checkCollision(playerRow-1, playerCol) == 0) {
          refreshSquare(playerCol, playerRow, 64);
          playerRow -= 1;
          drawPlayer(64, pColour);
        }
      }

      //Move Right
      if ( (data & BUTTON_RIGHT) == BUTTON_RIGHT ) {
        if ((playerRow < 15) && checkCollision(playerRow+1, playerCol) == 0) {
          refreshSquare(playerCol, playerRow, 64);
          playerRow += 1;
          drawPlayer(64, pColour);
        }
      }

      //Move Up
      if ( (data & BUTTON_UP) == BUTTON_UP ) {
        if((playerCol > 0) && checkCollision(playerRow, playerCol-1) == 0 ) {
          refreshSquare(playerCol, playerRow, 64);
          playerCol -= 1;
          drawPlayer(64, pColour);
        }
      }

      //Move Down
      if ( (data & BUTTON_DOWN) == BUTTON_DOWN ) {
        if((playerCol < 11) && checkCollision(playerRow, playerCol+1) == 0) {
          refreshSquare(playerCol, playerRow, 64);
          playerCol += 1;
          drawPlayer(64,pColour);
        }
      }

      if ((data & BUTTON_X) == BUTTON_X && (data & BUTTON_LEFT) == BUTTON_LEFT) {
        if (playerRow > 0) {
        refreshSquare(playerCol, playerRow-1, 64);
        if(maze[playerCol][playerRow-1] == 5) {
          maze[playerCol][playerRow-1] = 4;
          refreshSquare(playerCol, playerRow-1, 64);
          //refreshSquare(playerRow, playerCol, 64);
        }
        else if(maze[playerCol][playerRow-1] == 4) {
          maze[playerCol][playerRow-1] = 3;
          refreshSquare(playerCol, playerRow-1, 64);
          //refreshSquare(playerRow, playerCol, 64);
        }
        else if(maze[playerCol][playerRow-1] == 3) {
          maze[playerCol][playerRow-1] = 2;
          refreshSquare(playerCol, playerRow-1, 64);
          //refreshSquare(playerRow, playerCol, 64);
        }
        else if(maze[playerCol][playerRow-1] == 2) {
          maze[playerCol][playerRow-1] = 1;
          refreshSquare(playerCol, playerRow-1, 64);
          //refreshSquare(playerRow, playerCol, 64);
        }
        else if(maze[playerCol][playerRow-1] == 1) {
          maze[playerCol][playerRow-1] = 0;
          refreshSquare(playerCol, playerRow-1, 64);
          //refreshSquare(playerRow, playerCol, 64);
      }
    }
  }

      if ((data & BUTTON_X) == BUTTON_X && (data & BUTTON_RIGHT) == BUTTON_RIGHT) {
        if (playerRow < 15) {
        refreshSquare(playerCol, playerRow+1, 64);
        if(maze[playerCol][playerRow+1] == 5) {
          maze[playerCol][playerRow+1] = 4;
          refreshSquare(playerCol, playerRow+1, 64);
          //refreshSquare(playerRow, playerCol, 64);
        }
        else if(maze[playerCol][playerRow+1] == 4) {
          maze[playerCol][playerRow+1] = 3;
          refreshSquare(playerCol, playerRow+1, 64);
          //refreshSquare(playerRow, playerCol, 64);
        }
        else if(maze[playerCol][playerRow+1] == 3) {
          maze[playerCol][playerRow+1] = 2;
          refreshSquare(playerCol, playerRow+1, 64);
          //refreshSquare(playerRow, playerCol, 64);
        }
        else if(maze[playerCol][playerRow+1] == 2) {
          maze[playerCol][playerRow+1] = 1;
          refreshSquare(playerCol, playerRow+1, 64);
          //refreshSquare(playerRow, playerCol, 64);
        }
        else if(maze[playerCol][playerRow+1] == 1) {
          maze[playerCol][playerRow+1] = 0;
          refreshSquare(playerCol, playerRow+1, 64);
          //refreshSquare(playerRow, playerCol, 64);
        }
      }
    }

        if ((data & BUTTON_X) == BUTTON_X && (data & BUTTON_UP) == BUTTON_UP) {
          if(playerCol > 0) {
          refreshSquare(playerCol-1, playerRow, 64);
          if(maze[playerCol-1][playerRow] == 5) {
            maze[playerCol-1][playerRow] = 4;
            refreshSquare(playerCol-1, playerRow, 64);
            //refreshSquare(playerRow, playerCol, 64);
          }
          else if(maze[playerCol-1][playerRow] == 4) {
            maze[playerCol-1][playerRow] = 3;
            refreshSquare(playerCol-1, playerRow, 64);
            //refreshSquare(playerRow, playerCol, 64);
          }
          else if(maze[playerCol-1][playerRow] == 3) {
            maze[playerCol-1][playerRow] = 2;
            refreshSquare(playerCol-1, playerRow, 64);
            //refreshSquare(playerRow, playerCol, 64);
          }
          else if(maze[playerCol-1][playerRow] == 2) {
            maze[playerCol-1][playerRow] = 1;
            refreshSquare(playerCol-1, playerRow, 64);
            //refreshSquare(playerRow, playerCol, 64);
          }
          else if(maze[playerCol-1][playerRow] == 1) {
            maze[playerCol-1][playerRow] = 0;
            refreshSquare(playerCol-1, playerRow, 64);
            //refreshSquare(playerRow, playerCol, 64);
          }
        }
      }

        if ((data & BUTTON_X) == BUTTON_X && (data & BUTTON_DOWN) == BUTTON_DOWN) {
          if(playerCol < 11) {
          refreshSquare(playerCol+1, playerRow, 64);
          if(maze[playerCol+1][playerRow] == 5) {
            maze[playerCol+1][playerRow] = 4;
            refreshSquare(playerCol+1, playerRow, 64);
            //refreshSquare(playerRow, playerCol, 64);
          }
          else if(maze[playerCol+1][playerRow] == 4) {
            maze[playerCol+1][playerRow] = 3;
            refreshSquare(playerCol+1, playerRow, 64);
            //refreshSquare(playerRow, playerCol, 64);
          }
          else if(maze[playerCol+1][playerRow] == 3) {
            maze[playerCol+1][playerRow] = 2;
            refreshSquare(playerCol+1, playerRow, 64);
            //refreshSquare(playerRow, playerCol, 64);
          }
          else if(maze[playerCol+1][playerRow] == 2) {
            maze[playerCol+1][playerRow] = 1;
            refreshSquare(playerCol+1, playerRow, 64);
            //refreshSquare(playerRow, playerCol, 64);
          }
          else if(maze[playerCol+1][playerRow] == 1) {
            maze[playerCol+1][playerRow] = 0;
            refreshSquare(playerCol+1, playerRow, 64);
            //refreshSquare(playerRow, playerCol, 64);
          }
        }
      }

      //Record the state of the controller
      currentState = data;
      if ((endY == playerRow) && (endX == playerCol)) {
        uart_puts("END /ln");
        refreshSquare(playerCol, playerRow, 64);
        drawPlayer(64,GREEN);
        playerCol = 99;
        playerRow = 99;

    }
      // Delay 1/30th of a second
      microsecond_delay(16667);
      }
    }
  }
}

////////////////////////////////////////////////////////////////////////////////
//
//  Function:       get_SNES
//
//  Arguments:      none
//
//  Returns:        A short integer with the button presses encoded with 16
//                  bits. 1 means pressed, and 0 means unpressed. Bit 0 is
//                  button B, Bit 1 is button Y, etc. up to Bit 11, which is
//                  button R. Bits 12-15 are always 0.
//
//  Description:    This function samples the button presses on the SNES
//                  controller, and returns an encoding of these in a 16-bit
//                  integer. We assume that the CLOCK output is already high,
//                  and set the LATCH output to high for 12 microseconds. This
//                  causes the controller to latch the values of the button
//                  presses into its internal register. We then clock this data
//                  to the CPU over the DATA line in a serial fashion, by
//                  pulsing the CLOCK line low 16 times. We read the data on
//                  the falling edge of the clock. The rising edge of the clock
//                  causes the controller to output the next bit of serial data
//                  to be place on the DATA line. The clock cycle is 12
//                  microseconds long, so the clock is low for 6 microseconds,
//                  and then high for 6 microseconds.
//
////////////////////////////////////////////////////////////////////////////////

unsigned short get_SNES()
{
    int i;
    unsigned short data = 0;
    unsigned int value;


    // Set LATCH to high for 12 microseconds. This causes the controller to
    // latch the values of button presses into its internal register. The
    // first serial bit also becomes available on the DATA line.
    set_GPIO9();
    microsecond_delay(12);
    clear_GPIO9();

    // Output 16 clock pulses, and read 16 bits of serial data
    for (i = 0; i < 16; i++) {
	// Delay 6 microseconds (half a cycle)
	microsecond_delay(6);

	// Clear the CLOCK line (creates a falling edge)
	clear_GPIO11();

	// Read the value on the input DATA line
	value = get_GPIO10();

	// Store the bit read. Note we convert a 0 (which indicates a button
	// press) to a 1 in the returned 16-bit integer. Unpressed buttons
	// will be encoded as a 0.
	if (value == 0) {
	    data |= (0x1 << i);
	}

	// Delay 6 microseconds (half a cycle)
	microsecond_delay(6);

	// Set the CLOCK to 1 (creates a rising edge). This causes the
	// controller to output the next bit, which we read half a
	// cycle later.
	set_GPIO11();
    }

    // Return the encoded data
    return data;
}



////////////////////////////////////////////////////////////////////////////////
//
//  Function:       init_GPIO9_to_output
//
//  Arguments:      none
//
//  Returns:        void
//
//  Description:    This function sets GPIO pin 9 to an output pin without
//                  any pull-up or pull-down resistors.
//
////////////////////////////////////////////////////////////////////////////////

void init_GPIO9_to_output()
{
    register unsigned int r;


    // Get the current contents of the GPIO Function Select Register 0
    r = *GPFSEL0;

    // Clear bits 27 - 29. This is the field FSEL9, which maps to GPIO pin 9.
    // We clear the bits by ANDing with a 000 bit pattern in the field.
    r &= ~(0x7 << 27);

    // Set the field FSEL9 to 001, which sets pin 9 to an output pin.
    // We do so by ORing the bit pattern 001 into the field.
    r |= (0x1 << 27);

    // Write the modified bit pattern back to the
    // GPIO Function Select Register 0
    *GPFSEL0 = r;

    // Disable the pull-up/pull-down control line for GPIO pin 9. We follow the
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

    // Write to the GPIO Pull-Up/Down Clock Register 0, using a 1 on bit 9 to
    // clock in the control signal for GPIO pin 9. Note that all other pins
    // will retain their previous state.
    *GPPUDCLK0 = (0x1 << 9);

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
//  Function:       set_GPIO9
//
//  Arguments:      none
//
//  Returns:        void
//
//  Description:    This function sets the GPIO output pin 9
//                  to a 1 (high) level.
//
////////////////////////////////////////////////////////////////////////////////

void set_GPIO9()
{
    register unsigned int r;

    // Put a 1 into the SET9 field of the GPIO Pin Output Set Register 0
    r = (0x1 << 9);
    *GPSET0 = r;
}



////////////////////////////////////////////////////////////////////////////////
//
//  Function:       clear_GPIO9
//
//  Arguments:      none
//
//  Returns:        void
//
//  Description:    This function clears the GPIO output pin 9
//                  to a 0 (low) level.
//
////////////////////////////////////////////////////////////////////////////////

void clear_GPIO9()
{
    register unsigned int r;

    // Put a 1 into the CLR9 field of the GPIO Pin Output Clear Register 0
    r = (0x1 << 9);
    *GPCLR0 = r;
}



////////////////////////////////////////////////////////////////////////////////
//
//  Function:       init_GPIO11_to_output
//
//  Arguments:      none
//
//  Returns:        void
//
//  Description:    This function sets GPIO pin 11 to an output pin without
//                  any pull-up or pull-down resistors.
//
////////////////////////////////////////////////////////////////////////////////

void init_GPIO11_to_output()
{
    register unsigned int r;


    // Get the current contents of the GPIO Function Select Register 1
    r = *GPFSEL1;

    // Clear bits 3 - 5. This is the field FSEL11, which maps to GPIO pin 11.
    // We clear the bits by ANDing with a 000 bit pattern in the field.
    r &= ~(0x7 << 3);

    // Set the field FSEL11 to 001, which sets pin 9 to an output pin.
    // We do so by ORing the bit pattern 001 into the field.
    r |= (0x1 << 3);

    // Write the modified bit pattern back to the
    // GPIO Function Select Register 1
    *GPFSEL1 = r;

    // Disable the pull-up/pull-down control line for GPIO pin 11. We follow the
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

    // Write to the GPIO Pull-Up/Down Clock Register 0, using a 1 on bit 11 to
    // clock in the control signal for GPIO pin 11. Note that all other pins
    // will retain their previous state.
    *GPPUDCLK0 = (0x1 << 11);

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
//  Function:       set_GPIO11
//
//  Arguments:      none
//
//  Returns:        void
//
//  Description:    This function sets the GPIO output pin 11
//                  to a 1 (high) level.
//
////////////////////////////////////////////////////////////////////////////////

void set_GPIO11()
{
    register unsigned int r;

    // Put a 1 into the SET11 field of the GPIO Pin Output Set Register 0
    r = (0x1 << 11);
    *GPSET0 = r;
}



////////////////////////////////////////////////////////////////////////////////
//
//  Function:       clear_GPIO11
//
//  Arguments:      none
//
//  Returns:        void
//
//  Description:    This function clears the GPIO output pin 11
//                  to a 0 (low) level.
//
////////////////////////////////////////////////////////////////////////////////

void clear_GPIO11()
{
    register unsigned int r;

    // Put a 1 into the CLR11 field of the GPIO Pin Output Clear Register 0
    r = (0x1 << 11);
    *GPCLR0 = r;
}



////////////////////////////////////////////////////////////////////////////////
//
//  Function:       init_GPIO10_to_input
//
//  Arguments:      none
//
//  Returns:        void
//
//  Description:    This function sets GPIO pin 10 to an input pin without
//                  any internal pull-up or pull-down resistors. Note that
//                  a pull-down (or pull-up) resistor must be used externally
//                  on the bread board circuit connected to the pin. Be sure
//                  that the pin high level is 3.3V (definitely NOT 5V).
//
////////////////////////////////////////////////////////////////////////////////

void init_GPIO10_to_input()
{
    register unsigned int r;


    // Get the current contents of the GPIO Function Select Register 1
    r = *GPFSEL1;

    // Clear bits 0 - 2. This is the field FSEL10, which maps to GPIO pin 10.
    // We clear the bits by ANDing with a 000 bit pattern in the field. This
    // sets the pin to be an input pin.
    r &= ~(0x7 << 0);

    // Write the modified bit pattern back to the
    // GPIO Function Select Register 1
    *GPFSEL1 = r;

    // Disable the pull-up/pull-down control line for GPIO pin 10. We follow the
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

    // Write to the GPIO Pull-Up/Down Clock Register 0, using a 1 on bit 10 to
    // clock in the control signal for GPIO pin 10. Note that all other pins
    // will retain their previous state.
    *GPPUDCLK0 = (0x1 << 10);

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
//  Function:       get_GPIO10
//
//  Arguments:      none
//
//  Returns:        1 if the pin level is high, and 0 if the pin level is low.
//
//  Description:    This function gets the current value of pin 10.
//
////////////////////////////////////////////////////////////////////////////////

unsigned int get_GPIO10()
{
    register unsigned int r;


    // Get the current contents of the GPIO Pin Level Register 0
    r = *GPLEV0;

    // Isolate pin 10, and return its value (a 0 if low, or a 1 if high)
    return ((r >> 10) & 0x1);
}
