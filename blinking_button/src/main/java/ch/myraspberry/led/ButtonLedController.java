/**
 * The ButtonLedController program implements an application that 
 * controls a input pin on a Raspberry PI platform to regulate
 * a LED by a button on the board. If the button is
 * pressed, the LED changes its state (on/off).
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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class ButtonLedController {

	private enum ButtonState {
		DOWN, UP
	}

	private static final Logger log = LoggerFactory
			.getLogger(ButtonLedController.class);

	private final GpioController gpio = GpioFactory.getInstance();

	private final GpioPinDigitalOutput led = gpio.provisionDigitalOutputPin(
			RaspiPin.GPIO_01, "led", PinState.LOW);

	private final GpioPinDigitalInput button = gpio.provisionDigitalInputPin(
			RaspiPin.GPIO_02, "button", PinPullResistance.PULL_DOWN);

	private boolean buttonPressed = false;
	private boolean isRunning = false;
	private ButtonState state = ButtonState.UP;
	private ExecutorService executor;
	private Future<?> loop;

	public ButtonLedController() {
		super();
		configure();
	}

	public void start() {
		isRunning = true;
		createRunner();
		log.info("Controller has been started.");
	}

	public void stop() {
		isRunning = false;
		while (!loop.isDone()) {
			log.info("Waiting for runner to stop...");
		}
		executor.shutdown();
		log.info("Controller has been stopped.");
	}

	private void configure() {
		led.setShutdownOptions(true, PinState.LOW);
		addButtonListener();
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

	private void createRunner() {
		executor = Executors.newSingleThreadExecutor();
		loop = executor.submit(new Runnable() {
			@Override
			public void run() {
				while (isRunning) {
					if (buttonPressed) {
						led.toggle();
						buttonPressed = false;
					}
				}
			}
		});
	}

	private void handleButtonPressed(GpioPinDigitalStateChangeEvent event) {
		if (event.getState().isHigh()) {
			state = ButtonState.DOWN;
		} else if (event.getState().isLow() && state == ButtonState.DOWN) {
			buttonPressed = true;
			state = ButtonState.UP;
		}
	}
}
