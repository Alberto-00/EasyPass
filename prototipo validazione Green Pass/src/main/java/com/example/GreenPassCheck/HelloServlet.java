package com.example.GreenPassCheck;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String encodedDGC = encodeValue(request.getParameter("dgc"));
        URL urldemo = new URL("http://localhost:3000/?dgc=" + encodedDGC);
        URLConnection yc = urldemo.openConnection();
        BufferedReader in = null;
        try{
            in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        } catch (IOException e){
            System.out.println(request.getParameter("dgc"));
            System.out.println("Not validdd");
            return;
        }

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();
    }

    private String encodeValue(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    }

    public void destroy() {
    }
}