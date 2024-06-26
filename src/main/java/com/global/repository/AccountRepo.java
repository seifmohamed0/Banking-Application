package com.global.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.global.entity.Account;

public interface AccountRepo extends JpaRepository<Account , Long> {

}
