package br.unb.cic.rtgoretoprism.generator.goda.writer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import br.unb.cic.rtgoretoprism.console.ATCConsole;
import br.unb.cic.rtgoretoprism.generator.CodeGenerationException;
import br.unb.cic.rtgoretoprism.util.FileUtility;

public class ManageWriter {

	
	public static PrintWriter createFile(String adf, String outputFolder) throws CodeGenerationException {
		
		deleteOldFile(adf, outputFolder);
		
		try {
			PrintWriter adfFile = new PrintWriter( 
					new BufferedWriter(	new FileWriter( outputFolder + adf ) ) );
			
			return adfFile;
		} catch (IOException e) {
			String msg = "Error: Can't create output model file.";
			ATCConsole.println( msg );
			throw new CodeGenerationException( msg );
		}
	}
	
	private static void deleteOldFile(String adf, String outputFolder) throws CodeGenerationException {
	
		String filePath = outputFolder + adf;
		Path path = Paths.get(filePath);
	
		ManageWriter.deleteFile(path);
	}

	public static String readFileAsString( String filePath ) throws CodeGenerationException {
		String res = null;
		
		try {
			res = FileUtility.readFileAsString( filePath );			
		} catch (IOException e) {
			String msg = "Error: file " + filePath + " not found.";
			ATCConsole.println( msg );
			throw new CodeGenerationException( msg );
		}
		
		return res;
	}
	
	public static void deleteFile(Path path) throws CodeGenerationException {	
		try {
		    Files.deleteIfExists(path);
		}
		catch (NoSuchFileException x) {
			String msg = path + " no such file or directory.";
			ATCConsole.println( msg );
			throw new CodeGenerationException( msg );
		}
		catch (DirectoryNotEmptyException x) {
			String msg = path + " not empty.";
			ATCConsole.println( msg );
			throw new CodeGenerationException( msg );
		}
		catch (IOException x) {
			String msg = "File permission problems are caught here: " + path;
			ATCConsole.println( msg );
			throw new CodeGenerationException( msg );
		}
	}
	
	public static void printModel(PrintWriter adf, String module) {

		adf.println(module);
		adf.close();
	}
}
