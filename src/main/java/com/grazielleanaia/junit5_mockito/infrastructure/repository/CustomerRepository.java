package com.grazielleanaia.junit5_mockito.infrastructure.repository;

import com.grazielleanaia.junit5_mockito.infrastructure.entity.CustomerEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    CustomerEntity findByEmail(String email);

   @Transactional
    void deleteByEmail(String email);
}
