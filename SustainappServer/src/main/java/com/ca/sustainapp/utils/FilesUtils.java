package com.ca.sustainapp.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;

import static javax.imageio.ImageIO.read;
import static javax.imageio.ImageIO.write;

/**
 * Classe d'outils pour la gestion des fichiers uploadés
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @version 1.0
 * @since 15/10/2016
 */
public class FilesUtils {
	public static final String FORMAT_PNG = "png";
	public static final String FORMAT_JPG = "jpg";


	/**
	 * Constructeur priv�, classe statique
	 */
	private FilesUtils() {
	}
	
	/**
	 * Methode de reception compress�e d'une image
	 * @param filePart
	 * @return
	 */
	public static byte[] fileTobyteArray(Part filePart){
		if(null == filePart || 0 >= filePart.getSize()){
			return null;
		}
		try {
			return StreamTobyteArray(filePart.getInputStream());
		} catch (IOException e) {
			return null;
		}
	}
	
	/**
	 * Methode de reception compr�ss�e d'une image 
	 * @param input
	 * @return
	 */
	public static byte[] StreamTobyteArray(InputStream input){
		byte[] result = null;
		try {
			if(null == input || 0 >= input.available()){
				return result;
			}
			result = compressImage(IOUtils.toByteArray(input), FORMAT_PNG);
		} catch (IOException e1) {
			return result;
		}	
		return result;
	}
	
	/**
	 * Methode de compression des images.
	 * 
	 * @param input
	 * @param format
	 * @return byte[] compressedImage
	 */
	public static byte[] compressImage(byte[] input, String format) {
		InputStream inputStream = new ByteArrayInputStream(input);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		BufferedImage initialImage = null;

		try {
			initialImage = read(inputStream);
			int height = initialImage.getHeight();
			int width = initialImage.getWidth();
			BufferedImage resizedImage = initialImage;

			if ((width / 2) > 2048 || (height / 2) > 2048) {
				while ((width / 2) > 2048 || (height / 2) > 2048) {
					width = width / 2;
					height = height / 2;
				}

				resizedImage = resizeImage(initialImage, width, height);
			}
			BufferedImage finalImage = resizedImage;
			if (FORMAT_PNG.equals(format)) {
				finalImage = png2jpg(resizedImage);
			}
			write(finalImage, FORMAT_JPG, outputStream);
		} catch (IOException e) {
			return null;
		}

		return outputStream.toByteArray();
	}

	/**
	 * Methode de conversion d'un fichier PNG vers JPG.
	 * 
	 * @param png
	 * @return jpg
	 */
	public static BufferedImage png2jpg(BufferedImage png) {
		BufferedImage jpgFormatVersion = new BufferedImage(png.getWidth(), png.getHeight(), BufferedImage.TYPE_INT_RGB);
		jpgFormatVersion.createGraphics().drawImage(png, 0, 0, Color.WHITE, null);
		return jpgFormatVersion;
	}

	/**
	 * Methode de reduction de la hauteur et largeur d'une image.
	 * 
	 * @param img
	 * @param newW
	 * @param newH
	 * @return BufferedImage
	 */
	public static BufferedImage resizeImage(BufferedImage img, int newW, int newH) {
		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage dimg = new BufferedImage(newW, newH, img.getType());
		Graphics2D g = dimg.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
		g.dispose();
		return dimg;
	}

	/**
	 * Tester rapidement si un fichier existe
	 * 
	 * @param path
	 * @return boolean
	 */
	public static boolean existFile(String path) {
		File f = new File(path);
		return f.exists();
	}

	/**
	 * Supprimer rapidement un fichier
	 * 
	 * @param path
	 * @return boolean
	 */
	public static boolean deleteFileQuietly(String path) {
		if (existFile(path)) {
			File f = new File(path);
			boolean rtn = f.delete();

			// Seconde tentative (fmk de tests)
			if (existFile(path) || !rtn) {
				try {
					Path p = FileSystems.getDefault().getPath(path, new String[0]);
					Files.delete(p);
					return true;
				} catch (IOException e) {
					return false;
				}
			}
			return rtn;
		}
		return false;
	}

	/**
	 * Obtenir un tableau de byte � partir d'un fichier
	 * 
	 * @param path
	 * @return byte[]
	 */
	public static byte[] fileToByteQuietly(String path) {
		Path p = Paths.get(path);
		try {
			return Files.readAllBytes(p);
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * R�cup�rer le chemin absolu
	 * 
	 * @param relatifPath
	 * @return String
	 */
	public static String getAbsolutePath(String relatifPath) {
		File file = new File(relatifPath);
		return file.getAbsolutePath();
	}

	/**
	 * Retourne vrai si un fichier est de type image.
	 * 
	 * @param mimeType
	 * @return boolean
	 */
	public static boolean isAllowedTypeOfImage(String mimeType) {
		if (null == mimeType) {
			return false;
		}
		List<String> lstMimesTypes = new ArrayList<String>();
		lstMimesTypes.add("image/gif");
		lstMimesTypes.add("image/png");
		lstMimesTypes.add("image/jpeg");
		lstMimesTypes.add("image/jpg");
		return lstMimesTypes.contains(mimeType.toLowerCase());
	}

	/**
	 * Retourne vrai si un fichier est de type document
	 * 
	 * @param mimeType
	 * @return
	 */
	public static boolean isAllowedTypeOfDocument(String mimeType) {
		List<String> lstMimesTypes = new ArrayList<String>();
		lstMimesTypes.add("application/vnd.ms-powerpoint");
		lstMimesTypes.add("application/vnd.openxmlformats-officedocument.presentationml.presentation");
		lstMimesTypes.add("application/msword");
		lstMimesTypes.add("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		lstMimesTypes.add("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		lstMimesTypes.add("application/vnd.ms-excel");
		lstMimesTypes.add("application/vnd.oasis.opendocument.graphic");
		lstMimesTypes.add("application/vnd.oasis.opendocument.presentation");
		lstMimesTypes.add("application/vnd.oasis.opendocument.spreadsheet");
		lstMimesTypes.add("application/vnd.oasis.opendocument.text");
		lstMimesTypes.add("text");
		lstMimesTypes.add("application/pdf");
		lstMimesTypes.add("application/x-pdf");
		lstMimesTypes.add("application/x-tika-ooxml");
		return lstMimesTypes.contains(mimeType.toLowerCase());
	}

	/**
	 * Classification du type de document pour l'icone a afficher pour le
	 * t�l�chargement
	 * 
	 * @param mimeType
	 * @return int result
	 */
	public static int typeOfDocument(String mimeType) {
		int result = 4; // TEXT OR WORD FILE
		if (mimeType.equals("application/pdf") || mimeType.equals("application/x-pdf")) {
			result = 5; // PDF FILE
		} else if (mimeType.equals("application/vnd.ms-powerpoint")
				|| mimeType.equals("application/vnd.openxmlformats-officedocument.presentationml.presentation")
				|| mimeType.equals("application/vnd.oasis.opendocument.graphic")
				|| mimeType.equals("application/vnd.oasis.opendocument.presentation")) {
			result = 6; // GRAPHIC OR PRESENTATION FILE
		}
		return result;
	}
}