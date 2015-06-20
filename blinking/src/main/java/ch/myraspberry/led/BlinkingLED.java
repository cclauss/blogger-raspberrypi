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

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public final class BlinkingLED {

	public BlinkingLED() {

	}

	public static void main(String[] args) {

		// create gpio controller
		final GpioController gpio = GpioFactory.getInstance();

		// provision gpio pin #01 as an output pin and turn on
		final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(
				RaspiPin.GPIO_01, "MyLED", PinState.HIGH);

		try {
			// set shutdown state for this pin
			pin.setShutdownOptions(true, PinState.LOW);

			System.out.println("--> GPIO state should be: ON");

			Thread.sleep(5000);

			// turn off gpio pin #01
			pin.low();
			System.out.println("--> GPIO state should be: OFF");

			Thread.sleep(5000);

			// toggle the current state of gpio pin #01 (should turn on)
			pin.toggle();
			System.out.println("--> GPIO state should be: ON");

			Thread.sleep(5000);

			// toggle the current state of gpio pin #01 (should turn off)
			pin.toggle();
			System.out.println("--> GPIO state should be: OFF");

			Thread.sleep(5000);

			// turn on gpio pin #01 for 1 second and then off
			System.out
					.println("--> GPIO state should be: ON for only 1 second");
			pin.pulse(1000, true); // set second argument to 'true' use a
									// blocking
									// call
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// stop all GPIO activity/threads by shutting down the GPIO controller
		// (this method will forcefully shutdown all GPIO monitoring threads and
		// scheduled tasks)
		gpio.shutdown();
	}
}
