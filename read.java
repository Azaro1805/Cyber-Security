import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class read {

	public static void main(String[] args) throws IOException {
		File CipherText_Key2  = new File ("src/CipherText_Key.txt") ;
		String text2 = Files.readString(Paths.get(CipherText_Key2.getAbsolutePath()));
		System.out.println(text2);
		
		
	}

}
