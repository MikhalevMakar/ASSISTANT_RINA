package ru.nsu.sber_portal.ccfit.models.mappers;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import ru.nsu.sber_portal.ccfit.models.dto.CheckTableDto;
import ru.nsu.sber_portal.ccfit.models.entity.CheckTable;


@Slf4j
public class CheckMapper {

    @Contract("_ -> new")
    public static @NotNull CheckTableDto mapperToDto(@NotNull CheckTable checkTable){
        CheckTableDto checkTableDto = new CheckTableDto();
        checkTableDto.setCost (checkTable.getCost());
        checkTableDto.setNumberTable (checkTable.getNumberTable());
        checkTableDto.setRestId(checkTable.getRestaurant().getId());

        return checkTableDto;
    }

    @Contract("_ -> new")
    public static @NotNull CheckTable mapperToEntity(@NotNull CheckTableDto checkTableDto){
        CheckTable checkTable = new CheckTable ();
        checkTable.setNumberTable (checkTableDto.getNumberTable ());
        checkTable.setCost (checkTableDto.getCost ());
        return checkTable;
    }
}