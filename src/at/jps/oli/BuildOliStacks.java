package at.jps.oli;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

public class BuildOliStacks {

	static int counter = 1;

	static void workOnStack(List<File> stackList) throws IOException {
		boolean start = true;
		String dirname = "";

		for (File f : stackList) {
			if (start) {
				dirname = f.getParentFile().getPath() + File.separator + "Stack-"; // +
																					// String.format("%05d",
																					// counter);

				dirname += f.getName().substring(0, f.getName().lastIndexOf('.'));

				File dir = new File(dirname);
				dir.mkdir();
				counter++;
				start = false;

			}

			String newFileName = dirname + File.separator + f.getName();

			System.out.println(f.getPath() + "->" + newFileName);

			Files.move(Paths.get(f.getPath()), Paths.get(newFileName), StandardCopyOption.REPLACE_EXISTING);

			// f.renameTo(new File(newFileName));
		}

	}

	static void checkFolder(final String foldername) throws ImageProcessingException, IOException {

		File folder = new File(foldername);
		File[] listOfFiles = folder.listFiles();

		Arrays.sort(listOfFiles);

		List<File> stackList = new ArrayList<File>();

		for (File file : listOfFiles) {
			if (file.isFile() && ((file.getPath().toUpperCase().contains(".ORF")))) {
				int rc = checkFile(file.getPath());
				if (rc == 1) {
					if (!stackList.isEmpty()) {
						// work on list
						System.out.println(" stack completed 1");

						if (stackList.size() > 1)
							workOnStack(stackList);

						stackList.clear();
					}
				}
				if (rc == 0) {
					if (!stackList.isEmpty()) {
						// work on list
						System.out.println(" stack completed 2");
						if (stackList.size() > 1)
							workOnStack(stackList);
						stackList.clear();
					}
				}
				if (rc > 0) {
					stackList.add(file);
				}
			}
		}
		if (!stackList.isEmpty()) {
			// work on list
			System.out.println(" stack completed 3");
			if (stackList.size() > 1)
				workOnStack(stackList);
			stackList.clear();
		}

	}

	/**
	 * 
	 * @param filename
	 * @return 0 if plain
	 * @return 1 if stack start
	 * @return 2 if in stack
	 * @throws ImageProcessingException
	 * @throws IOException
	 */
	static int checkFile(final String filename) throws ImageProcessingException, IOException {

		int rc = 0;
		try {
			System.out.println("handle file=" + filename);
			if (filename.toUpperCase().contains(".ORF")) {
				InputStream imageFile = new FileInputStream(filename);

				Metadata metadata = ImageMetadataReader.readMetadata(imageFile);

				for (Directory directory : metadata.getDirectories()) {
					// System.out.println(" directory=" + directory);
					for (Tag tag : directory.getTags()) {
						if (tag.toString().contains("Drive Mode - Focus Bracketing")) {
							rc = 2;
							System.out.println(" tag=" + tag);
							if (tag.toString().endsWith("Shot 1"))
								rc = 1;
							break;
						}
					}
					if ( rc > 0) break;
				}
				imageFile.close();
			}
		} catch (Exception x) {
			System.out.println("File skipped Exception: " + x.getMessage());
		}
		return rc;
	}

	public static void main(String[] args) throws IOException, ImageProcessingException {

		if (args.length > 0)
			checkFolder(args[0]);

	}

}
