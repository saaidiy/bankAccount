package fr.ingeniance.kata.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.ingeniance.kata.bo.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
