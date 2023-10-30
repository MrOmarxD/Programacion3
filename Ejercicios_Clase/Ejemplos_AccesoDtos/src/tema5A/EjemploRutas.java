package tema5A;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EjemploRutas {

	public static void main(String[] args) {
		/*// Devuelve la ruta actual
		Path ruta = Paths.get("imagenes");
		//Path ruta = Paths.get("/imagenes"); --No funciona; esto haria algo asi c:/imagenes
		
		System.out.println(ruta); //en este caso no pasa nada
		System.out.println(ruta.toAbsolutePath()); //ruta absoluta
		System.out.println(ruta.toAbsolutePath().getRoot()); //ruta raiz
		System.out.println(ruta.toAbsolutePath().getFileName()); //Devulve la parte final
		*/
		/*
		Path ruta = Paths.get("imagenes/prueba.txt");
		try(BufferedReader reader = new BufferedReader(new FileReader(ruta.toFile()))){
			String line = null;
			while((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		class MiFiltro implements FileFilter{

			@Override
			public boolean accept(File pathname) {
				return pathname.getName().startsWith(".c");
			}
			
		}
		Path ruta = Paths.get("");
		for(File f : ruta.toAbsolutePath().toFile().listFiles(new MiFiltro())) {
			System.out.println(f);
		}

	}
}
