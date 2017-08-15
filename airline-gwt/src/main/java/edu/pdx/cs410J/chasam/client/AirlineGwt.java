package edu.pdx.cs410J.chasam.client;

import com.google.common.annotations.VisibleForTesting;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import java.util.Collection;
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

    @VisibleForTesting
    Button showAirlineButton;

    @VisibleForTesting
    Button addFlight;

    @VisibleForTesting
    Button Search;

    @VisibleForTesting
    Button Help;

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

        Help = new Button("Help");
        panel.setWidget(4,1,Help);

        Search.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {

                if(sName == null) {

                    if(sDest.getText() == null && sSrc.getText() !=null)
                        alerter.alert("Missing airline name and destination code");

                    else if (sSrc.getText() == null && searchDest.getText() != null);
                        alerter.alert("Missing airline name and departure code");

                }

            }
        });

        /*
        airlinePrettyText.setCharacterWidth(80);
        airlinePrettyText.setVisibleLines(10);

        */

        showAirlineButton = new Button("Show Airline");
        showAirlineButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                showAirline(airlineName.getText(), airlinePrettyText);
            }
        });


        addFlight.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {

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
        final TextBox text = new TextBox();
        text.setVisibleLength(10);
        //panel.add(text);

        Button showText = new Button("Show Text");
        showText.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                alerter.alert("The text box contained: " + text.getText());
            }
        });

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

    private void showAirline(String airlineName, final TextArea airlinePrettyText) {
        logger.info("Calling getAirline");
        airlineService.getAirline(airlineName, new AsyncCallback<Airline>() {

            @Override
            public void onFailure(Throwable ex) {
                alertOnException(ex);
            }

            @Override
            public void onSuccess(Airline airline) {
                prettyPrintAirline(airline, airlinePrettyText);
            }
        });
    }

    private void prettyPrintAirline(Airline airline, TextArea airlinePrettyText) {
        HotDisplay hottie = new HotDisplay();
        try {
            hottie.dump(airline);

        } catch (IOException e) {
            alertOnException(e);
        }

        airlinePrettyText.setText(hottie.getPrettyText());
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
        RootPanel rootPanel = RootPanel.get();
        //VerticalPanel panel = new VerticalPanel();
        Grid panel = new Grid(5,10);
        rootPanel.add(panel);
        addWidgets(panel);
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


/*
public class AirlineGwt implements EntryPoint {

  private final Alerter alerter;
  private final AirlineServiceAsync airlineService;
  private final Logger logger;

  @VisibleForTesting
  Button showAirlineButton;

  @VisibleForTesting
  Button showUndeclaredExceptionButton;

  @VisibleForTesting
  Button showDeclaredExceptionButton;

  @VisibleForTesting
  Button showClientSideExceptionButton;

  @VisibleForTesting
  TextBox airlineName = new TextBox();

  @VisibleForTesting
  TextArea prettyEverything = new TextArea();

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

  private void addWidgets(VerticalPanel panel) {

      panel.add(airlineName);

    showAirlineButton = new Button("Show Airline");
    showAirlineButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        showAirline();
      }
    });

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

    panel.add(showAirlineButton);
    panel.add(showUndeclaredExceptionButton);
    panel.add(showDeclaredExceptionButton);
    panel.add(showClientSideExceptionButton);
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

  private void showAirline() {
    logger.info("Calling getAirline");
    airlineService.getAirline(new AsyncCallback<Airline>() {

      @Override
      public void onFailure(Throwable ex) {
        alertOnException(ex);
      }

      @Override
      public void onSuccess(Airline airline) {
        StringBuilder sb = new StringBuilder(airline.toString());
        Collection<Flight> flights = airline.getFlights();
        for (Flight flight : flights) {
          sb.append(flight);
          sb.append("\n");
        }
        alerter.alert(sb.toString());
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
    RootPanel rootPanel = RootPanel.get();
    VerticalPanel panel = new VerticalPanel();
    rootPanel.add(panel);

    addWidgets(panel);
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
*/