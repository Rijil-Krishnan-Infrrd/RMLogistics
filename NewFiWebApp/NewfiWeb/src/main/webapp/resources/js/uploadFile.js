/*
 * Functions for upload items module
 */
var SPLIT_DOC = "Split Document";



function uploadNeededItemsPage() {
	var userId = newfiObject.user.id;
	var activeLoanId = newfiObject.user.defaultLoanId;
	currentUserAndLoanOnj.userId = userId;
	currentUserAndLoanOnj.activeLoanId = activeLoanId;
	getRequiredDocuments();
}

function getRequiredDocuments( ) {
	
	ajaxRequest("rest/fileupload/needlist/get/" + currentUserAndLoanOnj.userId  + "/" + currentUserAndLoanOnj.activeLoanId,
			"GET", "json", "", getRequiredDocumentData);
}

function getRequiredDocumentData(neededItemList) {
	neededItemListObject = neededItemList;
	$('#uploadedNeedContainer').remove();
	paintUploadNeededItemsPage(neededItemListObject);
}


function saveAssignmentonFile() {
	getRequiredDocuments();
}

function checkForSplitOption(select){
	
		if($(select).val()== SPLIT_DOC){
			var fileName = $(select).attr('fileName');
			var fileId = $(select).attr('fileId');
			var object = new Object();
			object.fileId = fileId;
			object.fileName = fileName;
			
			showDialogPopup("Confirm File Split" , 
					"Are you sure want to split the current PDF document." , function(){splitPDFDocument(object)} );
		}
		
}


function splitPDFDocument(dataObject){
	console.info(dataObject.fileName +" and "+dataObject.fileId);
	ajaxRequest("rest/fileupload/split/"+dataObject.fileId+"/"+  currentUserAndLoanOnj.activeLoanId+"/"+currentUserAndLoanOnj.userId,
			"GET", "json", "", afterPDFSplit);
}

function afterPDFSplit(response){
	console.info("pdf splitting done : "+response);
	getRequiredDocuments();
}


function getDocumentUploadColumn(listUploadedFiles) {
	var column = $('<div>').attr({
		"class" : "document-cont-col float-left"
	});
	var docImg = $('<div>').attr({
		"class" : "doc-img showAnchor"
	});
	
	var img = $("<img>").attr({
		 		"src" : listUploadedFiles.s3ThumbPath
	}).load(function(){
		docImg.css({
			"background" : "url('"+listUploadedFiles.s3ThumbPath+"') no-repeat center",
			"border": "2px solid #dddddd",
			"border-radius": "25px",
			"background-size" : "cover"
			
		});
	});
	
	
	var docDesc = $('<div>').attr({
		"class" : "doc-desc showAnchor"
	}).html(listUploadedFiles.fileName);
	docImg.click(function(){
		window.open(listUploadedFiles.s3path, '_blank');
	});
	var docAssign = $("<select>").attr({
		"class" : "assign",
		"fileId" : listUploadedFiles.id,
		"fileName" : listUploadedFiles.fileName,
		"onchange" : "checkForSplitOption(this)"
	});

	var assignOption = $("<option>").attr({
		"value" : "Assign"
	}).html("Assign");
	var splitOption = $("<option>").attr({
		"value" : SPLIT_DOC
		}).html(SPLIT_DOC);
	docAssign.append(assignOption).append(splitOption);

	var neededItemListObj = neededItemListObject.resultObject.listLoanNeedsListVO;

	for (i in neededItemListObj) {
		var needsListMasterobj = neededItemListObj[i];
		var option = $("<option>").attr({
			"value" : needsListMasterobj.id
		}).html(needsListMasterobj.needsListMaster.label);

		if (needsListMasterobj.id == listUploadedFiles.needType) {
			option.attr('selected', 'selected');
		}
		docAssign.append(option);
	}

	return column.append(docImg).append(docDesc).append(docAssign);
}


function showFileLink(uploadedItems) {

	$.each(uploadedItems, function(index, value) {
		var needId = value.needType;
		$('#needDoc' + needId).removeClass('hide');
		$('#needDoc' + needId).addClass('doc-link-icn');
		$('#needDoc' + needId).click(function() {
			window.open(value.s3path, '_blank');
		});
	});
}




function saveUserDocumentAssignments() {
	console.info("user assignemnt");
	var fileAssignMentVO = new Array();

	$(".assign").each(function(index) {
		console.info($(this).val())
		if($(this).val()!="Assign"){
			var fileAssignMent = new Object();
			fileAssignMent.fileId = $(this).attr('fileid');
			fileAssignMent.needListId = $(this).val();
			fileAssignMentVO.push(fileAssignMent);
		}
		
		
		
	});
	console.info(fileAssignMentVO);

	$.ajax({
		url : "rest/fileupload/assignment/"+currentUserAndLoanOnj.activeLoanId+"/"+currentUserAndLoanOnj.userId,
		type : "POST",
		data : JSON.stringify(fileAssignMentVO),
		dataType : "json",
		contentType : "application/json; charset=utf-8",
		success : function(data) {
			saveAssignmentonFile(data);
		},
		error : function(error) {
		}
	});
	// ajaxRequest("" , "POST" , "application/json" ,
	// JSON.stringify(fileAssignMentVO) , saveAssignmentonFile);
}





function paintUploadNeededItemsPage(neededItemListObject) {
	var uploadedNeedContainer = $("<div>").attr({
				"id" :"uploadedNeedContainer"
	});
	var header = $('<div>').attr({
		"class" : "upload-item-header uppercase"
	}).html("Upload needed items");
	var container = $('<div>').attr({
		"class" : "upload-item-container"
	});
	var fileDragDropCon = getFileDragAndDropContainer();
	var showSave = false;
	var documentContainer = getDocumentContainer();
	var submitBtn = $("<div>").attr({
		"class" : "submit-btn"
	}).click(saveUserDocumentAssignments).html("Save");

	var neededItemsWrapper = getNeedItemsWrapper(neededItemListObject);
	// var uploadedItemsWrapper = getUploadedItemsWrapper();

	container.append(fileDragDropCon).append(documentContainer).append(
			submitBtn).append(neededItemsWrapper);

	uploadedNeedContainer.append(header).append(container);
	$('#center-panel-cont').append(uploadedNeedContainer);
	// using dropzone js for file upload
	var myDropZone = new Dropzone("#drop-zone", {
		url : "documentUpload.do",
		clickable : "#file-upload-icn",
		params : {
			userID : currentUserAndLoanOnj.userId,
			loanId : currentUserAndLoanOnj.activeLoanId 
		},
		drop : function() {

		},
		complete : function(response) {
			hideOverlay();
			$('#file-upload-icn').removeClass('file-upload-hover-icn');
		},
		dragenter : function() {
			$('#file-upload-icn').addClass('file-upload-hover-icn');
		},
		dragleave : function() {
			$('#file-upload-icn').removeClass('file-upload-hover-icn');
		},
		dragover : function() {
			$('#file-upload-icn').addClass('file-upload-hover-icn');
		},
		queuecomplete : function() {
			
			getRequiredDocuments();
		},
		addedfile : function(){
			showOverlay();
			$('#file-upload-icn').addClass('file-upload-loading');
		}
	});
	var uploadedItems = neededItemListObject.resultObject.listUploadedFilesListVO;
	showFileLink(uploadedItems);

	if ($('.document-cont-col').length == undefined
			|| $('.document-cont-col').length == 0) {
		$('.submit-btn').addClass('hide');
	}

}
