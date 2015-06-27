/**
 * The ButtonLedController class acts a an controller for the components
 * (model) classes Led and Button that control components on a raspberry PI
 * board. In the Observer pattern this class represents the observer.
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

package ch.myraspberry.led.observer.controller;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.myraspberry.led.observer.component.Button;
import ch.myraspberry.led.observer.component.Button.ButtonStatus;
import ch.myraspberry.led.observer.component.Led;
import ch.myraspberry.led.observer.component.Led.LEDStatus;

public class ButtonLedController implements Observer {

	private static final Logger log = LoggerFactory
			.getLogger(ButtonLedController.class);

	private Button button;
	private Led led;
	private boolean isRunning;
	private ExecutorService executor;
	private Future<?> loop;

	public ButtonLedController() {
		super();
		isRunning = false;
		button = new Button();
		button.addObserver(this);
		led = new Led();
		led.addObserver(this);
	}

	@Override
	public void update(Observable component, Object status) {
		if (component instanceof Button) {
			handleButtonEvent((Button) component, (ButtonStatus) status);
		} else if (component instanceof Led) {
			LEDStatus ledStatus = (LEDStatus) status;
			log.info("LED has changed new status is " + ledStatus.toString());
		}
	}

	public void start() {
		isRunning = true;
		createLoop();
		log.info("Controller has been started.");
	}

	public void stop() {
		button.terminate();
		led.terminate();
		isRunning = false;
		while (!loop.isDone()) {
			log.info("Waiting for controller to stop...");
		}
		executor.shutdown();
		log.info("Controller has been stopped.");
	}

	private void createLoop() {
		executor = Executors.newSingleThreadExecutor();
		loop = executor.submit(new Runnable() {
			@Override
			public void run() {
				while (isRunning) {
				}
			}
		});
	}

	private void handleButtonEvent(Button button, ButtonStatus status) {
		if (button.isPressed()) {
			led.toggle();
		}
	}
}
