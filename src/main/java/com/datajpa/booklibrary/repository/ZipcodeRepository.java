package com.datajpa.booklibrary.repository;

import com.datajpa.booklibrary.model.Zipcode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZipcodeRepository extends JpaRepository<Zipcode, Long> {
}
