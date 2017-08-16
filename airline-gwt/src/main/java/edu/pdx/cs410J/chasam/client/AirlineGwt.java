package edu.pdx.cs410J.chasam.client;

import com.google.common.annotations.VisibleForTesting;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import edu.pdx.cs410J.AirportNames;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;


/**
 * A basic GWT class that makes sure that we can send an airline back from the server
 */
public class AirlineGwt implements EntryPoint {

    private final Alerter alerter;
    private final AirlineServiceAsync airlineService;
    private final Logger logger;
    private Airline theAirlines;
    private String flightDigit;
    private String destinCode;
    private String sourceCode;
    private String dateArrival;
    private String timeOfArrive;
    private String timeOfLeave;
    private String dateOfLeave;
    private String airlineID;
    private RootPanel rootPanel;
    private boolean ignoreClick = true;
    private int lastSelection =  -1;

    @VisibleForTesting
    Button showAirlineButton;

    @VisibleForTesting
    Button addFlight;

    @VisibleForTesting
    Button Search;

    @VisibleForTesting
    ListBox Help;

    @VisibleForTesting
    final TextBox airlineName = new TextBox();

    @VisibleForTesting
    final TextBox leaveDateBox = new TextBox();

    @VisibleForTesting
    final TextBox leaveTimeBox = new TextBox();

    @VisibleForTesting
    final TextBox srcBox = new TextBox();

    @VisibleForTesting
    final TextBox arriveDateBox = new TextBox();

    @VisibleForTesting
    final TextBox arriveTimeBox = new TextBox();

    @VisibleForTesting
    final TextBox destBox = new TextBox();

    @VisibleForTesting
    final TextBox fNumInfo = new TextBox();

    @VisibleForTesting
    final TextBox sName = new TextBox();

    @VisibleForTesting
    final TextBox sSrc = new TextBox();

    @VisibleForTesting
    final TextBox sDest = new TextBox();

    @VisibleForTesting
    final TextArea airlinePrettyText = new TextArea();

    public AirlineGwt() {
        this(new Alerter() {
            @Override
            public void alert(String message) {
                Window.alert(message);
            }
        });
    }

    @VisibleForTesting
    AirlineGwt(Alerter alerter) {
        this.alerter = alerter;
        this.airlineService = GWT.create(AirlineService.class);
        this.logger = Logger.getLogger("airline");
        Logger.getLogger("").setLevel(Level.INFO);  // Quiet down the default logging
    }

    private void alertOnException(Throwable throwable) {
        Throwable unwrapped = unwrapUmbrellaException(throwable);
        StringBuilder sb = new StringBuilder();
        sb.append(unwrapped.toString());
        sb.append('\n');

        for (StackTraceElement element : unwrapped.getStackTrace()) {
            sb.append("  at ");
            sb.append(element.toString());
            sb.append('\n');
        }

        this.alerter.alert(sb.toString());
    }

    private Throwable unwrapUmbrellaException(Throwable throwable) {
        if (throwable instanceof UmbrellaException) {
            UmbrellaException umbrella = (UmbrellaException) throwable;
            if (umbrella.getCauses().size() == 1) {
                return unwrapUmbrellaException(umbrella.getCauses().iterator().next());
            }

        }

        return throwable;
    }

