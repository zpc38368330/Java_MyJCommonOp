package MyJ_CommonLib;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.springframework.web.method.annotation.InitBinderDataBinderFactory;

public class MyJ_Ini4jOp {
	public void loadIni(String pathfileName)
	{
		
	}
	public void example() throws InvalidFileFormatException, FileNotFoundException, IOException
	{
		Ini ini=new Ini();
		ini.load(new FileReader("filename"));
        Ini.Section section = ini.get("happy");

        //
        // read some values
        //
        String age = section.get("age");
        String weight = section.get("weight");
        String homeDir = section.get("homeDir");

        //
        // .. or just use java.util.Map interface...
        //
        Map<String, String> map = ini.get("happy");

        age = map.get("age");
        weight = map.get("weight");
        homeDir = map.get("homeDir");

        // get all section names
        Set<String> sectionNames = ini.keySet();
	}
}
