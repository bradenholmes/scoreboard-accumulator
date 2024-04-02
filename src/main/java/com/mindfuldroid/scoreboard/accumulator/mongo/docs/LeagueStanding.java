package com.mindfuldroid.scoreboard.accumulator.mongo.docs;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mindfuldroid.scoreboard.accumulator.mongo.schema.League;

import lombok.Data;

@Document("standings")
@Data
public class LeagueStanding {
	
	@Id
	private String id;
	
	private League league;

	
}
