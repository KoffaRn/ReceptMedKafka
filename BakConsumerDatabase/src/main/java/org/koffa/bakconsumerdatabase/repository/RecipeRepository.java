package org.koffa.bakconsumerdatabase.repository;

import org.koffa.bakconsumerdatabase.dto.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

}
