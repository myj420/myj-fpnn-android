package com.fpnn.sdk.proto;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

/**
 * Created by shiwangxing on 2017/11/29.
 */

public class Message {

    protected Map payload;

    //-----------------[ Constructor Functions ]-------------------
    public Message() {
        payload = new TreeMap<String, Object>();
    }

    public Message(Map body) {
        payload = body;
    }

    //-----------------[ Properties Functions ]-------------------

    public Map getPayload() {
        return payload;
    }

    public void setPayload(Map p) {
        payload = p;
    }

    //-----------------[ Data Accessing Functions ]-------------------

    public void param(String key, Object value) {
        payload.put(key, value);
    }

    public Object get(String key) {
        return payload.get(key);
    }

    public Object get(String key, Object def) {
        Object o = payload.get(key);
        return (o != null) ? o : def;
    }

    public Object want(String key) throws NoSuchElementException {
        Object o = payload.get(key);
        if (o == null)
            throw new NoSuchElementException("Cannot found object for key: " + key);

        return o;
    }


    public String getString(String key) {
        Object o = get(key);
        return (o == null)? "" :String.valueOf(o);
    }

    public int getInt(String key) {
        Object o = get(key);
        if (o != null)
            return wantInt(key);
        return 0;
    }

    public long getLong(String key) {
        Object o = get(key);
        if (o != null)
            return wantLong(key);
        return 0;
    }

    public boolean wantBoolean(String key) {
        Object obj = want(key);
        return (obj == null)? false :(Boolean)obj;
    }

    public boolean getBoolean(String key) {
        Object obj = get(key);
        return (obj == null)? false :(Boolean)obj;
    }

    public int wantInt(String key) {
        int value = -1;
        Object obj = want(key);
        if (obj instanceof Integer)
            value = (Integer) obj;
        else if (obj instanceof Long)
            value = ((Long) obj).intValue();
        else if (obj instanceof BigInteger)
            value = ((BigInteger) obj).intValue();
        else if (obj instanceof Short)
            value = ((Short) obj).intValue();
        else if (obj instanceof Byte)
            value = ((Byte) obj).intValue();
        else
            value = Integer.valueOf(String.valueOf(obj));
        return value;
    }

    public long wantLong(String key) {
        Object obj = want(key);
        long value = -1;
        if (obj instanceof Integer)
            value = ((Integer) obj).longValue();
        else if (obj instanceof Long)
            value = (Long) obj;
        else if (obj instanceof BigInteger)
            value = ((BigInteger) obj).longValue();
        else if (obj instanceof Short)
            value = ((Short) obj).longValue();
        else if (obj instanceof Byte)
            value = ((Byte) obj).longValue();
        else
            value = Long.valueOf(String.valueOf(obj));
        return value;
    }

    public String wantString(String key) {
        Object obj = want(key);
        return String.valueOf(obj);
    }

    //-----------------[ To Bytes Array Functions ]-------------------
    public byte[] toByteArray() throws IOException {
        MessagePayloadPacker packer = new MessagePayloadPacker();
        packer.pack(payload);
        return packer.toByteArray();
    }

    public byte[] raw() throws IOException {
        return toByteArray();
    }
}
