package com.coinpany.core.android.widget;

import android.content.Context;
import android.graphics.Color;

import com.coinpany.core.android.widget.calendar.dateutil.CLocale;
import com.coinpany.core.android.widget.calendar.dateutil.PersianCalendar;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Hoda  on 11/16/2015
 */
public class Utils {
    private static final Object smsLock = new Object();
    private static boolean waitingForSms = false;
    public static int highlightColor(int originalColor) {
        float hsvColor[] = new float[3];
        Color.colorToHSV(originalColor, hsvColor);
        hsvColor[2] = 0.5f * (1f + hsvColor[2]);
        return Color.HSVToColor(hsvColor);
    }

    public static int shadowColor(int originalColor) {
        float hsvColor[] = new float[3];
        Color.colorToHSV(originalColor, hsvColor);
        hsvColor[2] = 0.5f * hsvColor[2];
        return Color.HSVToColor(hsvColor);
    }

    public static int shadowColor(int originalColor, float ratio) {
        float hsvColor[] = new float[3];
        Color.colorToHSV(originalColor, hsvColor);
        hsvColor[2] = ratio * hsvColor[2];
        return Color.HSVToColor(hsvColor);
    }


    public static int dp(Context context, float value) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) Math.ceil(density * value);
    }

    public static boolean isWaitingForSms() {
        boolean value;
        synchronized (smsLock) {
            value = waitingForSms;
        }
        return value;
    }
    public static void setWaitingForSms(boolean value) {
        synchronized (smsLock) {
            waitingForSms = value;
        }
    }
    public static String persianNumbers(String str) {
        char[] arabicChars = {'٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩'};
        String englishNumbers = "093598891239";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (englishNumbers.contains(String.valueOf(str.charAt(i)))) {
                builder.append(arabicChars[(int) (str.charAt(i)) - 48]);
            } else {
                builder.append(str.charAt(i));
            }
        }
        return builder.toString();
    }

    public static String timeElapsedFromDate(long from) {
        long diff = System.currentTimeMillis() - from;

        long diffSeconds = diff / 1000;
        long diffMinutes = diffSeconds / 60;

        long diffHours = diffMinutes / 60;
        long diffDays = diffHours / 24;
        long diffWeeks = diffDays / 7;

        String str;

        if (diffWeeks != 0) {
            str = String.format("%s هفته ", String.valueOf(diffWeeks)).trim();
        } else if (diffDays != 0) {
            str = String.format("%s روز ", String.valueOf(diffDays)).trim();
        } else if (diffHours != 0) {
            str = String.format("%s ساعت ", diffHours).trim();
        } else if (diffMinutes != 0) {
            str = String.format("%s دقیقه ", String.valueOf(diffMinutes)).trim();
        } else {
            str = "الان";
        }
        return persianNumbers(str);
    }

    public static String getDate(CLocale locale, long dateTime) {
        PersianCalendar calendar = new PersianCalendar(dateTime);
        String dow = calendar.getPersianWeekDayName();
        int dom = calendar.getPersianDay();
        String moy = calendar.getPersianMonthName();
        int y = calendar.getPersianYear();
        return dow + " " + dom + " " + moy + " " + y;
    }

    public static String getTime(CLocale locale, long dateTime) {
        PersianCalendar calendar = new PersianCalendar(dateTime);

        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int m = calendar.get(Calendar.MINUTE);

        return persianNumbers(String.format("%02d:%02d", h, m));
    }

    public static String getSimpleDate(Date date) {
        PersianCalendar calendar = new PersianCalendar(date.getTime());
        return persianNumbers(calendar.getPersianLongDate());

    }

    public static String getSimpleDateMilli(Long date) {
        PersianCalendar calendar = new PersianCalendar(date);
        return persianNumbers(calendar.getPersianLongDate());
    }

    public static String getShortSimpleDate(Date date) {
        PersianCalendar calendar = new PersianCalendar(date.getTime());
        return persianNumbers(calendar.getPersianShortDate());
    }

    public static boolean isEqualsDateOnly(Date d1, Date d2) {
        if (d1 == null && d2 == null) {
            return true;
        } else if (d1 == null) {
            return false;
        } else if (d2 == null) {
            return false;
        }


        PersianCalendar cal1 = new PersianCalendar(d1.getTime());
        PersianCalendar cal2 = new PersianCalendar(d2.getTime());

        return cal1.getPersianYear() == cal2.getPersianYear() &&
                cal1.getPersianMonth() == cal2.getPersianMonth() &&
                cal1.getPersianDay() == cal2.getPersianDay()
                ;
    }
}
