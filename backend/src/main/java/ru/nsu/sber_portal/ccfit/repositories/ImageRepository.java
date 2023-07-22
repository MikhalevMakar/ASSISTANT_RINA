package ru.nsu.sber_portal.ccfit.repositories;

import ru.nsu.sber_portal.ccfit.models.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ImageRepository extends JpaRepository<Image, Long> { }