package com.nexera.core.utility;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.PDFToImage;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDCcitt;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDPixelMap;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import org.apache.pdfbox.util.PDFMergerUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.nexera.common.entity.UploadedFilesList;
import com.nexera.core.service.UploadedFilesListService;
import com.nexera.core.service.impl.S3FileUploadServiceImpl;
import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;

@Component
public class NexeraUtility {

	

	@Autowired
	private S3FileUploadServiceImpl s3FileUploadServiceImpl;

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(NexeraUtility.class);

	private final String OUTPUT_FILENAME_EXT = "jpg";
	private final String OUTPUT_PREFIX = "-outputPrefix";
	private final String END_PAGE = "-endPage";

	private final String PAGE_NUMBER = "1";
	private final String START_PAGE = "-startPage";

	@SuppressWarnings("unchecked")
	public List<PDPage> splitPDFTOPages(File file) {
		PDDocument document = null;
		List<PDPage> pdfPages = null;
		// Create a Splitter object
		try {
			document = new PDDocument();
			document = PDDocument.loadNonSeq(file, null);
			return document.getDocumentCatalog().getAllPages();
		} catch (IOException e) {
			LOGGER.info("Exception in splitting pdf document : "
			        + e.getMessage());
		}
		return pdfPages;
	}

	public List<File> splitPDFPages(File file) {

		List<PDPage> pdfPages = splitPDFTOPages(file);
		List<File> newPdfpages = new ArrayList<File>();
		Integer pageNum = 0;
		for (PDPage pdPage : pdfPages) {

			try {
				PDDocument newDocument = new PDDocument();
				newDocument.addPage(pdPage);
				String filepath = tomcatDirectoryPath() + File.separator
				        + file.getName().replace(".pdf", "") + "_" + pageNum
				        + ".pdf";

				File newFile = new File(filepath);
				newFile.createNewFile();

				newDocument.save(filepath);
				newDocument.close();
				pageNum++;

				newPdfpages.add(newFile);
			} catch (Exception e) {
				LOGGER.info("Exception in converting pdf pages document : "
				        + e.getMessage());
			}
		}

		return newPdfpages;
	}

	public String tomcatDirectoryPath() {
		String rootPath = System.getProperty("catalina.home");
		return rootPath + File.separator + "tmpFiles";
	}

	public String uploadFileToLocal(File file) {
		String filePath = null;

		try {
			byte[] bytes = FileUtils.readFileToByteArray(file);

			// Creating the directory to store file

			File dir = new File(this.tomcatDirectoryPath());
			if (!dir.exists())
				dir.mkdirs();

			String fileName = file.getName();

			filePath = dir.getAbsolutePath() + File.separator + fileName;
			// Create the file on server
			File serverFile = new File(filePath);
			BufferedOutputStream stream = new BufferedOutputStream(
			        new FileOutputStream(serverFile));
			stream.write(bytes);
			stream.close();

			LOGGER.info("Server File Location=" + serverFile.getAbsolutePath());

		} catch (Exception e) {
			LOGGER.info("Exception in uploading file in local "
			        + e.getMessage());
			return null;
		}

		return filePath;
	}

	public String joinPDDocuments(List<String> fileUrls) throws IOException,
	        COSVisitorException {
		PDFMergerUtility mergePDF = new PDFMergerUtility();
		String newFilePath = this.tomcatDirectoryPath() + File.separator
		        + randomStringOfLength() + ".pdf";

		for (String fileUrl : fileUrls) {
			LOGGER.info("Adding File with URL" + fileUrl);
			mergePDF.addSource(new File(fileUrl));
		}
		mergePDF.setDestinationFileName(newFilePath);
		mergePDF.mergeDocuments();
		return newFilePath;
	}

	public String randomStringOfLength() {
		Integer length = 10;
		StringBuffer buffer = new StringBuffer();
		while (buffer.length() < length) {
			buffer.append(uuidString());
		}

		// this part controls the length of the returned string
		return buffer.substring(0, length);
	}

	public String convertPDFToThumbnail(String pdfFile, String imageFilePath)
	        throws Exception {

		String[] args = new String[7];
		args[0] = START_PAGE;
		args[1] = PAGE_NUMBER;
		args[2] = END_PAGE;
		args[3] = PAGE_NUMBER;
		args[4] = OUTPUT_PREFIX;
		args[5] = imageFilePath + File.separator;
		args[6] = pdfFile;

		try {
			File file = new File(pdfFile);
			String fileName = file.getName().replace(
			        FilenameUtils.getExtension(file.getName()), "");
			PDFToImage.main(args);
			String imageFile = imageFilePath + File.separator + PAGE_NUMBER
			        + "." + OUTPUT_FILENAME_EXT;
			LOGGER.info("Image path for thumbnail : " + imageFile);
			return imageFile;

		} catch (Exception e) {
			throw new Exception();
		}
	}

