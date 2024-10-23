package com.entrance_test.demo.utils;

import com.entrance_test.demo.exception.AppException;
import com.entrance_test.demo.exception.ErrorCode;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateTimeService {

    public String remainingTimeBetweenDays(Date endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date d1 = sdf.parse(sdf.format(new Date()));
            Date d2 = sdf.parse(endDate.toString());

            long differenceInTime = d2.getTime() - d1.getTime();

            long differenceInSeconds = (differenceInTime / 1000) % 60;

            long differenceInMinutes = (differenceInTime / (1000 * 60)) % 60;

            long differenceInHours = (differenceInTime / (1000 * 60 * 60)) % 24;

            long differenceInDays = (differenceInTime / (1000 * 60 * 60 * 24)) % 365;

            return differenceInDays + " days - " + differenceInHours + " hours - " + differenceInMinutes + " minutes - " + differenceInSeconds + " seconds";
        } catch (ParseException e) {
            throw new AppException(ErrorCode.PARSE_DATE_ERROR);
        }
    }
}
