package demo;

import org.springframework.stereotype.Component;

@Component
public class Dog implements Animal {

	@Override
	public String say() {
		return "woof!";
	}

}
