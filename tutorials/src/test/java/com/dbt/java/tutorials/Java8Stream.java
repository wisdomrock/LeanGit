package com.dbt.java.tutorials;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class Java8Stream {
	@Rule
    public TestRulesSetter pr = new TestRulesSetter(System.out);
	
	@Before
	public void println() {
		System.out.println();
	}
	
	@Test
	public void test1() {
		IntStream.range(1,  10)
			.forEachOrdered(System.out::println);	
	}
	
	@Test
	public void test2() {
		IntStream.range(1,  10).skip(5)
			.filter(i->i%2==0)
			.forEach(i->System.out.println(i));
	}
	
	@Test
	public void test3() {
		Stream.of("Ava", "Aneri", "Alberto")
			.sorted()
			.findFirst()
			.ifPresent(System.out::println);
	}
	
	@Test
	public void test4() {
		String[] names = {"Al", "Ankit", "Kushal", "Brent", "Sarika", "amanda", "Hans", "Shivka", "Saven"};
		Arrays.stream(names).filter(x -> x.startsWith("S"))
			.sorted()
			.forEach(System.out::println);
	}
	
	@Test
	public void test5() {
		Arrays.stream(new int[] {2,4,6,8,10})
			.map(x->x*2)
			.average()
			.ifPresent(System.out ::println);
	}
	
	@Test
	public void test6() {
		List<String> people = Arrays.asList("Al", "Ankit", "Kushal", "Brent", "Sarika", "amanda", "Hans", "Shivka", "Saven");
		people.stream()
			.map(String::toLowerCase)
			.filter(x -> x.startsWith("a"))
			.forEach(System.out::println);
	}
	
	@Test
	public void test7() throws IOException {
		Stream<String> bands = Files.lines(Paths.get("bands.txt"));
		bands.filter(x -> x.length()>7)
			.sorted()
			.forEach(System.out::println);
		bands.close();
	}
	
	@Test
	public void test8() throws IOException {
		Files.lines(Paths.get("bands.txt"))
			.filter(x -> x.length()>8)
			.collect(Collectors.toList())
			.forEach(System.out::println);
	}
	
	@Test
	public void test9() throws IOException {
		Supplier<List<String>> supplier = () -> new LinkedList<String>();
		Files.lines(Paths.get("bands.txt"))
			.filter(x -> x.length()>6)
			.sorted()
			.collect(Collectors.toCollection(supplier))
			.forEach(System.out::println);
	}
	
	@Test
	public void test10() throws IOException {
		Files.lines(Paths.get("bands.txt"))
			.filter(x -> x.length()>7)
			.sorted()
			.collect(Collectors.toCollection(LinkedList::new))
			.forEach(System.out::println);
	}
	
	@Test
	public void test11() throws IOException {
		List<Person> persons = Arrays.asList(new Person("Jun", "Shi"), new Person("William", "Shi"), new Person("Selina", "Zhang"));
		persons.parallelStream().map(Person::getFirstName).collect(Collectors.toCollection(TreeSet::new)).forEach(System.out::println);
		
		System.out.println(persons.stream().map(Person::getFirstName).collect(Collectors.joining(", ")));
	}
	
	@Test
	public void test12() throws IOException {
		IntStream.range(1,  10).average().ifPresent(System.out::println);
	}
	
	@Test
	public void test13() throws IOException {
		List<Person> persons = Arrays.asList(new Person(44), new Person(12), new Person(8));
		persons.parallelStream().collect(Collectors.summarizingInt(Person::getAge)).andThen(System.out::println);
	}
	
	@Test
	public void test15() throws IOException {
		Arrays.asList(1,2,3,4).stream().findAny().ifPresent(System.out::println);
	}	
}
