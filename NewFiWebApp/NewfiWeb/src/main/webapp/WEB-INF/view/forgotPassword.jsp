<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Forget Password</title>
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/jquery-ui.css" rel="stylesheet">
<link href="resources/css/styles.css" rel="stylesheet">
<link href="resources/css/styles-common.css" rel="stylesheet">
<link href="resources/css/customer-engagement.css" rel="stylesheet">
<link href="resources/css/customer-application.css" rel="stylesheet">
<link href="resources/css/style-resp.css" rel="stylesheet">

</head>
<body>
	<jsp:include page="loginHeader.jsp"></jsp:include>
	<div class="home-container container">
		<div class="login-container container">
				<div class="container-row row clearfix">
					<div class="reg-display-title">A New way to Finance your home</div>
					<div class="reg-display-title-subtxt">Lorem Ipsum is also known
						as: Greeked Text, blind text, placeholder text, dummy content,
						filter text, lipsum, and mock-content.</div>
					<div class="login-form-wrapper">
						<form id="loginForm" name="loginForm" action="#" method="POST">
							<div class="reg-input-cont reg-email" id="email-container">
					        <input class="reg-input" placeholder="Email" id="emailID" >	
							<div class="err-msg hide" style="display: block;"></div>
				</div>
							<div class="reg-btn-wrapper clearfix">
					<div class="reg-btn" onclick="$('#loginForm').submit();">Reset</div>

				</div>													
						</form>
					</div>
				</div>
			</div>
		</div>
	<script src="resources/js/jquery-2.1.3.min.js"></script>
	<script src="resources/js/jquery-ui.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/common.js"></script>
	<script src="resources/js/script.js"></script>
	<script src="resources/js/profile.js"></script>
	<script src="resources/js/include/jquery-maskMoney.js"></script>
	
</body>
<script>
var emailRegex = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]\.[0-9]\.[0-9]\.[0-9]\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]+))$/;

$('#loginForm').submit(function(event){
		  event.preventDefault();
		var user = new Object();
		user.emailId = $('#emailID').val();
		console.log("Create user button clicked. User : "
						+ JSON.stringify(user));
	if($('#emailID').val()==""||$('#emailID').val()==null){
		showErrorToastMessage("EmailID cannot be empty");
			return;
		
	}
	if (!emailRegex.test(user.emailId)) {
        showErrorToastMessage("Invalid EmailId");
		$('#emailID').val('');
		return;
	}else {	
		ajaxRequest("rest/userprofile/forgetPassword", "POST", "json", JSON.stringify(user),
				  paintForgetPasswordResponse);
	}
	});

function paintForgetPasswordResponse(data){
	if(data.resultObject!=null){
		showToastMessage(data.resultObject);
		$('#emailID').val('');
		
	}else{
		showErrorToastMessage(data.error.message);
		$('#emailID').val('');
	}
}


</script>
</html>