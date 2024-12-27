package org.koreait.admin.global.menu;

import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

/**
 * 반복되는 부분 정의 - submenus
 */
public interface SubMenus {
    String menuCode();

    @ModelAttribute("submenus")
    default List<MenuDetail> submenus() {
        return Menus.getMenus(menuCode());
    }
}
