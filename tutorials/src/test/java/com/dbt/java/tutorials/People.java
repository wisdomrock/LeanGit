package com.dbt.java.tutorials;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//@Builder
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter@Setter
public class People {
	private String name;
	private int age;
	private String gender;
	
}
