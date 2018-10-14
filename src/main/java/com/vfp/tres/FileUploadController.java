package com.vfp.tres;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.UploadedFile;

import tres.common.DbConstant;

@ManagedBean
@RequestScoped

public class FileUploadController implements Serializable,DbConstant {

	private static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());

	private static final long serialVersionUID = 1L;
	private String name;
	private Part file;
	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// return the context path of the project like /VfpProject_Dev
	public String getContextPath() {

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();

		return request.getContextPath();
	}

	// processing uploaded file
	public String processFileUpload() throws IOException {

		Part uploadedFile = getFile();
		String url = getContextPath();
		final Path destination = Paths
				.get(Root_Path+"\\" + FilenameUtils.getName(getSubmittedFileName(uploadedFile)));
		LOGGER.info(
				"Uploaded File name::------------>>>>>>" + FilenameUtils.getName(getSubmittedFileName(uploadedFile)));
		InputStream bytes = null;

		if (null != uploadedFile) {

			bytes = uploadedFile.getInputStream(); //

			// Copies bytes to destination.
			Files.copy(bytes, destination);
		}

		return FilenameUtils.getName(getSubmittedFileName(uploadedFile));
	}

	// code to get the submitted file name from the file part header.

	public static String getSubmittedFileName(Part filePart) {
		String header = filePart.getHeader("content-disposition");
		if (header == null)
			return null;
		for (String headerPart : header.split(";")) {
			if (headerPart.trim().startsWith("filename")) {
				return headerPart.substring(headerPart.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}
}