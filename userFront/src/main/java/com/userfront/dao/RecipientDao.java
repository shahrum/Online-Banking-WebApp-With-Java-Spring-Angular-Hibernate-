package com.userfront.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.userfront.domain.Recipient;

public interface RecipientDao extends CrudRepository<Recipient, Integer> {
	
	List<Recipient> findAll();

	Recipient findByName(String _recipientName);

	void deleteByName(String _recipientName);

}
