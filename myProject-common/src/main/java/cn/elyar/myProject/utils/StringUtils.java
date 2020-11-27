package cn.elyar.myProject.utils;



import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author elyar
 * @date 2020/11/25 11:32
 * @description
 */

public class StringUtils  {

        private static final char SEPARATOR = '_';

        private static final String UNKNOWN = "unknown";

        public final String NORMAL = "0";



        /**
         * 驼峰命名法工具
         *
         * @return toCamelCase(" hello_world ") == "helloWorld"
         * toCapitalizeCamelCase("hello_world") == "HelloWorld"
         * toUnderScoreCase("helloWorld") = "hello_world"
         */
        public static String toCamelCase(String s) {
            if (s == null) {
                return null;
            }

            s = s.toLowerCase();

            StringBuilder sb = new StringBuilder(s.length());
            boolean upperCase = false;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);

                if (c == SEPARATOR) {
                    upperCase = true;
                } else if (upperCase) {
                    sb.append(Character.toUpperCase(c));
                    upperCase = false;
                } else {
                    sb.append(c);
                }
            }

            return sb.toString();
        }

        /**
         * 驼峰命名法工具
         *
         * @return toCamelCase(" hello_world ") == "helloWorld"
         * toCapitalizeCamelCase("hello_world") == "HelloWorld"
         * toUnderScoreCase("helloWorld") = "hello_world"
         */
        public static String toCapitalizeCamelCase(String s) {
            if (s == null) {
                return null;
            }
            s = toCamelCase(s);
            return s.substring(0, 1).toUpperCase() + s.substring(1);
        }

        /**
         * 驼峰命名法工具
         *
         * @return toCamelCase(" hello_world ") == "helloWorld"
         * toCapitalizeCamelCase("hello_world") == "HelloWorld"
         * toUnderScoreCase("helloWorld") = "hello_world"
         */
        static String toUnderScoreCase(String s) {
            if (s == null) {
                return null;
            }

            StringBuilder sb = new StringBuilder();
            boolean upperCase = false;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);

                boolean nextUpperCase = true;

                if (i < (s.length() - 1)) {
                    nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
                }

                if ((i > 0) && Character.isUpperCase(c)) {
                    if (!upperCase || !nextUpperCase) {
                        sb.append(SEPARATOR);
                    }
                    upperCase = true;
                } else {
                    upperCase = false;
                }

                sb.append(Character.toLowerCase(c));
            }

            return sb.toString();
        }

        /**
         * 获取ip地址
         */
        public static String getIp(HttpServletRequest request) {
            String ip = request.getHeader("x-forwarded-for");
            if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            String comma = ",";
            String localhost = "127.0.0.1";
            if (ip.contains(comma)) {
                ip = ip.split(",")[0];
            }
            if  (localhost.equals(ip))  {
                // 获取本机真正的ip地址
                try {
                    ip = InetAddress.getLocalHost().getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
            return ip;
        }

//    /**
//     * 根据ip获取详细地址
//     */
//    public static String getCityInfo(String ip) {
//        DbSearcher searcher = null;
//        try {
//            String path = "ip2region/ip2region.db";
//            String name = "ip2region.db";
//            DbConfig config = new DbConfig();
//            File file = FileUtil.inputStreamToFile(new ClassPathResource(path).getStream(), name);
//            searcher = new DbSearcher(config, file.getPath());
//            Method method;
//            method = searcher.getClass().getMethod("btreeSearch", String.class);
//            DataBlock dataBlock;
//            dataBlock = (DataBlock) method.invoke(searcher, ip);
//            String address = dataBlock.getRegion().replace("0|","");
//            char symbol = '|';
//            if(address.charAt(address.length()-1) == symbol){
//                address = address.substring(0,address.length() - 1);
//            }
//            return address.equals(ElAdminConstant.REGION)?"内网IP":address;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            if(searcher!=null){
//                try {
//                    searcher.close();
//                } catch (IOException ignored) {
//                }
//            }
//
//        }
//        return "";
//    }
//
//    public static String getBrowser(HttpServletRequest request){
//        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
//        Browser browser = userAgent.getBrowser();
//        return browser.getName();
//    }

        /**
         * 获得当天是周几
         */
        public static String getWeekDay(){
            String[] weekDays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());

            int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
            if (w < 0){
                w = 0;
            }
            return weekDays[w];
        }

        /**
         * 获取当前机器的IP
         * @return /
         */
        public static String getLocalIp(){
            InetAddress addr;
            try {
                addr = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                return "unknown";
            }
            byte[] ipAddr = addr.getAddress();
            StringBuilder ipAddrStr = new StringBuilder();
            for (int i = 0; i < ipAddr.length; i++) {
                if (i > 0) {
                    ipAddrStr.append(".");
                }
                ipAddrStr.append(ipAddr[i] & 0xFF);
            }
            return ipAddrStr.toString();
        }

        /**
         * 比较两个字符串是否相等
         *
         * @param param1
         * @param param2
         * @return 结果集
         */
        public boolean isEquals(String param1, String param2) {
            return param1 == null ? param2 == null : param1.equals(param2);
        }

        public boolean isEquals(Integer param1, Integer param2) {
            return param1 == null ? param2 == null : param1.equals(param2);
        }

        /**
         * 响应状态为是否不正常状态
         *
         * @param code
         * @return 结果集
         */
        public boolean isAbnormalStatus(String code) {
            return !isEquals(code, NORMAL);
        }


        /**
         * 判断是否是中文
         * @param str
         * @return
         */
        public static boolean isChinese(String str){

            String regEx = "[\\u4e00-\\u9fa5]+";

            Pattern p = Pattern.compile(regEx);

            Matcher m = p.matcher(str);

            if(m.find())

                return true;

            else

                return false;

        }

       /* public String getHeader(String str){
            if (isChinese(str)){
                String convert="";
                for (int i = 0; i < str.length(); i++) {
                    char word=str.charAt(i);
                    String[] pinyinArray=PinyinHelper.toHanyuPinyinStringArray(word);
                    if(pinyinArray!=null){
                        convert +=pinyinArray[0].charAt(0);
                    }else {
                        convert += word;
                    }
                }
                return convert;
            }else{
                for (int i = 0; i < str.length(); i++) {
                    if(str.charAt(i)!=' '){
                        str=str.substring(i,str.length());
                        break;
                    }
                }
                String convert = str.substring(0, 1);

                for (int i = 0; i < str.length(); i++) {
                    char word =str.charAt(i);
                    if(Character.isSpaceChar(word) ){
                        convert+=str.charAt(i+1);
                    }
                }
                return convert;
            }

        }*/

        public static boolean isTime(String time){
            Pattern p = Pattern.compile("((((0?[0-9])|([1][0-9])|([2][0-4]))\\:([0-5]?[0-9])))?$");
            return p.matcher(time).matches();
        }

        public static boolean isDateTime(String datetime){

            Pattern p = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1][0-9])|([2][0-4]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");

            return p.matcher(datetime).matches();

        }


    }
