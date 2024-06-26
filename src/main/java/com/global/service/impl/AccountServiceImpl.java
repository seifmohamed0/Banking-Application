package com.global.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.global.dto.AccountDto;
import com.global.entity.Account;
import com.global.mapper.AccountMapper;
import com.global.repository.AccountRepo;
import com.global.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	private AccountRepo accountRepo;
	
	public AccountServiceImpl(AccountRepo accountRepo) {
		this.accountRepo = accountRepo;
	}

	@Override
	public AccountDto createAccount(AccountDto accountDto) {
		Account account = AccountMapper.mapToAccount(accountDto);
		Account savedAccount = accountRepo.save(account);
		return AccountMapper.mapToAccountDto(savedAccount);
	}

	@Override
	public AccountDto getAccountById(Long id) {
		Account account = accountRepo.findById(id).orElseThrow(() -> new RuntimeException("Account does not exists"));
		return AccountMapper.mapToAccountDto(account);
	}

	@Override
	public AccountDto deposit(Long id, double amount) {
		Account account = accountRepo.findById(id).orElseThrow(() -> new RuntimeException("Account does not exists"));
		double total = account.getBalance() + amount;
		account.setBalance(total);
		Account savedAccount = accountRepo.save(account);
		return AccountMapper.mapToAccountDto(savedAccount);
	}

	@Override
	public AccountDto withdraw(Long id, double amount) {
		Account account = accountRepo.findById(id).orElseThrow(() -> new RuntimeException("Account does not exists"));
		
		if(account.getBalance() < amount) {
			throw new RuntimeException("insufficient amount");
		}
		
		double total = account.getBalance() - amount;
 		account.setBalance(total);
 		Account savedAccount = accountRepo.save(account);
 		
		return AccountMapper.mapToAccountDto(savedAccount);
	}

	@Override
	public List<AccountDto> getAllAccounts() {
		List<Account>accounts = accountRepo.findAll();
		return accounts.stream().map((account) -> AccountMapper.mapToAccountDto(account))
			.collect(Collectors.toList());
	}

	@Override
	public void deleteAccount(Long id) {
		accountRepo.findById(id).orElseThrow(() -> new RuntimeException("Account does not exists"));
		accountRepo.deleteById(id);
	}
	
	

}
