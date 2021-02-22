/**
 * Copyright 2010 upTick Pty Ltd
 * 
 * Licensed under the terms of the GNU General Public License version 3 
 * as published by the Free Software Foundation. You may obtain a copy of the
 * License at: http://www.gnu.org/copyleft/gpl.html
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations 
 * under the License.
 */

package com.planetsystems.tela.managementapp.client.widget;

import com.google.gwt.core.shared.GWT;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.DataArrivedEvent;
import com.smartgwt.client.widgets.grid.events.DataArrivedHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.SectionStackSection;

//import com.allen_sauer.gwt.log.client.Log;

public class NavigationPaneSection extends SectionStackSection {
  
  private static final String ICON = "icon";
  private static final String ICON_DISPLAY_NAME = "Icon";
  private static final String NAME = "name";
  private static final String NAME_DISPLAY_NAME = "Name";
  private static final String URL_PREFIX = "icons/16/";
  private static final String URL_SUFFIX = ".png";
  
  private static final String LISTGRID_WIDTH = "100%";  
  private static final String LISTGRID_HEIGHT = "100%";      
  
  private static final int ICON_FIELD_WIDTH = 27;

  private ListGrid listGrid;
  boolean dataHasArrived = false;
  
  private DataSource dataSource;
  
  public NavigationPaneSection(String sectionName, DataSource dataSource) {// Changed from DataSource dataSource
  	super(sectionName);
  	
    // Log.debug("setID(sectionName) - " + sectionName);    	
  	this.setID(sectionName);  
  
  	
  	
	  // initialise the List Grid  
  	listGrid = new ListGrid();  
  	listGrid.setBaseStyle("crm-NavigationPaneGridCell");  
  	listGrid.setWidth(LISTGRID_WIDTH);  
  	listGrid.setHeight(LISTGRID_HEIGHT);  
  	listGrid.setShowAllRecords(true);  
  	listGrid.setShowHeader(false);
  	
  	// initialise the Icon field
  	ListGridField appIconField = new ListGridField(ICON, ICON_DISPLAY_NAME, ICON_FIELD_WIDTH);  
  	appIconField.setImageSize(16); 
  	appIconField.setAlign(Alignment.RIGHT);
  	appIconField.setType(ListGridFieldType.IMAGE);  
  	appIconField.setImageURLPrefix(URL_PREFIX);  
  	appIconField.setImageURLSuffix(URL_SUFFIX);  
  	appIconField.setCanEdit(false);  
  	
  	// initialise the Name field
  	ListGridField appNameField = new ListGridField(NAME, NAME_DISPLAY_NAME);  
  	   
  	// add the fields to the list Grid
  	listGrid.setFields(new ListGridField[] {appIconField, appNameField});  
  	
  	if (dataSource != null ) {
  	this.dataSource=dataSource;
    listGrid.setDataSource(dataSource); 
      
  	}
  	/*if(listGridRecord != null){
  		listGrid.setData(listGridRecord);
  	}*/

    this.addItem(listGrid);
    this.setExpanded(true);  
    
    listGrid.addDataArrivedHandler(new DataArrivedHandler() {
      public void onDataArrived(DataArrivedEvent event) {
        GWT.log("onDataArrived()");  
        
        dataHasArrived = true;
      }
    });
    
    // listGrid.setAutoFetchData(true); 
    listGrid.fetchData();
    
    // it takes a while to fetch the data so don't try to select the first record
    // by calling, listGrid.selectRecord(0)
  }
  
  public ListGrid getListGrid() {
	  return listGrid;
  }
  
  public void selectRecord(int record) {
    if (dataHasArrived) {
      listGrid.selectRecord(record);
    }
  }
  
  public int getRecord(String name) {
	  int result = -1;
	  
    // Log.debug("int getRecord() - " + name);  
	  
    ListGridRecord[] records = listGrid.getRecords();
    ListGridRecord record = null;
    String recordName = "";
    
    for (int i = 0; i < records.length; i++) { 
      
      record = listGrid.getRecord(i);
        
      recordName = record.getAttributeAsString(NAME);
      
      if (name.contentEquals(recordName)) {
        // Log.debug("name.contentEquals(recordName)");  
        result = i;
        break;
      }
    }
      
    return result;
  }
  
    
  public String getSelectedRecord() {
    String name = "";
    
    ListGridRecord[] records = listGrid.getSelection();
    
    if (records.length != 0) { 
      // get the name of the first selected record e.g. "Activities"
      name = records[0].getAttributeAsString(NAME);
    }
    else {
      // Log.debug("getSelectedRecord() - No selected record in ListGrid");  

      ListGridRecord record = listGrid.getRecord(0);
      
      if (record != null) {
        // get the name of the first record in the ListGrid e.g. "Activities"
        name = record.getAttributeAsString(NAME);
      }
    }
    
    // Log.debug("getSelectedRecord() - " + name);  

    return name;
  }
  
  public void selectRecord(String name) {
    // Log.debug("selectRecord(name) - " + name);    

    ListGridRecord[] records = listGrid.getRecords();
    ListGridRecord record = null;
    String recordName = "";
    
    for (int i = 0; i < records.length; i++) { 
      
      record = listGrid.getRecord(i);
      recordName = record.getAttributeAsString(NAME);
      
      if (name.contentEquals(recordName)) {
        // Log.debug("name.contentEquals(recordName)");  
        listGrid.deselectAllRecords();
        listGrid.selectRecord(i); 
        break;
      }
    }
  } 
  
  public void removeRecord(String name) { 
	     
	 
	 
  }
  
  public void addRecordClickHandler(RecordClickHandler clickHandler) {
      listGrid.addRecordClickHandler(clickHandler);
  }

public DataSource getDataSource() {
	return dataSource;
}

public void setDataSource(DataSource dataSource) {
	this.dataSource = dataSource;
}
  
  
}

