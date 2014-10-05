package all;

import java.io.*;

public class Main {

	public static void main(String[] args) throws IOException {
		InputStream in = new FileInputStream("F:\\校外通知列表.html");
		OutputStream out = new FileOutputStream("F:\\result.txt");			//把文件流封装好
		WorkClass example=new WorkClass();
		
		example.ContrastWrite(in,out);
		in.close(); 			out.close();

	}

}
