package ru.expram.bookingmachine.presentation.configurations;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SQLiteExtensionLoader {

    private final JdbcTemplate jdbcTemplate;
    private final Environment env;

    @PostConstruct
    public void loadExtension() {
        // SQLite doesn`t support unicode characters (required for UPPER and LOWER functions) :/
        final String path = env.getProperty("SQLEAN_LIB_PATH", "");
        if(path.isEmpty() || path.isBlank()) return;
        jdbcTemplate.execute("SELECT load_extension('%s');".formatted(path));
    }

}
