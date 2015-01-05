package nl.loadingdata.streams.impl;
import java.util.Iterator;
import java.util.stream.Stream;

public class Stepper<T> {
	private Iterator<T> in;
	private Boolean[] bitmap;
	private T lastValue;

	Stepper(int num, Stream<T> in) {
		this.in = in.iterator();
		bitmap = new Boolean[num];
		for (int i = 0; i < num; i++) {
			bitmap[i] = false;
		}
	}

	boolean hasNext(int id) {
		if (!bitmap[id]) {
			// first time we're asking
			if (lastValue == null) {
				return in.hasNext();
			} else {
				return true;
			}
		} else {
			// we've asked before
			boolean all = Stream.of(bitmap).allMatch(b -> b);
			if (all) {
				// all splits asked for the value. Reset and get the next value.
				lastValue = null;
				for (int i = 0; i < bitmap.length; i++) {
					bitmap[i] = false;
				}
				synchronized (bitmap) {
					bitmap.notifyAll();
					return in.hasNext();
				}
			} else {
				// not all splits have gotten the value, wait until they have.
				synchronized (bitmap) {
					try {
						bitmap.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					return in.hasNext();
				}
			}
		}
	}

	T next(int id) {
		if (bitmap[id]) {
			throw new IllegalStateException(
					"No 'hasNext' call before 'next' call");
		}
		bitmap[id] = true;
		if (lastValue == null) {
			lastValue = in.next();
		}
		return lastValue;
	}
}