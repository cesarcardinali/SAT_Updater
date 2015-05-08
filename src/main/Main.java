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
			writer.write("Updating ...");
			
			System.out.println("- Updating jar file");
			writer.write("- Updating jar file");
			try {
				copyScript(new File("S:\\Rio_Itu\\Temporário\\Cesar\\BatteryTool.jar"), new File("BatteryTool.jar"));
			} catch (IOException e1) {
				System.out.println("Could not update the JAR file. Process cancelled");
				writer.write("Could not update the JAR file. Process cancelled");
				e1.printStackTrace();
				writer.close();
				System.exit(0);
			}
			
			
			/*try {
				File dataFolder = new File("Data");
				if(dataFolder.isDirectory()){
					System.out.println("- Deleting old DATA folder: " + dataFolder);
					FileUtils.deleteDirectory(dataFolder);
				}
			} catch (IOException e) {
				System.out.println("Could not update the DATA folder. Process cancelled");
				e.printStackTrace();
				System.exit(0);
			}*/
			
			
			System.out.println("- Updating DATA folder");
			writer.write("- Updating DATA folder");
			try {
				FileUtils.copyDirectory(new File("S:\\Rio_Itu\\Temporário\\Cesar\\Data"), new File("Data"));
			} catch (IOException e) {
				System.out.println("Could not update the DATA folder. Process cancelled");
				writer.write("Could not update the DATA folder. Process cancelled");
				e.printStackTrace();
				writer.close();
				System.exit(0);
			}
			
			
			System.out.println("- Starting new application");
			writer.write("- Starting new application");
			ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "cd " + new File("").getAbsolutePath() + " && java -jar \"Batterytool.jar\"");
			
	        System.out.println("Update successful");
	        writer.write("Update successful");
			try {
				/*Process p =*/ builder.start();
			} catch (IOException e) {
				System.out.println("Could not restart the application");
				writer.write("Could not restart the application");
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
