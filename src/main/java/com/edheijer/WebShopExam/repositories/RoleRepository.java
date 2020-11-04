package com.edheijer.WebShopExam.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edheijer.WebShopExam.models.Role;
import com.edheijer.WebShopExam.models.RoleEnum;

public interface RoleRepository extends JpaRepository<Role, Long>{
	Optional<Role> findByName(RoleEnum name);
}
