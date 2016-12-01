package 识别有效的IP地址和掩码并进行分类统计;



import java.util.Scanner;  //import java.util.*的话是不会包括util下的子包的，*只代表类名
import java.util.regex.Matcher;  //正则表达式还需要去弄清楚
import java.util.regex.Pattern;

public class Main{
    /*	
    	思路：
    	首先判断IP和掩码的格式、以及判断掩码数字大小正确与否：
    		错了就wrongIP+1，无需接着判断，直接开始下一条line。
    		满足的话就，判断IP的种类内以及是否为私码
    	
    	一些需要注意的条件：
    	1.本题没有把127开头的IP列为错误IP.实际中127开头的IP为保留回环地址,
    	    不可能出现在网络上的。在此题中127既不是A-E中的种类，也没列入到错误IP中。
        2.同样的，0开头的也被设定是合法IP，没在各种类中。
        3.掩码不可以全为0或者1，如255.255.255.255是错误的掩码。
        4.IP和掩码一个错误就算做整条Line错误。
    */
    
	private static int A,B,C,D,E,wrongIP,privateIP;
   
    public static void main(String[] args){
        Scanner scan=new Scanner(System.in);
        while(scan.hasNextLine()){
            String curLine=scan.nextLine();
            String[] IPs=curLine.split("~"); 
            if(judgeFormat(IPs[0]) && judgeFormat(IPs[1]) && judgeMask(IPs[1])) {
            	int kind=judgeKind(IPs[0]);
            	if(kind==0){
            		//wrongIP++;
                	continue;
            	}else if(kind==1){
            		A++;
            	}else if(kind==2){
            		B++;
            	}else if(kind==3){
            		C++;
            	}else if(kind==4){
            		D++;
            	}else if(kind==5){
            		E++;
            	}else{
            		System.out.print("Error");
            		break;
            	}
            	if(judgePrivateIP(IPs[0])){
            		privateIP++;
            	}
            	
            }else{
            	wrongIP++;
            	continue;
            }
        }
        System.out.print(A+" "+B+" "+C+" "+D+" "+E+" "+wrongIP+" "+privateIP);
    }
    
    public static boolean judgeFormat(String s){
    	if (s== null || "".equals(s))  return false;  
        Pattern pattern = Pattern.compile("^(\\d+)\\.(\\d+)\\.(\\d+)\\.(\\d+)$");  
        Matcher matcher = pattern.matcher(s);  
           
        if (matcher.matches()) {  
            String[] nums = s.split("\\.");  //此处因为"."在正则里面是元字符，需要转义;其余的如indexOf不需要。另外，split用的是字符串，非字符。
            for (String num : nums) {  
                int n = Integer.valueOf(num);  
                if (n < 0 || n > 255) {  
                    return false;
                }  
            }  
        } else {  
            return false;  
        }  
        return true;  
    }
    
    
    public static int judgeKind(String s){
    	String firstNumStr = s.substring(0,s.indexOf("."));
    	int firstNum=Integer.valueOf(firstNumStr);
    	if (firstNum > 0 && firstNum < 127){
    		//A
    		return 1;
    	}else if (firstNum >= 128 && firstNum < 192) {  
            // B  
            return 2;  
        } else if (firstNum >= 192 && firstNum < 224) {  
            // C  
            return 3;  
        } else if (firstNum >= 224 && firstNum < 240) {  
            // D  
            return 4;  
        } else if (firstNum >= 240 && firstNum <= 255) {  
            // E  
            return 5;  
        }  else{
        	//wrong
        	return 0;  
            /*本题没有把127开头的IP列为错误IP.实际中127开头的IP为保留回环地址,
    	         不可能出现在网络上的。在此题中127既不是A-E中的种类，也没列入到错误IP中。*/
        }
    }
    
    
    public static boolean judgePrivateIP(String s){
    	String fns=s.substring(0,s.indexOf('.'));
    	String excess=s.substring(s.indexOf('.')+1);
    	String sns=excess.substring(0, excess.indexOf('.'));
    	int fn=Integer.valueOf(fns);
    	int sn=Integer.valueOf(sns);
    	if(fn==10 || (fn==172 && sn>=16 && sn<=31) || (fn==192 && sn==168)){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    public static boolean judgeMask(String s){    //判断掩码的规则通过使用indexOf和lastIndexOf实现，值得留意。￥￥￥￥
    	String[] nums=s.split("\\.");
    	StringBuffer total=new StringBuffer();
    	for(int i=0;i<nums.length;i++){
    		int num=Integer.valueOf(nums[i]);
    		int flag=1<<7;//1000 0000 
    		for(int j=0;j<8;j++){
    			if((num & flag)!=0){
    				total.append('1');
    			}else{
    				total.append('0');
    			}
    			num= num<<1;
    		}
    	}
    	if(total.lastIndexOf("1")==(total.indexOf("0")-1) ){//因为掩码不可以全为0或者1，所以不能加上下面条件：|| total.indexOf("1")==-1 || total.indexOf("0")==-1
    		return true;
    	}
        return false;
    }
}