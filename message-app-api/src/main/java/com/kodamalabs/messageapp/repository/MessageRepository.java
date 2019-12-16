package com.kodamalabs.messageapp.repository;

import com.kodamalabs.messageapp.entity.Message;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {
}
