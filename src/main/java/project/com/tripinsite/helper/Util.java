package project.com.tripinsite.helper;

/**
 * 기본적인 공통 기능들을 묶어 놓은 클래스
 */
public class Util {
	/**
	 * 범위를 갖는 랜덤값을 생성하여 리턴하는 메서드
	 * 
	 * @param min - 범위 안에서의 최소값
	 * @param max - 범위 안에서의 최대값
	 * @return min~max 안에서의 랜덤값
	 */
	public int random(int min, int max) {
		int num = (int) ((Math.random() * (max - min + 1)) + min);
		return num;
	}

	/**
	 * 랜덤한 비밀번호를 생성하여 리턴한다.
	 * 
	 * @return String
	 */
	public String getRandomPassword() {
		// 리턴할 문자열
		String password = "";

		// A~Z, a~z, 1~0
		String words = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
		// 글자길이
		int words_len = words.length();

		for (int i = 0; i < 8; i++) {
			// 랜덤한 위치에서 한 글자를 추출한다.
			int random = random(0, words_len - 1);
			String c = words.substring(random, random + 1);

			// 추출한 글자를 미리 준비한 변수에 추가한다.
			password += c;
		}

		return password;
	}
}
