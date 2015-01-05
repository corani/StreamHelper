package nl.loadingdata.streams;
import java.util.stream.Stream;

import nl.loadingdata.streams.impl.SplitStreamImpl;
import nl.loadingdata.streams.impl.ZipperImpl;

public class StreamHelper {

	@SafeVarargs
	public static <T> Zipper<T> zip(Stream<T> ... ss) {
		if (ss == null || ss.length < 2) {
			throw new IllegalArgumentException("Can't zip less than two Streams");
		}
		return new ZipperImpl<>(Stream.of(ss));
	}

	/**
	 * Split a Stream into several new Streams. A value must be read from each of these new Streams
	 * before a new value will be read from the input Stream. Streams will BLOCK until this happens.
	 * @param in
	 * @param num
	 * @return
	 */
	public static <T> SplitStream<T> split(Stream<T> in, int num) {
		if (in == null) {
			throw new IllegalArgumentException("In Stream can't be NULL");
		}
		if (num < 2) {
			throw new IllegalArgumentException("Can't split into less than two Streams");
		}
		return new SplitStreamImpl<T>(in, num);
	}

}
