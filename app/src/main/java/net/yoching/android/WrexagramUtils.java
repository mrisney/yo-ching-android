package net.yoching.android;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by marcrisney on 5/11/16.
 */

public class WrexagramUtils {


    public static String getResourceText(Context context, int resId) {
        InputStream is = null;
        BufferedInputStream bis;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            is = context.getResources().openRawResource(resId);
            bis = new BufferedInputStream(is);
            int result = bis.read();
            while (result != -1) {
                byte b = (byte) result;
                baos.write(b);
                result = bis.read();
            }
        } catch (IOException e) {
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
            }
        }
        return baos.toString();
    }

    public static int getOutcome(int wrexNum) {

        int wrexagram = 1;

        switch (wrexNum) {

            case 111111:
                wrexagram = 1;
                break;
            case 111112:
                wrexagram = 43;
                break;
            case 111121:
                wrexagram = 14;
                break;
            case 111122:
                wrexagram = 34;
                break;
            case 111211:
                wrexagram = 9;
                break;
            case 111212:
                wrexagram = 5;
                break;
            case 111221:
                wrexagram = 26;
                break;
            case 111222:
                wrexagram = 11;
                break;
            case 112111:
                wrexagram = 10;
                break;
            case 112112:
                wrexagram = 58;
                break;
            case 112121:
                wrexagram = 38;
                break;
            case 112122:
                wrexagram = 54;
                break;
            case 112211:
                wrexagram = 61;
                break;
            case 112212:
                wrexagram = 60;
                break;
            case 112221:
                wrexagram = 41;
                break;
            case 112222:
                wrexagram = 19;
                break;
            case 121111:
                wrexagram = 13;
                break;
            case 121112:
                wrexagram = 49;
                break;
            case 121121:
                wrexagram = 30;
                break;
            case 121122:
                wrexagram = 55;
                break;
            case 121211:
                wrexagram = 37;
                break;
            case 121212:
                wrexagram = 63;
                break;
            case 121221:
                wrexagram = 22;
                break;
            case 121222:
                wrexagram = 36;
                break;
            case 122111:
                wrexagram = 25;
                break;
            case 122112:
                wrexagram = 17;
                break;
            case 122121:
                wrexagram = 21;
                break;
            case 122122:
                wrexagram = 51;
                break;
            case 122211:
                wrexagram = 42;
                break;
            case 122212:
                wrexagram = 3;
                break;
            case 122221:
                wrexagram = 27;
                break;
            case 122222:
                wrexagram = 24;
                break;
            case 211111:
                wrexagram = 44;
                break;
            case 211112:
                wrexagram = 28;
                break;
            case 211121:
                wrexagram = 50;
                break;
            case 211122:
                wrexagram = 32;
                break;
            case 211211:
                wrexagram = 57;
                break;
            case 211212:
                wrexagram = 48;
                break;
            case 211221:
                wrexagram = 18;
                break;
            case 211222:
                wrexagram = 46;
                break;
            case 212111:
                wrexagram = 6;
                break;
            case 212112:
                wrexagram = 47;
                break;
            case 212121:
                wrexagram = 64;
                break;
            case 212122:
                wrexagram = 40;
                break;
            case 212211:
                wrexagram = 59;
                break;
            case 212212:
                wrexagram = 29;
                break;
            case 212221:
                wrexagram = 4;
                break;
            case 212222:
                wrexagram = 7;
                break;
            case 221111:
                wrexagram = 33;
                break;
            case 221112:
                wrexagram = 31;
                break;
            case 221121:
                wrexagram = 56;
                break;
            case 221122:
                wrexagram = 62;
                break;
            case 221211:
                wrexagram = 53;
                break;
            case 221212:
                wrexagram = 39;
                break;
            case 221221:
                wrexagram = 52;
                break;
            case 221222:
                wrexagram = 15;
                break;
            case 222111:
                wrexagram = 12;
                break;
            case 222112:
                wrexagram = 45;
                break;
            case 222121:
                wrexagram = 35;
                break;
            case 222122:
                wrexagram = 16;
                break;
            case 222211:
                wrexagram = 20;
                break;
            case 222212:
                wrexagram = 8;
                break;
            case 222221:
                wrexagram = 23;
                break;
            case 222222:
                wrexagram = 2;
                break;
        }
        return wrexagram;
    }

}
