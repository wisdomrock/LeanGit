package com.dbt.java.tutorials;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Java8GroupTest {
	@Rule
    public TestRulesSetter pr = new TestRulesSetter(System.out);
	
	private List<People> people;
	
	@Before
	public void setup() throws IOException {
		System.out.println();
		//String content = Files.readAllLines(Paths.get("students.json")).stream().collect(Collectors.joining(System.getProperty("line.separator")));
		//jsonRoot = JsonPath.read(content, "$");
		this.people = new ObjectMapper().readValue(new File("people.json"), new TypeReference<List<People>>() {});
		assertThat(this.people).size().isGreaterThan(2);
	}
	
	@After
	public void tearDown() throws IOException {
		//jsonReader.close();
	}
	
	@Test
	public void test1() {
		assertThat(people.get(1).getName()).isEqualTo("Peter");
	}
	
	@Test
	public void test2() {
		people.stream().filter(p->p.getAge()>15)
		.collect(Collectors.groupingBy(People::getGender, Collectors.summarizingInt(People::getAge))).values().stream().forEach(System.out::println);
	}
}
