import java.util.stream.Stream;

import nl.loadingdata.streams.SplitStream;
import nl.loadingdata.streams.StreamHelper;

public class Example {

	public static void main(String[] args) {
		SplitStream<Integer> as = StreamHelper.split(
			Stream.of(new Integer[] {1, 2, 3, 4, 5}),
			2
		);

		Stream<String> rows = StreamHelper.zip(
			as.get(0),
			as.get(1).map(i -> i + 1)
		).with(
			nums -> nums.get(0) + "\t" + nums.get(1) + "\t" + (int) Math.pow(nums.get(0), nums.get(1))
		);

		rows.forEach(System.out::println);
	}
}
