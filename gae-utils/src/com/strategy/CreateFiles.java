package com.strategy;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * TODO instead of String list , perhaps should use a Map instead, greater speed in accessing keys.....
 * 
 * @author Adrian
 *
 */

public class CreateFiles {
	
	private Set<String> filmNames;
	private Map<String, String> filmYearMap;
	
	public CreateFiles(Set<String> filmNames , Map<String, String> filmYearMap){
		this.filmNames = filmNames;
		this.filmYearMap = filmYearMap;
	}
	
	public void generateFiles(){
		int lineCounter = 0;
		int fileCounter = 0;

		try {
			
		List<String> lines = new ArrayList<String>();

		
		BufferedWriter out1 = new BufferedWriter(new FileWriter("d:/tmp/Movie.java"));
		out1.write("package com.movie.values;");
		out1.newLine();
		out1.write("import java.util.ArrayList;");
		out1.newLine();
		out1.write("import java.util.List;");
		out1.newLine();
		out1.write("public interface Movie {");
		out1.write("public List<String> getMovieList();");
		out1.newLine();
		out1.write("}");
		out1.close();
		
		BufferedWriter out2 = new BufferedWriter(new FileWriter("d:/tmp/MovieDetails.java"));
		out2.write("package com.movie.values;");
		out2.newLine();
		out2.write("import java.util.ArrayList;");
		out2.newLine();
		out2.write("import java.util.List;");
		out2.newLine();
		out2.write("public class MovieDetails {");
		out2.write("public static List<Movie> movieDetailsList = new ArrayList<Movie>();");
		out2.write("static {");
		
		for (String filmName : filmNames) {
			System.out.println(filmName + "," + filmYearMap.get(filmName));

			/**
			 * This line will be amended each time a new strategy is added
			 * e.g-  String line = filmName + "," + filmYearMap.get(filmName) +"," +filmActorMap.get(filmName) 
			 */
			String line = filmName + "," + filmYearMap.get(filmName);

			line = line.replace("\\", "");
			line = line.replace("\"", "\\\"");

			//line = line.replaceAll("\\", "");
			lines.add(line);
		
			++lineCounter;
			if (lineCounter == 5000) {
				System.out.println("Creating file d:/tmp/MovieImpl" + fileCounter+ ".java");

				out2.newLine();
				out2.write("movieDetailsList.add(new MovieImpl" + fileCounter+"());");
				
					BufferedWriter out = new BufferedWriter(new FileWriter("d:/tmp/MovieImpl" + fileCounter + ".java"));

					out.write("package com.movie.values;");
					out.newLine();
					out.write("import java.util.ArrayList;");
					out.newLine();
					out.write("import java.util.List;");
					out.newLine();
					out.write("public class MovieImpl" + fileCounter + " implements Movie {");
					out.newLine();
					out.write("public static List<String> movieList = new ArrayList<String>();");
					out.newLine();
					out.write("static {");
					out.newLine();

					for (String fileLine : lines) {

						out.write("movieList.add(\"" + fileLine + "\");");
						out.newLine();
					}
					out.write("}");
					out.newLine();
					out.newLine();
					
					out.write("@Override");
					out.newLine();
					out.write("public List<String> getMovieList() {");
					out.newLine();
					out.write("     return movieList;");
					out.newLine();
					out.write("}");
					out.newLine();
					
					out.write("}");
					out.close();
					++fileCounter;
					lineCounter = 0;
					lines = new ArrayList<String>();
				} 
			}
		
		
		out2.write("}");
		out2.newLine();
		out2.write("}");
		out2.close();
		}
		
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createAccessorClass(){
		
	}
}
