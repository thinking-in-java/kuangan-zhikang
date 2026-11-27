package com.yihe.framework.ehr;
import java.time.LocalDate;import java.util.Locale;import static com.yihe.framework.ehr.EhrService.*;
/** Validate an explicitly supplied roster identity and derive its birth date without changing sex. */
final class EmployeeIdentity {
 static String normalize(String raw){String v=raw.trim().toUpperCase(Locale.ROOT);if(v.isEmpty())return v;require(v.matches("[0-9]{15}|[0-9]{17}[0-9X]"),422,"身份证号应为15位或18位，末位可为X");String date=v.length()==18?v.substring(6,14):"19"+v.substring(6,12);try{LocalDate d=LocalDate.of(Integer.parseInt(date.substring(0,4)),Integer.parseInt(date.substring(4,6)),Integer.parseInt(date.substring(6,8)));require(!d.isAfter(LocalDate.now()),422,"身份证号中的出生日期无效");}catch(java.time.DateTimeException e){throw new EhrException(422,"身份证号中的出生日期无效");}if(v.length()==18){int[] weights={7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};int sum=0;for(int i=0;i<17;i++)sum+=(v.charAt(i)-'0')*weights[i];require("10X98765432".charAt(sum%11)==v.charAt(17),422,"身份证号校验位不正确，请核对原证件");}return v;}
 static String birth(String identity){String v=normalize(identity);if(v.isEmpty())return "";String d=v.length()==18?v.substring(6,14):"19"+v.substring(6,12);return d.substring(0,4)+"-"+d.substring(4,6)+"-"+d.substring(6,8);}
}