	private String uuidString() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public String convertImageToPDF(File file, String contentType) {
		MultipartFile multipartPDF = null;
		String filepath = null;
		try {

			PDDocument document = new PDDocument();

			// InputStream in = new FileInputStream(file);
			// BufferedImage bimg = ImageIO.read(in);
			float width, height;
			if (contentType.equalsIgnoreCase("image/tiff")) {
				FileSeekableStream fss = new FileSeekableStream(file);
				ImageDecoder decoder = ImageCodec.createImageDecoder("tiff",
				        fss, null);
				RenderedImage image = decoder.decodeAsRenderedImage();
				System.out.println(image);
				BufferedImage bimg = convertRenderedImage(image);
				width = bimg.getWidth();
				height = bimg.getHeight();
				System.out.println("width : " + width);
				System.out.println("height : " + height);
			} else {
				InputStream in = new FileInputStream(file);
				BufferedImage bimg = ImageIO.read(in);
				width = bimg.getWidth();
				height = bimg.getHeight();

			}

			PDPage page = new PDPage(new PDRectangle(width, height));
			document.addPage(page);

			PDXObjectImage img = null;

			if (contentType.equalsIgnoreCase("image/jpeg")) {
				img = new PDJpeg(document, new FileInputStream(file));
			} else if (contentType.equalsIgnoreCase("image/png")) {
				img = new PDPixelMap(document, ImageIO.read(file));
			} else if (contentType.equalsIgnoreCase("image/tiff")) {
				img = new PDCcitt(document, new RandomAccessFile(file, "r"));
			}

			PDPageContentStream contentStream = new PDPageContentStream(
			        document, page);
			contentStream.drawImage(img, 0, 0);

			contentStream.close();
			// in.close();

			filepath = tomcatDirectoryPath()
			        + File.separator
			        + file.getName().replace(
			                FilenameUtils.getExtension(file.getName()), "")
			        + "pdf";

			LOGGER.info("filepath after convertin to PDF : " + filepath);

			File newFile = new File(filepath);
			newFile.createNewFile();

			document.save(filepath);
			document.close();

		} catch (Exception e) {
			LOGGER.error("Exception in convertImageToPDF : " + e.getMessage());
			e.printStackTrace();
		}
		return filepath;

	}

	public File multipartToFile(MultipartFile multipart)
	        throws IllegalStateException, IOException {
		File convFile = new File(multipart.getOriginalFilename());
		multipart.transferTo(convFile);
		return convFile;
	}

	public MultipartFile filePathToMultipart(File file) throws IOException {

		DiskFileItem fileItem = new DiskFileItem("file", "application/pdf",
		        false, file.getName(), (int) file.length(),
		        file.getParentFile());
		fileItem.getOutputStream();
		MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
		return multipartFile;
	}

