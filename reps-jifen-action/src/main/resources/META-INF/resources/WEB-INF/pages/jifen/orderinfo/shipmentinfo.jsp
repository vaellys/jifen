<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
     <title>运单信息</title>
	 <reps:theme/>
  </head>
  <body>
    <reps:container >
  	<reps:panel id="myform" border="true" action="edit.mvc" formId="xform" validForm="true" method="post" style="width:350px;">
		<input type="hidden" name="id" value="${data.id}">
		<reps:formcontent style="margin-top:30px" >
			<reps:formfield label="运单号"  fullRow="true">
				<reps:input name="shipmentNo"  maxLength="30" required="true">${data.shipmentNo}</reps:input>
           </reps:formfield>
           <reps:formfield label="快递公司"  fullRow="true">
				<reps:input name="expressCompany"  maxLength="30" required="true">${data.expressCompany}</reps:input>
           </reps:formfield>
	   </reps:formcontent>
	    <reps:formbar style="margin-top:20px">
  		   <reps:ajax confirm="你确定要提交吗？" formId="xform"  type="link" cssClass="btn_save_a" callBack="my"  messageCode="edit.button.save"/>
      	 </reps:formbar>
  	</reps:panel>
  </reps:container>
  <script type="text/javascript">
	var back = function() {
		window.parent.location.href = "list.mvc";
	}
	
	var my = function(data){
		messager.message(data, function() {
			window.parent.location.href = "list.mvc";
		});
	}
  </script>
  </body>
</html>