    private void addWidgets(Grid panel) {


        Label aName = new Label("Airline Name");
        panel.setWidget(0,0,aName);
        panel.setWidget(0,1,airlineName);

        Label fNum = new Label("Flight Number");
        panel.setWidget(0,2,fNum);
        panel.setWidget(0,3,fNumInfo);

        Label src = new Label("SRC");
        panel.setWidget(0,4,src);
        panel.setWidget(0,5,srcBox);

        Label leaveDate = new Label("Depart Date");
        panel.setWidget(0,6,leaveDate);
        panel.setWidget(0,7,leaveDateBox);

        Label leaveTime = new Label("Depart time");
        panel.setWidget(1,0,leaveTime);
        panel.setWidget(1,1,leaveTimeBox);

        Label dest = new Label("Dest");
        panel.setWidget(1,2,dest);
        panel.setWidget(1,3,destBox);

        Label arriveDate = new Label("Arrival Date");
        panel.setWidget(1,4,arriveDate);
        panel.setWidget(1,5,arriveDateBox);

        Label arriveTime = new Label("Arrival Time");
        panel.setWidget(1,6,arriveTime);
        panel.setWidget(1,7, arriveTimeBox);

        addFlight = new Button("Add Flight");
        panel.setWidget(2,0,addFlight);

        Label searchName = new Label("Airport Name");
        panel.setWidget(3,0,searchName);
        panel.setWidget(3,1,sName);

        Label searchSrc = new Label("Src");
        panel.setWidget(3,2,searchSrc);
        panel.setWidget(3,3,sSrc);

        Label searchDest = new Label("Dest");
        panel.setWidget(3,4,searchDest);
        panel.setWidget(3,5,sDest);
        Search = new Button("Search");
        panel.setWidget(4,0,Search);

        Help = new ListBox();
        Help.addItem("Select Menu....");
        Help.addItem("README");
        Help.addItem("Instructions");


        Help.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {

                if (!ignoreClick) {

                    lastSelection = Help.getSelectedIndex();

                    if (lastSelection == 1)
                        alerter.alert("This is the last project for CS410p Advanced Java." + "" +
                                "The purpose of this program is to create an airline and add a flight to the airline as well as search.\n" +
                            "The program takes arguments from the web app and error checks for bad inputs.\nAfter this step a flight is created\n" +
                            "and stored inside an arraylist data structure from the server side." + "\nThe other features that this program has is readme and help." +
                                "\nThe help feature tells the user the correct formats to enter information" + "\n" +
                            "the readme displays a read me to the user. The readme gives a user the details of the prgoram\n");

                    if (lastSelection == 2){

                        String helping = new String("To add a flight enter in flight information and press add button\n.If any errors occur a pop up" +
                                "will tell you\n."+"Flight number must be 3 numbers\n.Date and time must be in mm/dd/yyyyy hh:mm AM/PM format\n."+"The src and dest have to be 3" +
                                "letters and correspound to a known airport\n"+"All flights will be displayed in the textbox once the show all button is pressed\n"+
                        "Use the drop down menu to select an option readme or Instructions\n");
                        alerter.alert(helping);
                    }
                }


                ignoreClick = !ignoreClick;
            }
        });

        panel.setWidget(4,1,Help);



        Search.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {

                StringBuilder errorDisplay = new StringBuilder();

                if(sName.getText().isEmpty())
                    errorDisplay.append("Error Missing airline name\n");

                if (sDest.getText().isEmpty())
                    errorDisplay.append("Error missing dest code\n");

                if (sSrc.getText().isEmpty())
                    errorDisplay.append("Error missing source code\n");

                // if the string is not empty
                if (!errorDisplay.toString().isEmpty())
                    alerter.alert(errorDisplay.toString());

                // string is empty so its valid
                else if(errorDisplay.toString().isEmpty()){

                    airlineService.getAirline("get airline", new AsyncCallback<Airline>() {

                        @Override
                        public void onFailure(Throwable throwable) {

                        }

                        @Override
                        public void onSuccess(Airline airline) {

                            // there is a match

                            if(airline.getName().equals(sName.getText())){

                                theAirlines = airline;
                                String pretty = findMe(sSrc.getText(), sDest.getText());

                                alerter.alert(pretty);
                            }

                            else
                                alerter.alert("Error no matching airline name");
                        }
                    });
                }
            }
        });


        airlinePrettyText.setCharacterWidth(80);
        airlinePrettyText.setVisibleLines(10);
        showAirlineButton = new Button("Show all airlines");
        panel.setWidget(5,7,airlinePrettyText);
        panel.setWidget(6,7,showAirlineButton);

        showAirlineButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {

                airlineService.getAirline("a string", new AsyncCallback<Airline>() {

                    @Override
                    public void onFailure(Throwable throwable) {

                        alerter.alert("error");
                    }

                    @Override
                    public void onSuccess(Airline airline) {

                        String superP = airline.displayPretty();
                        airlinePrettyText.setText(superP);
                    }
                });

            }
        });


        addFlight.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {

                StringBuilder sb = new StringBuilder();

                if (airlineName.getText().isEmpty())
                    sb.append("Error missing airline name\n");

                else
                    airlineID = airlineName.getText();

                if (fNumInfo.getText().isEmpty())
                    sb.append("Error missing flight number\n");

                else
                    flightDigit = fNumInfo.getText();

                if (srcBox.getText().isEmpty())
                    sb.append("Error missing src code\n");

                else
                    sourceCode = srcBox.getText();

                if (leaveDateBox.getText().isEmpty())
                    sb.append("Error missing departure date\n");

                else
                    dateOfLeave = leaveDateBox.getText();

                if (leaveTimeBox.getText().isEmpty())
                    sb.append("Error missing departure time\n");

                else
                    timeOfLeave = leaveTimeBox.getText();

                if (destBox.getText().isEmpty())
                    sb.append("Error missing dest code\n");

                else
                    destinCode = destBox.getText();

                if (arriveDateBox.getText().isEmpty())
                    sb.append("Error missing date of arrival\n");

                else
                    dateArrival = arriveDateBox.getText();

                if (arriveTimeBox.getText().isEmpty())
                    sb.append("Error missing arrival time");

                else
                    timeOfArrive = arriveTimeBox.getText();

                // i can add a flight
                if (sb.toString().isEmpty()){

                    String badMessage = Bad_Input();

                    // checks for bad input
                    if (badMessage.isEmpty()){

                        /*
                        String dateLeave = dateOfLeave+" "+timeOfLeave;
                        String dateArrive = dateArrival+" "+timeOfArrive;

                        Flight thisFlight = new Flight(flightDigit,sourceCode,dateLeave,destinCode,dateArrive);
*/
                        // i already have a flight

                        airlineService.getAirline("hello", new AsyncCallback<Airline>() {
                            @Override
                            public void onFailure(Throwable throwable) {
                                alerter.alert("fail");
                            }

                            @Override
                            public void onSuccess(Airline airline) {
                                String dateLeave = dateOfLeave+" "+timeOfLeave;
                                String dateArrive = dateArrival+" "+timeOfArrive;

                                Flight thisFlight = new Flight(flightDigit,sourceCode,dateLeave,destinCode,dateArrive);
                                alerter.alert(airline.getName());

                                if(airline.getName() != null) {

                                    // same flight name
                                    if (airline.getName().equals(airlineID)){


                                        if (airline.find(thisFlight)) {
                                            alerter.alert("Flight has been added1");
                                            return;
                                        }

                                        else {

                                            addthis(dateLeave, dateArrive);
                                        }
                                    }

                                    else
                                        alerter.alert("Airline names do not match");
                                }

                                else{

                                    addthis(dateLeave,dateArrive);
                                }
                            }
                        });

                        /*
                        alerter.alert(theAirlines.getName());
                        if(theAirlines.getName() != null) {

                            alerter.alert("1");
                            // same flight name
                            if (theAirlines.getName().equals(airlineID)){

                                alerter.alert("2");

                                if (theAirlines.find(thisFlight)) {
                                    alerter.alert("Flight has been added1");
                                    return;
                                }

                                else {

                                    alerter.alert("hello");
                                    addthis(dateLeave, dateArrive);
                                }
                            }

                            else
                                alerter.alert("Airline names do not match");
                        }

                        else{

                            addthis(dateLeave,dateArrive);
                        }
*/
                        /*
                        airlineService.add(airlineID,flightDigit,sourceCode,dateLeave,destinCode, dateArrive, new AsyncCallback<String>() {

                            @Override
                            public void onFailure(Throwable throwable) {
                            }

                            @Override
                            public void onSuccess(String someString) {

                                if (someString.equals("true"))
                                    alerter.alert("Flight has been added");

                                else
                                    alerter.alert("Airline names do not match");
                            }

                        });
                        */
                    }

                    // I have bad input
                    else
                        alerter.alert(badMessage);

                }

                else
                    alerter.alert(sb.toString());
            }
        });

        /*
        showUndeclaredExceptionButton = new Button("Show undeclared exception");
        showUndeclaredExceptionButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                showUndeclaredException();
            }
        });

        showDeclaredExceptionButton = new Button("Show declared exception");
        showDeclaredExceptionButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                showDeclaredException();
            }
        });

        showClientSideExceptionButton= new Button("Show client-side exception");
        showClientSideExceptionButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                throwClientSideException();
            }
        });

        panel.add(showUndeclaredExceptionButton);
        panel.add(showDeclaredExceptionButton);
        panel.add(showClientSideExceptionButton);

        */
        //panel.add(showAirlineButton);
    }

    public void addthis(String dateLeave,String dateArrive){


        airlineService.add(airlineID,flightDigit,sourceCode,dateLeave,destinCode, dateArrive, new AsyncCallback<String>() {

            @Override
            public void onFailure(Throwable throwable) {
            }

            @Override
            public void onSuccess(String someString) {

                if (someString.equals("true"))
                    alerter.alert("Flight has been added");

                else
                    alerter.alert("Airline names do not match");
            }

        });

    }


    public String Bad_Input(){

        String regex ="[0-9]+";
        StringBuilder sb = new StringBuilder();

        if(!flightDigit.matches(regex))
            sb.append("Error flight number is invalid\n");

        // Src greater than length and contains numbers
        if(sourceCode.length() != 3 ||!sourceCode.matches("[a-zA-Z]+"))
            sb.append("Error departure code needs to be a three letter code ex:abc");


        if(!Check_date(dateArrival)) // check for valid arrival date format
            sb.append("Error arrival date is wrong format\n");

        if (!Check_time(timeOfArrive)) // check for valid arrival time format
            sb.append("Error arrival time is invalid, ex: 5:23 PM\n");

        // check for valid dest code
        if(destinCode.length() != 3 || !destinCode.matches("[a-zA-Z]+"))
            sb.append("Error code of arrival needs to be three letters ex:abc\n");


        // check for valid arrival datei
        if(!Check_date(dateArrival)) // check for valid departure date format
            sb.append("Error arrival date wrong format\n");


        if (!Check_time(timeOfLeave))
            sb.append("Error departure time is incorrect\n");

        sb.append(noZone(sourceCode,destinCode));

        return sb.toString();
    }

    // checks for zone
    public String noZone(String src,String destiny){

        if(AirportNames.getName(src) == null && AirportNames.getName(destiny) == null)
            return "Error unknown code of arrival and departure";

        else if(AirportNames.getName(destiny) == null)
            return "Error unknown code of arrival";

        else if(AirportNames.getName(src)== null)
            return "Error unknown code of departure";

        return "";
    }


    // checks for correct time format
    public  boolean Check_time(String timeString){

        DateTimeFormat myForm = DateTimeFormat.getFormat("hh:mm a");

        try {

            Date myDate = myForm.parseStrict(timeString);

        }catch(Exception e){

            alerter.alert("Incorrect time format");
            return false;

        }

        return true;
    }


    // checks the date
    public boolean Check_date(String theDate){

        DateTimeFormat myDate = DateTimeFormat.getFormat("MM/dd/yyyy");

        try {

            Date dateDate = myDate.parseStrict(theDate);

        }catch (Exception e){

            alerter.alert("Incorrect date format");
            return false;
        }

        return true;
    }


    // look for matching
    public String findMe(String lookSRC, String lookDEST){


       String looking = theAirlines.searchDestSrc(lookSRC,lookDEST);

        if (looking.length() == 0)
            return "No matching flights";


        return "Fligths between "+lookSRC+" and "+lookDEST+"" +"\n"+looking;
    }

    private void throwClientSideException() {
        logger.info("About to throw a client-side exception");
        throw new IllegalStateException("Expected exception on the client side");
    }

    private void showUndeclaredException() {
        logger.info("Calling throwUndeclaredException");
        airlineService.throwUndeclaredException(new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable ex) {
                alertOnException(ex);
            }

            @Override
            public void onSuccess(Void aVoid) {
                alerter.alert("This shouldn't happen");
            }
        });
    }

    private void showDeclaredException() {
        logger.info("Calling throwDeclaredException");
        airlineService.throwDeclaredException(new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable ex) {
                alertOnException(ex);
            }

            @Override
            public void onSuccess(Void aVoid) {
                alerter.alert("This shouldn't happen");
            }
        });
    }

    @Override
    public void onModuleLoad() {
        setUpUncaughtExceptionHandler();

        // The UncaughtExceptionHandler won't catch exceptions during module load
        // So, you have to set up the UI after module load...
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                setupUI();
            }
        });

    }

    private void setupUI() {
        rootPanel = RootPanel.get();
        //VerticalPanel panel = new VerticalPanel();
        Grid panel = new Grid(7,10);
        rootPanel.add(panel);
        addWidgets(panel);

        airlineService.getAirline("hello", new AsyncCallback<Airline>() {
            @Override
            public void onFailure(Throwable throwable) {
                alerter.alert("failed to get airline");
            }

            @Override
            public void onSuccess(Airline airline) {

                theAirlines = airline;
            }
        });
    }

    private void setUpUncaughtExceptionHandler() {
        GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
            @Override
            public void onUncaughtException(Throwable throwable) {
                alertOnException(throwable);
            }
        });
    }

    @VisibleForTesting
    interface Alerter {
        void alert(String message);
    }
}