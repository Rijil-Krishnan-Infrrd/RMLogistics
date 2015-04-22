/**
 * 
 */
package com.nexera.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.mule.mvel2.util.ThisLiteral;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.newfi.nexera.vo.AuthenticateVO;


/**
 * @author Utsav
 *
 */
public class Utils
{
    private static final Logger LOG = Logger.getLogger( Utils.class );


    public static String getUserTicket( String userName, String passWord )
    {
        String url = "http://localhost:8181/authCall";
        LOG.debug( "Inside method getUserTicket " );
        AuthenticateVO authenticate = new AuthenticateVO();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );
        headers.setAccept( Arrays.asList( MediaType.APPLICATION_JSON ) );
        ResponseEntity<String> response = new ResponseEntity<String>( headers, HttpStatus.OK );
        authenticate.setOpName( "GetUserAuthTicket" );
        authenticate.setUserName( userName );
        authenticate.setPassWord( passWord );
        Gson gson = new Gson();
        String jsonString = gson.toJson( authenticate );
        RestTemplate restTemplate = new RestTemplate();
        response = restTemplate.postForEntity( url, jsonString, String.class );
        String ticket = response.getBody();
        return ticket;
    }


    public static String readFileAsString( String fileName ) throws IOException
    {
        ClassLoader classLoader = Utils.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream( fileName );
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ) );
        char[] buf = new char[1024];
        int numRead = 0;
        while ( ( numRead = reader.read( buf ) ) != -1 ) {
            String readData = String.valueOf( buf, 0, numRead );
            fileData.append( readData );
        }
        reader.close();

        return fileData.toString();
    }


    public static String applyMapOnString( Map<String, String> map, String fileData )
    {
        if ( map != null ) {
            Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();
            while ( entries.hasNext() ) {
                Map.Entry<String, String> entry = entries.next();
                System.out.println( "Key = " + entry.getKey() + ", Value = " + entry.getValue() );

                if ( fileData.contains( entry.getKey() ) ) {
                    fileData = fileData.replace( entry.getKey(), entry.getValue() );
                }
            }
        }
        return fileData;
    }


    /**
     * @param absolutePath
     * @return
     * @throws IOException 
     */
    public static String readFileAsStringFromPath( String absolutePath ) throws IOException
    {
        StringBuilder fileData = new StringBuilder( 1000 );//Constructs a string buffer with no characters in it and the specified initial capacity
        BufferedReader reader = new BufferedReader( new FileReader( absolutePath ) );

        char[] buf = new char[1024];
        int numRead = 0;
        while ( ( numRead = reader.read( buf ) ) != -1 ) {
            String readData = String.valueOf( buf, 0, numRead );
            fileData.append( readData );
            buf = new char[1024];
        }

        reader.close();

        String returnStr = fileData.toString();
        return returnStr;
    }
}
