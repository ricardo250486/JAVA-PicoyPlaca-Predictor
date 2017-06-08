/*
 * Author: Ricardo Gomez Paredes
 * Date: 06/06/2017
 * Program: "Pico y Placa" Predictor
 * Language: JAVA
 */
package picoplacaproject;

/**
 *
 * @author ricardox
 */
/*Load the libraries neede for the program*/

import java.util.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import org.joda.time.DateTime;
import java.text.DateFormat;
import java.time.LocalTime;

/*Declare the main class*/
public class Picoplacaproject {
 public static void main (String[]args) {
   
     /*Declare a general try-catch to get the errors*/
 try {
   String err = "";  //Variables for accumulating the errors of the inputs and show them to the user 
   String errmess = "";
   
   System.out.println(" ENTER THE REQUESTED DATA IN ORDER TO KNOW WHEN YOU CAN DRIVE YOUR CAR *****PICO Y PLACA***** \n\n");
   
   /*DATE*/
   System.out.println("Enter the date using the format - dd/mm/yyyy");  //Ask the user for the date
   Scanner da = new Scanner(System.in);
   String ndate = da.nextLine();
   SimpleDateFormat nd = new SimpleDateFormat("dd/MM/yyyy"); //Declare a Date Format to be used later to compare the dates
   
   /*LICENSE PLATE*/
   System.out.println("Enter the license plate using the next format with capital letters only XXX-1234"); //Ask the user for the license plate
   Scanner pl = new Scanner(System.in);
   String plate = pl.nextLine();
   
   /*TIME*/
   System.out.println("Enter the time using the format 24 hours - XX:XX:XX"); //Ask the user for the time
   Scanner tim = new Scanner(System.in);
   String timere = tim.nextLine();
   
   /*Validate the date, time and license plate according to the correct formats*/
   if(ndate.matches("^(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)$")){ //Validate date to match the format dd/MM/yyyy, using regular expressions
         System.out.println("Valid Date"); //Show success message to the user if the date was entered correctly
	 } else{
	  
	  errmess = err + "Invalid Date"; //Error message showed to the user
	  System.out.println(errmess);
	 }
	 
	 if(plate.matches("^([A-Z]{3}-\\d{3,4})$")){ //Validate the license plate to match the format ex. TYR-6756 or TYR-675, using regular expressions
            System.out.println("Valid License Plate"); //Show success message to the user if the license plate was entered correctly
	 } else{
	  
	    errmess = err + "Invalid License Plate"; //Error message showed to the user
	    System.out.println(errmess);
	 
	 }
	  if(timere.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$")){ //Validate the time to match the format xx:xx:xx, using regular expressions
            System.out.println("Valid Time"); //Show success message to the user if the time was entered correctly
	 } else{
	  
	   errmess = err + "Invalid Time";  //Error message showed to the user
	   System.out.println(errmess);
	 
	 }
          
          /*End of input data validation*/
          
	 if(errmess.equals("")){ //If no input errors (error messages empty), continue with the program
             
           String pla = licenses(plate);     //Call the method created to validate the license plate last digit
	  
          /*Declare a date format to extract only the day as text in the date parameter*/           
            DateFormat d = new SimpleDateFormat("EEEE"); //Declare the date format, EEEE means the day as text
            Date dat = nd.parse(ndate); //Convert the date from a string into a date
            String days = d.format(dat); //From the date format extract the day only, this is used later in the validation method for day and ´plate digit
            
            /*Convert the time into a long(epoch) form in order to compare to "pico y placa times"*/
            SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss"); //Declare the format to convert to date the times
            Date txy = f.parse(timere); //Convert the time from string into time format
            long ep = txy.getTime(); //Convert the time into long 
            
            /*Declare the 4 constants ("Pico y Placa times")*/
            String hour1 = "07:00:00"; //ep1
            String hour2 = "09:30:00"; //ep2
            String hour3 = "16:00:00"; //ep3
            String hour4 = "19:30:00"; //ep4
            
            /*Convert all the "Pico y Placa" times into long format*/
            Date h1 = f.parse(hour1);
            long ep1 = h1.getTime();    
            Date h2 = f.parse(hour2);
            long ep2 = h2.getTime(); 
            Date h3 = f.parse(hour3);
            long ep3 = h3.getTime();
            Date h4 = f.parse(hour4);
            long ep4 = h4.getTime();
   
                   
          if((ep >= ep1 && ep <= ep2) || (ep >= ep3 && ep <= ep4)){ //Validate the "Pico y Placa" hours, if the time entered by the user(ep) is between these times, validate the data
              validation(days,pla); //Call the method used for validate the license plate and day of the week in order to show the user if his/her car is on "Pico y Placa" or no
           
          }else{ //If the time entered by the user is not between the "Pico y Placa" hours, show the user that his/her car can be on the road 
           
               System.out.println("The car is allowed to be on the road"); 
          }
         }
        
    } catch (ParseException e) { //Catch the errors
         e.printStackTrace();
    }
	} //End of the main class
 
 /*Methods used for the predictor are declared below*/
   public static String licenses(String plate){ //Method to extract the last digit of the license plate in order to use it to be sure if the car is allowed or not to be on the road
       String nplate = ""; 
		 if(plate.length() == 7){  //Identify if the license plate has the format XXX-1234 or XXX-123
		 nplate = plate.substring(6); //Extract the last digit of the plate
		 }
		 if(plate.length() == 8){
		 nplate = plate.substring(7); //Extract the last digit of the plate
		 }
               return nplate; //Return the last digit of the plate
             }
   
   public static void validation(String days, String pla){ //Method to validate accordign to the "Pico y Placa" rules (day and last plate digit) when the car is not allowed to be on the road 
      
      if(days.equals("lunes") && (pla.equals("1") || pla.equals("2"))){ //Validate the "Pico y Placa" for Monday and plates 1 or 2
         System.out.println("The car cannot be on the road since the plate ends on 1 or 2 and is Monday");
        }
      else if(days.equals("martes") &&(pla.equals("3") || pla.equals("4"))){ //Validate the "Pico y Placa" for Tuesday and plates 3 or 4
             System.out.println("The car cannot be on the road since the plate ends on 3 or 4 and is Tuesday");
           }
              
      else if(days.equals("miércoles") && (pla.equals("5") || pla.equals("6"))){ //Validate the "Pico y Placa" for Wednesday and plates 5 or 6
              System.out.println("The car cannot be on the road since the plate ends on 5 or 6 and is Wednesday");
            }
         
      else if(days.equals("jueves") && (pla.equals("7") || pla.equals("8"))){ //Validate the "Pico y Placa" for Thursday and plates 7 or 8
               System.out.println("The car cannot be on the road since the plate ends on 7 or 8 and is Thursday");
            }
          
      else if(days.equals("viernes") && (pla.equals("9") || pla.equals("0"))){ //Validate the "Pico y Placa" for Friday and plates 9 or 0
              System.out.println("The car cannot be on the road since the plate ends on 9 or 0 and is Friday");
             }
      else{
                System.out.println("The car is permitted to be on the road"); ////Validate the "Pico y Placa" for the rest of the cases (Saturday and Sunday) when the "Pico y Placa" is not working

      }     //The method is void since it does not need to return any value
   }       
   } //end of the program
  


