/**
 * The Component class is the super class for all components that control
 * digital components on a Raspbery PI board. In the observer pattern this
 * class represents the subject.
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

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

abstract class Component extends Observable {

	private List<Observer> observers = new ArrayList<>();
	private boolean changed = false;

	public abstract void terminate();

	@Override
	public void addObserver(Observer observer) {
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	@Override
	public void deleteObserver(Observer observer) {
		observers.remove(observer);
	}

	@Override
	public int countObservers() {
		return observers.size();
	}

	@Override
	public void deleteObservers() {
		observers.clear();
	}

	@Override
	public boolean hasChanged() {
		return changed;
	}

	@Override
	public void notifyObservers() {
		notifyComponentObservers(null);
	}

	@Override
	public void notifyObservers(Object status) {
		notifyComponentObservers(status);
	}

	@Override
	protected void setChanged() {
		changed = true;
	}

	@Override
	protected void clearChanged() {
		changed = false;
	}

	private void notifyComponentObservers(Object status) {
		observers.forEach(o -> o.update(this, status));
		setChanged();
	}
}
