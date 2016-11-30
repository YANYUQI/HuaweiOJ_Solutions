package 字符串最后一个单词的长度;

public class Main {
	public static void getLastWordLen(String s){
        String[] words=s.split(" ");
        System.out.print(words[words.length-1].length());
    }
    
    public static void main(String[] args){
        Scanner scan=new Scanner(System.in);
        String s=scan.nextLine();
        getLastWordLen(s);
    }
}
