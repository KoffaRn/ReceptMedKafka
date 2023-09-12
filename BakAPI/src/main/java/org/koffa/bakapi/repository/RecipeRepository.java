package org.koffa.bakapi.repository;

import org.koffa.bakapi.dto.Recipe;
import org.mariadb.jdbc.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeRepository {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    private Connection connection;
    private final Logger LOGGER = LoggerFactory.getLogger(RecipeRepository.class);

    public RecipeRepository() throws SQLException {
        this.connection = (Connection) DriverManager.getConnection(url, username, password);
    }
    public List<Recipe> findAll() throws SQLException {
        List<Recipe> recipes = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("""
                SELECT
                r.id,
                r.name,
                r.description,
                rs.steps,
                ri.quantity,
                ri.unit,
                i.name
                FROM recipe r
                JOIN recipe_steps rs ON r.id = rs.recipe_id
                JOIN recipe_ingredient ri ON r.id = ri.recipe_id
                JOIN ingredient i ON ri.ingredient_id = i.id
                GROUP BY r.id;
                """);
        try {
            ResultSet resultSet = preparedStatement.executeQuery() {
                while(resultSet.next()) {
                    Recipe recipe = new Recipe();
                    recipe.setId(resultSet.getLong("r.id"));
                    recipe.setName(resultSet.getString("r.name"));
                    recipe.setDescription(resultSet.getString("r.description"));
                    String[] steps = resultSet.getString("rs.steps").split(",");
                    for(String step : steps) {
                        recipe.addStep(step);
                    }

                    );
                }
            }
        }
    }
}
