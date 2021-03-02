public class Decryption {

	public Decryption() {
	}

	public String decrypt(String sentence) {
		
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < sentence.length(); i++) {
			if (sentence.charAt(i) == ' ') {
				sb.append(' ');
			}
			else{
				int newLetter = (sentence.charAt(i) - 6);
				sb.append((char)newLetter);
			}
		}
		String decryptedMessage = sb.toString();
		return decryptedMessage;
	}
}
