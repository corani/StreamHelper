package nl.loadingdata.streams.impl;
import java.util.Iterator;


public class Teeerator<T> implements Iterator<T> {
	private Stepper<T> stepper;
	private int id;

	Teeerator(Stepper<T> stepper, int id) {
		this.stepper = stepper;
		this.id = id;
	}

	@Override
	public boolean hasNext() {
		return stepper.hasNext(id);
	}

	@Override
	public T next() {
		return stepper.next(id);
	}
}
