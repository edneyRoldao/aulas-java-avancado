package com.edntisolutions.bankproject.repositories;

import com.edntisolutions.bankproject.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
