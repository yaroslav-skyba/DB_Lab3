package com.github.yaroslavskybadev.repository;

import com.github.yaroslavskybadev.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}