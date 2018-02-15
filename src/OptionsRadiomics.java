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

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.JSpinner;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

@SuppressWarnings("serial")
public class OptionsRadiomics extends JDialog {
	
	private JTextField txtMin,txtMax, textField, txtNormalizeScale, txtRemoveOutliners, txtX, txtY, txtZ, textField_GeometryTolerance, txt_setDistances,txt_setAlfa,txtWaveletTxt ;
	private JCheckBox chckbxFixedBinWidth, chckbxFixedNumberOf_1, chckbxResgmentationvalueLimits, chckbxFirstOrder, chckbxShape,chckbxGlcm,chckbxGlrlm,chckbxGlszm,chckbxNgtdm,chckbxGtdm, checkBoxNormalize,chckbxCorrectMask,
	chckbxResampleImage, chckbxValidateMask, chckbxForcedExtraction, chckbxPrecropping, chckbxSymetricalGlcm, chckbxSetDistances, chckbxDiscretize;
	private JSpinner spinner_Dimension2D, spinnerBinFixed, spinner_padDistance, spinner_minRoiDimension, spinner_minRoiSize, spinner_VoxelArrayShift,
	spinner_startLevelWavelet,spinner_Wavelet_Level;
	List<JCheckBox> imageType = new ArrayList<JCheckBox>();
	private Double fixedBinWidth, min, max;
	private int numberOfBin;
	private File settingsFile=null;
	private JComboBox<String> comboBox_Interpolator, comboBox_Weighting;
	private JTextField txtGaussianSigma;
	private boolean ok=false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			OptionsRadiomics dialog = new OptionsRadiomics();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public OptionsRadiomics() {
		this.setTitle("pyRadiomics Options");
		getContentPane().setLayout(new BorderLayout());
		
		JPanel buttonPane = new JPanel();
		buttonPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		buttonPane.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel_4 = new JPanel();
		buttonPane.add(panel_4);
		
		JLabel lblDocumentationOnHttpwwwradiomicsio = new JLabel("Documentation : http://www.radiomics.io/");
		panel_4.add(lblDocumentationOnHttpwwwradiomicsio);
		lblDocumentationOnHttpwwwradiomicsio.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panel_5 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_5.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		buttonPane.add(panel_5);
		
		JButton btnTestPyradiomics = new JButton("Test pyRadiomics");
		panel_5.add(btnTestPyradiomics);
		
		JButton btnSetSettingsFile = new JButton("Set Settings File");
		panel_5.add(btnSetSettingsFile);
		
		JButton okButton = new JButton("OK");
		panel_5.add(okButton);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ok=true;
				dispose();
			}
		});
		
		okButton.setActionCommand("OK");
		getRootPane().setDefaultButton(okButton);
		
				JButton cancelButton = new JButton("Cancel");
				panel_5.add(cancelButton);
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
		btnSetSettingsFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Warning override message
				JOptionPane.showMessageDialog(null,
				    "Setting a Yaml file will overide your personnal settings",
				    "YAML File",
				    JOptionPane.WARNING_MESSAGE);
				JFileChooser fileChooser=new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("YAML Files", "yaml");
				fileChooser.setFileFilter(filter);
				int ouvrir=fileChooser.showOpenDialog(null);
				if(ouvrir==JFileChooser.APPROVE_OPTION) {
				settingsFile=fileChooser.getSelectedFile();
				btnSetSettingsFile.setBackground(Color.green);
				}
			}
		});
		btnTestPyradiomics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Radiomics.testPyRadiomics();
			}
		});

		JPanel Settings_Panel = new JPanel();
		getContentPane().add(Settings_Panel, BorderLayout.CENTER);
		Settings_Panel.setLayout(new GridLayout(0, 2, 0, 0));
			
			{
			//////////////////////////////////////PANEL IMAGE DISCRETIZATION /////////////////////////////////////////////	
			JPanel Discretization_Panel = new JPanel();
			Discretization_Panel.setBorder(new LineBorder(new Color(0, 0, 0)));
			Settings_Panel.add(Discretization_Panel);
			Discretization_Panel.setLayout(new BorderLayout(0, 0));
			
			JPanel Discretization_Title = new JPanel();
			Discretization_Title.setBorder(new LineBorder(Color.LIGHT_GRAY));
			Discretization_Panel.add(Discretization_Title, BorderLayout.NORTH);
			
			JLabel lblDiscretization = new JLabel("Discretization / Resegmentation");
			Discretization_Title.add(lblDiscretization);
				
			JPanel Discretization_Center = new JPanel();
			Discretization_Panel.add(Discretization_Center, BorderLayout.CENTER);
			Discretization_Center.setLayout(new GridLayout(0, 1, 0, 0));
		
			JPanel panel_3 = new JPanel();
			Discretization_Center.add(panel_3);
			chckbxFixedBinWidth = new JCheckBox("Fixed bin width");
			chckbxFixedBinWidth.setSelected(true);
			panel_3.add(chckbxFixedBinWidth);
				
			textField = new JTextField("25");
			panel_3.add(textField);
			textField.setColumns(5);
				
			JPanel panel_2 = new JPanel();
			Discretization_Center.add(panel_2);
			
			chckbxResgmentationvalueLimits = new JCheckBox("Resgmentation (Value limits)");
			chckbxResgmentationvalueLimits.addChangeListener(new ChangeListener()  {
				public void stateChanged (ChangeEvent e) {
					if (!chckbxFixedNumberOf_1.isSelected()){
						if (chckbxResgmentationvalueLimits.isSelected()) {
						txtMin.setEnabled(true); 
						txtMax.setEnabled(true);
					}
						else if (!chckbxResgmentationvalueLimits.isSelected()) {
						txtMin.setEnabled(false); 
						txtMax.setEnabled(false);	
					}
					}
					
				}
			});
			panel_2.add(chckbxResgmentationvalueLimits);
			
			JLabel lblMin = new JLabel("min");
			panel_2.add(lblMin);

			txtMin = new JTextField();
			txtMin.setEnabled(false);
			panel_2.add(txtMin);
			txtMin.setText("0");
			txtMin.setColumns(5);
			
			JLabel lblMax = new JLabel("max");
			panel_2.add(lblMax);
	
			txtMax = new JTextField();
			txtMax.setEnabled(false);
			panel_2.add(txtMax);
			txtMax.setText("0");
			txtMax.setColumns(5);
	
			JPanel panel_1 = new JPanel();
			Discretization_Center.add(panel_1);
			chckbxFixedNumberOf_1 = new JCheckBox("Fixed bins per ROI");
			panel_1.add(chckbxFixedNumberOf_1);
		
			spinnerBinFixed = new JSpinner();
			spinnerBinFixed.setModel(new SpinnerNumberModel(0, null, 1000, 1));
			spinnerBinFixed.setEnabled(false);
			panel_1.add(spinnerBinFixed);
			
			chckbxDiscretize = new JCheckBox("Discretize");
			chckbxDiscretize.setSelected(true);
			chckbxDiscretize.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					enableDiscretize(chckbxDiscretize.isSelected());
				}
			});
			Discretization_Panel.add(chckbxDiscretize, BorderLayout.WEST);
					
			chckbxFixedNumberOf_1.addChangeListener(new ChangeListener() {
				public void stateChanged (ChangeEvent e) {
					if (chckbxFixedNumberOf_1.isSelected()) {
						chckbxFixedBinWidth.setSelected(false);
						chckbxResgmentationvalueLimits.setSelected(false);
						textField.setEnabled(false);
						txtMin.setEnabled(false);
						txtMax.setEnabled(false);
						spinnerBinFixed.setEnabled(true);
						
					}
					else{
						chckbxFixedBinWidth.setSelected(true);
						textField.setEnabled(true);
						spinnerBinFixed.setEnabled(false);
					}
				}
			});
				
			chckbxFixedBinWidth.addChangeListener(new ChangeListener() {
				public void stateChanged (ChangeEvent e) {
				//Disable fixed bin number
				if (chckbxFixedBinWidth.isSelected()) {
					chckbxFixedNumberOf_1.setSelected(false);
					textField.setEnabled(true);
				}
				else{
					textField.setEnabled(false);
					chckbxFixedNumberOf_1.setSelected(true);
					
				}
				}
			});
			
			}
		
			
			{
			//////////////////////////////////////PANEL IMAGE NORMALIZATION /////////////////////////////////////////////
			JPanel Image_Normalization_panel = new JPanel();
			Image_Normalization_panel.setBorder(new LineBorder(new Color(0, 0, 0)));
			Settings_Panel.add(Image_Normalization_panel);
			Image_Normalization_panel.setLayout(new BorderLayout(0, 0));
			
			JPanel panel_NormalizationTitle = new JPanel();
			panel_NormalizationTitle.setBorder(new LineBorder(Color.LIGHT_GRAY));
			Image_Normalization_panel.add(panel_NormalizationTitle, BorderLayout.NORTH);
			
			JLabel lblImageNormalization = new JLabel("Image Normalization");
			panel_NormalizationTitle.add(lblImageNormalization);

			JPanel panel_Normalization_Settings = new JPanel();
			Image_Normalization_panel.add(panel_Normalization_Settings, BorderLayout.CENTER);
			panel_Normalization_Settings.setLayout(new BorderLayout(0, 0));
			
			checkBoxNormalize = new JCheckBox("Normalize");
			checkBoxNormalize.setToolTipText("Enable normalizing of the image before any resampling.");
			checkBoxNormalize.addChangeListener(new ChangeListener() {
				public void stateChanged (ChangeEvent e) {
					if (checkBoxNormalize.isSelected()) enableNormalize(true);
					else enableNormalize(false);				
				}
			});
			panel_Normalization_Settings.add(checkBoxNormalize, BorderLayout.WEST);

			JPanel panel_Nomalization = new JPanel();
			panel_Normalization_Settings.add(panel_Nomalization, BorderLayout.CENTER);
			panel_Nomalization.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
			JPanel panel_NormalizationElements = new JPanel();
			panel_Nomalization.add(panel_NormalizationElements);
			panel_NormalizationElements.setLayout(new GridLayout(0, 2, 0, 0));
			
			JLabel lblNormalizeScale = new JLabel("Normalize Scale :");
			txtNormalizeScale = new JTextField();
			txtNormalizeScale.setToolTipText("Determines the scale after normalizing the image");
			txtNormalizeScale.setEnabled(false);
			txtNormalizeScale.setText("1.0");
			txtNormalizeScale.setColumns(5);
			JLabel lblRemoveOutliners = new JLabel("Remove Outliers :");
			txtRemoveOutliners = new JTextField();
			txtRemoveOutliners.setToolTipText("Defines the outliers to remove from the image");
			txtRemoveOutliners.setEnabled(false);
			txtRemoveOutliners.setText("0.0");
			txtRemoveOutliners.setColumns(5);
			
			panel_NormalizationElements.add(lblNormalizeScale);
			panel_NormalizationElements.add(txtNormalizeScale);
			panel_NormalizationElements.add(lblRemoveOutliners);
			panel_NormalizationElements.add(txtRemoveOutliners);
			}

			{
			//////////////////////////////////////PANEL IMAGE REASEMBLING /////////////////////////////////////////////
			JPanel panel_ImageResampling = new JPanel();
			panel_ImageResampling.setBorder(new LineBorder(new Color(0, 0, 0)));
			Settings_Panel.add(panel_ImageResampling);
			panel_ImageResampling.setLayout(new BorderLayout(0, 0));
				
			JPanel panel_Resampling_Title = new JPanel();
			panel_Resampling_Title.setBorder(new LineBorder(Color.LIGHT_GRAY));
			panel_ImageResampling.add(panel_Resampling_Title, BorderLayout.NORTH);
					
			JLabel lblImageResampling = new JLabel("Image Resampling");
			panel_Resampling_Title.add(lblImageResampling);
				
			JPanel panel_Resempling_Settings = new JPanel();
			panel_ImageResampling.add(panel_Resempling_Settings, BorderLayout.CENTER);
			panel_Resempling_Settings.setLayout(new BorderLayout(0, 0));
					
			JPanel panel_Resampling = new JPanel();
			panel_Resempling_Settings.add(panel_Resampling, BorderLayout.CENTER);
			panel_Resampling.setLayout(new GridLayout(0, 1, 0, 0));
						
			JPanel panel_pixelSpacing = new JPanel();
			panel_Resampling.add(panel_pixelSpacing);
							
			JLabel lblPixelSpacing = new JLabel("Pixel Spacing");
			panel_pixelSpacing.add(lblPixelSpacing);		
							
			txtX = new JTextField();
			txtX.setEnabled(false);
			txtX.setText("0.0");
			txtX.setColumns(3);			
			txtY = new JTextField();
			txtY.setEnabled(false);
			txtY.setText("0.0");
			txtY.setColumns(3);				
			txtZ = new JTextField();
			txtZ.setEnabled(false);
			txtZ.setText("0.0");
			txtZ.setColumns(3);
			
			JLabel lblX = new JLabel("X");
			panel_pixelSpacing.add(lblX);
			
			panel_pixelSpacing.add(txtX);
			
			JLabel lblY = new JLabel("Y");
			panel_pixelSpacing.add(lblY);
			panel_pixelSpacing.add(txtY);
			
			JLabel lblZ = new JLabel("Z");
			panel_pixelSpacing.add(lblZ);
			panel_pixelSpacing.add(txtZ);
	
			JPanel panel_Interpolator = new JPanel();
			panel_Resampling.add(panel_Interpolator);
			
			JLabel lblInterpolator = new JLabel("Interpolator");
			panel_Interpolator.add(lblInterpolator);
			
			comboBox_Interpolator = new JComboBox<String>();
			comboBox_Interpolator.setEnabled(false);
			panel_Interpolator.add(comboBox_Interpolator);
			comboBox_Interpolator.setModel(new DefaultComboBoxModel<String>(new String[] {"sitkNearestNeighbor", "sitkLinear", "sitkBSpline", "sitkGaussian", "sitkLabelGaussian", "sitkHammingWindowedSinc", "sitkCosineWindowedSinc", "sitkWelchWindowedSinc", "sitkLanczosWindowedSinc", "sitkBlackmanWindowedSinc"}));
			comboBox_Interpolator.setSelectedIndex(2);

			JLabel lbl_PadDistance = new JLabel("Pad Distance");
			panel_Interpolator.add(lbl_PadDistance);

			spinner_padDistance = new JSpinner();
			spinner_padDistance.setToolTipText("Set the number of voxels pad cropped tumor volume with during resampling");
			spinner_padDistance.setEnabled(false);
			spinner_padDistance.setModel(new SpinnerNumberModel(new Integer(5), new Integer(0), null, new Integer(1)));
			panel_Interpolator.add(spinner_padDistance);

			chckbxResampleImage = new JCheckBox("Resample Image");
			chckbxResampleImage.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					if (chckbxResampleImage.isSelected()) enableResample(true);
					else enableResample(false);
				}
			});
			panel_Resempling_Settings.add(chckbxResampleImage, BorderLayout.WEST);

			}

			{
			//////////////////////////////////////PANEL MASK VALIDATION /////////////////////////////////////////////
			JPanel panel_MaskValidation = new JPanel();
			panel_MaskValidation.setBorder(new LineBorder(new Color(0, 0, 0)));
			Settings_Panel.add(panel_MaskValidation);
			panel_MaskValidation.setLayout(new BorderLayout(0, 0));
			
			JPanel panel_MaskValidation_Title = new JPanel();
			panel_MaskValidation_Title.setBorder(new LineBorder(Color.LIGHT_GRAY));
			panel_MaskValidation.add(panel_MaskValidation_Title, BorderLayout.NORTH);
			
			JLabel lblMaskValidation = new JLabel("Mask Validation");
			panel_MaskValidation_Title.add(lblMaskValidation);

			chckbxValidateMask = new JCheckBox("Validate Mask");
			chckbxValidateMask.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					if (chckbxValidateMask.isSelected()) enableMaskValidate(true);
					else enableMaskValidate(false);
				}
			});
				
			panel_MaskValidation.add(chckbxValidateMask, BorderLayout.WEST);
		
		
			JPanel panel_MaskValidation_Settings = new JPanel();
			panel_MaskValidation.add(panel_MaskValidation_Settings, BorderLayout.CENTER);
			
			JPanel panel_ValidationElements = new JPanel();
			panel_MaskValidation_Settings.add(panel_ValidationElements);
			panel_ValidationElements.setLayout(new GridLayout(0, 2, 0, 0));
			
			JLabel lblMinimumRoiDimension = new JLabel("Minimum ROI dimensions");
			spinner_minRoiDimension = new JSpinner();
			spinner_minRoiDimension.setEnabled(false);
			spinner_minRoiDimension.setToolTipText("Number of needed dimensions in the max (1 to 3)");
			spinner_minRoiDimension.setModel(new SpinnerNumberModel(1, 1, 3, 1));
			JLabel lblMinimumRoiSize = new JLabel("Minimum ROI Size");
			spinner_minRoiSize = new JSpinner();
			spinner_minRoiSize.setEnabled(false);
			spinner_minRoiSize.setToolTipText("minimum of Voxels needed in a ROI");
			JLabel lblGeometryTolerance = new JLabel("Geometry Tolerance");
			textField_GeometryTolerance = new JTextField();
			textField_GeometryTolerance.setToolTipText("Determines the tolarance used by SimpleITK to compare origin, direction and spacing between image and mask");
			textField_GeometryTolerance.setEnabled(false);
			textField_GeometryTolerance.setText("0.0");
			textField_GeometryTolerance.setColumns(10);
			chckbxCorrectMask = new JCheckBox("Correct Mask");
			chckbxCorrectMask.setToolTipText("PyRadiomics will attempt to resample the mask to the image geometry");
			chckbxCorrectMask.setEnabled(false);
			
			panel_ValidationElements.add(lblMinimumRoiDimension);
			panel_ValidationElements.add(spinner_minRoiDimension);
			panel_ValidationElements.add(lblMinimumRoiSize);
			panel_ValidationElements.add(spinner_minRoiSize);
			panel_ValidationElements.add(lblGeometryTolerance);
			panel_ValidationElements.add(textField_GeometryTolerance);
			panel_ValidationElements.add(chckbxCorrectMask);
			}
			
						
			{
			//////////////////////////////////////PANEL OTHER SETTINGS /////////////////////////////////////////////
			JPanel Other_Panel = new JPanel();
			Other_Panel.setBorder(new LineBorder(new Color(0, 0, 0)));
			Settings_Panel.add(Other_Panel);
			Other_Panel.setLayout(new BorderLayout(0, 0));
			
			JPanel Other_Title = new JPanel();
			Other_Title.setBorder(new LineBorder(Color.LIGHT_GRAY));
			Other_Panel.add(Other_Title, BorderLayout.NORTH);
			
			JLabel lblOthers = new JLabel("Other");
			Other_Title.add(lblOthers);

			JPanel panel_OrtherSettings = new JPanel();
			Other_Panel.add(panel_OrtherSettings, BorderLayout.CENTER);
			panel_OrtherSettings.setLayout(new GridLayout(0, 1, 0, 0));
			
			JPanel panel_2DExtraction = new JPanel();
			panel_OrtherSettings.add(panel_2DExtraction);
			
			chckbxForcedExtraction = new JCheckBox("Force 2D Extraction");
				chckbxForcedExtraction.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent arg0) {
						if (chckbxForcedExtraction.isSelected()) spinner_Dimension2D.setEnabled(true);
						if (!chckbxForcedExtraction.isSelected()) spinner_Dimension2D.setEnabled(false);
					}
				});
			panel_2DExtraction.add(chckbxForcedExtraction);	
					
			JLabel lblDimension2D = new JLabel("Dimension :");
			panel_2DExtraction.add(lblDimension2D);
		
			spinner_Dimension2D = new JSpinner();
			spinner_Dimension2D.setToolTipText("Specifies the \u2018slice\u2019 dimension for a by-slice feature extraction");
			spinner_Dimension2D.setEnabled(false);
			spinner_Dimension2D.setModel(new SpinnerNumberModel(0, 0, 2, 1));
			panel_2DExtraction.add(spinner_Dimension2D);
					
			JPanel panel_Weighting = new JPanel();
			panel_OrtherSettings.add(panel_Weighting);
			
			JLabel lblTextureMatrixWeighting = new JLabel("Texture matrix weighting");
			panel_Weighting.add(lblTextureMatrixWeighting);
			
			comboBox_Weighting = new JComboBox<String>();
			comboBox_Weighting.setModel(new DefaultComboBoxModel<String>(new String[] {"manhattan", "euclidean", "infinity", "no_weighting", "None"}));
			comboBox_Weighting.setSelectedIndex(4);
			panel_Weighting.add(comboBox_Weighting);
								
			JLabel label = new JLabel("");
			panel_Weighting.add(label);
				
			JPanel panel_SetDistances = new JPanel();
			panel_OrtherSettings.add(panel_SetDistances);
			
			chckbxSetDistances = new JCheckBox("Set Distances to neighbour");
			chckbxSetDistances.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent e) {
						if (chckbxSetDistances.isSelected()) {
							txt_setDistances.setEnabled(true);
						}
						else {
							txt_setDistances.setEnabled(false);
						}
					}
				});
			panel_SetDistances.add(chckbxSetDistances);
			
			txt_setDistances = new JTextField();
			txt_setDistances.setToolTipText(" List of integers specifies the distances between the center voxel and the neighbor");
			txt_setDistances.setEnabled(false);
			txt_setDistances.setText("0");
			panel_SetDistances.add(txt_setDistances);
			txt_setDistances.setColumns(10);

			JPanel panel_PreCropping = new JPanel();
			panel_OrtherSettings.add(panel_PreCropping);
			
			chckbxPrecropping = new JCheckBox("Pre-Cropping");
			panel_PreCropping.add(chckbxPrecropping);
			chckbxPrecropping.setHorizontalAlignment(SwingConstants.CENTER);
			}	
			
			{
				///////////////////////////////////////PANEL SPECIFIC SETTINGS /////////////////////////////////////////////
			JPanel panel_specificSettings = new JPanel();
			panel_specificSettings.setBorder(new LineBorder(new Color(0, 0, 0)));
			Settings_Panel.add(panel_specificSettings);
			panel_specificSettings.setLayout(new BorderLayout(0, 0));
				
			JPanel panel_title = new JPanel();
			panel_title.setBorder(new LineBorder(Color.LIGHT_GRAY));
			panel_specificSettings.add(panel_title, BorderLayout.NORTH);
				
			JLabel lblSpecificSettings = new JLabel("Specific Settings");
			panel_title.add(lblSpecificSettings);
						
			JPanel panel_SpecificSettings_Options = new JPanel();
			panel_specificSettings.add(panel_SpecificSettings_Options, BorderLayout.CENTER);
			panel_SpecificSettings_Options.setLayout(new GridLayout(0, 1, 0, 0));
			
			JPanel panel_SpecificSettingsPanel = new JPanel();
			panel_SpecificSettings_Options.add(panel_SpecificSettingsPanel);
			panel_SpecificSettingsPanel.setLayout(new GridLayout(0, 1, 0, 0));
							
			JPanel panel_specificFirstOrder = new JPanel();
			panel_SpecificSettingsPanel.add(panel_specificFirstOrder);				
			JLabel lblFirstOrder = new JLabel("First Order : ");
			panel_specificFirstOrder.add(lblFirstOrder);
			JLabel lblVoxelArrayShift = new JLabel("Voxel Array Shift");
			panel_specificFirstOrder.add(lblVoxelArrayShift);
			spinner_VoxelArrayShift = new JSpinner();
			spinner_VoxelArrayShift.setToolTipText("This amount is added to the gray level intensity in features Energy, Total Energy and RMS, this is to prevent negative values.");
			panel_specificFirstOrder.add(spinner_VoxelArrayShift);
			
			JPanel panel_SpecificSettingsGLCM = new JPanel();
			panel_SpecificSettings_Options.add(panel_SpecificSettingsGLCM);			
			JLabel lblGlcm = new JLabel("GLCM : ");
			panel_SpecificSettingsGLCM.add(lblGlcm);
			chckbxSymetricalGlcm = new JCheckBox("Symetrical GLCM");
			chckbxSymetricalGlcm.setToolTipText("indicates whether co-occurrences should be assessed in two directions per angle");
			panel_SpecificSettingsGLCM.add(chckbxSymetricalGlcm);
			chckbxSymetricalGlcm.setSelected(true);
			
			JPanel panel_SpecificSettingsGLDM = new JPanel();
			panel_SpecificSettings_Options.add(panel_SpecificSettingsGLDM);
			JLabel lblGldm = new JLabel("GLDM : ");
			panel_SpecificSettingsGLDM.add(lblGldm);
			
			JLabel lblAlfa = new JLabel("Alfa");
			panel_SpecificSettingsGLDM.add(lblAlfa);
				
			txt_setAlfa = new JTextField();
			txt_setAlfa.setToolTipText("\u03B1  cutoff value for dependence");
			txt_setAlfa.setText("0.0");
			panel_SpecificSettingsGLDM.add(txt_setAlfa);
			txt_setAlfa.setColumns(10);
				
			}
			
			{
			///////////////////////////////////////PANEL IMAGE TYPE /////////////////////////////////////////////
			JPanel ImageType_panel = new JPanel();
			ImageType_panel.setBorder(new LineBorder(new Color(0, 0, 0)));
			Settings_Panel.add(ImageType_panel);
			ImageType_panel.setLayout(new BorderLayout(0, 0));
			JPanel ImageType_Title = new JPanel();
			ImageType_Title.setBorder(new LineBorder(Color.LIGHT_GRAY));
			ImageType_panel.add(ImageType_Title, BorderLayout.NORTH);
				
			JLabel lblImageType = new JLabel("Image Type");
			ImageType_Title.add(lblImageType);
				
			JPanel ImageType_CheckBox = new JPanel();
			ImageType_panel.add(ImageType_CheckBox, BorderLayout.CENTER);
			
			ImageType_CheckBox.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
			JPanel panel_2 = new JPanel();
			ImageType_CheckBox.add(panel_2);
			panel_2.setLayout(new GridLayout(0, 2, 0, 0));
			
			JCheckBox chckbxOriginal = new JCheckBox("Original");
			panel_2.add(chckbxOriginal);
			chckbxOriginal.setSelected(true);
			imageType.add(chckbxOriginal);
			JCheckBox chckbxLog = new JCheckBox("LoG");
			chckbxLog.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
				if (chckbxLog.isSelected()) txtGaussianSigma.setEnabled(true);
				else if (!chckbxLog.isSelected()) txtGaussianSigma.setEnabled(false);
				}
			});
			panel_2.add(chckbxLog);
			imageType.add(chckbxLog);
			JCheckBox chckbxWavelet = new JCheckBox("Wavelet");
			chckbxWavelet.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					if (chckbxWavelet.isSelected()) {
						spinner_startLevelWavelet.setEnabled(true);
						spinner_Wavelet_Level.setEnabled(true);
						txtWaveletTxt.setEnabled(true);
					}
					else if (!chckbxWavelet.isSelected()) {
						spinner_startLevelWavelet.setEnabled(false);
						spinner_Wavelet_Level.setEnabled(false);
						txtWaveletTxt.setEnabled(false);
					}
				}
			});
			panel_2.add(chckbxWavelet);
			imageType.add(chckbxWavelet);
			JCheckBox chckbxSquare = new JCheckBox("Square");
			panel_2.add(chckbxSquare);
			imageType.add(chckbxSquare);
			JCheckBox chckbxSquareroot = new JCheckBox("SquareRoot");
			panel_2.add(chckbxSquareroot);
			imageType.add(chckbxSquareroot);
			JCheckBox chckbxLogarithm = new JCheckBox("Logarithm");
			panel_2.add(chckbxLogarithm);
			imageType.add(chckbxLogarithm);
			JCheckBox chckbxExponential = new JCheckBox("Exponential");
			panel_2.add(chckbxExponential);
			imageType.add(chckbxExponential);
			
			JPanel panel = new JPanel();
			ImageType_CheckBox.add(panel);
			panel.setLayout(new BorderLayout(0, 0));
			
			JPanel panel_1 = new JPanel();
			panel.add(panel_1, BorderLayout.CENTER);
			panel_1.setLayout(new GridLayout(0, 2, 0, 0));
			
			JLabel lblWavelet = new JLabel("Wavelet");
			panel_1.add(lblWavelet);
			
			Component horizontalStrut = Box.createHorizontalStrut(20);
			panel_1.add(horizontalStrut);
			
			JLabel lblStartLevel = new JLabel("Start Level");
			panel_1.add(lblStartLevel);
			
			spinner_startLevelWavelet = new JSpinner();
			spinner_startLevelWavelet.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
			spinner_startLevelWavelet.setEnabled(false);
			panel_1.add(spinner_startLevelWavelet);
			
			JLabel lblLevel = new JLabel("Level");
			panel_1.add(lblLevel);
			
			spinner_Wavelet_Level = new JSpinner();
			spinner_Wavelet_Level.setModel(new SpinnerNumberModel(new Integer(1), new Integer(0), null, new Integer(1)));
			spinner_Wavelet_Level.setEnabled(false);
			panel_1.add(spinner_Wavelet_Level);
			
			JLabel lblWavelet_1 = new JLabel("Wavelet");
			panel_1.add(lblWavelet_1);
			
			txtWaveletTxt = new JTextField();
			txtWaveletTxt.setEnabled(false);
			txtWaveletTxt.setText("coif1");
			panel_1.add(txtWaveletTxt);
			txtWaveletTxt.setColumns(10);
			
			JPanel panel_3 = new JPanel();
			panel.add(panel_3, BorderLayout.NORTH);
			
			JLabel lblLog = new JLabel("LoG");
			panel_3.add(lblLog);
			
			JLabel lblSigma = new JLabel("Sigma");
			panel_3.add(lblSigma);
			
			txtGaussianSigma = new JTextField();
			txtGaussianSigma.setToolTipText("List of floats or integers, must be greater than 0");
			txtGaussianSigma.setEnabled(false);
			panel_3.add(txtGaussianSigma);
			txtGaussianSigma.setColumns(10);
			}
			
			{
			///////////////////////////////////////PANEL FEATURES /////////////////////////////////////////////
			JPanel Features_Panel = new JPanel();
			Features_Panel.setBorder(new LineBorder(new Color(0, 0, 0)));
			Settings_Panel.add(Features_Panel);
			Features_Panel.setLayout(new BorderLayout(0, 0));
			
			JPanel Enabled_Feature_Panel = new JPanel();
			Enabled_Feature_Panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
			Features_Panel.add(Enabled_Feature_Panel, BorderLayout.NORTH);
			
			JLabel lblEnabledFeatures = new JLabel("Enabled Features");
			Enabled_Feature_Panel.add(lblEnabledFeatures);
			
			JPanel Features_Options = new JPanel();
			Features_Panel.add(Features_Options, BorderLayout.CENTER);
			Features_Options.setLayout(new GridLayout(0, 3, 0, 0));
				
				
			chckbxFirstOrder = new JCheckBox("First Order");
			chckbxFirstOrder.setSelected(true);
			chckbxShape = new JCheckBox("Shape");
			chckbxShape.setSelected(true);
			chckbxGlcm = new JCheckBox("GLCM");
			chckbxGlcm.setSelected(true);
			chckbxGlrlm = new JCheckBox("GLRLM");
			chckbxGlrlm.setSelected(true);
			chckbxGlszm = new JCheckBox("GLSZM");
			chckbxGlszm.setSelected(true);
			chckbxNgtdm = new JCheckBox("NGTDM");
			chckbxNgtdm.setSelected(true);
			chckbxGtdm = new JCheckBox("GTDM");
			chckbxGtdm.setSelected(true);
			
			
			Features_Options.add(chckbxFirstOrder);
			Features_Options.add(chckbxShape);
			Features_Options.add(chckbxGlcm);
			Features_Options.add(chckbxGlrlm);
			Features_Options.add(chckbxGlszm);
			Features_Options.add(chckbxNgtdm);
			Features_Options.add(chckbxGtdm);
			
			}
		
			{
			this.pack();
			try {
				this.setIconImage(ImageIO.read(getClass().getResource("/logo/logo.png")));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			this.setSize(this.getPreferredSize());
			}
		
		
	}
	
	private void enableNormalize(boolean enable) {
		if (enable) {
			txtNormalizeScale.setEnabled(true);
			txtRemoveOutliners.setEnabled(true);
		}
		else {
			txtNormalizeScale.setEnabled(false);
			txtRemoveOutliners.setEnabled(false);
		}
	}
	
	private void enableResample(boolean enable) {
		if (enable) {
			txtX.setEnabled(true);
			txtY.setEnabled(true);
			txtZ.setEnabled(true);
			spinner_padDistance.setEnabled(true);
			comboBox_Interpolator.setEnabled(true);
		}
		else {
			txtX.setEnabled(false);
			txtY.setEnabled(false);
			txtZ.setEnabled(false);
			spinner_padDistance.setEnabled(false);
			comboBox_Interpolator.setEnabled(false);
		}
	}
	
	private void enableMaskValidate(boolean validate) {
		if (validate) {
			textField_GeometryTolerance.setEnabled(true);
			spinner_minRoiDimension.setEnabled(true);
			spinner_minRoiSize.setEnabled(true);
			chckbxCorrectMask.setEnabled(true);
		}
		else {
			textField_GeometryTolerance.setEnabled(false);
			spinner_minRoiDimension.setEnabled(false);
			spinner_minRoiSize.setEnabled(false);
			chckbxCorrectMask.setEnabled(false);
		}
	}
	
	private void enableDiscretize(boolean discretize) {
		if (discretize) {
			chckbxFixedBinWidth.setEnabled(true);
			chckbxResgmentationvalueLimits.setEnabled(true);
			chckbxFixedNumberOf_1.setEnabled(true);
			textField.setEnabled(true);
			
		}
		else {
			chckbxFixedBinWidth.setEnabled(false);
			chckbxResgmentationvalueLimits.setEnabled(false);
			chckbxFixedNumberOf_1.setEnabled(false);
			
		}
	}
	
	public boolean isDiscretize() {
		return chckbxDiscretize.isSelected();
	}
	
	public boolean getOk() {
		return ok;
	}
	
	public Double getfixedBinWidth(){
		fixedBinWidth=Double.parseDouble(textField.getText());
		return fixedBinWidth;
	}
	
	public double getMin(){
		min=Double.parseDouble(txtMin.getText());
		return min;
	}
	
	public double getMax(){
		max=Double.parseDouble(txtMax.getText());
		return max;
	}
	
	public int getNumberOfBin(){
		numberOfBin=(int) spinnerBinFixed.getValue();
		return numberOfBin;
	}
	
	public boolean getResegmentPerRoi(){
		return chckbxFixedNumberOf_1.isSelected();
	}
	
	public boolean isFixedBin(){
		return chckbxFixedBinWidth.isSelected();
	}
	
	public boolean isResgmentLimit(){
		return chckbxResgmentationvalueLimits.isSelected();
	}
	
	public boolean isNormalize(){
		return checkBoxNormalize.isSelected();
	}
	
	public double getNormalizeScale() {
		return Double.parseDouble(txtNormalizeScale.getText());
	}
	
	public double getRemoveOutliners() {
		return Double.parseDouble(txtRemoveOutliners.getText());
	}
	
	public boolean isResampleImage() {
		return chckbxResampleImage.isSelected();
	}
	
	public double[] getResamplePixelSpacing() {
		double[] pixelSpacing=new double[3];
		pixelSpacing[0] = Double.parseDouble(txtX.getText());
		pixelSpacing[1] = Double.parseDouble(txtY.getText());
		pixelSpacing[2] = Double.parseDouble(txtZ.getText());
		return pixelSpacing;
	}
	
	public String getInterpolator() {
		return (String) comboBox_Interpolator.getSelectedItem();
	}
	
	public int getPadDistance() {
		return (int) spinner_padDistance.getValue();
	}
	
	public boolean isValidateMask() {
		return chckbxValidateMask.isSelected();
	}
	
	public int getMinRoiDimension() {
		return (int) spinner_minRoiDimension.getValue();
	}
	
	public int getMinRoiSize() {
		return (int) spinner_minRoiSize.getValue();
	}
	
	public double getGeometryTolerance() {
		return Double.parseDouble(textField_GeometryTolerance.getText());
	}
	
	public boolean getCorrectMask() {
		return chckbxCorrectMask.isSelected();
	}
	
	public boolean isForce2DExtraction() {
		return chckbxForcedExtraction.isSelected();
	}
	
	public int get2DExtractionDimension() {
		return (int) spinner_Dimension2D.getValue();
	}
	
	public String getWeighting() {
		return (String) comboBox_Weighting.getSelectedItem();
	}
	
	public boolean isDistance(){
		return chckbxSetDistances.isSelected();
	}
	
	public String getDistances() {
		return txt_setDistances.getText();
	}
	
	public boolean getPreCropping() {
		return chckbxPrecropping.isSelected();
	}
	
	public int getVoxelShiftArray() {
		return (int)spinner_VoxelArrayShift.getValue();
	}
	
	public boolean getSymetricalGLCM() {
		return chckbxSymetricalGlcm.isSelected();
	}
	
	public double getAlfaGLDM() {
		return Double.parseDouble(txt_setAlfa.getText());
	}
	
	public File getSettingFile(){
		return settingsFile;
	}
	
	public HashMap<String, Boolean> getSelectedFeatures(){
		HashMap<String, Boolean> features=new HashMap<String, Boolean>();
		features.put("First Order", chckbxFirstOrder.isSelected());
		features.put("Shape", chckbxShape.isSelected());
		features.put("GLCM", chckbxGlcm.isSelected());
		features.put("GLRLM", chckbxGlrlm.isSelected());
		features.put("GLSZM", chckbxGlszm.isSelected());
		features.put("NGTDM", chckbxNgtdm.isSelected());
		features.put("GLDM", chckbxGtdm.isSelected());
		return features;
	}
	
	public HashMap<String, Boolean> getImageType() {
		HashMap<String, Boolean> imageType=new HashMap<String, Boolean>();
		imageType.put("typeOriginal", this.imageType.get(0).isSelected());
		imageType.put("typeLoG", this.imageType.get(1).isSelected());
		imageType.put("typeWavelet", this.imageType.get(2).isSelected());
		imageType.put("typeSquare", this.imageType.get(3).isSelected());
		imageType.put("typeSquareRoot", this.imageType.get(4).isSelected());
		imageType.put("typeLogarithm", this.imageType.get(5).isSelected());
		imageType.put("typeExponential", this.imageType.get(6).isSelected());
		return  imageType;
	}
	
	public String getLogSigma() {
		return txtGaussianSigma.getText();
	}
	
	public int getWaveletStart() {
		return (int)spinner_startLevelWavelet.getValue();
	}
	
	public int getWaveletLevel() {
		return (int)spinner_Wavelet_Level.getValue();
	}
	
	public String getWaveletString() {
		return txtWaveletTxt.getText();
	}
	// Set settings before opening settings GUI
	public void setExistingSettings(boolean optionSet, boolean fixedBin, boolean resegmentPerRoi, double binWidth, boolean resegmentLimit, double min, double max, int fixedBinPerRoi,
			boolean normalize, double normalizeScale, double removeOutliners, boolean resample, double[] pixelSpacing, String interpolator, int padDistance,
			boolean validateMask, int minRoiDimension, int minRoiSize, double geometryTolerance, boolean correctMask,
			boolean isForce2DExtraction, boolean isDistance, int Dimension2D, String matrixWeighting, String distanceNeighbour, boolean preCroping,
			int voxelArrayShift, boolean symetricalGLCM, double alfa, String logSigma, String waveletString, int waveletStart, int waveletLevel, HashMap<String, Boolean> imageType
			){
		if (optionSet) {
			chckbxFixedBinWidth.setSelected(fixedBin);
			textField.setText(String.valueOf(binWidth));
			chckbxResgmentationvalueLimits.setSelected(resegmentLimit);
			txtMin.setText(String.valueOf(min)); 
			txtMax.setText(String.valueOf(max));
			chckbxFixedNumberOf_1.setSelected(!fixedBin);
			spinnerBinFixed.setValue(fixedBinPerRoi);
			
			checkBoxNormalize.setSelected(normalize);
			txtNormalizeScale.setText(String.valueOf(normalizeScale));
			txtRemoveOutliners.setText(String.valueOf(removeOutliners));
			
			chckbxResampleImage.setSelected(resample);
			txtX.setText(String.valueOf(pixelSpacing[0]));
			txtY.setText(String.valueOf(pixelSpacing[1]));
			txtZ.setText(String.valueOf(pixelSpacing[2]));
			comboBox_Interpolator.setSelectedItem(interpolator);
			spinner_padDistance.setValue(padDistance);
			
			chckbxValidateMask.setSelected(validateMask);
			spinner_minRoiDimension.setValue(minRoiDimension);
			spinner_minRoiSize.setValue(minRoiSize);
			textField_GeometryTolerance.setText(String.valueOf(geometryTolerance));
			chckbxCorrectMask.setSelected(correctMask);
			
			chckbxForcedExtraction.setSelected(isForce2DExtraction);
			spinner_Dimension2D.setValue(Dimension2D);
			comboBox_Weighting.setSelectedItem(matrixWeighting);
			chckbxSetDistances.setSelected(isDistance);
			txt_setDistances.setText(distanceNeighbour);
			chckbxPrecropping.setSelected(preCroping);
			
			spinner_VoxelArrayShift.setValue(voxelArrayShift);
			chckbxSymetricalGlcm.setSelected(symetricalGLCM);
			txt_setAlfa.setText(String.valueOf(alfa));
			
			txtGaussianSigma.setText(logSigma);
			txtWaveletTxt.setText(waveletString);
			spinner_startLevelWavelet.setValue(waveletStart);
			spinner_Wavelet_Level.setValue(waveletLevel);
			
			if (imageType.get("typeOriginal")) this.imageType.get(0).setSelected(true);
			if (imageType.get("typeLoG")) this.imageType.get(1).setSelected(true);
			if (imageType.get("typeWavelet")) this.imageType.get(2).setSelected(true);
			if (imageType.get("typeSquare")) this.imageType.get(3).setSelected(true);
			if (imageType.get("typeSquareRoot")) this.imageType.get(4).setSelected(true);
			if (imageType.get("typeLogarithm")) this.imageType.get(5).setSelected(true);
			if (imageType.get("typeExponential")) this.imageType.get(6).setSelected(true);
			
		}
	}
	
	public void setSelectedFeatures(HashMap<String, Boolean> features){
		if (!features.get("First Order")) chckbxFirstOrder.setSelected(false);
		if (!features.get("Shape")) chckbxShape.setSelected(false);
		if (!features.get("GLCM")) chckbxGlcm.setSelected(false);
		if (!features.get("GLRLM")) chckbxGlrlm.setSelected(false);
		if (!features.get("GLSZM")) chckbxGlszm.setSelected(false);
		if (!features.get("NGTDM")) chckbxNgtdm.setSelected(false);
		if (!features.get("GLDM")) chckbxGtdm.setSelected(false);
		
	}

}
