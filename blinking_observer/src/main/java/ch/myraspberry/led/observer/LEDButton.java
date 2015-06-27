/**
 * The LEDButton program implements an application for 
 * starting and stopping a controller that controls
 * a led turned on or off by pressing a button.
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

package ch.myraspberry.led.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.myraspberry.led.observer.controller.ButtonLedController;

public final class LEDButton {

	private static final Logger log = LoggerFactory.getLogger(LEDButton.class);

	public static void main(String[] args) {

		try {
			ButtonLedController controller = new ButtonLedController();
			controller.start();
			log.info("Application is running, press Ctrl-c to terminate.");
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					controller.stop();
					log.info("LED observer terminated.");
				}
			});
		} catch (Exception e) {
			log.error("An unexpected error occurred!", e);
		}
	}
}
