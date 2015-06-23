/**
 * The ButtonLED program implements an application for 
 * starting and stopping a manager class that controls
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
 * @since   2014-06-20
 * 
 */

package ch.myraspberry.led;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ButtonLED {

	private static final Logger log = LoggerFactory.getLogger(ButtonLED.class);

	public static void main(String[] args) {

		try {
			log.info("Starting LED controller...");
			ButtonLedController controller = new ButtonLedController();
			controller.start();
			log.info("LED controller is running...");
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					controller.stop();
					log.info("LED controller terminated.");
				}
			});
		} catch (Exception e) {
			log.error("An unexpected error occurred!", e);
		}
	}
}
