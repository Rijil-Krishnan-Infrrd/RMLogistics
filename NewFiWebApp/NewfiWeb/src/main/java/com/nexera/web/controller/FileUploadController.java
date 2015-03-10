package com.nexera.web.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.nexera.common.entity.User;
import com.nexera.core.service.UploadedFilesListService;
import com.nexera.core.service.impl.S3FileUploadServiceImpl;
import com.nexera.core.utility.NexeraUtility;

@Controller
public class FileUploadController {

	@Autowired
	private S3FileUploadServiceImpl s3FileUploadServiceImpl;
	
	@Autowired
	private UploadedFilesListService uploadedFilesListService;
	
	
	private static final Logger LOG = LoggerFactory.getLogger(FileUploadController.class);
	
	@RequestMapping(value = "documentUpload.do" , method = RequestMethod.POST  )
	public @ResponseBody String  filesUploadToS3System( @RequestParam("file") MultipartFile[] file , @RequestParam("userID") Integer userID ,  @RequestParam("loanId") Integer loanId ){
		LOG.info("in document upload  wuth user id "+userID + " and loanId :"+loanId);
		List<String> s3paths = new ArrayList<String>();
		for (MultipartFile multipartFile : file) {
			s3paths.add(uploadFile(multipartFile, userID , loanId) );
		}
		return new Gson().toJson(s3paths);
	} 
	
	public String uploadFile(MultipartFile file , Integer userId , Integer loanId){
		String s3Path = null;
		 try{
			String localFilePath =  NexeraUtility.uploadFileToLocal(file);
			File serverFile = new File(localFilePath );
			Integer savedRowId = uploadedFilesListService.addUploadedFilelistObejct(serverFile , loanId , userId);
			LOG.info("Added File document row : "+savedRowId);
		 }catch(Exception e){
			 LOG.info(" Exception uploading s3 :  "+e.getMessage());
		 }
		 LOG.info("file.getOriginalFilename() : "+file.getOriginalFilename());
		 
		 LOG.info("The s3 path is : "+s3Path);
		 return s3Path;
	}
	
	private User getUserObject() {
		final Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if (principal instanceof User) {
			return (User) principal;
		} else {
			return null;
		}

	}
}
