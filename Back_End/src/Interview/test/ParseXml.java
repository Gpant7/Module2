package Interview.test;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;


import Interview.test.mySqlAccess;

public class ParseXml {
	public void ParseXmlToJava(){
	try {	     
		File fXmlFile = new File("/Users/Geo/Desktop/EVALTEST-BED-JAVA-JUNIOR-RMIN/final-day-one-backend/EmployeeCatalogTest.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        
	    doc.getDocumentElement().normalize();
	    
        System.out.println("Root element :" 
           + doc.getDocumentElement().getNodeName());
        
        NodeList nList = doc.getElementsByTagName("Employee");
        System.out.println("----------------------------");
        
        String IdAttribute, EmployeeTypeIdAttribute,FirstName,LastName,Query1,Query2;
        int employeeTypeId;
        
        for (int temp = 23; temp < nList.getLength(); temp++) {
           Node nNode = nList.item(temp);
           //System.out.println(temp);
           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
        	   Element eElement = (Element) nNode;
               IdAttribute = eElement.getAttribute("Id").toString();
               EmployeeTypeIdAttribute = eElement.getAttribute("EmployeeTypeId").toString();
               FirstName = eElement.getElementsByTagName("FirstName").item(0).getTextContent();
               LastName = eElement.getElementsByTagName("LastName").item(0).getTextContent();
            	 
               employeeTypeId= Integer.parseInt(EmployeeTypeIdAttribute);
            	  
               Query1 = "insert into employees"
            			  + "(id,employeetypeid,firstname,lastname) "
            			  + "values (\""
            			  +IdAttribute+"\","
            			  +employeeTypeId+",\""
            			  +FirstName+"\",\""
            			  +LastName+"\""
            			  +")";
               
                mySqlAccess mySqlCommands = new mySqlAccess();
         	    mySqlCommands.storeDataBase(Query1);
            	  
               	Object AccessDateTime;
               	String AccessType,AccessAreaId;
               	int areaId;               
               	NodeList elemList = eElement.getElementsByTagName("Access"); 
            	for (int tns = 0; tns < elemList.getLength(); tns++) {
            		  Node AccessNode = elemList.item(tns);
                      
                      if (AccessNode.getNodeType() == Node.ELEMENT_NODE) {
                         Element accessElement = (Element) AccessNode;
                         
                         if(accessElement.getAttribute("AreaId").toString()!=null){
	                         AccessAreaId = accessElement.getAttribute("AreaId").toString();
	                         AccessDateTime = accessElement.getAttribute("DateTime").toString();
	                         AccessType = accessElement.getAttribute("Type").toString();

	                         areaId= Integer.parseInt(AccessAreaId);
	                      
	                         Query2 = "insert into AccessHistory"
	                   			  + "(employeeId,AreaId,Type,Date) "
	                   			  + "values (\""
	                   			  +IdAttribute+"\","
	                   			  +areaId+",\""
	                   			  +AccessType+"\",\""
	                   			  +AccessDateTime+"\""
	                   			  +")";
	                         System.out.println(Query2);
	                         
	                         mySqlAccess mySqlCommands2 = new mySqlAccess();
	                   	  	 mySqlCommands2.storeDataBase(Query2);
                         }
                      }
            	  }             
           }
        }
     } catch (Exception e) {
        e.printStackTrace();
     	}
  }
}
