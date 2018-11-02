package guru.springframework.repositories;

import guru.springframework.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface RecipeRepository extends CrudRepository<Recipe, String>{

	Set<Recipe> findAll();

	Recipe save(Recipe recipe);
}
