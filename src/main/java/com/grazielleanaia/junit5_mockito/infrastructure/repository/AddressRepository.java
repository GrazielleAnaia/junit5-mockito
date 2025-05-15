package com.grazielleanaia.junit5_mockito.infrastructure.repository;


import com.grazielleanaia.junit5_mockito.infrastructure.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
}
