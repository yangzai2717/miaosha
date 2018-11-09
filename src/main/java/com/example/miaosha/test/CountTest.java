package com.example.miaosha.test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CountTest {

    public static void main(String[] args) {
        File file = new File("d://pyy.txt");
        try {
            int spaceCount = 0, dianCount = 0, douhaoCount = 0;
            StringBuffer sb = new StringBuffer();
            BufferedReader br = new BufferedReader(new FileReader(file));
            String str = "";
            while ((str=br.readLine())!=null){
                sb.append(str);
            }

            //符号
            int size = sb.length();
            for(int i=0; i<size; i++){
                if(sb.charAt(i) == ' '){
                    spaceCount++;
                } else if (sb.charAt(i) == '.'){
                    dianCount++;
                    sb.setCharAt(i, ' ');
                } else if (sb.charAt(i) == ','){
                    douhaoCount++;
                    sb.setCharAt(i, ' ');
                }
            }

            //字母
            String s = sb.toString();
            String[] ss = s.split(" ");

            Map<String, Integer> map = new HashMap<>();
            for (String e : ss) {
                if(e != "" && e!=null){
                    if(map.containsKey(e)){
                        map.put(e, map.get(e) + 1);
                    }else {
                        map.put(e, 1);
                    }
                }
            }

            System.out.println( map.toString()+ "\n" + "空格次数 " + spaceCount + " 点次数 " + dianCount);
            String count = map.toString()+ "\n" + "空格次数 " + spaceCount + " 点次数 " + dianCount;
            File outFile = new File("d://outFile.txt");
            /*OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(outFile));
            outputStreamWriter.write(count);*/
            BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
            bw.write(count);
            br.close();
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
