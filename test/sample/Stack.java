package sample;


import java.util.LinkedList;
import java.util.List;

/**
 * 凝集性の高いクラス
 * あるメソッドが操作するインスタンス変数が多いほど、そのクラスのメソッドの凝集性は高い
 */
public class Stack {
	private int topOfStack = 0;
	List<Integer> elements = new LinkedList<Integer>();
	
	public int size(){
		return topOfStack;
	}
	
	public void push(int element){
		topOfStack++;
		elements.add(element);
	}
	
	public int pop() {
		if(topOfStack==0){
			System.out.println("PoppedWhenEmpty!");
		}
		int element = elements.get(--topOfStack);
		elements.remove(topOfStack);
		return element;
	}
	
}
