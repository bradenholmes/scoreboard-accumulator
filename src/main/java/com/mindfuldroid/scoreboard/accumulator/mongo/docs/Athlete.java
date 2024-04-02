package com.mindfuldroid.scoreboard.accumulator.mongo.docs;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mindfuldroid.scoreboard.accumulator.mongo.schema.AthleteGamedayDetails;
import com.mindfuldroid.scoreboard.accumulator.mongo.schema.AthletePersonalDetails;
import com.mindfuldroid.scoreboard.accumulator.mongo.schema.AthleteSeasonDetails;
import com.mindfuldroid.scoreboard.accumulator.mongo.schema.AthleteTeamDetails;
import com.mindfuldroid.scoreboard.accumulator.mongo.schema.League;

import lombok.Data;

@Document("athletes")
@Data
public class Athlete {
	
	@Id
	private String id;
	
	private League league;                        // NBA, NFL, MLB, etc
	private AthletePersonalDetails personalDeets; // contains name, age, height, weight, etc.
	private AthleteSeasonDetails seasonDeets;     // contains season-long statistics
	private AthleteGamedayDetails gamedayDeets;   // contains live gameday statistics
	private AthleteTeamDetails teamDeets;         // contians team data - position(s), number, 
	
}
