package nl.loadingdata.streams.impl;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Ziperator<U, T> implements Iterator<U> {
	private List<Iterator<T>> iters;
	private Function<List<T>, U> func;
	private Stream<Stream<T>> iterStream;

	static <U, T> Ziperator<U, T> of(Function<List<T>, U> func, Stream<Stream<T>> iterStream) {
		return new Ziperator<U, T>(func, iterStream);
	}
	
	private Ziperator(Function<List<T>, U> func, Stream<Stream<T>> iterStream) {
		this.func  = func;
		this.iterStream = iterStream;
	}

	private Stream<Iterator<T>> iters() {
		if (iters == null) {
			iters = iterStream
					.map(s -> s.iterator())
					.collect(Collectors.toList());

		}
		return iters.stream();
	}
	
	@Override
	public boolean hasNext() {
		return iters().allMatch(i -> i.hasNext());
	}

	@Override
	public U next() {
		return func.apply(
			iters()
				.map(i -> i.next())
				.collect(Collectors.toList())
		);
	}
}