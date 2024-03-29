package edu.pdx.cs410J.chasam;
import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.web.HttpRequestHelper;
import sun.security.ssl.SSLSocketImpl;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.util.Map;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send key/value pairs.
 */

public class AirlineRestClient extends HttpRequestHelper
{
    private static final String WEB_APP = "airline";
    private static final String SERVLET = "flights";
    private final String url;


    /**
     * Creates a client to the airline REST service running on the given host and port
     * @param hostName The name of the host
     * @param port The port
     */
    public AirlineRestClient( String hostName, int port )
    {
        this.url = String.format( "http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET );
    }

    /**
     * Returns all keys and values from the server
     */
    public Map<String, String> getAllKeysAndValues() throws IOException {
        Response response = get(this.url);
        return Messages.parseKeyValueMap(response.getContent());
    }

    /**
     * Returns the value for the given key
     */
    public String getValue(String key) throws IOException {
        Response response = get(this.url, "key", key);
        throwExceptionIfNotOkayHttpStatus(response);
        String content = response.getContent();
        return Messages.parseKeyValuePair(content).getValue();
    }

    public void addKeyValuePair(String key, String value) throws IOException {

        Response response = postToMyURL("name", key, "flight info", value);
        throwExceptionIfNotOkayHttpStatus(response);
    }

    @VisibleForTesting
    Response postToMyURL(String... keysAndValues) throws IOException {

        return post(this.url, keysAndValues);
    }

    public void removeAllMappings() throws IOException {
        Response response = delete(this.url);
        throwExceptionIfNotOkayHttpStatus(response);
    }

    private Response throwExceptionIfNotOkayHttpStatus(Response response) {
        int code = response.getCode();
        if (code != HTTP_OK) {
            throw new AppointmentBookRestException(code);
        }

        return response;
    }

    public boolean addMe(String flightName, String fNum, String theSRC, String theLeave, String theDest, String theArrival){

        try {

            Response response =  postToMyURL("name",flightName,"flightNumber",fNum,"src",
                    theSRC,"departTime",theLeave, "dest",theDest,"arriveTime",theArrival);

            if (response.getContent().equals("Success"))
                return true;

            return false;


        }catch (IOException e){

            System.out.println("server is down");
                return false;
        }

    }

    // gets from user
    public void getMe(String aName, String src3, String dest3){

        try {

            Response response = get(this.url, "name",aName,"src",src3,"dest",dest3);
            throwExceptionIfNotOkayHttpStatus(response);
            String content = response.getContent();
            System.out.println(content);

        }catch (IOException e){
            System.out.println("Server is down");
        }
    }


    private class AppointmentBookRestException extends RuntimeException {
        public AppointmentBookRestException(int httpStatusCode) {
            super("Got an HTTP Status Code of " + httpStatusCode);
        }
    }
}
