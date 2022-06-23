package com.tts.techtalenttwiter.repository;

import org.springframework.data.repository.CrudRepository;

import com.tts.techtalenttwiter.model.Tag;

public interface TagRepository extends CrudRepository<Tag, Long>{
	
	Tag findByPhrase(String phrase);

}
