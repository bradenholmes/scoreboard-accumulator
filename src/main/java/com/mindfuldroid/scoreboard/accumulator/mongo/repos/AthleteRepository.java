package com.mindfuldroid.scoreboard.accumulator.mongo.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mindfuldroid.scoreboard.accumulator.mongo.docs.Athlete;

@Repository
public interface AthleteRepository extends MongoRepository<Athlete, String>{

}
