package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;

public class Main {

	
	public static void main(String[] args) {
		//System.out.println(new File("").getAbsolutePath());
		updateApplication();
	}
	
	
//--------------------------------------------------------------------------------------------------------------------------------------------------------
	private static void updateThread(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					JOptionPane.showMessageDialog(null, "Updating application.\nIt will restart when finished\nPlease wait ...");
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					updateApplication();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
			}
		}).start();
	}

	
	
	private static void updateApplication() {
		BufferedWriter writer = null;
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			writer = new BufferedWriter(new FileWriter(new File("update_log.txt")));
			System.out.println("Updating ...");
			writer.write("Updating ...\n");
			
			System.out.println("- Updating jar file");
			writer.write("- Updating jar file\n");
			try {
				copyScript(new File("S:\\Rio_Itu\\SAT\\BatteryTool.jar"), new File("BatteryTool.jar"));
			} catch (IOException e1) {
				System.out.println("Could not update the JAR file. Process cancelled");
				writer.write("Could not update the JAR file. Process cancelled\n");
				writer.write(e1.getMessage() + "\n");
				e1.printStackTrace();
				writer.close();
				System.exit(0);
			}
			
			
			System.out.println("- Creating user cfg bkp");
			writer.write("- Creating user cfg bkp\n");
			copyScript(new File("Data\\cfgs\\user_cfg.xml"), new File("Data\\cfgs\\user_cfg.xml.bkp"));
			copyScript(new File("Data\\cfgs\\pass.ini"), new File("Data\\cfgs\\pass.bkp"));
			
			
			System.out.println("- Updating DATA folder");
			writer.write("- Updating DATA folder\n");
			try {
				FileUtils.copyDirectory(new File("S:\\Rio_Itu\\SAT\\Data"), new File("Data"));
			} catch (IOException e) {
				System.out.println("Could not update the DATA folder. Process cancelled");
				writer.write("Could not update the DATA folder. Process cancelled\n");
				writer.write(e.getMessage() + "\n");
				e.printStackTrace();
				writer.close();
				System.exit(0);
			}
			
			
			System.out.println("- Restoring user cfg\n");
			writer.write("- Restoring user cfg\n");
			copyScript(new File("Data\\cfgs\\user_cfg.xml.bkp"), new File("Data\\cfgs\\user_cfg.xml"));
			copyScript(new File("Data\\cfgs\\pass.bkp"), new File("Data\\cfgs\\pass.ini"));
			System.out.println("- Deleting old file");
			writer.write("- Deleting old file\n");
			new File("Data\\cfgs\\user_cfg.xml.bkp").delete();
			new File("Data\\cfgs\\pass.bkp").delete();
			
			
			System.out.println("- Starting new application");
			writer.write("- Starting new application\n");
			ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "cd " + new File("").getAbsolutePath() + " && java -jar \"Batterytool.jar\"");
			
	        System.out.println("Update successful");
	        writer.write("Update successful\n");
			try {
				/*Process p =*/ builder.start();
			} catch (IOException e) {
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
	
	
	
	public static void copyScript(File source, File dest)
			throws IOException {
		FileUtils.copyFile(source, dest);
	}
}
