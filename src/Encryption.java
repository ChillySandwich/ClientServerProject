public class Encryption {

	
	char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z', 
			'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z', ' ', ':'};

	public Encryption() {
	}



	public String convert(String sentence) {
		
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < sentence.length(); i++) {
			
			if (sentence.charAt(i) == ' ') {
				sb.append(' ');
			}
			
			else {
				int newLetter = sentence.charAt(i) + 6;
				sb.append((char)newLetter);
			}
				
		}
		String encryptedMessage = sb.toString();
		return encryptedMessage;
	}
	
	private void StringBuilder(String s) {
		// TODO Auto-generated method stub
	}

//	public void bruteForce(String sentence) {
//		Decryption d = new Decryption();
//		for (int i = 0; i < alphabet.length; i++) {
//			System.out.println(d.decrypt(sentence, i));	
//			System.out.print("Key " + i + " ");
//		}
	}
