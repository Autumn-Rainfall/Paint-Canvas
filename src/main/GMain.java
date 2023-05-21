package main;

import frame.GFrame;

public class GMain {

	private static GFrame frame; // Frame의 주소를 저장하고 있는 Pointer
	
	public static void main(String[] args) {
		frame = new GFrame();
		frame.initialize(); // 초기화(만든 후랑 만들어지고 설정한 후 두 번 초기화)
		
		frame.setVisible(true);
	}

}
