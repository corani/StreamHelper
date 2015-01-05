package nl.loadingdata.streams;

import java.util.stream.Stream;

public interface SplitStream<T> {

	public Stream<T> get(int id);

}
