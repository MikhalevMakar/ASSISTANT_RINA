package ru.nsu.sber_portal.ccfit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsu.sber_portal.ccfit.models.entity.*;
import ru.nsu.sber_portal.ccfit.models.enums.SessionStatus;


@Repository
public interface CheckTableRepository extends JpaRepository<CheckTable, Long> {
    CheckTable findByNumberTableAndRestaurantIdAndSessionStatus(Integer numberTable,
                                                                Long id,
                                                                SessionStatus status);
}