package all;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class WorkClass {
	final int LEFT_ANGLE='<';
	final int RIGHT_ANGLE='>';
	boolean WriteSwitch ;
	int datalength;
	int[] td=new int[4];
	int[] tdEnd=new int[5];
	int[] trEnd=new int[5];
	int[] data=new int[100];
	WorkClass(){
		datalength=-1;					//length最先存储的当前最有一位已有数据元素的下标
		td[0]='<';				td[1]='t';				td[2]='d';				td[3]='>';
		tdEnd[0]='<';		tdEnd[1]='/';		tdEnd[2]='t';		tdEnd[3]='d';		tdEnd[4]='>';
		trEnd[0]='<';		trEnd[1]='/';			trEnd[2]='t';			trEnd[3]='r';			trEnd[4]='>';
	}
	void ContrastWrite(InputStream in,OutputStream out) throws IOException{
		int temp,Flag;
		while(true){						//最外层的死循环
			temp=in.read();				//读入一个字符，存入temp
			if(-1==temp){						//如果读进来的字符为结束符就退出
				break;
			}
			if(temp==LEFT_ANGLE){				//如果读到的字符为左尖括号
				do{
					MatchData(temp);				//把temp这个字符内容存进data数组里
					temp=in.read();
				}while(temp!=RIGHT_ANGLE);			//如果还没读到右尖括号，将继续循环
				MatchData(temp);										//把最后的右尖括号也写进判断data数组里
				Flag=SameWord();								//判断一下data数组里的信息与哪个对比标准相匹配，
				ClearData();											//清空存储空间
				FlagFunction( in,out,Flag);				//对于Flag的值选择不同的功能
			}
			else{									//如果读到的不是左尖括号，那么就有可能是正文内容，但是有可能不是，但是如果写入开关是开着的，就代表是正文
				if(WriteSwitch==true){
					out.write(temp);							//写入文件内
				}
			}
		}//while   loop end location
	}//method end location
	void MatchData(int temp){		
		if(datalength<99){
			data[datalength+1]=temp;				//如果length=-1，说明现在数组内的元素有效位在-1的位置，也就是说，没有存入元素，所以说要想使用这个数组空间，就要length+1，
			datalength++;							//	然后length可以自增，length永远指向有效的最后一位
		}
	}
	int SameWord(){					//判断字符串匹配的方法
		int  Flag=0,i;
		for(i=0;(i<4)&&(td[i]==data[i]);i++){					//循环这个数组，如果它与标准td完全一致，则结束时i应该等于4，
		}
		if(i==4){
			Flag=1;
		}
		for(i=0;(i<5)&&(tdEnd[i]==data[i]);i++){			//循环这个数组，如果它与标准tdEnd完全一致，则结束时i应该等于5，
		}
		if(i==5){
			Flag=2;
		}
		for( i=0;(i<5)&&(trEnd[i]==data[i]);i++){			//循环这个数组，如果它与标准trEnd完全一致，则结束时i应该等于5，
		}
		if(i==5){
			Flag=3;
		}
		return Flag;						//返回匹配的结果，如果与以上的标准都不等，那么Flag会等于0
	}
	void ClearData(){					//每次要存入新数据之前，要把之前存入的数据全部归零，并且把字长指针初始化，
		for(int i=0;i<=datalength;i++){
			data[i]=0;
		}
		datalength=-1;
	}
	void FlagFunction(InputStream in,OutputStream out,int Flag) throws IOException{
		if(Flag==1){										//如果返回1代表这是<td>，可以打开书写开关
			WriteSwitch=true;
		}
		else{
			if(Flag==2){								//如果返回2代表这是</td>，可以关闭书写开关
				out.write('\t');								//可以顺便写入一个制表符，作为间断，增加可读性
				WriteSwitch=false;
			}
			else{
				if(Flag==3){						//如果返回3代表这是</tr>，代表这是一行的结束，可以用来增加一个换行符
					out.write('\r');
					out.write('\n');
				}
			}
		}
	}
	
	
}			//文件结尾
