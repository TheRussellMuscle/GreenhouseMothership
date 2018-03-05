/**
 * Copyright (c) 2018 Brendan Russell BrendanLeeRussell72@gmail.com
 *
 * This program was designed to work on a SparkFun RedBoard but it should work on most Arduino models.
 * The Microcontroller should be hooked up to a DS18B20 waterproof temperature sensor with the data pin
 * connected to digital 11. The board should also be hooked up to a BlueSMiRF Silver Bluetooth modem
 * with serial wires connected directly to the board's rx and tx pins.
 *
 * @author Brendan Russell
 * @version 1.0
 * @date 1/4/18
 *
 * Note: Bluetooth Module rx and tx must be unplugged while using a USB interface with Arduino and while
 * programming.
 */
#include <OneWire.h>

int DS18S20_Pin = 11; //DS18S20 Signal pin on digital 11
char tmpstring[10];

//Temperature chip i/o
OneWire ds(DS18S20_Pin);  // on digital pin 11

/**
 * Begins serial communication with the Bluetooth Module before running the main loop.
 */
void setup() {
	Serial.begin(115200); // The BlueSMiRF defaults to 115200bps
}

/**
 * The main loop of the program, currently waits for Bluetooth message and then sends data
 */
void loop() {
	if (Serial.available()) // If the Bluetooth module received a message
	{
		String message = Serial.readString();
		message = message.substring(0, message.length() - 2); //Get rid of "\n" from the end
		if (message == "ready") { //if message equals super secret password... send data
			getTemp();
			delay(100);
			float temperature = getTemp();
			int count = 0;
			/**
			 * Makes sure data is within acceptable ranges
			 * Asks for new data if outside of temperature range
			 * runs a maximum of 25 times to avoid holdups
			 */
			while (temperature < -200.0f || temperature > 200.0f) {
				temperature = getTemp();
				count++;
				delay(500);
				if (count > 25) {
					break;
				}
			}

			/**
			 * sends the temperature over Bluetooth, or an error if the temperature
			 * is not within acceptable ranges
			 */
			if (temperature > -200.0f && temperature < 200.0f) {
				Serial.println(temperature);  //sends data
			} else {
				Serial.println("error");

			}
		} else {
			//sends a default message if the password was wrong
			Serial.println("fail");
		}
	}
}

/**
 * Copyright (c) 2010 bildr community
 * This function was taken from a program by bildr community
 * See bildr permission notice below
 * gets the temperature from the DS18S20 waterproof temperature sensor
 * @return the temperature in degrees Fahrenheit
 */
float getTemp() {

	byte data[12];
	byte addr[8];

	/**
	 * returns -1000 if the sensor cannot be found
	 */
	if (!ds.search(addr)) {
		//no more sensors on chain, reset search
		ds.reset_search();
		return -1000;
	}

	/**
	 * returns -1000 if CRC is not valid
	 */
	if (OneWire::crc8(addr, 7) != addr[7]) {
		Serial.println("CRC is not valid!");
		return -1000;
	}

	/**
	 * returns -1000 if the device is not recognized
	 */
	if (addr[0] != 0x10 && addr[0] != 0x28) {
		Serial.print("Device is not recognized");
		return -1000;
	}

	ds.reset();
	ds.select(addr);
	ds.write(0x44, 1); // start conversion, with parasite power on at the end

	byte present = ds.reset();
	ds.select(addr);
	ds.write(0xBE); // Read Scratchpad

	for (int i = 0; i < 9; i++) { // we need 9 bytes
		data[i] = ds.read();
	}

	ds.reset_search();

	byte MSB = data[1];
	byte LSB = data[0];

	float tempRead = ((MSB << 8) | LSB); //using two's compliment
	float TemperatureSum = tempRead / 16;

	return (TemperatureSum * 18 + 5) / 10 + 32;
}

/**
 * Copyright (c) 2010 bildr community
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

