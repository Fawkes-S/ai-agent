package com.sjc.aiagent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.ai.autoconfigure.vectorstore.pgvector.PgVectorStoreAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
		PgVectorStoreAutoConfiguration.class})
public class AiAgentApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiAgentApplication.class, args);
	}

}
