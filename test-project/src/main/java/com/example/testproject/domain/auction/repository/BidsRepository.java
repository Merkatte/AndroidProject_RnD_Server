package com.example.testproject.domain.auction.repository;

import com.example.testproject.domain.auction.entity.Bids;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidsRepository extends JpaRepository<Bids, Long> {
}
