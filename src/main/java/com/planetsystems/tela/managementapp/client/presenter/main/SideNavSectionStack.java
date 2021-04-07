package com.planetsystems.tela.managementapp.client.presenter.main;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.DataArrivedEvent;
import com.smartgwt.client.widgets.grid.events.DataArrivedHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;

public class SideNavSectionStack extends SectionStack {
	public static String ID = "ID";
	public static String NAME = "NAME";
	public static String ICON = "ICON";
	private static final String URL_PREFIX = "icons/16/";
	private static final String URL_SUFFIX = ".png";

	private static final String LISTGRID_WIDTH = "100%";
	private static final String LISTGRID_HEIGHT = "100%";
	private static final int ICON_FIELD_WIDTH = 27;
	

	public SideNavSectionStack(String sectionName) {

		this.setID(sectionName);

		this.setVisibilityMode(VisibilityMode.MUTEX);
		this.setAnimateSections(true);
		this.setWidth(300);
		this.setHeight(350);

		SectionStackSection section1 = new SectionStackSection("Blue Pawn");
		section1.setExpanded(true);
		section1.addItem(new Img("pieces/48/pawn_blue.png", 48, 48));
		this.addSection(section1);

		// section 1 list grid
		SystemSectionStackListGrid listGrid = new SystemSectionStackListGrid();
		listGrid.addRecordClickHandler(new RecordClickHandler() {
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				SC.say("Hello "+event.getRecord().getAttribute(NAME));
			}
		});
		
		listGrid.addRecordsToGrid(Arrays.asList(new SideNavItem("", "", "Item1"), new SideNavItem("", "", "Item2"),
				new SideNavItem("", "", "Item3")));

		section1.addItem(listGrid);

		SectionStackSection section3 = new SectionStackSection("Green Pawn");
		section3.setExpanded(true);
		section3.setCanCollapse(false);
		section3.addItem(new Img("pieces/48/pawn_green.png", 48, 48));
		this.addSection(section3);

		SectionStackSection section4 = new SectionStackSection("Yellow Piece");
		section4.setExpanded(false);
		section4.addItem(new Img("pieces/48/piece_yellow.png", 48, 48));
		this.addSection(section4);

	}

	public class SystemSectionStackDatasource extends DataSource {

		public SystemSectionStackDatasource() {
			super();
			setID(id);

			DataSourceTextField idField = new DataSourceTextField(ID, "Id");
			idField.setHidden(true);
			idField.setPrimaryKey(true);

			DataSourceTextField nameField = new DataSourceTextField(NAME, "Name");

			DataSourceTextField iconField = new DataSourceTextField(ICON, "Icon");

			this.setFields(idField, nameField, iconField);
		}

	}

	public class SystemSectionStackListGrid extends ListGrid {
		
        private  boolean dataHasArrived;
		private SystemSectionStackDatasource dataSource;

		public SystemSectionStackListGrid() {
			super();

			dataSource = new SystemSectionStackDatasource();
			ListGridField idField = new ListGridField(ID, "id");
			idField.setHidden(true);

			ListGridField nameField = new ListGridField(NAME, "Name");
			nameField.setShowTitle(false);
			
			ListGridField iconField = new ListGridField(ICON, "Icon", ICON_FIELD_WIDTH);
			iconField.setShowTitle(false);
			iconField.setImageSize(16);
			iconField.setAlign(Alignment.RIGHT);
			iconField.setType(ListGridFieldType.IMAGE);
			iconField.setImageURLPrefix(URL_PREFIX);
			iconField.setImageURLSuffix(URL_SUFFIX);
			iconField.setCanEdit(false);

			this.setFields(idField, iconField, nameField);
			this.setDataSource(dataSource);
			
			
			  this.addDataArrivedHandler(new DataArrivedHandler() {
			      public void onDataArrived(DataArrivedEvent event) {
			        GWT.log("onDataArrived()");  
			        dataHasArrived = true;
			      }
			    });


			this.setWrapCells(false);
			this.setMargin(3);
			this.setWidth(LISTGRID_WIDTH);
			this.setHeight(LISTGRID_HEIGHT);
		}

		public void addRecordsToGrid(List<SideNavItem> sideNavItems) {
			ListGridRecord[] records = new ListGridRecord[sideNavItems.size()];
			int row = 0;
			for (SideNavItem item : sideNavItems) {
				records[row] = addRowData(item);
				row++;
			}
			this.setData(records);
			dataSource.setTestData(records);
		}

		private ListGridRecord addRowData(SideNavItem sideNavItem) {
			ListGridRecord record = new ListGridRecord();
			record.setAttribute(ID, sideNavItem.getId());
			record.setAttribute(NAME, sideNavItem.getName());
			record.setAttribute(ICON, sideNavItem.getIcon());
			return record;
		}
		
		
	}
	
	

}
