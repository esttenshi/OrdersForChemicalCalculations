package com.javtest.Reprositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.javtest.Models.Note;
import com.javtest.Models.Order;

import java.util.List;

public interface OrderReprository extends JpaRepository<Order, Integer> {
    @Query("SELECT o FROM Order o WHERE o.id_researcher = ?1")
    List<Order> findOrdersByResearcherId(Integer researcherId);
    @Query("SELECT o FROM Order o WHERE o.id_user = ?1")
    List<Order> findOrdersByUserId(Integer userId);
    void save(Note newNote);
}
