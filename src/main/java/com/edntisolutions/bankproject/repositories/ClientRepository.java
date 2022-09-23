package com.edntisolutions.bankproject.repositories;

import com.edntisolutions.bankproject.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
