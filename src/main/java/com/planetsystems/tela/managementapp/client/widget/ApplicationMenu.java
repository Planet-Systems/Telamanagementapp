/**
 * 
 */
package com.planetsystems.tela.managementapp.client.widget;

import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuBar;

public class ApplicationMenu extends HLayout {

	// private final int APPLICATION_MENU_HEIGHT=20;
	private final int DEFAULT_SHADOW_DEPTH = 10;
	private MenuBar menuBar;
	private int menuPosition;

	public ApplicationMenu() {
		super();
		// this.setHeight(APPLICATION_MENU_HEIGHT);
		this.setBackgroundColor("#f0f0f0");
		menuBar = new MenuBar();
		this.addMember(menuBar);

	}

	public Menu addMenu(String menuName, int width) {
		Menu menu = new Menu();
		menu.setTitle(menuName);
		menu.setShowShadow(true);
		menu.setShadowDepth(DEFAULT_SHADOW_DEPTH);
		menu.setWidth(width);

		Menu[] menus = new Menu[1];
		menus[0] = menu;
		menuBar.addMenus(menus, menuPosition);
		menuPosition++;

		return menu;

	}

}
