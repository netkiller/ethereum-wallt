package cn.netkiller.wallet.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.netkiller.wallet.domain.TransactionHistory;

@Repository
public interface TransactionHistoryRepository extends CrudRepository<TransactionHistory, Integer> {

	@Query(value = "SELECT * FROM transaction_history th WHERE (th.from_address = :address or th.to_address = :address) and contract_address is NULL", countQuery = "SELEÇT count(*) FROM transaction_history th WHERE (th.from_address = :address or th.to_address = :address) and contract_address is NULL", nativeQuery = true)
	public Page<TransactionHistory> findEthByAddress(@Param("address") String address, Pageable pageable);

	@Query(value = "SELECT * FROM transaction_history th WHERE (th.from_address = :address or th.to_address = :address) and contract_address = :contractAddress", countQuery = "SELEÇT count(*) FROM transaction_history th WHERE (th.from_address = :address or th.to_address = :address) and contract_address = :contractAddress", nativeQuery = true)
	public Page<TransactionHistory> findTokenByAddressAndContactAddress(@Param("address") String address, @Param("contractAddress") String contractAddress, Pageable pageable);

}
