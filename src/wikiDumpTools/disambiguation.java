package wikiDumpTools;

import java.util.*;

import java.io.*;
import java.io.FileWriter;
import java.io.BufferedWriter;

import edu.jhu.*;
import edu.jhu.nlp.wikipedia.*;

/**
 * @author S1LV3R@DELL
 * @since 2015-05-25
 * @version 1
 */
public class disambiguation {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//TODO 加入路径支持
		WikiXMLParser wxsp = WikiXMLParserFactory.getSAXParser("E:/ELDATA/enwiki.xml.bz2");
        try {    
            wxsp.setPageCallback(
            		new PageCallbackHandler() { 
                           public void process(WikiPage page) {
//                        	   System.out.println(page.getTitle());
                        	   if (page.isDisambiguationPage())
                        	   {
                        		   String fileNameAndPath="E:/ELDATA/wikiDisambiguation/"+page.getID();
                        		   System.out.println(fileNameAndPath);
                        		   File file =new File(fileNameAndPath);
                        		   try{
	                        		   if(!file.exists()){
	                        		          file.createNewFile();
	                        		   }
	                        		   FileOutputStream out=new FileOutputStream(file);
	                        		   OutputStreamWriter osw=new OutputStreamWriter(out, "UTF8");
	                        		   BufferedWriter buff=new BufferedWriter(osw);
	                        		   buff.write((page.getTitle()+"\n"));
	                        		   buff.write(page.getWikiText());
	                        		   buff.flush();
	                        		   buff.close();
	                        		   out.close();
                        		   }catch(IOException e){
                        			   e.printStackTrace();
                        		   }
                        	   }
                           }
                    }
            );
            wxsp.parse();
        }catch(Exception e) {
                e.printStackTrace();
        }
	}
}
