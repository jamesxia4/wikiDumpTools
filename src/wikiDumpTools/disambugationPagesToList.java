package wikiDumpTools;

import java.util.*;
import java.util.regex.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


import java.io.*;
import java.io.File.*;

import edu.jhu.*;
import edu.jhu.nlp.wikipedia.*;

/**
 * @author S1LV3R@DELL
 * @since 2015-05-26
 * @version 1
 */

public class disambugationPagesToList {
	public static void main(String[] args){
		//TODO 加入路径支持
		File dir= new File("C:/wikiDisambiguation");
//		File dir= new File("E:/ELDATA/test");

		File file[]=dir.listFiles();
		for (int i =0;i<file.length;i++)
		{
			try{
				StringBuffer text=new StringBuffer("");
//				FileReader fr = new FileReader(file[i].getAbsolutePath());
				FileInputStream inStream = new FileInputStream(file[i].getAbsolutePath());
				InputStreamReader isr = new InputStreamReader(inStream,"UTF8");
//				FileWriter fw = new FileWriter("C:/processed/"+file[i].getName());
				BufferedReader br = new BufferedReader(isr);
				
				FileOutputStream out=new FileOutputStream("C:/processed/"+file[i].getName());
     		    OutputStreamWriter osw=new OutputStreamWriter(out, "UTF8");
     		    BufferedWriter buff=new BufferedWriter(osw);
     		    
				String nextLine=br.readLine();
				buff.write(nextLine.replaceAll(" \\(.*\\)", "")+"|\n");
				buff.flush();
				while(nextLine!=null){
					text.append(nextLine);
					text.append("\n");
					nextLine=br.readLine();
				}
				br.close();
				//TODO 多层XML输出，Text属性
//				Pattern pattern=Pattern.compile("(\\*).*(\\[\\[).*(\\]\\]).*(\\n)",Pattern.MULTILINE);
				Pattern pattern=Pattern.compile("(\\*)+.*(\\[\\[).*(\\]\\])",Pattern.MULTILINE);
				String data=text.toString();
				Matcher matcher=pattern.matcher((CharSequence)data);
				StringBuffer outTemp=new StringBuffer("");
				while(matcher.find()){
					//TODO 处理特殊情况，清理锚文本内属性值
					String matchLine=matcher.group();
					outTemp=outTemp.append(matchLine+"\n");
				}
				
				Pattern pattern1=Pattern.compile("\\[\\[.*?\\]\\]",Pattern.MULTILINE);
				Matcher matcher1=pattern1.matcher((CharSequence)outTemp);
				while(matcher1.find()){
					String matchLine1=matcher1.group().replaceAll("\\|.*\\]\\]", "]]");
					matchLine1.replaceAll("\\[\\[","");
					matchLine1.replaceAll("\\]\\]","");
//					System.out.println(matchLine1);
					buff.write(matchLine1+"\n");
					buff.flush();
				}
				buff.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
}