package demo;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class Person {
	
	@Resource private Animal favoriteAnimal;

	public Animal getFavoriteAnimal() {
		return favoriteAnimal;
	}

	public void setFavoriteAnimal(Animal favoriteAnimal) {
		this.favoriteAnimal = favoriteAnimal;
	}

}
