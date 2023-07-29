package ru.nsu.sber_portal.ccfit.models.mappers;

import org.jetbrains.annotations.*;
import ru.nsu.sber_portal.ccfit.models.dto.CategoryMenuDto;
import ru.nsu.sber_portal.ccfit.models.entity.CategoryMenu;

public class CategoryMenuMapper {
    private CategoryMenuMapper() {
        throw new UnsupportedOperationException("This class cannot be instantiated.");
    }

    @Contract(pure = true)
    public static @NotNull CategoryMenuDto mapperToDto(@NotNull CategoryMenu categoryMenu) {
        return new CategoryMenuDto(categoryMenu.getId(),
                                   categoryMenu.getTitle(),
                                   categoryMenu.getLinkImage());
    }

    public static @NotNull CategoryMenu mapperToEntity(@NotNull CategoryMenuDto categoryMenuDto) {
        CategoryMenu categoryMenu = new CategoryMenu();
        categoryMenu.setTitle(categoryMenuDto.getTitle());
        categoryMenu.setLinkImage(categoryMenuDto.getLinkImage());
        return categoryMenu;
    }
}
