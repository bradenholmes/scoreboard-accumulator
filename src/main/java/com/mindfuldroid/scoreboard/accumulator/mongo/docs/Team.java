package com.mindfuldroid.scoreboard.accumulator.mongo.docs;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document("teams")
@Data
public class Team {
	
	@Id
	private String id;

	
}
