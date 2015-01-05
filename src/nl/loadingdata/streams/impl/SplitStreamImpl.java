package nl.loadingdata.streams.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import nl.loadingdata.streams.SplitStream;

public class SplitStreamImpl<T> implements SplitStream<T> {
	private Map<Integer, Stream<T>> res;
	private Stream<T> in;
	private int num;

	public SplitStreamImpl(Stream<T> in, int num) {
		this.in = in;
		this.num = num;
	}

	@Override
	public Stream<T> get(int id) {
		if (id < 0 || id >= num) {
			throw new IllegalArgumentException("No such split: " + id);
		}
		if (res == null) {
			res = new HashMap<>();

			Stepper<T> stepper = new Stepper<T>(num, in);
			for (int i = 0; i < num; i++) {
				Iterator<T> iter = new Teeerator<T>(stepper, i);
				Spliterator<T> split = Spliterators.spliteratorUnknownSize(iter, 0);
				res.put(i, StreamSupport.stream(split, false));
			}
		}
		return res.get(id);
	}
	
}
