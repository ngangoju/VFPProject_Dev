package com.vfp.tres;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.Part;
 
import org.apache.commons.io.FilenameUtils;
 
@ManagedBean
@RequestScoped
 
public class FileUploadController implements Serializable{
	 
	
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
	 
	//Processing of  file uploading.
	 
	    public String processFileUpload() throws IOException{
	 
	        Part uploadedFile=getFile();
	 
	         final Path destination = Paths.get("c:/temp/"+ FilenameUtils.getName(getSubmittedFileName(uploadedFile)));
	 
	         //When using servlet 3.1
	          //final Path destination = Paths.get("c:/temp/"+ FilenameUtils.getName(uploadedFile.getSubmittedFileName()));
	 
	         InputStream bytes=null;
	 
	            if (null!=uploadedFile) {
	 
	                bytes = uploadedFile.getInputStream();  //
	 
	        //Copies bytes to destination.
	                  Files.copy(bytes, destination);
	            }
	 
	        return "success";
	    }
	 
	    //code to get the submitted file name from the file part header. 
	 
	    public static String getSubmittedFileName(Part filePart)
	    {
	        String header = filePart.getHeader("content-disposition");
	        if(header == null)
	            return null;
	        for(String headerPart : header.split(";"))
	        {
	            if(headerPart.trim().startsWith("filename"))
	            {
	                return headerPart.substring(headerPart.indexOf('=') + 1).trim().replace("\"", "");
	            }
	        }
	        return null;
	    }
	}