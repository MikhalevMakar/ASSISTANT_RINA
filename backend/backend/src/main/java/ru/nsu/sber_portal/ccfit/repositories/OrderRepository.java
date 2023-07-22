package ru.nsu.sber_portal.ccfit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.sber_portal.ccfit.models.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    void deleteById(Long id);

    Order findOrderByCheckIdAndDishId(Long checkId, Long dishId);

    Order findByDishIdAndNumberTable(Long dishId, Integer numberTable);
}
