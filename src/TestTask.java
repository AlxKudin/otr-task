import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestTask {
    public static void main(String[] args) throws IOException, URISyntaxException {
        var intArray = new int[]{3, 5, 0, 0, 4, 0, -3};
        offsetZeros(intArray);
        System.out.println(Arrays.toString(intArray));

        System.out.println(getPersonsFromFile());
    }

    private static List<Person> getPersonsFromFile() throws IOException, URISyntaxException {
        final AtomicInteger counter = new AtomicInteger();
        try (Stream<String> lines = Files.lines(Path.of(Objects.requireNonNull(TestTask.class.getClassLoader().getResource("persons.txt")).toURI().getPath()), Charset.defaultCharset())) {
            return lines.collect(Collectors.groupingBy(it -> counter.getAndIncrement() / 3)).values()
                    .stream().map(x -> new Person(x.get(0), x.get(1), Integer.parseInt(x.get(2))))
                    .collect(Collectors.toList());
        }
    }


    private static void offsetZeros(int[] array) {
        int countOfNonZeros = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != 0) {
                array[countOfNonZeros++] = array[i];
            }
        }
        for (int i = countOfNonZeros; i < array.length; i++) {
            array[i] = 0;
        }
    }
}
