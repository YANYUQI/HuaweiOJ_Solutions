package ʶ����Ч��IP��ַ�����벢���з���ͳ��;



import java.util.Scanner;  //import java.util.*�Ļ��ǲ������util�µ��Ӱ��ģ�*ֻ��������
import java.util.regex.Matcher;  //������ʽ����ҪȥŪ���
import java.util.regex.Pattern;

public class Main{
    /*	
    	˼·��
    	�����ж�IP������ĸ�ʽ���Լ��ж��������ִ�С��ȷ���
    		���˾�wrongIP+1����������жϣ�ֱ�ӿ�ʼ��һ��line��
    		����Ļ��ͣ��ж�IP���������Լ��Ƿ�Ϊ˽��
    	
    	һЩ��Ҫע���������
    	1.����û�а�127��ͷ��IP��Ϊ����IP.ʵ����127��ͷ��IPΪ�����ػ���ַ,
    	    �����ܳ����������ϵġ��ڴ�����127�Ȳ���A-E�е����࣬Ҳû���뵽����IP�С�
        2.ͬ���ģ�0��ͷ��Ҳ���趨�ǺϷ�IP��û�ڸ������С�
        3.���벻����ȫΪ0����1����255.255.255.255�Ǵ�������롣
        4.IP������һ���������������Line����
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
            String[] nums = s.split("\\.");  //�˴���Ϊ"."������������Ԫ�ַ�����Ҫת��;�������indexOf����Ҫ�����⣬split�õ����ַ��������ַ���
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
            /*����û�а�127��ͷ��IP��Ϊ����IP.ʵ����127��ͷ��IPΪ�����ػ���ַ,
    	         �����ܳ����������ϵġ��ڴ�����127�Ȳ���A-E�е����࣬Ҳû���뵽����IP�С�*/
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
    
    public static boolean judgeMask(String s){    //�ж�����Ĺ���ͨ��ʹ��indexOf��lastIndexOfʵ�֣�ֵ�����⡣��������
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
    	if(total.lastIndexOf("1")==(total.indexOf("0")-1) ){//��Ϊ���벻����ȫΪ0����1�����Բ��ܼ�������������|| total.indexOf("1")==-1 || total.indexOf("0")==-1
    		return true;
    	}
        return false;
    }
}