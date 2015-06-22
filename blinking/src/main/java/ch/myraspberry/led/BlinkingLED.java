/**
 * The BlinkingLED program implements an application that 
 * controls a pin on a Raspberry PI platform to regulate
 * a simple LED.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * @author  Markus Keller
 * @version 0.1
 * @since   2014-06-20
 * 
 */

package ch.myraspberry.led;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public final class BlinkingLED {

	private static final Logger log = LoggerFactory
			.getLogger(BlinkingLED.class);

	public static void main(String[] args) {

		// create gpio controller
		final GpioController gpio = GpioFactory.getInstance();

		// define gpio pin number 1 as an output pin and turn it off
		final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(
				RaspiPin.GPIO_01, "LED", PinState.LOW);

		// set shutdown state for pin 1 (LED)
		pin.setShutdownOptions(true, PinState.LOW);

		try {
			// toggle pin state for 25 times
			for (int i = 0; i < 25; i++) {
				pin.toggle();
				log.info(pin.getState().toString());
				Thread.sleep(2500);
			}
			// done shut down the GPIO controller now
			gpio.shutdown();
		} catch (InterruptedException e) {
			log.error("An error occurred!", e);
		}
	}
}
