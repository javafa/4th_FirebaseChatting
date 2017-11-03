package net.flow9.firebasechatting.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pc on 11/3/2017.
 */

public class VerificationUtil {

    /**
     * 패스워드 검증하기
     * @param password
     * @return
     */
    public static boolean isValidPassword(String password) {
        String regex = "^[A-Za-z0-9]{8,16}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);
        return m.matches();
    }
    /**
     * 이메일 검증하기
     * @param email
     * @return
     */
    public static boolean isValidEmail(String email) {
        String regex = "^[a-z0-9A-Z_-]+(.[a-z0-9A-Z_-])*@([a-z0-9A-Z.])+.([a-zA-Z]){2,}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        return m.matches();
    }
    /**
     * 이름 검증하기
     * @param str
     * @return
     */
    public static boolean isValidName(String str){
        String regex = "^[가-힣A-Za-z0-9]{2,12}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.matches();

    }
}
