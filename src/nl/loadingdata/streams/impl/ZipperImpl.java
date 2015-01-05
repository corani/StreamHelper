package nl.loadingdata.streams.impl;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import nl.loadingdata.streams.Zipper;

public class ZipperImpl<T> implements Zipper<T> {
	private Stream<Stream<T>> ss;

	public ZipperImpl(Stream<Stream<T>> ss) {
		this.ss = ss;
	}

	@Override
	public <U> Stream<U> with(Function<List<T>, U> func) {
		Iterator<U> outIter = Ziperator.of(func, ss);
		Spliterator<U> split = Spliterators.spliteratorUnknownSize(outIter, 0);
		return StreamSupport.stream(split, false);
	}
}