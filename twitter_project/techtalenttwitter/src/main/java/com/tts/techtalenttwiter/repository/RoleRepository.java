package com.tts.techtalenttwiter.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.tts.techtalenttwiter.model.Role;


@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{

	Role findByRole(String role);
}
