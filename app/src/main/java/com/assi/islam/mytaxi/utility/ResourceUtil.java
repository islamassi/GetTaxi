package com.assi.islam.mytaxi.utility;

import android.content.Context;

import com.assi.islam.mytaxi.MyTaxiApplication;
import com.assi.islam.mytaxi.R;

import androidx.annotation.IdRes;
import androidx.annotation.StringRes;

/**
 * Created by islam assi
 */
public class ResourceUtil {

    private static Context mContext = MyTaxiApplication.getContext();

    public static String bindString(@StringRes int stringRes){

        return mContext.getString(stringRes);
    }

    /**
     * convert heading to direction text
     */
    public static String getHeadingHumanReadableText(double heading){

        String headingText = "";
        if ((heading > 337.5 && heading<= 360) || (heading>0 && heading<= 22.5)){

            headingText =  bindString(R.string.east);

        }else if(heading>22.5 && heading <= 67.5){

            headingText =  bindString(R.string.northeast);

        }else if(heading>67.5 && heading <= 112.5){

            headingText =  bindString(R.string.north);

        }else if(heading>112.5 && heading <= 157.5){

            headingText =  bindString(R.string.northwest);

        }else if(heading>157.5 && heading <= 202.5){

            headingText =  bindString(R.string.west);

        }else if(heading>202.5 && heading <= 247.5){

            headingText =  bindString(R.string.southwest);
        } else if(heading>247.5 && heading <= 292.5){

            headingText =  bindString(R.string.south);
        }else if(heading>292.5 && heading <= 337.5){

            headingText =  bindString(R.string.southeast);
        }

        return bindString(R.string.heading)+ " " + headingText;
    }
}
