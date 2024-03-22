package com.MedhVrushti.checkerslab_edulearning;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StaticFile {

    public static String Url="https://medhvrushti.checkerslab.com";
    public static String userId="";
    public static String bearToken="";
    public static String samplePaymentId="100022";
    public static String roleId="100001";

    public static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    public static String date = df.format(Calendar.getInstance().getTime());

    public static String todayDate=date;
}
