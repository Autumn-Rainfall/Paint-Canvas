package main;

import frame.GFrame;

public class GMain {

	private static GFrame frame; // Frame�� �ּҸ� �����ϰ� �ִ� Pointer
	
	public static void main(String[] args) {
		frame = new GFrame();
		frame.initialize(); // �ʱ�ȭ(���� �Ķ� ��������� ������ �� �� �� �ʱ�ȭ)
		
		frame.setVisible(true);
	}

}
