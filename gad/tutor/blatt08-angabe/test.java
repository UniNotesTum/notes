import java.util.Optional;

class test {

	public static void main(String[] args) {

		if (!Optional.of(null).isPresent()) System.out.println("fine");
		
	}
	
}
