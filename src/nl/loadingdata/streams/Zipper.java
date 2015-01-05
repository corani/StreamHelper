package nl.loadingdata.streams;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public interface Zipper<T> {

	public <U> Stream<U> with(Function<List<T>, U> func);

}