	public String convertImageToPDFDocument(MultipartFile multipartFile) {

		File file = null;
		String filepath = null;
		PDDocument document = null;
		try {
			file = multipartToFile(multipartFile);
			document = PDDocument.loadNonSeq(file, null);

			// we will add the image to the first page.
			PDPage page = (PDPage) document.getDocumentCatalog().getAllPages()
			        .get(0);

			PDXObjectImage ximage = null;
			if (FilenameUtils.getExtension(file.getName()).toLowerCase()
			        .endsWith(".jpg")) {
				ximage = new PDJpeg(document, new FileInputStream(file));
			} else if (FilenameUtils.getExtension(file.getName()).toLowerCase()
			        .endsWith(".tif")
			        || FilenameUtils.getExtension(file.getName()).toLowerCase()
			                .endsWith(".tiff")) {
				ximage = new PDCcitt(document, new RandomAccessFile(file, "r"));
			} else if (FilenameUtils.getExtension(file.getName()).toLowerCase()
			        .endsWith(".png")) {
				ximage = new PDPixelMap(document, ImageIO.read(file));
			} else {
				// BufferedImage awtImage = ImageIO.read( new File( image ) );
				// ximage = new PDPixelMap(doc, awtImage);
				throw new IOException("Image type not supported:"
				        + FilenameUtils.getExtension(file.getName()));
			}

			PDPageContentStream contentStream = new PDPageContentStream(
			        document, page);
			contentStream.drawImage(ximage, 0, 0);

			contentStream.close();

			filepath = tomcatDirectoryPath()
			        + File.separator
			        + file.getName().replace(
			                FilenameUtils.getExtension(file.getName()), "")
			        + "pdf";
			LOGGER.info("filepath after convertin to PDF : " + filepath);

			File newFile = new File(filepath);
			newFile.createNewFile();

			document.save(filepath);
			document.close();
		} catch (Exception e) {
			LOGGER.error("Exception in convertImageToPDF : " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (document != null) {
				try {
					document.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return filepath;

	}

	public BufferedImage convertRenderedImage(RenderedImage img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}
		ColorModel cm = img.getColorModel();
		int width = img.getWidth();
		int height = img.getHeight();
		WritableRaster raster = cm
		        .createCompatibleWritableRaster(width, height);
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		Hashtable properties = new Hashtable();
		String[] keys = img.getPropertyNames();
		if (keys != null) {
			for (int i = 0; i < keys.length; i++) {
				properties.put(keys[i], img.getProperty(keys[i]));
			}
		}
		BufferedImage result = new BufferedImage(cm, raster,
		        isAlphaPremultiplied, properties);
		img.copyData(raster);
		return result;
	}

	public String createPDFFromStream(InputStream inputStream)
	        throws IOException, COSVisitorException {
		PDDocument document = null;
		String filePath = null;
		try {
			// create the PDF document object
			document = new PDDocument();
			InputStream input = (InputStream) inputStream;
			BufferedReader bufferReader = new BufferedReader(
			        new InputStreamReader(input));
			String theString = "";
			String line = "";
			while ((line = bufferReader.readLine()) != null)
				theString += line + "\r\n";

			byte[] byteArray = theString.getBytes();
			theString = new String(byteArray, "UTF-8");
			PDPage page = new PDPage();
			document.addPage(page);
			PDPageContentStream stream = new PDPageContentStream(document, page);
			stream.beginText();
			PDFont font = PDType1Font.HELVETICA_BOLD;
			stream.setFont(font, 12);
			stream.moveTextPositionByAmount(100, 700);
			stream.setNonStrokingColor(Color.ORANGE);
			stream.drawString(theString);
			stream.endText();

			filePath = tomcatDirectoryPath() + File.separator
			        + randomStringOfLength() + ".pdf";

			// save the document to the file stream.
			document.save(filePath);
		} finally {
			if (document != null) {
				document.close();
			}

		}
		return filePath;
	}

	public String getContentFromFile(UploadedFilesList uploadedFilesList)
	        throws IOException, Exception {
		String s3pathOfFile =uploadedFilesList.getS3path();
		byte[] bytes = IOUtils.toByteArray(s3FileUploadServiceImpl
		        .getInputStreamFromFile(s3pathOfFile , String.valueOf(0)));
		String encodedText = new String(Base64.encodeBase64(bytes));
		return encodedText;
	}

	public File copyInputStreamToFile(InputStream in) throws IOException {
		File file = null;
		OutputStream out = null;
		try {
			file = new File(tomcatDirectoryPath() + File.separator
			        + randomStringOfLength() + ".pdf");
			out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
			in.close();
		}
		return file;
	}

	public MultipartFile getMultipartFileFromInputStream(InputStream instream)
	        throws IOException {
		File file = copyInputStreamToFile(instream);
		return filePathToMultipart(file);

	}

	public byte[] convertInputStreamToByteArray(InputStream inputStream) {
		byte[] buffer = new byte[8192];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		int bytesRead;
		try {
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				baos.write(buffer, 0, bytesRead);
			}
		} catch (IOException e) {
			// TODO call exception class
		}
		return baos.toByteArray();
	}

	public File convertInputStreamToFile(InputStream inputStream)
	        throws COSVisitorException, IOException {

		/*
		 * String filePath = createPDFFromStream(inputStream); return new
		 * File(filePath);
		 */

		OutputStream outputStream = null;
		File file = null;
		try {
			file = new File(tomcatDirectoryPath() + File.separator
			        + randomStringOfLength() + ".pdf");
			if (file.createNewFile()) {
				outputStream = new FileOutputStream(file);
				byte[] bytes = convertInputStreamToByteArray(inputStream);
				outputStream.write(bytes);
				outputStream.flush();

			}
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {

				}
			}
			if (outputStream != null) {
				try {
					// outputStream.flush();
					outputStream.close();
				} catch (IOException e) {

				}

			}

		}
		return file;

	}

	
	
	
}