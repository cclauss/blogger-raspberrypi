/**
 * The Led class controls a LED on a raspberry PI board using
 * Pi4J. In the observer pattern this class represents the subject.
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
 * @since   2014-06-27
 * 
 */

package ch.myraspberry.led.observer.component;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class Led extends Component {

	public enum LEDStatus {
		ON, OFF
	}

	private final GpioController gpio = GpioFactory.getInstance();

	private final GpioPinDigitalOutput led = gpio.provisionDigitalOutputPin(
			RaspiPin.GPIO_01, "led", PinState.LOW);

	private boolean off;
	private LEDStatus status;

	public Led() {
		super();
		led.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
		turnOff();
	}

	@Override
	public void terminate() {
		gpio.shutdown();
	}

	public void turnOff() {
		led.setState(PinState.LOW);
		off = true;
		status = LEDStatus.OFF;
		notifyObservers(status);
	}

	public void turnOn() {
		led.setState(PinState.HIGH);
		off = false;
		status = LEDStatus.ON;
		notifyObservers(status);
	}

	public void toggle() {
		led.toggle();
		off = !off;
		status = off ? status = LEDStatus.OFF : LEDStatus.ON;
		notifyObservers(status);
	}
}
