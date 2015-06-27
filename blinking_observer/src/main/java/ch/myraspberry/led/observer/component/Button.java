/**
 * The Button class controls a button on a raspberry PI board using
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
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class Button extends Component {

	public enum ButtonStatus {
		DOWN, UP
	}

	private final GpioController gpio = GpioFactory.getInstance();

	private final GpioPinDigitalInput button = gpio.provisionDigitalInputPin(
			RaspiPin.GPIO_02, "button", PinPullResistance.PULL_DOWN);

	private ButtonStatus status = ButtonStatus.UP;
	private boolean pressed = false;

	public Button() {
		super();
		addButtonListener();
	}

	@Override
	public void terminate() {
		gpio.shutdown();
	}

	public boolean isPressed() {
		return pressed;
	}

	private void addButtonListener() {
		button.addListener(new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(
					GpioPinDigitalStateChangeEvent event) {
				handleButtonPressed(event);
			}
		});
	}

	@Override
	public void notifyObservers(Object state) {
		super.notifyObservers(state);
		clearChanged();
	}

	@Override
	protected void clearChanged() {
		pressed = false;
		super.clearChanged();
	}

	private void handleButtonPressed(GpioPinDigitalStateChangeEvent event) {
		if (event.getState().isHigh()) {
			status = ButtonStatus.DOWN;
		} else if (event.getState().isLow() && status == ButtonStatus.DOWN) {
			pressed = true;
			status = ButtonStatus.UP;
		} else if (event.getState().isLow()) {
		}

		notifyObservers(status);
		clearChanged();
	}
}
