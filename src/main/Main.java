package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.FlowLayout;

import javax.swing.SwingConstants;

import org.apache.commons.io.FileUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.awt.Font;
import java.awt.Dimension;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends JFrame {
	/**
	 * Global variables.
	 */
	private static final long serialVersionUID = -3060854721871781995L;
	private JPanel contentPane;
	private static JLabel lblNewLabel_0;
	private static JLabel lblNewLabel_1;
	private static JLabel lblNewLabel_2;
	private static JLabel lblNewLabel_3;
	private static JLabel lblNewLabel_4;
	private static JLabel lblNewLabel_5;
	private static final String configLocation = "Data/cfgs/system_cfg.xml";
	private static String updaterLocation;
	private static String toolFile;
	private static String contentFolder;
	private static String usrConfig;
	private static String passConfig;
	private static String updateFolder1;
	private static String updateFolder2;
	private static String ovrwt_usrcfg;
	private static String clean_install;

	
	/**
	 * Launch application.
	 */
	public static void main(String[] args) {
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							
							loadInitialData();
							updateApplication();

						}
					}).start();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	/**
	 * Create the frame.
	 */
	public Main() {
		setResizable(false);
		setTitle("SAT Updater");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 807, 173);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSatIsUpdating = new JLabel("SAT is updating");
		lblSatIsUpdating.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		lblSatIsUpdating.setHorizontalAlignment(SwingConstants.CENTER);
		lblSatIsUpdating.setBounds(10, 11, 780, 24);
		contentPane.add(lblSatIsUpdating);
		
		JLabel lblPleaseWaitA = new JLabel("Please, wait a minute ...");
		lblPleaseWaitA.setHorizontalAlignment(SwingConstants.CENTER);
		lblPleaseWaitA.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		lblPleaseWaitA.setBounds(10, 37, 780, 14);
		contentPane.add(lblPleaseWaitA);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 76, 780, 24);
		contentPane.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		lblNewLabel_0 = new JLabel("Updating jar file");
		lblNewLabel_0.setVisible(false);
		lblNewLabel_0.setOpaque(true);
		lblNewLabel_0.setBackground(new Color(153, 204, 255));
		lblNewLabel_0.setForeground(new Color(255, 255, 255));
		lblNewLabel_0.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_0.setPreferredSize(new Dimension(130, 20));
		panel.add(lblNewLabel_0);
		
		lblNewLabel_1 = new JLabel("Creating user cfg bkp");
		lblNewLabel_1.setVisible(false);
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBackground(new Color(153, 204, 204));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setPreferredSize(new Dimension(130, 20));
		panel.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("Updating DATA folder");
		lblNewLabel_2.setVisible(false);
		lblNewLabel_2.setBackground(new Color(153, 204, 153));
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setOpaque(true);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setPreferredSize(new Dimension(130, 20));
		panel.add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel("Restoring user cfg");
		lblNewLabel_3.setVisible(false);
		lblNewLabel_3.setBackground(new Color(153, 204, 102));
		lblNewLabel_3.setForeground(new Color(255, 255, 255));
		lblNewLabel_3.setOpaque(true);
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setPreferredSize(new Dimension(130, 20));
		panel.add(lblNewLabel_3);
		
		lblNewLabel_4 = new JLabel("Deleting old files");
		lblNewLabel_4.setVisible(false);
		lblNewLabel_4.setBackground(new Color(153, 204, 0));
		lblNewLabel_4.setForeground(new Color(255, 255, 255));
		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setPreferredSize(new Dimension(130, 20));
		panel.add(lblNewLabel_4);
		
		lblNewLabel_5 = new JLabel("Done");
		lblNewLabel_5.setVisible(false);
		lblNewLabel_5.setBackground(new Color(102, 204, 102));
		lblNewLabel_5.setForeground(new Color(255, 255, 255));
		lblNewLabel_5.setOpaque(true);
		lblNewLabel_5.setPreferredSize(new Dimension(130, 20));
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_5);
	}
	
	
	/**
	 * Update function.
	 */
	
	private static void loadInitialData() {
		try{
			//Abre o arquivo XML
			File xmlFile1 = new File(configLocation);
			
			//Cria o builder da estrutura XML
			SAXBuilder builder = new SAXBuilder();
			
			//Cria documento formatado de acordo com a lib XML
			Document sysconfig = (Document) builder.build(xmlFile1);
	
			//Pega o nó raiz do XML
			Element satNode = sysconfig.getRootElement();
			
			//Pega o nó referente ao option pane
			Element tool_configuration = satNode.getChild("configs");
			for(Element e : tool_configuration.getChildren()){
				if(e.getName().equals("tool_file")){
					toolFile = (e.getValue());
					
				} else if(e.getName().equals("content_folder")){
					contentFolder = (e.getValue());
							
				} else if(e.getName().equals("usr_config")){
					usrConfig = (e.getValue());
					
				} else if(e.getName().equals("pwd_config")){
					passConfig = (e.getValue());
					
				} else if(e.getName().equals("update_path1")){
					updateFolder1 = (e.getValue());
					
				} else if(e.getName().equals("update_path2")){
					updateFolder2 = (e.getValue());
					
				} else if(e.getName().equals("update_config")){
					updaterLocation = (e.getValue());
					
				}
			}
						
			System.out.println("Configs: " + configLocation);
			System.out.println("Content Folder: " + contentFolder);
			System.out.println("Update path1: " + updateFolder1 + "\nUpdate path2: " + updateFolder2 + "\nUser Config: " +
													"\nUpdate Config: " + updaterLocation + usrConfig + "\nPass Config: " + passConfig);
			System.out.println("Tool Configuration Loaded");
		
		} catch (IOException | JDOMException e){
			e.printStackTrace();
		}
		
		try{
			File xmlFile2 = new File(updaterLocation);
			
			SAXBuilder builder = new SAXBuilder();
			
			Document updconfig = (Document) builder.build(xmlFile2);
	
			Element satNode = updconfig.getRootElement();
			
			Element updater_configuration = satNode.getChild("configs");
			for(Element e : updater_configuration.getChildren()){
				if(e.getName().equals("clean_install")){
					clean_install = (e.getValue());
					
				} else if(e.getName().equals("overwrite_usr_cfg")){
					ovrwt_usrcfg = (e.getValue());
							
				} 
			}
						
			System.out.println("Configs: " + updaterLocation);
			System.out.println("Clean Install? " + clean_install + "\nOverwrite User Configuration? " + ovrwt_usrcfg);
			System.out.println("Updater Configuration Loaded");
			
		
		} catch (IOException | JDOMException e){
			e.printStackTrace();
		}
	}
	
	
	private static void updateApplication() {
		BufferedWriter writer = null;
		lblNewLabel_0.setVisible(true);
		

			try {
				writer = new BufferedWriter(new FileWriter(new File("update_log.txt")));
				System.out.println("Updating ...");
				writer.write("Updating ...\n");
				
				System.out.println("- Updating jar file");
				lblNewLabel_0.setVisible(true);
				writer.write("- Updating jar file\n");
				try {
					copyScript(new File(updateFolder1 + toolFile), new File(toolFile));
				} catch (IOException e1) {
					System.out.println("Could not update the JAR file. Process cancelled");
					writer.write("Could not update the JAR file. Process cancelled\n");
					writer.write(e1.getMessage() + "\n");
					e1.printStackTrace();
					writer.close();
					System.exit(0);
				}
			
			//If clean install is enabled
			if(clean_install.equalsIgnoreCase("true")){
				System.out.println("Performing a clean reinstallation");
				writer.write("- Recreating DATA folder\n");
				lblNewLabel_2.setVisible(true);	
				
				FileUtils.cleanDirectory(new File(contentFolder));

				try {
					FileUtils.copyDirectory(new File(updateFolder1 + contentFolder), new File(contentFolder));
				} catch (IOException e) {
					System.out.println("Could not recreate the DATA folder. Process cancelled");
					writer.write("Could not recreate the DATA folder. Process cancelled\n");
					writer.write(e.getMessage() + "\n");
					e.printStackTrace();
					writer.close();
					System.exit(0);
				}
				
			//If both updater settings in updater_cfg.xml are set "false"
			} else {
				
					if(!ovrwt_usrcfg.equalsIgnoreCase("true")){
					
						System.out.println("- Creating user cfg bkp");
						writer.write("- Creating user cfg bkp\n");
						lblNewLabel_1.setVisible(true);
						try{
						copyScript(new File(contentFolder + usrConfig), new File(contentFolder + usrConfig + ".bkp"));
						}catch (IOException e1) {
							new File(contentFolder + usrConfig).createNewFile();
							copyScript(new File(contentFolder + usrConfig), new File(contentFolder + usrConfig + ".bkp"));
						}
						copyScript(new File(contentFolder + passConfig), new File(contentFolder + passConfig + ".bkp"));
					
					}
				
					//If only clean_install updater settings in updater_cfg.xml are set "false"	
					System.out.println("- Updating DATA folder");
					writer.write("- Updating DATA folder");
					lblNewLabel_2.setVisible(true);
					ArrayList<File> aremote = new ArrayList<File>(FileUtils.listFiles(new File(updateFolder1 + contentFolder), null, true));
					ArrayList<File> alocal = new ArrayList<File>(FileUtils.listFiles(new File(contentFolder), null, true));
					ArrayList<String> namesremote = new ArrayList<String>(aremote.size());
					ArrayList<String> nameslocal = new ArrayList<String>(alocal.size());
					System.out.println(aremote);
					System.out.println(alocal);
					
					for(int i = 0;i<aremote.size();i++){
						namesremote.add(aremote.get(i).getName());					
					}
					
					for(int i = 0;i<alocal.size();i++){
						nameslocal.add(alocal.get(i).getName());
					}
					
					for(int i = 0;i<namesremote.size();i++){
						if(!namesremote.get(i).contains(".db")){
							if(!nameslocal.contains(namesremote.get(i))){
								System.out.println("Does not contain FileName: "+namesremote.get(i));
								FileUtils.copyFileToDirectory(aremote.get(i), new File(contentFolder));
							}else{
								System.out.println("Contains FileName: "+namesremote.get(i));
								if(aremote.get(i).lastModified() > alocal.get(nameslocal.indexOf(namesremote.get(i))).lastModified() ||
										(namesremote.get(i).equals("user_cfg.xml") && ovrwt_usrcfg.equalsIgnoreCase("true")))
										//The second condition forces "user_cfg.xml" to update if it's set on updater_cfg.xml
								{
									System.out.println("Newer File: "+namesremote.get(i));
									FileUtils.copyFileToDirectory(aremote.get(i), new File(contentFolder));
								}
							}
						}						
					}
										
				
					//If both updater settings in updater_cfg.xml are set "false"
					if(!ovrwt_usrcfg.equalsIgnoreCase("true")){
						System.out.println("- Restoring user cfg\n");
						writer.write("- Restoring user cfg\n");
						lblNewLabel_3.setVisible(true);
						copyScript(new File(contentFolder + usrConfig + ".bkp"), new File(contentFolder + usrConfig));
						copyScript(new File(contentFolder + passConfig + ".bkp"), new File(contentFolder + passConfig));
						System.out.println("- Deleting old file");
						writer.write("- Deleting old file\n");
						lblNewLabel_4.setVisible(true);
						new File(contentFolder + usrConfig + ".bkp").delete();
						new File(contentFolder + passConfig + ".bkp").delete();
					}
			
			}
						
				
				lblNewLabel_5.setVisible(true);
				System.out.println("- Starting new application");
				writer.write("- Starting new application\n");
				
				ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "cd " + new File("").getAbsolutePath() + " && java -jar \"" + toolFile + "\"");
				
		        System.out.println("Update successful");
		        writer.write("Update successful\n");
				try {
					/*Process p =*/ builder.start();
					Thread.sleep(1000);
				} catch (IOException | InterruptedException e) {
					System.out.println("Could not restart the application");
					writer.write("Could not restart the application\n");
					writer.write(e.getMessage() + "\n");
					e.printStackTrace();
					writer.close();
					System.exit(0);
				}
				writer.close();
				

			
				
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.exit(0);
			}
		
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
		System.exit(0);
	}
	
	
	/**
	 * Supportive functions.
	 */
	public static void copyScript(File source, File dest)
			throws IOException {
		FileUtils.copyFile(source, dest);
	}
}
