package org.koffa.bakconsumerdatabase.repository;

import org.koffa.bakconsumerdatabase.dto.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    boolean existsByName(String name);

    Ingredient findByName(String name);
}
