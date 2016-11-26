package neha.example.com.campusmap;

import com.google.api.client.util.Key;

import java.io.Serializable;

/**
 * Created by np6023 on 10/30/16.
 */

        import com.google.api.client.util.Key;

        import java.io.Serializable;


/**
 * Created by np6023 on 7/18/2016.
 */

        import java.io.Serializable;

        import com.google.api.client.util.Key;

/** Implement this class from "Serializable"
 * So that you can pass this class Object to another using Intents
 * Otherwise you can't pass to another actitivy
 * */
public class Position implements Serializable {

    @Key
    public String[] destination_addresses;

    @Key
    public String[] origin_addresses;

    @Key
    public String status;

    @Key
    public Rows[] rows;

    public static class Rows implements Serializable {
        @Key
        public Elements[] elements;
    }

    public static class Elements implements Serializable {
        @Key
        public Duration duration;

        @Key
        public Distance distance;

        @Key
        public String status;

    }
        public static class Distance implements Serializable
    {
        @Key
        public String text;
        @Key
        public int value;
    }

    public static class Duration implements Serializable
    {
        @Key
        public String text;
        @Key
        public int value;
    }

    // ND ++



}