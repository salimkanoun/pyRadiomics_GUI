/**
Copyright (C) 2017 KANOUN Salim
This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public v.3 License as published by
the Free Software Foundation;
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License along
with this program; if not, write to the Free Software Foundation, Inc.,
51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class Radiomics {
	List<String> labels=new ArrayList<String>();

	/**
	 * constructor with awaiting labels
	 */
	public Radiomics(HashMap<String, Boolean> imageType) {
		populateLabel(imageType);
	}
	
	private void populateLabel(HashMap<String, Boolean> imageType) {
		
		String[] radiomicsResults={
				"original_firstorder_10Percentile",
				"original_firstorder_90Percentile",
				"original_firstorder_Energy",
				"original_firstorder_Entropy",
				"original_firstorder_InterquartileRange",
				"original_firstorder_Kurtosis",
				"original_firstorder_Maximum",
				"original_firstorder_Mean",
				"original_firstorder_MeanAbsoluteDeviation",
				"original_firstorder_Median",
				"original_firstorder_Minimum",
				"original_firstorder_Range",
				"original_firstorder_RobustMeanAbsoluteDeviation",
				"original_firstorder_RootMeanSquared",
				"original_firstorder_Skewness",
				"original_firstorder_StandardDeviation",
				"original_firstorder_TotalEnergy",
				"original_firstorder_Uniformity",
				"original_firstorder_Variance",
				"original_glcm_Autocorrelation",
				"original_glcm_ClusterProminence",
				"original_glcm_ClusterShade",
				"original_glcm_ClusterTendency",
				"original_glcm_Contrast",
				"original_glcm_Correlation",
				"original_glcm_DifferenceAverage",
				"original_glcm_DifferenceEntropy",
				"original_glcm_DifferenceVariance",
				"original_glcm_Dissimilarity",
				"original_glcm_Homogeneity1",
				"original_glcm_Homogeneity2",
				"original_glcm_Id",
				"original_glcm_Idm",
				"original_glcm_Idmn",
				"original_glcm_Idn",
				"original_glcm_Imc1",
				"original_glcm_Imc2",
				"original_glcm_InverseVariance",
				"original_glcm_JointAverage",
				"original_glcm_JointEnergy",
				"original_glcm_JointEntropy",
				"original_glcm_MaximumProbability",
				"original_glcm_SumAverage",
				"original_glcm_SumEntropy",
				"original_glcm_SumSquares",
				"original_gldm_DependenceEntropy",
				"original_gldm_DependenceNonUniformity",
				"original_gldm_DependenceNonUniformityNormalized",
				"original_gldm_DependenceVariance",
				"original_gldm_GrayLevelNonUniformity",
				"original_gldm_GrayLevelNonUniformityNormalized",
				"original_gldm_GrayLevelVariance",
				"original_gldm_HighGrayLevelEmphasis",
				"original_gldm_LargeDependenceEmphasis",
				"original_gldm_LargeDependenceHighGrayLevelEmphasis",
				"original_gldm_LargeDependenceLowGrayLevelEmphasis",
				"original_gldm_LowGrayLevelEmphasis",
				"original_gldm_SmallDependenceEmphasis",
				"original_gldm_SmallDependenceHighGrayLevelEmphasis",
				"original_gldm_SmallDependenceLowGrayLevelEmphasis",
				"original_glrlm_GrayLevelNonUniformity",
				"original_glrlm_GrayLevelNonUniformityNormalized",
				"original_glrlm_GrayLevelVariance",
				"original_glrlm_HighGrayLevelRunEmphasis",
				"original_glrlm_LongRunEmphasis",
				"original_glrlm_LongRunHighGrayLevelEmphasis",
				"original_glrlm_LongRunLowGrayLevelEmphasis",
				"original_glrlm_LowGrayLevelRunEmphasis",
				"original_glrlm_RunEntropy",
				"original_glrlm_RunLengthNonUniformity",
				"original_glrlm_RunLengthNonUniformityNormalized",
				"original_glrlm_RunPercentage",
				"original_glrlm_RunVariance",
				"original_glrlm_ShortRunEmphasis",
				"original_glrlm_ShortRunHighGrayLevelEmphasis",
				"original_glrlm_ShortRunLowGrayLevelEmphasis",
				"original_glszm_GrayLevelNonUniformity",
				"original_glszm_GrayLevelNonUniformityNormalized",
				"original_glszm_GrayLevelVariance",
				"original_glszm_HighGrayLevelZoneEmphasis",
				"original_glszm_LargeAreaEmphasis",
				"original_glszm_LargeAreaHighGrayLevelEmphasis",
				"original_glszm_LargeAreaLowGrayLevelEmphasis",
				"original_glszm_LowGrayLevelZoneEmphasis",
				"original_glszm_SizeZoneNonUniformity",
				"original_glszm_SizeZoneNonUniformityNormalized",
				"original_glszm_SmallAreaEmphasis",
				"original_glszm_SmallAreaHighGrayLevelEmphasis",
				"original_glszm_SmallAreaLowGrayLevelEmphasis",
				"original_glszm_ZoneEntropy",
				"original_glszm_ZonePercentage",
				"original_glszm_ZoneVariance",
				"original_ngtdm_Busyness",
				"original_ngtdm_Coarseness",
				"original_ngtdm_Complexity",
				"original_ngtdm_Contrast",
				"original_ngtdm_Strength"};
		
		String[] radiomicsShapeResults=	{"original_shape_Compactness1",
				"original_shape_Compactness2",
				"original_shape_Elongation",
				"original_shape_Flatness",
				"original_shape_LeastAxis",
				"original_shape_MajorAxis",
				"original_shape_Maximum2DDiameterColumn",
				"original_shape_Maximum2DDiameterRow",
				"original_shape_Maximum2DDiameterSlice",
				"original_shape_Maximum3DDiameter",
				"original_shape_MinorAxis",
				"original_shape_SphericalDisproportion",
				"original_shape_Sphericity",
				"original_shape_SurfaceArea",
				"original_shape_SurfaceVolumeRatio",
				"original_shape_Volume"};
		
		String[] radiomicsGeneralResults=	{"general_info_MaskHash",
				"general_info_GeneralSettings",
				"general_info_VoxelNum",
				"general_info_Version",
				"general_info_ImageSpacing",
				"general_info_BoundingBox",
				"general_info_ImageHash",
				"general_info_VolumeNum",
				"general_info_EnabledImageTypes"};
				
		
		List<String> labelsOriginal=new ArrayList<String>();
		List<String> labelsWavelet=new ArrayList<String>();
		List<String> labelsSquareRoot=new ArrayList<String>();
		List<String> labelsExponential=new ArrayList<String>();
		List<String> labelsLoG=new ArrayList<String>();
		List<String> labelsSquare=new ArrayList<String>();
		List<String> labelsLogarithm=new ArrayList<String>();
		
		for (int i=0; i<radiomicsResults.length; i++) {
			String original=radiomicsResults[i];
			String newlabelWavelet = radiomicsResults[i].replace("original", "wavelet");
			String newlabelSquareRoot = radiomicsResults[i].replace("original", "squareroot");
			String newlabelExponential = radiomicsResults[i].replace("original", "exponential");
			String newlabelLoG = radiomicsResults[i].replace("original", "log");
			String newlabelSquare = radiomicsResults[i].replace("original", "square");
			String newlabelLogarithm = radiomicsResults[i].replace("original", "logarithm");
			
			labelsOriginal.add(original);
			labelsWavelet.add(newlabelWavelet);
			labelsSquareRoot.add(newlabelSquareRoot);
			labelsExponential.add(newlabelExponential);
			labelsLoG.add(newlabelLoG);
			labelsSquare.add(newlabelSquare);
			labelsLogarithm.add(newlabelLogarithm);
		}
		
		if (imageType.get("typeOriginal")) labels.addAll(labelsOriginal);;
		if (imageType.get("typeLoG")) labels.addAll(labelsLoG);
		if (imageType.get("typeWavelet")) labels.addAll(labelsWavelet);;
		if (imageType.get("typeSquare")) labels.addAll(labelsSquare);
		if (imageType.get("typeSquareRoot")) labels.addAll(labelsSquareRoot);
		if (imageType.get("typeLogarithm")) labels.addAll(labelsLogarithm);
		if (imageType.get("typeExponential")) labels.addAll(labelsExponential);
		
		//Add Shape non dependent of filtering
		for (int i=0; i<radiomicsShapeResults.length; i++) {
			labels.add(radiomicsShapeResults[i]);
		}
		for (int i=0; i<radiomicsGeneralResults.length; i++) {
			labels.add(radiomicsGeneralResults[i]);
		}
		
		
	}
	
	
	/**
	 * Send pyRadiomics Requests and return result as JSONObject
	 * @param image
	 * @param mask
	 * @param settingsYaml
	 * @return
	 * @throws IOException
	 */
	public JsonObject sendPyRadiomics(String image, String mask, File settingsYaml) throws IOException {
		String json;
		
		ProcessBuilder pb = new ProcessBuilder("pyradiomics", image , mask ,"--format", "json", "--param", settingsYaml.getAbsolutePath().toString());
		pb.redirectErrorStream(true); 
        Process process = pb.start();  
        //OutputStream stdin = process.getOutputStream(); 
        //InputStream stderr = process.getErrorStream(); 
        InputStream stdout = process.getInputStream();  
		 
        BufferedReader reader = new BufferedReader (new InputStreamReader(stdout));

		 StringBuilder builder = new StringBuilder();
		 String line = null;
		 JsonObject resultsJson=null;
		 while ( (line = reader.readLine()) != null) {
			 	//If JSON Object parse it
			 	if (line.startsWith("{")){
			 		json=line;
			 		json=json.replace("NaN", "\"NaN\"");
			 		resultsJson=readJson(json);
			 	}
			 	//if not add to string builder and log it
			 	else{
			 		builder.append(line);
			 		builder.append(System.getProperty("line.separator"));
			 	}
			 	
		 }
		 
		 System.out.println(builder);
		 System.out.println(resultsJson.toString());
		 
		 return resultsJson;
	}
	/**
	 * Test if pyRadiomics is reachable in the OS.
	 */
	public static void testPyRadiomics()  {
		ProcessBuilder pb = new ProcessBuilder("pyradiomics" ,"--version");
		pb.redirectErrorStream(true); 
		BufferedReader reader = null;
        Process process;
		try {
			process = pb.start();
			InputStream stdout = process.getInputStream();
			reader = new BufferedReader (new InputStreamReader(stdout));
		} catch (IOException e) {
			e.printStackTrace(); 
			JOptionPane.showMessageDialog(null,
				    "pyRadiomics is not found, install it on your system. \nVisit pyRadiomics.io or petctviewer.org",
				    "Not Found",
				    JOptionPane.ERROR_MESSAGE);
		}  
  
		StringBuilder builder = new StringBuilder();
		String line = null;
		 try {
			while ( (line = reader.readLine()) != null) {
				 		builder.append(line);
				 		builder.append(System.getProperty("line.separator"));
				 	
			 }
		} catch (IOException e) {
			e.printStackTrace();
		}
		 JOptionPane.showMessageDialog(null,
				    "pyRadiomics OK "+builder.toString());
	}
	
	/**
	 * Parse JsonString to JsonObject
	 * @param json
	 * @return
	 */
	private JsonObject readJson(String json) {
		JsonObject resultsJson=null; 
		
		JsonParser parser = new JsonParser();
				
		resultsJson= parser.parse(json).getAsJsonObject();
		
		 return resultsJson;
	}

	/**
	 * Add column Title to CSV that will be generated
	 * @param csv
	 */
	protected void addColumnheader(StringBuilder csv){
		//Add Roi Column Title
		csv.append("ROI number,");
		for (int i=0; i<labels.size(); i++) {
			csv.append(labels.get(i)+",");
		}
		csv.append("\n");
	}
	/**
	 * Write JSON response into CSV format
	 * @param csv
	 * @param resultsJson
	 * @param roiNumber
	 */
	public void jsonToCsv(StringBuilder csv, JsonObject resultsJson, int roiNumber) {
		//On ajoute le numero de la ROI
		csv.append(roiNumber+",");
		//On ecrit les resultats
		for (int i=0; i<labels.size(); i++) {
			if (resultsJson.has(labels.get(i))){
				String value=resultsJson.get(labels.get(i)).toString();
				//replace comma by semicolon to avoid breaking CSV structure
				value=value.replaceAll(",", "|");
				//Append comma to separate results in CSV
				csv.append(value+",");
			}
			else csv.append(",");
			
		}
		csv.append("\n");
	}
	
	/**
	 * Write the Finale CSV file
	 * @param csv
	 * @param path
	 */
	protected void writeCSV(StringBuilder csv, String path) {
		System.out.println(csv);
		
		File f = new File(path);

		// On ecrit les CSV
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(f);
			pw.write(csv.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.close();
		}
	}
	
	/**
	 * Write the config file in a temp file for pyradiomics processing for each request
	 * @throws IOException 
	 */
	protected File writeYaml(boolean discretize, double binWidth, boolean validate,  int minimumROIDimensions, int minimumROISize, double geometryTolerance, boolean correctMask, int label, boolean normalize, double normalizeScale, double removeOutliers, boolean resample, double[] pixelSpacing, String interpolator, int padDistance, boolean force2D, int force2DDimensions, boolean distance, String distancesValues, boolean resegment, double min, double max, boolean preCrop, String sigma, int startLevelWavelet, int levelWavelet, String stringWavelet, int voxelArrayShift, boolean symmetricalGLCM, String weightingNorm, double gldmAlfa, HashMap<String, Boolean> imageType, HashMap<String,Boolean> features) throws IOException{
	
	//Prepare resegment string
	String resegmentRange=null;
	if (resegment) resegmentRange="["+min+","+max+"]";
	
	//Prepare feature string
	StringBuilder featuresString=new StringBuilder();
	if (features.size()!=0){
		if (features.get("First Order")== true) featuresString.append("  firstorder:\n");
		if (features.get("Shape")== true) featuresString.append("  shape:\n");
		if (features.get("GLCM")==true) featuresString.append("  glcm:\n");
		if (features.get("GLRLM")==true) featuresString.append("  glrlm:\n");
		if (features.get("GLSZM")==true) featuresString.append("  glszm:\n");
		if (features.get("NGTDM")==true) featuresString.append("  ngtdm:\n");
	}
	
 		
	String settingsYaml= "\nsetting:\n"
			+ "  enableCExtensions: true"+"\n";
	
	if (validate) {
		if (minimumROIDimensions!=0) settingsYaml+="  minimumROIDimensions: "+String.valueOf(minimumROIDimensions)+"\n";
		if (minimumROISize!=0)	settingsYaml+="  minimumROISize: "+String.valueOf(minimumROISize)+"\n";
		if (geometryTolerance!=0) settingsYaml+="  geometryTolerance: "+geometryTolerance+"\n";
		settingsYaml+= "  correctMask: "+String.valueOf(correctMask)+"\n";
	}
			
	settingsYaml += "  additionalInfo: true"+"\n"
			+ "  label: "+ label+"\n";
	
	if (discretize) settingsYaml += "  binWidth: "+binWidth+"\n";
	
	if (normalize) {
		settingsYaml+= "  normalize: true"+"\n";
		if (normalizeScale!=0)	settingsYaml += "  normalizeScale: "+normalizeScale+"\n";
		if (removeOutliers!=0)	settingsYaml += "  removeOutliers: "+removeOutliers+"\n";
	}
	
	if (resample) {
		if ((pixelSpacing[0]+pixelSpacing[1]+pixelSpacing[2])!=0)settingsYaml += "  resampledPixelSpacing: "+"[" + String.valueOf(pixelSpacing[0])+ ","+String.valueOf(pixelSpacing[1])+","+String.valueOf(pixelSpacing[2])+"]"+"\n";
		else settingsYaml +="  resampledPixelSpacing: None"+"\n";
		settingsYaml += "  interpolator: "+interpolator+"\n";
		settingsYaml += "  padDistance: "+String.valueOf(padDistance)+"\n";
	}
	
	if (distance) settingsYaml +="  distances: "+distancesValues+"\n";
	if (force2D) {
		settingsYaml +="  force2D: true"+"\n"
		+ "  force2Ddimension: "+String.valueOf(force2DDimensions)+"\n";
	}
	
	if (resegment) settingsYaml +="  resegmentRange: "+resegmentRange+"\n";
	//A TESTER LE PRECROP QUE POUR FILTRE
	if (imageType.get("typeLoG")|| imageType.get("typeWavelet")||imageType.get("typeSquare")||imageType.get("typeSquareRoot")||imageType.get("typeLogarithm")||imageType.get("typeExponential") )settingsYaml +="  #preCrop: "+String.valueOf(preCrop)+"\n";
			
	if (voxelArrayShift!=0) settingsYaml +="  voxelArrayShift: "+voxelArrayShift+"\n";
	
	settingsYaml +=	"  symmetricalGLCM: "+String.valueOf(symmetricalGLCM)+"\n";
	
	if (!weightingNorm.equals("None"))	settingsYaml += "  weightingNorm: "+weightingNorm+"\n";
	
	if (gldmAlfa!=0)	settingsYaml +="  gldm_a: "+String.valueOf(gldmAlfa)+"\n";
	
	settingsYaml += "\n\n"
			+ "imageType:\n";
	//LOG ET WAVELET A TESTER
	if (imageType.get("typeOriginal")) settingsYaml += "  Original: {}"+"\n";
	if (imageType.get("typeLoG")) settingsYaml += "  LoG: {'sigma' : ["+sigma+"]}" + "\n";
	if (imageType.get("typeWavelet")) settingsYaml += "  Wavelet: {'start_level' : ["+String.valueOf(startLevelWavelet)+"], 'level' : ["+String.valueOf(levelWavelet)+"], 'wavelet' : [\""+stringWavelet+"\"] }"+"\n";
	if (imageType.get("typeSquare")) settingsYaml += "  Square: {}"+"\n";
	if (imageType.get("typeSquareRoot")) settingsYaml += "  SquareRoot: {}"+"\n";
	if (imageType.get("typeLogarithm")) settingsYaml += "  Logarithm: {}"+"\n";
	if (imageType.get("typeExponential")) settingsYaml += "  Exponential: {}"+"\n";
	if (featuresString.length()!=0) settingsYaml+="featureClass:\n"+ featuresString+"\n";
	
	System.out.println(settingsYaml);
	
	return writeYaml(settingsYaml);
	}
	
	protected File writeYaml(int label, boolean resegment, double min, double max) throws IOException{
	//Prepare resegment string
	String resegmentRange=null;
	if (resegment) resegmentRange="["+min+","+max+"]"; 
		
		String settingsYaml= "\nsetting:\n"
			+ "  label: "+ label+"\n"
			+ "  #interpolator: 'sitkBSpline' \n"
			+ "  resampledPixelSpacing: \n"
			+ "  weightingNorm: \n\n"
			+ "  resegmentRange: "+resegmentRange+"\n"
			+ "imageType:\n"
			+ "  Original: {} \n\n"
			+ "featureClass:\n"
			+ "  firstorder: []\n";
	
	return writeYaml(settingsYaml);
	}
	
	/**
	 * Make YAML with label only, and use all default options
	 * @param label
	 * @return
	 * @throws IOException
	 */
	protected File writeYaml(int label) throws IOException{

			String settingsYaml= "\nsetting:\n"
				+ "  label: "+ label+"\n"
				+ "imageType:\n"
				+ "  Original: {} \n\n";
		System.out.println(settingsYaml);
		return writeYaml(settingsYaml);
		}
	
	
	/**
	 * Write Yaml config file to a temporary file (will be deleted on exit of JVM)
	 * @param settingsYaml
	 * @return
	 * @throws IOException
	 */
	private File writeYaml(String settingsYaml) throws IOException{
		PrintWriter pw = null;
		File temp = File.createTempFile("settings", ".yaml");
		temp.deleteOnExit();
		try {
			pw = new PrintWriter(temp);
			pw.write(settingsYaml);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.close();
		}
		return temp;
	}
	
	/**
	 * To read a YAML File, not needed anymore
	 * @param settingsFileYaml
	 * @return
	 */
	@Deprecated
	protected String getYamlSettings(File settingsFileYaml) {
		StringBuilder sb = new StringBuilder();
		
		try(BufferedReader br = new BufferedReader(new FileReader(settingsFileYaml))) {
		    String line = br.readLine();
		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
